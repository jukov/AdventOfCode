package info.jukov.adventofcode.y2022

import info.jukov.adventofcode.Day
import java.io.BufferedReader
import java.util.LinkedList
import java.util.SortedSet
import java.util.TreeSet

object Day7 : Day() {

    override val year: Int = 2022

    override val day: Int = 7

    private const val NEED_SPACE = 30_000_000
    private const val TOTAL_SPACE = 70_000_000
    override fun part1(reader: BufferedReader): String {
        val tree = makeFileTree(reader)

        return count(tree).toString()
    }

    override fun part2(reader: BufferedReader): String {
        val tree = makeFileTree(reader)

        val size = tree.size
        val needToFree = NEED_SPACE - (TOTAL_SPACE - size)
        val dirs = dirList(tree)

        return dirs.first { it > needToFree }.toString()
    }

    private fun makeFileTree(reader: BufferedReader): Dir {
        val tree = Dir("/", null)

        var workingDir: Dir? = null
        reader.forEachLine { line ->
            when {
                line.startsWith("$ cd") -> {
                    val where = line.substring(5)

                    workingDir = when (where) {
                        "/" -> tree
                        ".." -> workingDir?.parent
                        else -> find(where, workingDir!!) as Dir
                    }
                }

                line.startsWith("$ ls") -> {

                }

                line.startsWith("dir") -> {
                    val name = line.substring(4)
                    workingDir?.files?.add(Dir(name, workingDir))
                }

                else -> {
                    val (size, name) = line.split(' ')
                    workingDir?.files?.add(File(name, size.toLong(), workingDir!!))
                }
            }
        }
        return tree
    }

    private fun dirList(tree: Dir): SortedSet<Long> {
        val set = TreeSet<Long>()
        set += tree.size

        val workingDirs = LinkedList<Dir>()
        workingDirs += tree
        while (workingDirs.isNotEmpty()) {
            val next = workingDirs[0]
            next.files.forEach { entry ->
                if (entry is Dir) {
                    set += entry.size
                    workingDirs += entry
                }
            }
            workingDirs.removeAt(0)
        }

        return set
    }

    private fun find(name: String, from: Dir): Entry? {
        from.files.forEach { entry ->
            if (entry.name == name) {
                return entry
            }
            if (entry is Dir) {
                find(name, entry)
            }
        }

        return null
    }

    private fun count(from: Dir): Long =
        from.files.sumOf { entry ->
            if (entry is Dir) {
                if (entry.size < 100000) {
                    entry.size + count(entry)
                } else {
                    count(entry)
                }
            } else {
                0L
            }
        }

    sealed class Entry {
        abstract val name: String
        abstract val size: Long
        abstract val parent: Dir?
    }

    class File(
        override val name: String,
        override val size: Long,
        override val parent: Dir
    ) : Entry() {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as File

            if (name != other.name) return false
            if (size != other.size) return false

            return true
        }

        override fun hashCode(): Int {
            var result = name.hashCode()
            result = 31 * result + size.hashCode()
            return result
        }
    }

    class Dir(
        override val name: String,
        override val parent: Dir?,
    ) : Entry() {

        val files = ArrayList<Entry>()

        override val size: Long
            get() = files.sumOf { it.size }

        override fun toString(): String {
            return "Dir(name='$name', parent=$parent)"
        }
    }
}

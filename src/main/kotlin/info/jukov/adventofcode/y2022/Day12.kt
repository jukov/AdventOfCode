package info.jukov.adventofcode.y2022

import info.jukov.adventofcode.Day
import java.io.BufferedReader

object Day12 : Day() {

    override val year: Int = 2022

    override val day: Int = 12
    override fun part1(reader: BufferedReader): String {
        val maze = ArrayList<List<Int>>()

        reader.forEachLine { line ->
            maze += line.map { char ->
                when (char) {
                    'S' -> 0
                    'E' -> 27
                    in 'a'..'z' -> char.code - 96
                    else -> error("Unexpected char $char")
                }
            }
        }

        var start: Node? = null
        var end: Node? = null

        outer@ for (i in maze.indices) {
            for (j in maze[i].indices) {
                if (maze[i][j] == 0) {
                    start = Node(i, j, 0, 0, 0)
                }
                if (maze[i][j] == 27) {
                    end = Node(i, j, 27, 27, 0)
                }
                if (start != null && end != null) {
                    break@outer
                }
            }
        }

        requireNotNull(start)
        requireNotNull(end)

        val result = findPathLength(start, maze, end)

        return requireNotNull(result).g.toString()
    }

    override fun part2(reader: BufferedReader): String {
        val maze = ArrayList<List<Int>>()

        reader.forEachLine { line ->
            maze += line.map { char ->
                when (char) {
                    'S' -> 0
                    'E' -> 26
                    in 'a'..'z' -> char.code - 97
                    else -> error("Unexpected char $char")
                }
            }
        }

        val starts = ArrayList<Node>()
        var end: Node? = null

        outer@ for (i in maze.indices) {
            for (j in maze[i].indices) {
                if (maze[i][j] == 0) {
                    starts += Node(i, j, 0, 0, 0)
                }
                if (maze[i][j] == 26) {
                    end = Node(i, j, 26, 26, 0)
                }
            }
        }

        require(starts.isNotEmpty())
        requireNotNull(end)

        val min = starts
            .mapNotNull { start -> findPathLength(start, maze, end) }
            .minBy { it.g }

        return min.g.toString()
    }

    private fun findPathLength(
        start: Node,
        maze: List<List<Int>>,
        end: Node
    ): Node? {
        val openList = ArrayList<Node>()
        val closedList = ArrayList<Node>()

        openList += start

        var finish: Node? = null

        main@ while (openList.isNotEmpty()) {
            val q = openList.minBy { it.f }
            openList.remove(q)

            val scs = getSuccessors(maze, q)

            for (sc in scs) {
                if (sc.i == end.i && sc.j == end.j) {
                    finish = sc
                    break@main
                }

                val inOpen = openList.find { open -> sc.i == open.i && sc.j == open.j }

                if (inOpen != null && sc.f >= inOpen.f) {
                    continue
                }

                val inClosed =
                    closedList.find { closed -> sc.i == closed.i && sc.j == closed.j }

                if (inClosed == null || sc.f < inClosed.f) {
                    openList += sc
                }
            }

            closedList += q
        }

        return finish
    }

    private fun getSuccessors(
        maze: List<List<Int>>,
        q: Node
    ): List<Node> {
        val list = ArrayList<Node>()
        maze.getSuccessor(q.i - 1, q.j, q.alt, q.g)?.let(list::add)
        maze.getSuccessor(q.i + 1, q.j, q.alt, q.g)?.let(list::add)
        maze.getSuccessor(q.i, q.j - 1, q.alt, q.g)?.let(list::add)
        maze.getSuccessor(q.i, q.j + 1, q.alt, q.g)?.let(list::add)
        return list
    }

    private fun List<List<Int>>.getSuccessor(i: Int, j: Int, alt: Int, g: Int): Node? {
        if (i < 0 || j < 0 || i >= this.size || j >= this[i].size) {
            return null
        }

        val scAlt = this[i][j]

        val diff = scAlt - alt
        if (diff > 1) {
            return null
        }

        val scG = g + 1
        val scH = 27 - scAlt

        return Node(i, j, scAlt, scG, scH)
    }

    @Suppress("unused")
    private fun debug(
        maze: ArrayList<List<Int>>,
        openList: ArrayList<Node>,
        closedList: ArrayList<Node>
    ) {
        val arr = Array(maze.size) { CharArray(maze[0].size) }
        for (i in maze.indices) {
            for (j in maze[i].indices) {
                arr[i][j] = when (maze[i][j]) {
                    0 -> 'S'
                    27 -> 'E'
                    else -> (maze[i][j] + 96).toChar()
                }
            }
        }
        openList.forEach { node ->
            arr[node.i][node.j] = 'O'
        }
        closedList.forEach { node ->
            arr[node.i][node.j] = 'C'
        }
        println(arr.joinToString(separator = "") { it.joinToString(separator = "", postfix = "\n") })
    }

    data class Node(
        val i: Int,
        val j: Int,
        val alt: Int,
        val g: Int,
        val h: Int
    ) {
        val f = g + h

        override fun toString(): String {
            return "Node(i=$i, j=$j, alt=${(alt + 97).toChar()}, g=$g, h=$h, f=$f)"
        }
    }
}

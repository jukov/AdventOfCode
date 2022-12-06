package info.jukov.adventofcode.y2022

import info.jukov.adventofcode.Day
import java.io.BufferedReader

object Day5 : Day() {

    override val year: Int = 2022

    override val day: Int = 5
    override fun part1(reader: BufferedReader): String {
        val stacks = readStacks(reader)

        reader.forEachLine { line ->
            var (move, from, to) = line
                .split(' ')
                .filter { it.contains("[0-9]".toRegex()) }
                .map { it.toInt() }

            from--
            to--

            for (i in 1..move) {
                stacks[to].add(stacks[from].removeAt(stacks[from].lastIndex))
            }
        }

        return String(stacks.map { it.last() }.toCharArray())
    }

    override fun part2(reader: BufferedReader): String {
        val stacks = readStacks(reader)

        reader.forEachLine { line ->
            var (move, from, to) = line
                .split(' ')
                .filter { it.contains("[0-9]".toRegex()) }
                .map { it.toInt() }

            from--
            to--

            for (i in move downTo 1) {
                val fromSize = stacks[from].size
                stacks[to].add(stacks[from][fromSize - i])
            }

            for (i in 1..move) {
                stacks[from].removeLast()
            }
        }

        return String(stacks.map { it.last() }.toCharArray())
    }

    private fun readStacks(reader: BufferedReader): ArrayList<ArrayList<Char>> {
        var line = reader.readLine()

        val stacks = ArrayList<ArrayList<Char>>()

        while (!line.startsWith(" 1")) {
            var currentStack = 0
            for (i in 1..line.length step 4) {
                if (stacks.size <= currentStack) {
                    stacks.add(ArrayList())
                }

                if (line[i] in 'A'..'Z') {
                    stacks[currentStack].add(0, line[i])
                }

                currentStack++
            }
            line = reader.readLine()
        }

        reader.readLine()
        return stacks
    }
}

package info.jukov.adventofcode.y2022

import info.jukov.adventofcode.Day
import java.io.BufferedReader

object Day6 : Day() {

    override val year: Int = 2022

    override val day: Int = 6
    override fun part1(reader: BufferedReader): String {
        val line = reader.readLine()

        for (i in line.indices) {
            if (line.substring(i, i + 4).toSet().size == 4) {
                return (i + 4).toString()
            }
        }

        error("No solution")
    }

    override fun part2(reader: BufferedReader): String {
        val line = reader.readLine()

        for (i in line.indices) {
            if (line.substring(i, i + 14).toSet().size == 14) {
                return (i + 14).toString()
            }
        }

        error("No solution")
    }
}

package info.jukov.adventofcode.y2021

import info.jukov.adventofcode.Day
import java.io.BufferedReader

object Day6: Day() {

    override val year: Int = 2021

    override val day: Int = 6

    private val cache = HashMap<Int, Long>()

    override fun part1(reader: BufferedReader): String {
        val input = reader.readLine()

        return input
            .split(',')
            .map { it.toInt() }
            .sumOf {
                solve(it, 80)
            }
            .toString()
    }

    override fun part2(reader: BufferedReader): String {
        val input = reader.readLine()

        return input
            .split(',')
            .map { it.toInt() }
            .sumOf {
                solve(it, 256)
            }
            .toString()
    }

    private fun solve(it: Int, days: Int): Long {
        if (days <= 0) {
            return 1L
        }
        return if (it == 0) {
            cache.getOrPut(days) { solve(0, days - 7) + solve(2, days - 7) }
        } else {
            solve(it - 1, days - 1)
        }
    }
}
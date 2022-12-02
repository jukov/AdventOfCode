package info.jukov.adventofcode.y2022

import info.jukov.adventofcode.Day
import java.io.BufferedReader

object Day2: Day() {

    override val year: Int = 2022

    override val day: Int = 2

    override fun part1(reader: BufferedReader): String {
        val roundResults = mapOf(
            "A Y" to 8,
            "B X" to 1,
            "C Z" to 6,
            "A X" to 4,
            "A Z" to 3,
            "B Y" to 5,
            "B Z" to 9,
            "C Y" to 2,
            "C X" to 7
        )
        var sum = 0
        reader.forEachLine {
            sum += roundResults[it] ?: error("Unexpected key $it")
        }

        return sum.toString()
    }

    override fun part2(reader: BufferedReader): String {
        val roundResults = mapOf(
            "A Y" to 4,
            "B X" to 1,
            "C Z" to 7,
            "A X" to 3,
            "A Z" to 8,
            "B Y" to 5,
            "B Z" to 9,
            "C Y" to 6,
            "C X" to 2
        )
        var sum = 0
        reader.forEachLine {
            sum += roundResults[it] ?: error("Unexpected key $it")
        }

        return sum.toString()
    }
}
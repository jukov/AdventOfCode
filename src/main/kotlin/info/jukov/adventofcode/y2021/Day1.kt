package info.jukov.adventofcode.y2021

import info.jukov.adventofcode.Day
import java.io.BufferedReader

object Day1: Day() {

    override val year: Int = 2021

    override val day: Int = 1

    override fun part1(reader: BufferedReader): String {
        var previous = Int.MAX_VALUE
        var increases = 0

        reader.useLines { depths ->
            for (depth in depths.map { it.toInt() }) {
                if (depth > previous) {
                    increases++
                }
                previous = depth
            }
        }

        return increases.toString()
    }

    override fun part2(reader: BufferedReader): String {
        val previous = IntArray(3) { -1 }
        var previousSum = Int.MAX_VALUE

        var increases = 0
        var globalI = 0
        var previousI: Int

        reader.useLines { depths ->
            for (depth in depths.map { it.toInt() }) {
                previousI = globalI.rem(3)

                previous[previousI] = depth

                if (previous.all { it != -1 }) {
                    if (previous.sum() > previousSum) {
                        increases++
                    }
                    previousSum = previous.sum()
                }

                globalI++
            }
        }

        return increases.toString()
    }
}

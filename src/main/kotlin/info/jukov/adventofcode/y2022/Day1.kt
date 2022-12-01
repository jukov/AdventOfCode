package info.jukov.adventofcode.y2022

import info.jukov.adventofcode.Day
import java.io.BufferedReader

object Day1: Day() {

    override val year: Int = 2022

    override val day: Int = 1

    override fun part1(reader: BufferedReader): String {
        return solve(reader, howMuch = 1)
    }

    override fun part2(reader: BufferedReader): String {
        return solve(reader, howMuch = 3)
    }

    private fun solve(reader: BufferedReader, howMuch: Int): String {
        val sums = ArrayList<Int>()
        sums.add(0)
        reader.useLines { depths ->
            depths.forEach {
                if (it.isBlank()) {
                    sums.add(0)
                } else {
                    sums[sums.lastIndex] = sums.last() + it.toInt()
                }
            }
        }

        sums.sort()

        val top = sums.subList(sums.size - howMuch, sums.size).sum()

        return top.toString()
    }
}
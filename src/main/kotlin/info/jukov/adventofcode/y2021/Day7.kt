package info.jukov.adventofcode.y2021

import info.jukov.adventofcode.Day
import java.io.BufferedReader
import java.lang.Long.min
import kotlin.math.abs

object Day7: Day() {

    override val year: Int = 2021

    override val day: Int = 7

    override fun part1(reader: BufferedReader): String {
        return solve(reader) { k, v, i ->
            (abs(k - i) * v).toLong()
        }
    }

    override fun part2(reader: BufferedReader): String {
        return solve(reader) { k, v, i ->
            val moves = abs(k - i)
            val fuelSpent = moves * (moves + 1) / 2
            (fuelSpent * v).toLong()
        }
    }

    private fun solve(
        reader: BufferedReader,
        computeFuelSpent: (k: Int, v: Int, i: Int) -> Long
    ): String {
        val input = reader.readLine().split(',')

        val map = HashMap<Int, Int>()
        input.map { it.toInt() }.forEach { value ->
            map.getOrPut(value) { 0 }.let { map[value] = it + 1 }
        }

        val minI = map.minBy { it.key }.key
        val maxI = map.maxBy { it.key }.key

        var min = Long.MAX_VALUE
        for (i in minI..maxI) {
            var fuel = 0L
            map.forEach { (k, v) ->
                fuel += computeFuelSpent(k, v, i)
            }
            min = min(min, fuel)
        }

        return min.toString()
    }
}
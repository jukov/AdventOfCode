package info.jukov.adventofcode.y2022

import info.jukov.adventofcode.Day
import org.json.JSONArray
import java.io.BufferedReader

/**
 * Thanks to some dude from vas3k whiteboard chat who point me that
 * puzzle input is valid json arrays.
 */
object Day13 : Day() {

    override val year: Int = 2022

    override val day: Int = 13
    override fun part1(reader: BufferedReader): String {
        val pairs = parse(reader)

        return pairs
            .windowed(2, 2)
            .withIndex()
            .map { (index, pair) ->
                val first = pair.component1()
                val second = pair.component2()
                index + 1 to compare(first, second)
            }
            .filter { (_, value) -> value == -1 }
            .sumOf { (index, _) -> index }
            .toString()
    }

    override fun part2(reader: BufferedReader): String {
        val pairs = parse(reader)

        val divider1 = JSONArray(listOf(2))
        val divider2 = JSONArray(listOf(6))

        val sorted = pairs
            .toMutableList()
            .apply {
                add(divider1)
                add(divider2)
            }
            .sortedWith(::compare)

        return ((sorted.indexOf(divider1) + 1) * (sorted.indexOf(divider2) + 1)).toString()
    }

    private fun parse(reader: BufferedReader): List<JSONArray> =
        reader
            .lineSequence()
            .mapNotNull { line ->
                if (line.isNotEmpty()) {
                    JSONArray(line)
                } else {
                    null
                }
            }
            .toList()

    private fun compare(first: Any, second: Any): Int {
        when {
            first is Int && second is Int && first != second -> {
                return if (first < second) -1 else 1
            }

            first is JSONArray && second is JSONArray -> {
                for (i in 0 until first.length()) {
                    val firstVal = first[i]
                    val secondVal = second.opt(i)

                    return if (secondVal == null) {
                        1
                    } else {
                        val comparison = compare(firstVal, secondVal)
                        if (comparison == 0) {
                            continue
                        } else {
                            return comparison
                        }
                    }
                }
                if (second.length() == first.length()) {
                    return 0
                }
                if (second.length() > first.length()) {
                    return -1
                }
            }

            first is JSONArray && second is Int -> {
                return compare(first, JSONArray(listOf(second)))
            }

            first is Int && second is JSONArray -> {
                return compare(JSONArray(listOf(first)), second)
            }
        }

        return 0
    }
}

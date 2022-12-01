package info.jukov.adventofcode.y2022

import Main
import java.io.BufferedReader
import java.io.InputStreamReader

fun year2022day1part1(): String {
    return solve(howMuch = 1)
}

fun year2022day1part2(): String {
    return solve(howMuch = 3)
}

private fun solve(howMuch: Int): String {
    val reader = BufferedReader(
        InputStreamReader(
            requireNotNull(Main::class.java.getResourceAsStream("2022/1.txt"))
        )
    )

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
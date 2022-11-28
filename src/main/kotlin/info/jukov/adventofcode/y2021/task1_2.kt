package info.jukov.adventofcode.y2021

import Main
import java.io.BufferedReader
import java.io.InputStreamReader

fun solve2021_1_2(): String {
    val reader = BufferedReader(
        InputStreamReader(
            requireNotNull(Main::class.java.getResourceAsStream("2021/1.txt"))
        )
    )

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
package info.jukov.adventofcode.y2021

import Main
import java.io.BufferedReader
import java.io.InputStreamReader

fun year2021task1part1(): String {
    val reader = BufferedReader(
        InputStreamReader(
            requireNotNull(Main::class.java.getResourceAsStream("2021/1.txt"))
        )
    )

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
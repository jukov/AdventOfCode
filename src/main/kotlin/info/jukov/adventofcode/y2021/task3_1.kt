package info.jukov.adventofcode.y2021

import Main
import info.jukov.adventofcode.util.convertBinaryToDecimal
import java.io.BufferedReader
import java.io.InputStreamReader

fun year2021task3part1(): String {
    val reader = BufferedReader(
        InputStreamReader(
            requireNotNull(Main::class.java.getResourceAsStream("2021/3.txt"))
        )
    )

    val zeroCounters = IntArray(12) { 0 }
    var i = -1

    reader.useLines { rows ->
        for (row in rows) {
            i++
            row.forEachIndexed { index, c ->
                if (c == '0') {
                    zeroCounters[index]++
                }
            }
        }
    }

    val gamma = String(zeroCounters.map { if (it > 500) '0' else '1' }.toCharArray()).toLong().convertBinaryToDecimal()
    val epsilon = String(zeroCounters.map { if (it > 500) '1' else '0' }.toCharArray()).toLong().convertBinaryToDecimal()

    return (gamma * epsilon).toString()
}
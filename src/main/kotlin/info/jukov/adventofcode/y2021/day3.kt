package info.jukov.adventofcode.y2021

import Main
import info.jukov.adventofcode.util.convertBinaryToDecimal
import java.io.BufferedReader
import java.io.InputStreamReader

fun year2021day3part1(): String {
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

fun year2021day3part2(): String {
    val rows =
        BufferedReader(
            InputStreamReader(
                requireNotNull(Main::class.java.getResourceAsStream("2021/3.txt"))
            )
        )
            .readLines()

    val length = rows.first().length

    val oxygen = find(rows, length) { a, b -> a >= b}
    val co2 = find(rows, length) { a, b -> a < b}

    return (co2 * oxygen).toString()
}

private fun find(
    rows: List<String>,
    length: Int,
    comparison: (Int, Int) -> Boolean
): Int {
    val innerRows = ArrayList(rows)
    val a = ArrayList<String>(rows.size / 2)
    val b = ArrayList<String>(rows.size / 2)

    for (i in 0 until length) {
        while (innerRows.size > 0) {
            if (innerRows[0][i] == '1') {
                a += innerRows[0]
            } else {
                b += innerRows[0]
            }
            innerRows.removeAt(0)
        }

        if (comparison(a.size, b.size)) {
            innerRows.addAll(a)
        } else {
            innerRows.addAll(b)
        }
        a.clear()
        b.clear()
        if (innerRows.size == 1) break
    }

    return innerRows.first().toLong().convertBinaryToDecimal()
}

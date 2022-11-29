package info.jukov.adventofcode.y2021

import Main
import info.jukov.adventofcode.util.convertBinaryToDecimal
import java.io.BufferedReader
import java.io.InputStreamReader

fun year2021task3part2(): String {
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


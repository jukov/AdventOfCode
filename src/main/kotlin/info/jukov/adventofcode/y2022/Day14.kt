package info.jukov.adventofcode.y2022

import info.jukov.adventofcode.Day
import java.io.BufferedReader
import kotlin.math.max
import kotlin.math.min

object Day14 : Day() {

    override val year: Int = 2022

    override val day: Int = 14

    override fun part1(reader: BufferedReader): String {
        var minX = Int.MAX_VALUE
        var maxX = 0
        var minY = Int.MAX_VALUE
        var maxY = 0

        val input = reader.readLines()
            .map { line -> line.split(",", " -> ").map { it.toInt() } }
            .map { path -> path.windowed(2, 2) }

        input.forEach { path ->
            path.forEach { (x, y) ->
                minX = min(x, minX)
                maxX = max(x, maxX)
                minY = min(y, minY)
                maxY = max(y, maxY)
            }
        }

        val dX = maxX - minX
        val dY = maxY - minY + 50

        val field = Array(dY + 1) { ByteArray(dX + 1) }

        drawPaths(input, field, minX)

        var count = 0
        main@while (true) {
            var sandX = 500 - minX
            var sandY = 0

            while (true) {
                if (field[sandY + 1][sandX] == 0.toByte()) {
                    sandY++
                } else {
                    if (field[sandY + 1][sandX - 1] == 0.toByte()) {
                        sandY++
                        sandX--
                    } else if (field[sandY + 1][sandX + 1] == 0.toByte()) {
                        sandY++
                        sandX++
                    } else {
                        field[sandY][sandX] = 2
                        break
                    }
                }

                if (sandX !in 0 until dX || sandY !in 0 until dY) {
                    break@main
                }
            }

            count++
        }

        return count.toString()
    }

    override fun part2(reader: BufferedReader): String {
        var minX = Int.MAX_VALUE
        var maxX = 0
        var minY = Int.MAX_VALUE
        var maxY = 0

        val input = reader.readLines()
            .map { line -> line.split(",", " -> ").map { it.toInt() } }
            .map { path -> path.windowed(2, 2) }

        input.forEach { path ->
            path.forEach { (x, y) ->
                minX = min(x, minX)
                maxX = max(x, maxX)
                minY = min(y, minY)
                maxY = max(y, maxY)
            }
        }

        val xInc = 400
        val dX = maxX - minX + xInc

        val field = Array(maxY + 3) { ByteArray(dX + 1) }

        drawPaths(input, field, minX, xInc = xInc / 2)

        for (i in field.last().indices) {
            field.last()[i] = 1
        }

        var count = 0
        main@ while (true) {
            var sandX = 500 - minX + 200
            var sandY = 0

            if (field[sandY][sandX] == 2.toByte()) {
                break@main
            }

            while (true) {
                if (field[sandY + 1][sandX] == 0.toByte()) {
                    sandY++
                } else {
                    if (field[sandY + 1][sandX - 1] == 0.toByte()) {
                        sandY++
                        sandX--
                    } else if (field[sandY + 1][sandX + 1] == 0.toByte()) {
                        sandY++
                        sandX++
                    } else {
                        field[sandY][sandX] = 2
                        break
                    }
                }
            }

            count++
        }

        return count.toString()
    }

    private fun drawPaths(
        input: List<List<List<Int>>>,
        field: Array<ByteArray>,
        minX: Int,
        xInc: Int = 0
    ) {
        input.forEach { path ->
            var x = path.first().component1()
            var y = path.first().component2()

            for (i in 1 until path.size) {
                val (toX, toY) = path[i]

                if (x == toX) {
                    for (j in min(y, toY)..max(y, toY)) {
                        field[j][x - minX + xInc] = 1
                    }
                } else if (y == toY) {
                    for (j in min(x, toX)..max(x, toX)) {
                        field[y][j - minX + xInc] = 1
                    }
                } else {
                    error("Line not straight, x $x toX $toX, y $y toY $toY")
                }

                x = toX
                y = toY
            }
        }
    }

    @Suppress("unused")
    private fun debug(field: Array<ByteArray>) {
        print(field.joinToString(separator = "\n") {
            it.joinToString(separator = "") { cell ->
                when (cell) {
                    1.toByte() -> "X"
                    2.toByte() -> "o"
                    else -> "."
                }
            }
        })
    }
}

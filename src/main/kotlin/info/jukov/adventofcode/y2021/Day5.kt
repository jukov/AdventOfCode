package info.jukov.adventofcode.y2021

import info.jukov.adventofcode.Day
import info.jukov.adventofcode.util.sign
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.BufferedReader
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import javax.imageio.ImageIO
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

object Day5: Day() {

    override val year: Int = 2021

    override val day: Int = 5

    override fun part1(reader: BufferedReader): String {
        return solve(reader, ignoreDiagonal = true)
    }

    override fun part2(reader: BufferedReader): String {
        return solve(reader, ignoreDiagonal = false)
    }

    private fun solve(reader: BufferedReader, ignoreDiagonal: Boolean): String {
        val field = Array(1000) { IntArray(1000) }

        reader.forEachLine { line ->
            val (x0, y0, x1, y1) = line.split(",", " -> ").map { it.toInt() }
            field.drawLine(x0, y0, x1, y1, ignoreDiagonal)
        }

        return field.sumOf { fieldRow ->
            fieldRow.count { it >= 2 }
        }.toString()
    }

    fun Array<IntArray>.drawLine(x0: Int, y0: Int, x1: Int, y1: Int, ignoreDiagonal: Boolean) {
        if (x0 == x1) {
            val start = min(y0, y1)
            val stop = max(y0, y1)
            for (i in start..stop) {
                this[x0][i] = this[x0][i] + 1
            }
        } else if (y0 == y1) {
            val start = min(x0, x1)
            val stop = max(x0, x1)
            for (i in start..stop) {
                this[i][y0] = this[i][y0] + 1
            }
        } else if (!ignoreDiagonal) {
            val xSign = sign(x0 - x1)
            val ySign = sign(y0 - y1)
            for (i in 0..abs(x0 - x1)) {
                val x = x0 - i * xSign
                val y = y0 - i * ySign
                this[x][y] = this[x][y] + 1
            }
        }
    }

    @Suppress("unused")
    fun Array<IntArray>.debug() {
        val image = BufferedImage(this.size, this.first().size, BufferedImage.TYPE_INT_RGB)
        val graphics = image.createGraphics()

        for (i in indices) {
            for (j in this[i].indices) {
                val point = this[i][j]
                graphics.color = when (point) {
                    0 -> Color.WHITE
                    1 -> Color.GRAY
                    else -> Color.GREEN
                }
                graphics.drawLine(i, j, i, j)
            }
        }

        Files.createDirectories(Path.of("debug"))
        val output = File("debug/debug5.png")
        ImageIO.write(image, "png", output)
    }
}
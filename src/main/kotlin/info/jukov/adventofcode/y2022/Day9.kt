package info.jukov.adventofcode.y2022

import info.jukov.adventofcode.Day
import java.io.BufferedReader
import kotlin.math.abs

object Day9 : Day() {

    override val year: Int = 2022

    override val day: Int = 9
    override fun part1(reader: BufferedReader): String {
        var headX = 0
        var headY = 0
        var tailX = 0
        var tailY = 0

        val tailVisited = HashSet<Pair<Int, Int>>()

        reader.forEachLine { command ->
            val split = command.split(' ')
            val direction = split[0].first()
            val count = split[1].toInt()
            when (direction) {
                'R' -> {
                    for (i in 1..count) {
                        headX++
                        if (tailX !in headX - 1..headX + 1) {
                            if (tailY > headY) {
                                tailY--
                            } else if (tailY < headY) {
                                tailY++
                            }
                            tailX++
                        }
                        tailVisited += tailX to tailY
                    }
                }

                'U' -> {
                    for (i in 1..count) {
                        headY--
                        if (tailY !in headY - 1..headY + 1) {
                            if (tailX > headX) {
                                tailX--
                            } else if (tailX < headX) {
                                tailX++
                            }
                            tailY--
                        }
                        tailVisited += tailX to tailY
                    }
                }

                'L' -> {
                    for (i in 1..count) {
                        headX--
                        if (tailX !in headX - 1..headX + 1) {
                            if (tailY > headY) {
                                tailY--
                            } else if (tailY < headY) {
                                tailY++
                            }
                            tailX--
                        }
                        tailVisited += tailX to tailY
                    }
                }

                'D' -> {
                    for (i in 1..count) {
                        headY++
                        if (tailY !in headY - 1..headY + 1) {
                            if (tailX > headX) {
                                tailX--
                            } else if (tailX < headX) {
                                tailX++
                            }
                            tailY++
                        }
                        tailVisited += tailX to tailY
                    }
                }
            }
        }

        return tailVisited.size.toString()
    }

    override fun part2(reader: BufferedReader): String {
        val rope = ArrayList<Entry>()

        for (i in 0 until 10) {
            rope += Entry(0, 0)
        }

        val tailVisited = HashSet<Pair<Int, Int>>()

        reader.forEachLine { command ->
            val split = command.split(' ')
            val direction = split[0].first()
            val count = split[1].toInt()
            for (i in 1..count) {
                when (direction) {
                    'R' -> rope[0].x++
                    'U' -> rope[0].y--
                    'L' -> rope[0].x--
                    'D' -> rope[0].y++
                }
                for (j in 1 until rope.size) {
                    if (rope[j].x !in rope[j - 1].x - 1..rope[j - 1].x + 1) {
                        checkY(rope, j)
                        checkX(rope, j)
                    } else if (rope[j].y !in rope[j - 1].y - 1..rope[j - 1].y + 1) {
                        checkY(rope, j)
                        checkX(rope, j)
                    }
                }
                tailVisited += rope.last().let { it.x to it.y }
            }
        }

        return tailVisited.size.toString()
    }

    private fun checkX(rope: ArrayList<Entry>, j: Int) {
        if (rope[j].x > rope[j - 1].x) {
            rope[j].x--
        } else if (rope[j].x < rope[j - 1].x) {
            rope[j].x++
        }
    }

    private fun checkY(rope: ArrayList<Entry>, j: Int) {
        if (rope[j].y > rope[j - 1].y) {
            rope[j].y--
        } else if (rope[j].y < rope[j - 1].y) {
            rope[j].y++
        }
    }

    @Suppress("unused")
    fun debug(list: List<Entry>): Array<CharArray> {
        val xMin = list.minBy { it.x }.x
        val xMax = list.maxBy { it.x }.x
        val yMin = list.minBy { it.y }.y
        val yMax = list.maxBy { it.y }.y

        val xInc = abs(xMin)
        val yInc = abs(yMin)

        val field = Array(yMax + 1 + yInc) { CharArray(xMax + 1 + xInc) { '.' } }

        list.withIndex().forEach { entry ->
            field[entry.value.y + yInc][entry.value.x + xInc] = entry.index.digitToChar()
        }

        field.forEach {
            println(it.joinToString(separator = ""))
        }
        println()

        return field
    }

    class Entry(var x: Int, var y: Int) {
        override fun toString(): String {
            return "Entry(x=$x, y=$y)"
        }
    }
}

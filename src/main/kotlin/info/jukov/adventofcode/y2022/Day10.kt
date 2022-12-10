package info.jukov.adventofcode.y2022

import info.jukov.adventofcode.Day
import java.io.BufferedReader

object Day10 : Day() {

    override val year: Int = 2022

    override val day: Int = 10
    override fun part1(reader: BufferedReader): String {
        var sum = 0
        var cycle = 0
        var reg = 1
        reader.forEachLine { line ->
            when {
                line == "noop" -> {
                    cycle++
                    if (cycle == 20 || (cycle - 20) % 40 == 0) {
                        sum += cycle * reg
                    }
                }

                line.startsWith("addx") -> {
                    val add = line.split(' ').last().toInt()
                    cycle++
                    if (cycle == 20 || (cycle - 20) % 40 == 0) {
                        sum += cycle * reg
                    }
                    cycle++
                    if (cycle == 20 || (cycle - 20) % 40 == 0) {
                        sum += cycle * reg
                    }
                    reg += add
                }

                else -> error("Unexpected line $line")
            }

        }

        return sum.toString()
    }

    override fun part2(reader: BufferedReader): String {
        val display = CharArray(240) { ' ' }

        var cycle = 0
        var reg = 1

        reader.forEachLine { line ->
            when {
                line == "noop" -> {
                    if (cycle % 40 in (reg - 1)..(reg + 1)) {
                        display[cycle] = '#'
                    }
                    cycle++
                }

                line.startsWith("addx") -> {
                    val add = line.split(' ').last().toInt()
                    if (cycle % 40 in (reg - 1)..(reg + 1)) {
                        display[cycle] = '#'
                    }
                    cycle++
                    if (cycle % 40 in (reg - 1)..(reg + 1)) {
                        display[cycle] = '#'
                    }
                    cycle++
                    reg += add
                }

                else -> error("Unexpected line $line")
            }

        }

        return display
            .toList()
            .windowed(40, 40, false) { row ->
                String(row.toCharArray()) + "\n"
            }
            .joinToString("")
    }
}

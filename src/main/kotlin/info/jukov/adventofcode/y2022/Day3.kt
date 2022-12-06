package info.jukov.adventofcode.y2022

import info.jukov.adventofcode.Day
import java.io.BufferedReader

object Day3 : Day() {

    override val year: Int = 2022

    override val day: Int = 3
    override fun part1(reader: BufferedReader): String {
        var sum = 0

        reader.forEachLine { input: String ->
            val first = input.substring(0, input.length / 2)
            val second = input.substring(input.length / 2)

            val set = HashSet<Char>(first.toSet())

            val duplicates = ArrayList<Char>()
            second.forEach { char ->
                if (set.contains(char)) {
                    set.remove(char)
                    duplicates += char
                }
            }

            sum += duplicates.map { char ->
                when (char) {
                    in 'a'..'z' -> {
                        char.code - 96
                    }

                    in 'A'..'Z' -> {
                        char.code - 38
                    }

                    else -> {
                        error("Unexpected char $char")
                    }
                }
            }
                .sum()
        }
        return sum.toString()
    }

    override fun part2(reader: BufferedReader): String {
        var sum = 0

        reader.readLines().let { lines ->
            lines.windowed(3, 3) { group ->
                val map = HashMap<Char, Int>()
                group.forEach { line ->
                    line.toSet().forEach { char ->
                        map.putIfAbsent(char, 0)
                        map[char] = (map.getValue(char) + 1)
                    }
                }

                sum += map.firstNotNullOf { if (it.value == 3) it.key else null }
                    .let { char ->
                        when (char) {
                            in 'a'..'z' -> {
                                char.code - 96
                            }

                            in 'A'..'Z' -> {
                                char.code - 38
                            }

                            else -> {
                                error("Unexpected char $char")
                            }
                        }
                    }
            }
        }

        return sum.toString()
    }
}
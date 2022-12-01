package info.jukov.adventofcode.y2021

import info.jukov.adventofcode.Day
import java.io.BufferedReader

object Day2: Day() {

    override val year: Int = 2021

    override val day: Int = 2

    override fun part1(reader: BufferedReader): String {
        var depth = 0
        var horizontal = 0

        reader.useLines { commands ->
            for (command in commands) {
                when (command.first()) {
                    'f' -> horizontal += command.last().digitToInt()
                    'd' -> depth += command.last().digitToInt()
                    'u' -> depth -= command.last().digitToInt()
                    else -> error("Unexpected command $command")
                }
            }
        }

        return (depth * horizontal).toString()
    }

    override fun part2(reader: BufferedReader): String {
        var depth = 0L
        var horizontal = 0L
        var aim = 0L

        reader.useLines { commands ->
            for (command in commands) {
                when (command.first()) {
                    'f' -> {
                        command.last().digitToInt().let {
                            horizontal += it
                            depth += aim * it
                        }
                    }

                    'd' -> {
                        command.last().digitToInt().let {
                            aim += it
                        }
                    }

                    'u' -> {
                        command.last().digitToInt().let {
                            aim -= it
                        }
                    }

                    else -> {
                        error("Unexpected command $command")
                    }
                }
            }
        }

        return (depth * horizontal).toString()
    }
}

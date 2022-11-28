package info.jukov.adventofcode.y2021

import Main
import java.io.BufferedReader
import java.io.InputStreamReader

fun year2021task2part2(): String {
    val reader = BufferedReader(
        InputStreamReader(
            requireNotNull(Main::class.java.getResourceAsStream("2021/2.txt"))
        )
    )

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
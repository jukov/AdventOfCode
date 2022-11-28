package info.jukov.adventofcode.y2021

import Main
import java.io.BufferedReader
import java.io.InputStreamReader

fun year2021task2part1(): String {
    val reader = BufferedReader(
        InputStreamReader(
            requireNotNull(Main::class.java.getResourceAsStream("2021/2.txt"))
        )
    )

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
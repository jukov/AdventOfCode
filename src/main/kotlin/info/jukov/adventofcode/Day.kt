package info.jukov.adventofcode

import java.io.BufferedReader
import java.io.InputStreamReader

abstract class Day {

    abstract val year: Int

    abstract val day: Int

    private val inputPath: String
        get() = "/$year/${day}.txt"

    fun run() {
        println("Year $year day $day part 1: ${runPart1()}, part 2: ${runPart2()}")
    }

    private fun runPart1(): String {
        return getReader().use { reader -> part1(reader) }
    }
    private fun runPart2(): String {
        return getReader().use { reader -> part2(reader) }
    }

    private fun getReader() = BufferedReader(
        InputStreamReader(
            requireNotNull(this::class.java.getResourceAsStream(inputPath))
        )
    )

    abstract fun part1(reader: BufferedReader): String

    abstract fun part2(reader: BufferedReader): String

}
package info.jukov.adventofcode

import java.io.BufferedReader
import java.io.InputStreamReader

abstract class Day {

    abstract val year: Int

    abstract val day: Int

    private val inputPath: String
        get() = "/$year/${day}.txt"

    fun runPart1() {
        println("Year $year day $day. Part 1:\n${getReader().use { reader -> part1(reader) }}")
    }
    fun runPart2(){
        println("Year $year day $day. Part 2:\n${getReader().use { reader -> part2(reader) }}")
    }

    private fun getReader() = BufferedReader(
        InputStreamReader(
            requireNotNull(this::class.java.getResourceAsStream(inputPath))
        )
    )

    abstract fun part1(reader: BufferedReader): String

    abstract fun part2(reader: BufferedReader): String
}
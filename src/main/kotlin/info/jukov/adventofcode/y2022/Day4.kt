package info.jukov.adventofcode.y2022

import info.jukov.adventofcode.Day
import info.jukov.adventofcode.util.contains
import info.jukov.adventofcode.util.intersect
import java.io.BufferedReader

object Day4 : Day() {

    override val year: Int = 2022

    override val day: Int = 4
    override fun part1(reader: BufferedReader): String {
        var overlaps = 0

        reader.forEachLine { line ->
            val (start0, end0, start1, end1) = line.split('-', ',').map { it.toInt() }

            if ((start0..end0).contains(start1..end1) || (start1..end1).contains(start0..end0)) {
                overlaps++
            }
        }

        return overlaps.toString()
    }

    override fun part2(reader: BufferedReader): String {
        var overlaps = 0

        reader.forEachLine { line ->
            val (start0, end0, start1, end1) = line.split('-', ',').map { it.toInt() }

            if ((start0..end0).intersect(start1..end1)) {
                overlaps++
            }
        }

        return overlaps.toString()
    }
}

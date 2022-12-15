package info.jukov.adventofcode.y2022

import info.jukov.adventofcode.Day
import info.jukov.adventofcode.util.calcDistance
import info.jukov.adventofcode.util.combine
import info.jukov.adventofcode.util.delta
import java.io.BufferedReader
import kotlin.math.max
import kotlin.math.min

object Day15 : Day() {

    override val year: Int = 2022

    override val day: Int = 15

    override fun part1(reader: BufferedReader): String {
        val input = reader.readLines()
            .map { line ->
                line.split(
                    "Sensor at x=",
                    ", y=",
                    ": closest beacon is at x="
                )
                    .takeLast(4)
                    .map { it.toInt() }
            }

        val target = 2_000_000

        var minX = Int.MAX_VALUE
        var maxX = Int.MIN_VALUE
        var minY = Int.MAX_VALUE
        var maxY = Int.MIN_VALUE

        val beaconsOnTarget = HashSet<Pair<Int, Int>>()

        input.forEach { (sX, sY, bX, bY) ->
            minX = min(minX, min(sX, bX))
            maxX = max(maxX, max(sX, bX))
            minY = min(minY, min(sY, bY))
            maxY = max(maxY, max(sY, bY))
            if (bY == target) {
                beaconsOnTarget += bX to bY
            }
        }

        val targetRow = HashSet<Int>(target * 2)

        input.forEach { (sX, sY, bX, bY) ->
            val distanceToBeacon = calcDistance(sX, sY, bX, bY)
            val sensorToTarget = delta(target, sY)
            if (distanceToBeacon > sensorToTarget) {
                val coveredHalf = distanceToBeacon - sensorToTarget
                for (i in (sX - coveredHalf)..(sX + coveredHalf)) {
                    targetRow.add(i)
                }
            }
        }

        return (targetRow.size - beaconsOnTarget.size).toString()
    }

    override fun part2(reader: BufferedReader): String {
        val input = reader.readLines()
            .map { line ->
                line.split(
                    "Sensor at x=",
                    ", y=",
                    ": closest beacon is at x="
                )
                    .takeLast(4)
                    .map { it.toInt() }
            }

        val beacons = input.map { (sX, sY, bX, bY) ->
            val d = calcDistance(sX, sY, bX, bY)
            Triple(sX, sY, d)
        }

        var solutionX = 0
        var solutionY = 0

        val max = 4_000_000

        for (i in 0..max) {
            val rangeList = ArrayList<IntRange>()

            beacons.forEach { (sX, sY, d) ->
                val sensorToTarget = delta(i, sY)
                if (d > sensorToTarget) {
                    val coveredHalf = d - sensorToTarget

                    var newRange = sX - coveredHalf..sX + coveredHalf

                    if (rangeList.isEmpty()) {
                        rangeList += newRange
                    } else {
                        rangeList.removeAll { range ->
                            val combined = range.combine(newRange)
                            if (combined != null) {
                                newRange = combined
                                true
                            } else {
                                false
                            }
                        }
                        rangeList += newRange
                    }
                }
            }

            if (rangeList.size == 2) {
                val (first, second) = rangeList
                println(rangeList.joinToString())
                solutionX = if (first.last > second.first) {
                    first.last + 1
                } else {
                    second.first - 1
                }
                solutionY = i
                break
            }
        }

        return (solutionX.toLong() * max.toLong() + solutionY.toLong()).toString()
    }
}

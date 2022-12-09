package info.jukov.adventofcode.y2022

import info.jukov.adventofcode.Day
import java.io.BufferedReader
import kotlin.math.max

object Day8 : Day() {

    override val year: Int = 2022

    override val day: Int = 8
    override fun part1(reader: BufferedReader): String {
        val wood = ArrayList<IntArray>()

        reader.forEachLine { line ->
            wood += line.map { it.digitToInt() }.toIntArray()
        }

        var answer = 0

        answer += wood.size * 2
        answer += wood.first().size * 2 - 4

        for (i in 1 until wood.size - 1) {
            second@for (j in 1 until wood.first().size - 1) {
                val target = wood[i][j]
                var surroundings = 0
                var k = i - 1
                while (k >= 0) {
                    if (wood[k][j] >= target) {
                        surroundings++
                        break
                    }
                    k--
                }
                k = i + 1
                while (k < wood.size) {
                    if (wood[k][j] >= target) {
                        surroundings++
                        break
                    }
                    k++
                }
                k = j - 1
                while (k >= 0) {
                    if (wood[i][k] >= target) {
                        surroundings++
                        break
                    }
                    k--
                }
                k = j + 1
                while (k < wood[i].size) {
                    if (wood[i][k] >= target) {
                        surroundings++
                        break
                    }
                    k++
                }
                if (surroundings != 4) {
                    answer++
                }
            }
        }

        return answer.toString()
    }

    override fun part2(reader: BufferedReader): String {
        val wood = ArrayList<IntArray>()

        reader.forEachLine { line ->
            wood += line.map { it.digitToInt() }.toIntArray()
        }

        var answer = 0

        for (i in 1 until wood.size - 1) {
            second@for (j in 1 until wood.first().size - 1) {
                val target = wood[i][j]
                var score = 1
                var visibleTrees = 0
                var k = i - 1
                while (k >= 0) {
                    if (wood[k][j] < target) {
                        visibleTrees++
                    } else {
                        visibleTrees++
                        break
                    }
                    k--
                }
                score *= visibleTrees
                visibleTrees = 0
                k = i + 1
                while (k < wood.size) {
                    if (wood[k][j] < target) {
                        visibleTrees++
                    } else {
                        visibleTrees++
                        break
                    }
                    k++
                }
                score *= visibleTrees
                visibleTrees = 0
                k = j - 1
                while (k >= 0) {
                    if (wood[i][k] < target) {
                        visibleTrees++
                    } else {
                        visibleTrees++
                        break
                    }
                    k--
                }
                score *= visibleTrees
                visibleTrees = 0
                k = j + 1
                while (k < wood[i].size) {
                    if (wood[i][k] < target) {
                        visibleTrees++
                    } else {
                        visibleTrees++
                        break
                    }
                    k++
                }
                score *= visibleTrees
                answer = max(answer, score)
            }
        }

        return answer.toString()
    }
}

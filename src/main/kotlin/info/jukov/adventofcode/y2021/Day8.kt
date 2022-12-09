package info.jukov.adventofcode.y2021

import info.jukov.adventofcode.Day
import java.io.BufferedReader
import kotlin.math.pow

object Day8 : Day() {

    override val year: Int = 2021

    override val day: Int = 8

    override fun part1(reader: BufferedReader): String {
        reader.useLines { lines ->
            return lines
                .map { line -> line.substring(line.indexOf('|') + 2) }
                .map { line ->
                    line
                        .split(' ')
                        .map { it.length }
                        .count { count -> count in 2..4 || count == 7 }
                }
                .sum()
                .toString()
        }
    }

    /**
     * Indicator keys:
     *  0
     * 1 2
     *  3
     * 4 5
     *  6
     */
    override fun part2(reader: BufferedReader): String {
        reader.useLines { lines ->
            return lines
                .map { line ->
                    val split = line.split(" | ")
                    val test = split[0].split(' ').map { it.toSet() }
                    val digitCode = HashMap<Int, Set<Char>>()
                    val indicator = HashMap<Int, Char>()

                    digitCode[8] = EIGHT
                    digitCode[1] = test.first { digit -> digit.size == 2 }
                    digitCode[7] = test.first { digit -> digit.size == 3 }
                    digitCode[4] = test.first { digit -> digit.size == 4 }
                    indicator[0] = digitCode[7]!!.minus(digitCode[1]!!).first()

                    val nineKnown = digitCode[7]!! + digitCode[4]!!
                    digitCode[9] = test.first { digit -> digit.size == 6 && digit.containsAll(nineKnown) }
                    indicator[4] = EIGHT.minus(digitCode[9]!!).first()
                    indicator[6] = EIGHT
                        .minus(digitCode[4]!!)
                        .minus(indicator[0]!!)
                        .minus(indicator[4]!!)
                        .first()
                    val zeroKnown = digitCode[7]!! + indicator[4]!!
                    digitCode[0] = test.first { digit -> digit.size == 6 && digit.containsAll(zeroKnown) }

                    indicator[3] = EIGHT.minus(digitCode[0]!!).first()
                    digitCode[2] = test.first { digit ->
                        digit.size == 5 && digit.containsAll(indicator.values)
                    }
                    digitCode[3] = test.first { digit ->
                        digit.size == 5 &&
                                !digit.contains(indicator[4]!!) && digit.containsAll(digitCode[1]!!)
                    }
                    digitCode[5] = test.first { digit ->
                        digit.size == 5 &&
                                !digit.contains(indicator[4]!!) && !digit.containsAll(digitCode[1]!!)
                    }
                    digitCode[6] = test.first { digit ->
                        digit.size == 6 && digit.contains(indicator[3]!!) && digit.contains(indicator[4]!!)
                    }

                    val invertedMap = digitCode.entries.associate { it.value to it.key }

                    split[1]
                        .split(' ')
                        .map { it.toSet() }
                        .map { invertedMap.getValue(it) }
                        .reversed()
                        .withIndex()
                        .fold(0) { acc, indexedValue ->
                            val (index, value) = indexedValue
                            acc + value * 10f.pow(index).toInt()
                        }
                }
                .sum()
                .toString()
        }
    }

    private val EIGHT = setOf('a', 'b', 'c', 'd', 'e', 'f', 'g')
}
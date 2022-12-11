package info.jukov.adventofcode.y2022

import info.jukov.adventofcode.Day
import java.io.BufferedReader

/**
 * Thanks to https://todd.ginsberg.com/post/advent-of-code/2022/day11/
 * for explaining proper solution of part 2
 */
object Day11 : Day() {

    override val year: Int = 2022

    override val day: Int = 11
    override fun part1(reader: BufferedReader): String {
        val monkeys = readMonkeys(reader)

        for (i in 1..20) {
            runRound(monkeys) { value -> value / 3}
        }

        monkeys.sortByDescending { it.inspection }

        return (monkeys[0].inspection * monkeys[1].inspection).toString()
    }

    override fun part2(reader: BufferedReader): String {
        val monkeys = readMonkeys(reader)

        val testProduct: Long = monkeys.map { it.divTest }.reduce { l, other -> l.times(other) }

        for (i in 1..10000) {
            runRound(monkeys) { value -> value % testProduct }
        }

        monkeys.sortByDescending { it.inspection }

        return (monkeys[0].inspection * monkeys[1].inspection).toString()
    }

    private fun runRound(monkeys: ArrayList<Monkey>, op: (Long) -> Long) {
        monkeys.forEach { monkey ->
            monkey.worry.forEach { item ->
                val second = if (monkey.opValue == -1L) {
                    item
                } else {
                    monkey.opValue
                }
                var newValue = when (monkey.op) {
                    '*' -> item * second
                    '+' -> item + second
                    else -> error("Unexpected op ${monkey.op}")
                }
                newValue = op(newValue)
                if (newValue % monkey.divTest == 0L) {
                    monkeys[monkey.trueReceiver].worry += newValue
                } else {
                    monkeys[monkey.falseReceiver].worry += newValue
                }
                monkey.inspection++
            }
            monkey.worry.clear()
        }
    }

    private fun readMonkeys(reader: BufferedReader): ArrayList<Monkey> {
        val monkeys = ArrayList<Monkey>()

        val worry = ArrayList<Long>()
        var op = '0'
        var opValue = 0L
        var divTest = 0L
        var trueReceiver = 0
        var falseReceiver = 0

        reader.forEachLine { line ->
            when {
                line.startsWith("Monkey") -> Unit

                line.startsWith("  Starting items") -> {
                    worry += line.substring(18).split(", ").map { it.toLong() }
                }

                line.startsWith("  Operation") -> {
                    val (operator, second) = line.substring(23).split(' ')
                    op = operator.first()
                    opValue = if (second == "old") {
                        -1L
                    } else {
                        second.toLong()
                    }
                }

                line.startsWith("  Test") -> {
                    divTest = line.substring(21).toLong()
                }

                line.startsWith("    If true") -> {
                    trueReceiver = line.substring(29).toInt()
                }

                line.startsWith("    If false") -> {
                    falseReceiver = line.substring(30).toInt()
                }

                line.isBlank() -> {
                    monkeys += Monkey(
                        ArrayList(worry),
                        op,
                        opValue,
                        divTest,
                        trueReceiver,
                        falseReceiver
                    )
                    worry.clear()
                }

                else -> error("Unexpected line $line")
            }
        }

        monkeys += Monkey(
            worry,
            op,
            opValue,
            divTest,
            trueReceiver,
            falseReceiver
        )

        return monkeys
    }

    data class Monkey(
        val worry: ArrayList<Long>,
        val op: Char,
        val opValue: Long, // -1 = old
        val divTest: Long,
        val trueReceiver: Int,
        val falseReceiver: Int,
        var inspection: Long = 0
    ) {

    }
}

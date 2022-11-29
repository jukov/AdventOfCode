package info.jukov.adventofcode.y2021

import Main
import java.io.BufferedReader
import java.io.InputStreamReader

fun year2021task4part1(): String {
    val reader = BufferedReader(
        InputStreamReader(
            requireNotNull(Main::class.java.getResourceAsStream("2021/4.txt"))
        )
    )

    val turns = reader.readLine().split(',').map { it.toInt() }

    val boards = ArrayList<HashMap<Int, Pair<Int, Int>>>()
    val winSequences = HashMap<WinKey, Int>()

    var current: HashMap<Int, Pair<Int, Int>>? = null
    var i = 0
    reader.forEachLine { line ->
        if (line.isBlank()) {
            current?.let { boards += it }
            current = HashMap()
            i = 0
        } else {
            line.split(' ').filter { it.isNotBlank() }.map { it }.forEachIndexed { j, number ->
                current?.set(number.toInt(), j to i)
            }
            i++
        }
    }
    current?.let { boards += it }

    turns.forEach { turn ->
        boards.forEachIndexed { boardIndex, board ->
            val turnIndices = board.remove(turn)
            if (turnIndices != null) {
                val sequenceId1 = turnIndices.first
                val sequenceId2 = turnIndices.second + 10
                val currentCount1 =
                    winSequences.compute(WinKey(boardIndex, sequenceId1)) { _, value ->
                        (value ?: 0) + 1
                    }
                val currentCount2 =
                    winSequences.compute(WinKey(boardIndex, sequenceId2)) { _, value ->
                        (value ?: 0) + 1
                    }
                if (currentCount1 == 5 || currentCount2 == 5) {
                    val leftKeysSum = board.keys.sum()
                    return (leftKeysSum * turn).toString()
                }
            }
        }
    }

    error("No solution")
}

fun year2021task4part2(): String {
    val reader = BufferedReader(
        InputStreamReader(
            requireNotNull(Main::class.java.getResourceAsStream("2021/4.txt"))
        )
    )

    val turns = reader.readLine().split(',').map { it.toInt() }

    val boards = ArrayList<HashMap<Int, Pair<Int, Int>>>()
    val winSequences = ArrayList<HashMap<Int, Int>>()

    var current: HashMap<Int, Pair<Int, Int>>? = null
    var i = 0
    reader.forEachLine { line ->
        if (line.isBlank()) {
            current?.let { boards += it }
            current = HashMap()
            i = 0
        } else {
            line.split(' ').filter { it.isNotBlank() }.map { it }.forEachIndexed { j, number ->
                current?.set(number.toInt(), j to i)
            }
            i++
        }
    }
    current?.let { boards += it }

    repeat(boards.size) {
        winSequences.add(HashMap())
    }

    var lastWinCode = 0

    turns.forEach { turn ->
        var boardIndex = 0
        while (boardIndex < boards.size) {
            val board = boards[boardIndex]
            val turnIndices = board.remove(turn)
            if (turnIndices != null) {
                val sequenceId1 = turnIndices.first
                val sequenceId2 = turnIndices.second + 10
                val currentCount1 =
                    winSequences[boardIndex].compute(sequenceId1) { _, value ->
                        (value ?: 0) + 1
                    }
                val currentCount2 =
                    winSequences[boardIndex].compute(sequenceId2) { _, value ->
                        (value ?: 0) + 1
                    }
                if (currentCount1 == 5 || currentCount2 == 5) {
                    val leftKeysSum = board.keys.sum()
                    lastWinCode = leftKeysSum * turn
                    boards.removeAt(boardIndex)
                    winSequences.removeAt(boardIndex)
                } else {
                    boardIndex++
                }
            } else {
                boardIndex++
            }
        }
    }

    require(lastWinCode != 0) { "No solution" }

    return lastWinCode.toString()
}

@Suppress("unused")
fun debug(boards: ArrayList<HashMap<Int, Pair<Int, Int>>>) {
    if (boards.isEmpty()) return

    for (i in 0 until 5) {
        for (j in 0 until boards.size) {
            for (k in 0 until 5) {
                print(boards[j].firstNotNullOfOrNull { if (it.value == k to i) it else null }?.key ?: "__")
                print(' ')
            }
            print('\t')
        }
        println()
    }
    println()
}

private data class WinKey(
    val boardId: Int,
    val sequenceId: Int
)

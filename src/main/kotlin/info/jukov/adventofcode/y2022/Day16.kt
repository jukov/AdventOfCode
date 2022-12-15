package info.jukov.adventofcode.y2022

import info.jukov.adventofcode.Day
import info.jukov.adventofcode.util.fixHashCode
import java.io.BufferedReader
import kotlin.math.max

class Day16 : Day() {

    override val year: Int = 2022

    override val day: Int = 16

    private val cache1 = HashMap<CacheKey1, Int>()
    private val cache2 = HashMap<CacheKey2, Int>()
    private val activated = HashSet<Int>()
    private lateinit var weights: Array<IntArray>
    private lateinit var valves: IntArray

    override fun part1(reader: BufferedReader): String {
        var nodes = readGraph(reader)

        val indices = nodes.withIndex().associate { (index, node) -> node.name to index }

        var graphMatrix = Array(nodes.size) { IntArray(nodes.size) }

        buildGraphMatrix(graphMatrix, nodes, indices)
        ignoreEmptyNodes(graphMatrix, nodes)

        nodes = nodes.filter { it.value != 0 || it.name == "AA" }

        graphMatrix = dropEmptyNodes(graphMatrix, nodes)

        this.weights = floydWarshall(graphMatrix)

        //Last index is always AA
        return solve(valves.lastIndex, time = 30).toString()
    }

    private fun readGraph(reader: BufferedReader) = reader
        .readLines()
        .map { line ->
            val split = line.split(
                "Valve ",
                " has flow rate=",
                "; tunnels lead to valves ",
                "; tunnel leads to valve ",
                ", "
            )
                .drop(1)
            Valve(
                name = split.component1(),
                value = max(0, split.component2().toInt()),
                next = split.drop(2)
            )
        }
        .sortedByDescending { it.value }

    private fun dropEmptyNodes(
        graphMatrix: Array<IntArray>,
        valves: List<Valve>
    ): Array<IntArray> {
        this.valves = valves.map { it.value }.toIntArray()
        val weights = graphMatrix.filter { i -> !i.all { j: Int -> j == -1 } }.toTypedArray()
        for (i in weights.indices) {
            weights[i] = weights[i].filter { it != -1 }.toIntArray()
        }
        return weights
    }

    private fun ignoreEmptyNodes(
        outArray: Array<IntArray>,
        valves: List<Valve>
    ) {
        for (i in outArray.indices) {
            if (valves[i].value == 0 && valves[i].name != "AA") {
                val edgeIndices = outArray[i].withIndex().filter { it.value > 0 }.map { it.index }
                edgeIndices.forEach { j ->
                    edgeIndices.forEach { k ->
                        if (j != k) {
                            outArray[j][k] = max(outArray[j][k], outArray[j][i] + 1)
                            outArray[k][j] = max(outArray[k][j], outArray[i][j] + 1)
                        }
                    }
                }
                for (j in outArray.indices) {
                    outArray[i][j] = -1
                    outArray[j][i] = -1
                }
            }
        }
    }

    private fun buildGraphMatrix(
        ourArray: Array<IntArray>,
        valves: List<Valve>,
        indices: Map<String, Int>
    ): Array<IntArray> {
        valves.forEach { node ->
            node.next.forEach { next ->
                ourArray[indices[node.name]!!][indices[next]!!] = 1
            }
        }
        return ourArray
    }

    override fun part2(reader: BufferedReader): String {
        var nodes = readGraph(reader)

        val indices = nodes.withIndex().associate { (index, node) -> node.name to index }

        var graphMatrix = Array(nodes.size) { IntArray(nodes.size) }

        buildGraphMatrix(graphMatrix, nodes, indices)
        ignoreEmptyNodes(graphMatrix, nodes)

        nodes = nodes.filter { it.value != 0 || it.name == "AA" }

        graphMatrix = dropEmptyNodes(graphMatrix, nodes)

        this.weights = floydWarshall(graphMatrix)

        return solve2(
            current1 = valves.lastIndex,//Last index is always AA
            current2 = valves.lastIndex,
            time1 = 26,
            time2 = 26
        ).toString()
    }

    private fun floydWarshall(weights: Array<IntArray>): Array<IntArray> {
        val fw = Array(weights.size) { IntArray(weights.size) }
        for (i in weights.indices) {
            for (j in weights.indices) {
                if (weights[i][j] == 0) {
                    fw[i][j] = 9999
                } else {
                    fw[i][j] = weights[i][j]
                }
            }
        }
        for (k in fw.indices) {
            for (i in fw.indices) {
                for (j in fw.indices) {
                    if (fw[i][k] + fw[k][j] < fw[i][j]) {
                        fw[i][j] = fw[i][k] + fw[k][j]
                    }
                }
            }
            fw[k][k] = 0
        }
        return fw
    }

    private fun solve(current: Int, time: Int): Int {
        if (time <= 0) return 0

        cache1[CacheKey1(activated.fixHashCode(), current, time)]?.let { return it }

        val weights = weights[current]

        var best = 0

        for (next in 0 until (weights.size - 1)) {
            if (next == current) continue

            val nextWeight = weights[next]

            if (!activated.contains(next)) {
                activated += next
                val nextTime = max(0, time - nextWeight - 1)
                val result = valves[next] * nextTime + solve(next, nextTime)
                activated -= next

                best = max(result, best)
            }
        }

        cache1[CacheKey1(activated.fixHashCode(), current, time,)] = best

        return best
    }

    private fun solve2(
        current1: Int,
        current2: Int,
        time1: Int,
        time2: Int
    ): Int {
        if (time1 <= 0 && time2 <= 0) return 0

        cache2[
            CacheKey2(activated.fixHashCode(), current1, current2, time1, time2)
        ]
            ?.let { return it }

        val weights1 = weights[current1]
        val weights2 = weights[current2]

        var best = 0

        for (next1 in 0 until (weights1.size - 1)) {
            if (next1 == current1) continue

            var localBest = 0

            val nextWeight1 = weights1[next1]

            for (next2 in 0 until (weights2.size - 1)) {
                if (next2 == current2) continue
                if (next1 == next2) continue

                val nextWeight2 = weights2[next2]

                if (!activated.contains(next1) && !activated.contains(next2) &&
                    time1 > 0 && time2 > 0
                ) {
                    activated += next1
                    activated += next2

                    val nextTime1 = max(0, time1 - nextWeight1 - 1)
                    val nextTime2 = max(0, time2 - nextWeight2 - 1)

                    val result = valves[next1] * nextTime1 + valves[next2] * nextTime2 +
                            solve2(next1, next2, nextTime1, nextTime2)

                    activated -= next1
                    activated -= next2

                    if (localBest / 2 > result) {
                        break
                    }

                    localBest = max(result, localBest)
                }
            }

            best = max(best, localBest)
        }

        cache2[
            CacheKey2(activated.fixHashCode(), current1, current2, time1, time2)
        ] = best

        return best
    }

    @Suppress("unused")
    private fun debug(valves: List<Valve>): String {
        val iter = valves.iterator()
        return buildString {
            append(valves.joinToString(prefix = "   ", separator = "\t") { it.name })
            append("\n")
            append(weights.joinToString(separator = "") { row ->
                iter.next().name + " " + row.joinToString(separator = "\t", postfix = "\n")
            })
            append(this@Day16.valves.joinToString(prefix = "   ", separator = "\t"))
        }
    }

    data class CacheKey1(
        val activatedHash: Int,
        val current: Int,
        val time: Int,
    )

    data class CacheKey2(
        val activatedHash: Int,
        val current1: Int,
        val current2: Int,
        val time1: Int,
        val time2: Int
    )

    data class Valve(
        val name: String,
        var value: Int,
        val next: List<String>
    )
}
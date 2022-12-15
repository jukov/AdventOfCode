package info.jukov.adventofcode.util

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun calcDistance(sX: Int, sY: Int, bX: Int, bY: Int): Int {
    val dX = delta(sX, bX)
    val dY = delta(sY, bY)

    return dX + dY
}

fun delta(a: Int, b: Int) = when {
    a >= 0 && b >= 0 -> max(a, b) - min(a, b)
    a >= 0 && b < 0 -> abs(b) + a
    a < 0 && b >= 0 -> abs(a) + b
    else -> abs(min(a, b) - max(a, b))
}
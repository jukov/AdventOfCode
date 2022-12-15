package info.jukov.adventofcode.util

import kotlin.math.max
import kotlin.math.min

fun IntRange.contains(other: IntRange) =
    contains(other.first) && contains(other.last)

fun IntRange.intersect(other: IntRange) =
    this.contains(other.first) || this.contains(other.last) ||
            other.contains(this.first) || other.contains(this.last)

fun IntRange.combine(other: IntRange): IntRange? =
    if (this.intersect(other)) {
        min(this.first, other.first)..max(this.last, other.last)
    } else {
        null
    }
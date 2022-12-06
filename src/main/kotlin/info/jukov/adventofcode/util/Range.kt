package info.jukov.adventofcode.util

fun IntRange.contains(other: IntRange) =
    contains(other.first) && contains(other.last)

fun IntRange.intersect(other: IntRange) =
    this.contains(other.first) || this.contains(other.last) ||
            other.contains(this.first) || other.contains(this.last)
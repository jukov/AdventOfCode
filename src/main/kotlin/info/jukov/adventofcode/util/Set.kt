package info.jukov.adventofcode.util

fun <E> java.util.HashSet<E>.fixHashCode(): Int {
    var hash = 17
    forEach {
        hash = hash * 31 + it.hashCode()
    }
    return hash
}
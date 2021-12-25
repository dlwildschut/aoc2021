import kotlin.math.abs

fun main() {

    fun parseCrabPositions(input: List<String>) = input.flatMap { line ->
        line.split(",").map { it.toInt() }
    }

    fun part1(input: List<String>): Int {
        val crabPositions = parseCrabPositions(input)
        val crabPositionsByCount = crabPositions.groupingBy { it }.eachCount()

        val min = crabPositions.minOrNull() ?: 0
        val max = crabPositions.maxOrNull() ?: 0

        return (min..max).minOf { crabPositionsByCount.requiredFuelToReach(it) }
    }

    fun part2(input: List<String>): Int {
        val crabPositions = parseCrabPositions(input)
        val crabPositionsByCount = crabPositions.groupingBy { it }.eachCount()

        val min = crabPositions.minOrNull() ?: 0
        val max = crabPositions.maxOrNull() ?: 0

        return (min..max).minOf { crabPositionsByCount.requiredFuelToReach2(it) }
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

private fun Map<Int, Int>.requiredFuelToReach(pos: Int): Int =
    asSequence().map { abs(it.key - pos) * it.value }.sum()

private fun Map<Int, Int>.requiredFuelToReach2(pos: Int): Int =
    asSequence().map { requiredFuelToReach2(it.key, pos) * it.value }.sum()

private fun requiredFuelToReach2(start: Int, end: Int): Int {
    val diff = abs(end - start).toDouble()
    return (diff / 2 * (diff + 1)).toInt()
}

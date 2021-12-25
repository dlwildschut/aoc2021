fun main() {

    fun countFish(initialState: List<Int>, days: Int): Long {
        val initialTotals = LongArray(9) { 0 }
        initialState.forEach { initialTotals[it] = initialTotals[it] + 1 }

        var currentTotals = initialTotals
        for (day in 1..days) {
            currentTotals = LongArray(9) {
                when (it) {
                    6 -> currentTotals[7] + currentTotals[0]
                    8 -> currentTotals[0]
                    else -> currentTotals[it + 1]
                }
            }
        }

        return currentTotals.sum()
    }

    fun parseInitialState(input: List<String>) = input.flatMap { line ->
        line.split(",").map { it.toInt() }
    }

    fun part1(input: List<String>): Long {
        val initialFish = parseInitialState(input)

        return countFish(initialFish, 80)
    }

    fun part2(input: List<String>): Long {
        val initialFish = parseInitialState(input)

        return countFish(initialFish, 256)
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934L)
    check(part2(testInput) == 26984457539L)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
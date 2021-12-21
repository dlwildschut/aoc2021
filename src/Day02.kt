fun main() {
    fun part1(input: List<String>): Int {
        val steps = input.map { it.toStep() }

        var horizontalPosition = 0
        var depth = 0

        for ((direction, units) in steps) {
            when (direction) {
                Direction.UP -> depth -= units
                Direction.DOWN -> depth += units
                Direction.FORWARD -> horizontalPosition += units
            }
        }

        return horizontalPosition * depth
    }

    fun part2(input: List<String>): Int {
        val steps = input.map { it.toStep() }

        var horizontalPosition = 0
        var depth = 0
        var aim = 0

        for ((direction, units) in steps) {
            when (direction) {
                Direction.UP -> aim -= units
                Direction.DOWN -> aim += units
                Direction.FORWARD -> {
                    horizontalPosition += units
                    depth += aim * units
                }
            }
        }

        return horizontalPosition * depth
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

private enum class Direction { UP, DOWN, FORWARD }
private data class Step(val direction: Direction, val units: Int)

private fun String.toStep() = Step(
    Direction.valueOf(substringBefore(" ").uppercase()),
    substringAfter(" ").toInt()
)
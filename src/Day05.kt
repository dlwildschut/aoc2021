import kotlin.math.sign

fun main() {

    fun parse(input: List<String>): List<Segment> {
        val inputLineRegex = """(\d+),(\d+) -> (\d+),(\d+)""".toRegex()

        return buildList {
            for (line in input) {
                val (startX, startY, endX, endY) = inputLineRegex
                    .matchEntire(line)
                    ?.destructured
                    ?: throw IllegalArgumentException("Incorrect input line $line")
                add(Segment(startX.toInt(), startY.toInt(), endX.toInt(), endY.toInt()))
            }
        }
    }

    fun part1(input: List<String>): Int {
        val segments = parse(input).filter { it.startX == it.endX || it.startY == it.endY }

        val points =
            segments.flatMap { it.points() }

        return points.groupingBy { it }.eachCount().filter { it.value > 1 }.size
    }

    fun part2(input: List<String>): Int {
        val segments = parse(input)

        val points =
            segments.flatMap { it.points() }

        return points.groupingBy { it }.eachCount().filter { it.value > 1 }.size
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

data class Segment(val startX: Int, val startY: Int, val endX: Int, val endY: Int) {
    fun points(): List<Pair<Int, Int>> = buildList {
        add(Pair(startX, startY))

        var currentX = startX
        var currentY = startY
        val dX = (endX - startX).sign
        val dY = (endY - startY).sign

        while (currentX != endX || currentY != endY) {
            currentX += dX
            currentY += dY
            add(Pair(currentX, currentY))
        }
    }
}
fun main() {

    fun part1(input: List<String>): Int {
        val points = input.map { it.map { it.digitToInt() }.toIntArray() }.points()

        // find the locations that are lower than any of its adjacent locations
        val lowest = points.filter {
            points.adjacent(it).fold(true, { result, adjacent -> result && it.height < adjacent.height })
        }

        // What is the sum of the risk levels of all low points on your heightmap
        return lowest.sumOf { it.riskLevel() }
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)

    val input = readInput("Day09")
    println(part1(input))
}

private data class Point(val x: Int, val y: Int, val height: Int)

private fun List<IntArray>.points(): List<Point> {
    return List(size * this[0].size) { index ->
        Point(index % this[0].size, index / this[0].size, this[index / this[0].size][index % this[0].size])
    }
}

private fun List<Point>.adjacent(point: Point): List<Point> {
    return this.filter {
        Pair(it.x, it.y) in listOf(
            Pair(point.x, point.y - 1),
            Pair(point.x - 1, point.y),
            Pair(point.x + 1, point.y),
            Pair(point.x, point.y + 1)
        )
    }
}

private fun Point.riskLevel(): Int {
    return 1 + height
}
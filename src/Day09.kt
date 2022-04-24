fun main() {

    fun isLowest(points: List<Point>, it: Point) =
        points.adjacent(it).fold(true, { result, adjacent -> result && it.height < adjacent.height })

    fun part1(input: List<String>): Int {
        val points = input.map { it.map { it.digitToInt() }.toIntArray() }.points()

        // find the locations that are lower than any of its adjacent locations
        val lowest = points.filter {
            isLowest(points, it)
        }

        // What is the sum of the risk levels of all low points on your heightmap
        return lowest.sumOf { it.riskLevel() }
    }


    fun part2(input: List<String>): Int {
        val points = input.map { it.map { it.digitToInt() }.toIntArray() }.points()

        // find the locations that are lower than any of its adjacent locations
        val lowest = points.filter {
            isLowest(points, it)
        }

        val basins = lowest.map { points.findBasins(it) }.sortedByDescending { it.size }

        // What do you get if you multiply together the sizes of the three largest basins?
        return basins.take(3).map { it.size }.reduce { acc, i -> acc * i }
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
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

private fun List<Point>.findBasins(initial: Point): Set<Point> {
    val visited = mutableSetOf<Point>()
    val queue = ArrayDeque<Point>()
    queue += initial
    while (queue.isNotEmpty()) {
        val current = queue.removeFirst()
        visited += current
        queue += adjacent(current).filter { it !in visited && it.height != 9 }
    }
    return visited
}
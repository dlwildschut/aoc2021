fun main() {

    fun List<String>.parse() = this.map { line ->
        line.split('|').map { it.split(" ").filter { it.isNotBlank() }.map { it.toSet() } }
    }

    fun part1(input: List<String>): Int {

        val lines = input.parse()

        return lines.sumOf { (_, outputs) ->
            outputs.map { it.size }.count { it == 2 || it == 4 || it == 3 || it == 7 }
        }
    }

    fun decode(patterns: List<Set<Char>>, outputs: List<Set<Char>>): Int {
        val digitsArray = Array<Set<Char>>(10) { setOf() }

        digitsArray[1] = patterns.first { it.size == 2 }
        digitsArray[4] = patterns.first { it.size == 4 }
        digitsArray[7] = patterns.first { it.size == 3 }
        digitsArray[8] = patterns.first { it.size == 7 }

        digitsArray[9] = patterns.first { it.size == 6 && it.containsAll(digitsArray[4]) }
        digitsArray[0] =
            patterns.first { it.size == 6 && it.containsAll(digitsArray[1]) && !it.containsAll(digitsArray[4]) }
        digitsArray[6] =
            patterns.first { it.size == 6 && it != digitsArray[0] && it != digitsArray[9] }

        digitsArray[3] =
            patterns.first { it.size == 5 && it.containsAll(digitsArray[7]) }
        digitsArray[5] =
            patterns.first { it.size == 5 && digitsArray[6].containsAll(it) }
        digitsArray[2] =
            patterns.first { it.size == 5 && it != digitsArray[3] && it != digitsArray[5] }

        val digits = digitsArray.mapIndexed { index, pattern -> pattern to index }.toMap()

        return outputs.map { digits[it] }.fold(0) { acc, i -> 10 * acc + (i ?: 0) }
    }

    fun part2(input: List<String>): Int {

        val lines = input.parse()

        return lines.sumOf { (patterns, outputs) -> decode(patterns, outputs) }
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

fun main() {
    fun part1(input: List<String>): Int {
        val gammaRate = buildString {
            for (index in input[0].indices) {
                val (ones, zeroes) = input.countBitsForIndex(index)
                val mostCommonBit = if (ones > zeroes) "1" else "0"
                append(mostCommonBit)
            }
        }
        val epsilonRate = gammaRate.invertBits()

        return gammaRate.toInt(2) * epsilonRate.toInt(2)
    }

    fun part2(input: List<String>): Int {

        fun rating(input: List<String>, type: RatingType): Int {
            var candidates = input

            for (index in input[0].indices) {
                if (candidates.size == 1)
                    break
                val (ones, zeroes) = candidates.countBitsForIndex(index)
                val filterBit = when (type) {
                    RatingType.OXYGEN -> if (ones >= zeroes) '1' else '0'
                    RatingType.CO2 -> if (ones < zeroes) '1' else '0'
                }

                candidates = candidates.filter { it[index] == filterBit }

            }
            return candidates.single().toInt(2)
        }

        val oxygenRating = rating(input, RatingType.OXYGEN)
        val co2Rating = rating(input, RatingType.CO2)

        return oxygenRating * co2Rating
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

private data class BitCount(val ones: Int, val zeroes: Int)

private fun List<String>.countBitsForIndex(index: Int): BitCount {
    var zeroes = 0
    var ones = 0
    for (line in this) {
        if (line[index] == '1') {
            ones += 1
        } else {
            zeroes += 1
        }
    }
    return BitCount(ones, zeroes)
}

private fun String.invertBits(): String {
    return this.map { if (it == '1') '0' else '1' }.joinToString("")
}

private enum class RatingType { OXYGEN, CO2 }
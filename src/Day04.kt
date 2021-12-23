fun main() {

    fun parse(input: List<String>): Pair<List<Int>, Set<BingoBoard>> {
        val bingoNumbers = input[0].split(",").map { it.toInt() }

        val boards =
            input.asSequence()
                .drop(1)
                .filter { it.isNotBlank() }
                .map { it ->
                    it.split(" ")
                        .filter { it.isNotBlank() }
                        .map { it.toInt() }
                }
                .chunked(5)
                .map { BingoBoard(it) }
                .toSet()

        return Pair(bingoNumbers, boards)
    }

    fun findWinners(
        boards: Set<BingoBoard>,
        bingoNumbers: List<Int>
    ): List<Pair<Int, BingoBoard>> {
        val mutableBoards = boards.toMutableList()

        val winners = buildList {
            for (number in bingoNumbers) {
                mutableBoards.replaceAll { it.markNumber(number) }
                val winningBoards = mutableBoards.filter { it.isComplete() }
                mutableBoards -= winningBoards.toSet()
                winningBoards.forEach { add(Pair(it.score(number), it)) }
            }
        }
        return winners
    }

    fun part1(input: List<String>): Int {
        val (bingoNumbers, boards) = parse(input)

        val winners = findWinners(boards, bingoNumbers)
        return winners.first().first
    }

    fun part2(input: List<String>): Int {
        val (bingoNumbers, boards) = parse(input)

        val winners = findWinners(boards, bingoNumbers)
        return winners.last().first
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

data class BingoBoard(val rows: List<List<Int>>) {
    fun isComplete(): Boolean {
        return this.rows.any { row -> row.all { it == -1 } }
                || this.rows.indices.any { col -> this.rows.all { it[col] == -1 } }
    }

    fun score(winningNumber: Int): Int {
        return winningNumber * rows.sumOf { row -> row.filter { it != -1 }.sum() }
    }

    fun markNumber(number: Int): BingoBoard {
        return BingoBoard(rows.map { row -> row.map { if (it == number) -1 else it } })
    }
}



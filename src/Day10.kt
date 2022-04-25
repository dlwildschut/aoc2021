fun main() {

    fun part1(input: List<String>): Int {
        // What is the total syntax error score for those errors?
        return input.sumOf { it.findCorruptedChar()?.toSyntaxErrorScore() ?: 0 }
    }

    fun part2(input: List<String>): Long {
        val scores = input.asSequence().filter { !it.isCorrupted() }.map { it.findMissingCharacters() }
            .filter { it.isNotEmpty() }
            .map { it.fold(0) { acc: Long, c: Char -> 5 * acc + c.toIncompleteScore() } }
            .sorted().toList()

        // Find the completion string for each incomplete line, score the completion strings, and sort the scores.
        // What is the middle score?
        return scores[scores.size / 2]
    }

    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}

private fun String.findCorruptedChar(): Char? {
    // A corrupted line is one where a chunk closes with the wrong character
    val stack = ArrayDeque<Char>()
    for (c in this) {
        if (c.isStartChar()) {
            stack.add(c)
        } else if (c.isEndChar()) {
            if (stack.isEmpty()) {
                throw IllegalStateException("Unexpected closing character, Stack is empty")
            }
            val start = stack.removeLast()
            if (!start.correspondsTo(c)) {
                return c
            }
        }
    }
    return null
}

private fun String.isCorrupted(): Boolean {
    return findCorruptedChar() != null
}

private fun String.findMissingCharacters(): List<Char> {
    // Incomplete lines don't have any incorrect characters - instead, they're missing some closing characters at the end of the line
    val stack = ArrayDeque<Char>()
    for (c in this) {
        if (c.isStartChar()) {
            stack.add(c)
        } else if (c.isEndChar()) {
            if (stack.isEmpty()) {
                throw IllegalStateException("Unexpected closing character, Stack is empty")
            }
            stack.removeLast()
        }
    }
    return stack.reversed().toList()
}

private fun Char.isStartChar() = this == '{' || this == '<' || this == '(' || this == '['

private fun Char.isEndChar() = this == '}' || this == '>' || this == ')' || this == ']'

private fun Char.correspondsTo(end: Char): Boolean {
    return when (this) {
        '{' -> end == '}'
        '<' -> end == '>'
        '(' -> end == ')'
        '[' -> end == ']'
        else -> false
    }
}

private fun Char.toSyntaxErrorScore(): Int {
    return when (this) {
        ')' -> 3
        ']' -> 57
        '}' -> 1197
        '>' -> 25137
        else -> 0
    }
}

private fun Char.toIncompleteScore(): Int {
    return when (this) {
        '(' -> 1
        '[' -> 2
        '{' -> 3
        '<' -> 4
        else -> 0
    }
}
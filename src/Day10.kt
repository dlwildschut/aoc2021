fun main() {

    fun part1(input: List<String>): Int {
        // What is the total syntax error score for those errors?
        return input.sumOf { it.findCorruptedChar()?.toSyntaxErrorScore() ?: 0 }
    }

    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)

    val input = readInput("Day10")
    println(part1(input))
}

private fun String.findCorruptedChar(): Char? {
    // A corrupted line is one where a chunk closes with the wrong character
    val stack = ArrayDeque<Char>()
    for (c in this) {
        if (c.isStartChar()) {
            stack.add(c)
        } else if (c.isEndChar()) {
            if (stack.isEmpty()) {
                return null
            }
            val start = stack.removeLast()
            if (!start.correspondsTo(c)) {
                return c
            }
        }
    }
    return null
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
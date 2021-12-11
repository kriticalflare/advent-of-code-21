import java.util.*

fun main() {

    fun legalPair(lhs: Char, rhs: Char): Boolean {
        return when (lhs) {
            '[', ']' -> rhs == ']' || rhs == '['
            '<', '>' -> rhs == '>' || rhs == '<'
            '{', '}' -> rhs == '}' || rhs == '{'
            '(', ')' -> rhs == ')' || rhs == '('
            else -> {
                throw Exception("Invalid LHS")
            }
        }
    }

    fun syntaxScore(token: Char): Int {
        return when (token) {
            '(', ')' -> 3
            '[', ']' -> 57
            '{', '}' -> 1197
            '<', '>' -> 25137
            else -> {
                throw Exception("Invalid Token")
            }
        }
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            val stack = Stack<Char>()
            for (char in line) {
                when (char) {
                    '[', '{', '(', '<' -> {
                        stack.push(char)
                    }
                    ']', '}', ')', '>' -> {
                        val popped = stack.pop()
                        if (!legalPair(lhs = char, rhs = popped)) {
                            // corrupted line
                            sum += syntaxScore(char)
                        }
                    }
                }
            }
        }
        return sum
    }

    fun autoCompleteScore(token: Char): Int {
        return when (token) {
            '(' -> 1
            '[' -> 2
            '{' -> 3
            '<' -> 4
            else -> throw Exception("Invalid Token")
        }
    }

    fun part2(input: List<String>): Long {
        var sumList = mutableListOf<Long>()
        for (line in input) {
            var isCorrupt = false
            val stack = Stack<Char>()
            for (char in line) {
                when (char) {
                    '[', '{', '(', '<' -> {
                        stack.push(char)
                    }
                    ']', '}', ')', '>' -> {
                        val popped = stack.pop()
                        if (!legalPair(lhs = char, rhs = popped)) {
                            // corrupted line
                            isCorrupt = true
                            stack.clear()
                            break
                        }
                    }
                }
            }
            if (!isCorrupt) {
                val sb = StringBuilder()
                var sum = 0L
                while (stack.size > 0) {
                    sb.append(stack.pop())
                }
                for (char in sb.toString()) {
                    sum *= 5
                    sum += autoCompleteScore(char)
                }
                sumList.add(sum)
            }
        }
        val midPoint = sumList.size / 2
        sumList.sort()
        return sumList[midPoint]
    }

    val input = readInput(
        "Day10"
    )
    val testInput = readInput("Day10test")
    check(part1(testInput) == 26397)
    println(part1(input))
    check(part2(testInput) == 288957L)
    println(part2(input))
}
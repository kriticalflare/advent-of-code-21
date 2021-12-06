import kotlin.text.StringBuilder

fun main() {

    fun part1(input: List<String>): Long {
        var gamma = 0L
        var epsilon = 0L
        val limit = input.first().length
        val sb = StringBuilder()
        for (i in limit - 1 downTo 0) {
            var zeros = 0
            var ones = 0
            var mask = 1L.shl(i)
            for (curr in input) {
                if (curr.toLong(2).and(mask) == 0L) {
                    zeros++
                } else {
                    ones++
                }
            }
            if (zeros > ones) {
                sb.append("0")
            } else {
                sb.append("1")
            }
        }
        gamma = sb.toString().toLong(2)
        var mask = 1L.shl(limit) - 1
        epsilon = gamma.inv().and(mask)
        return gamma * epsilon
    }


    fun part2(input: List<String>): Long {
        val o2 = o2Gen(input.toMutableList())
        val co2 = co2Scrub(input.toMutableList())
        return o2 * co2
    }

    val testInput = readInput("Day03test")
    check(part1(testInput) == 198L)
    check(part2(testInput) == 230L)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

private fun o2Gen(input: MutableList<String>): Long {
    val limit = input.first().length
    for (i in limit - 1 downTo 0) {
        var zeros = 0
        var ones = 0
        var mask = 1L.shl(i)
        for (curr in input) {
            if (curr.toLong(2).and(mask) == 0L) {
                zeros++
            } else {
                ones++
            }
        }
        if (zeros > ones) {
            input.removeIf { it.toLong(2).and(mask) != 0L }
        } else {
            input.removeIf { it.toLong(2).and(mask) == 0L }
        }
        if (input.size == 1) {
            break
        }
    }

    return input.first().toLong(2);
}

private fun co2Scrub(input: MutableList<String>): Long {
    val limit = input.first().length
    for (i in limit - 1 downTo 0) {
        var zeros = 0
        var ones = 0
        var mask = 1L.shl(i)
        for (curr in input) {
            if (curr.toLong(2).and(mask) == 0L) {
                zeros++
            } else {
                ones++
            }
        }
        if (zeros > ones) {
            input.removeIf { it.toLong(2).and(mask) == 0L }
        } else {
            input.removeIf { it.toLong(2).and(mask) != 0L }
        }
        if (input.size == 1 || (input.size == 2 && input.first() == input[1])) {
            break
        }
    }
    return input.first().toLong(2);
}
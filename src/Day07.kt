import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor

fun main() {

    fun cleanInput(input: List<String>): List<Int> {
        return input.toMutableList()
            .flatMap { it.split(",") }
            .map { it.toInt() }
            .sorted()
    }


    fun cleanInputDouble(input: List<String>): List<Double> {
        return input.toMutableList()
            .flatMap { it.split(",") }
            .map { it.toDouble() }
    }

    fun part1(input: List<String>): Int {
        val positions = cleanInput(input)
        var median: Int?
        if (positions.size % 2 == 0) {
            val p = (positions.size / 2) - 1
            median = (positions[p] + positions[p + 1]) / 2

        } else {
            val p = ((positions.size + 1) / 2) - 1
            median = positions[p]
        }
        var fuelUsed = 0
        positions.forEach {
            fuelUsed += (abs(median - it))
        }
        return fuelUsed
    }

    fun part2(input: List<String>): Long {
        val positions = cleanInputDouble(input)
        var sum = 0.0
        positions.forEach {
            sum += it
        }
        var mean = sum / positions.size
        val possibleValues = intArrayOf(floor(mean).toInt(), ceil(mean).toInt())
        var minDiff = Long.MAX_VALUE
        for (target in possibleValues) {
            var diff = 0L
            for (pos in positions) {
                val dist = abs(target - pos.toInt())
                val fuelUsed = (dist * (dist + 1)) / 2
                diff += fuelUsed
            }
            if (minDiff > diff) {
                minDiff = diff
            }
        }
        return minDiff
    }

    val input = readInput("Day07")
    val testInput = readInput("Day07test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168L)
    println(part1(input))
    println(part2(input))

}
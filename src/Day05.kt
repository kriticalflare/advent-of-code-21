import  kotlin.math.*;

fun main() {

    data class Coord(var x: Int, var y: Int)

    fun parseInput(rawInput: List<String>): List<Pair<Coord, Coord>> {
        val pInp = rawInput.toMutableList()

        val left = pInp.map {
            it.split("->")[0]
        }.map {
            val ordinals = it.trim().split(",")
            val x = ordinals[0].toInt()
            val y = ordinals[1].toInt()
            return@map Coord(x, y)
        }
        val right = pInp.map {
            it.split("->")[1]
        }.map {
            val ordinals = it.trim().split(",")
            val x = ordinals[0].toInt()
            val y = ordinals[1].toInt()
            return@map Coord(x, y)
        }

        return left.zip(right)
    }

    fun maxSize(input: List<String>): Int {
        var max = Int.MIN_VALUE
        input.toMutableList().flatMap {
            it.split("->")
        }.flatMap {
            it.split(",")
        }.map {
            it.trim().toInt()
        }.forEach {
            if (it > max) max = it
        }
        return max
    }

    fun part1(input: List<String>): Int {
        val directions = parseInput(input)
        val maxSize = maxSize(input) + 1
        val vents = Array(maxSize) { IntArray(maxSize) }
        directions.forEach { direction ->
            if (direction.first.x == direction.second.x) {
//                vertical line
                if (direction.first.y < direction.second.y) {
                    for (i in direction.first.y..direction.second.y) {
                        // ALL following matrix access is mat[col][row] coz thats how the PS has it
                        vents[i][direction.first.x]++
                    }
                } else {
                    for (i in direction.second.y..direction.first.y) {
                        vents[i][direction.first.x]++
                    }
                }
            } else if (direction.first.y == direction.second.y) {
//                horizontal line
                if (direction.first.x < direction.second.x) {
                    for (i in direction.first.x..direction.second.x) {
                        vents[direction.first.y][i]++
                    }
                } else {
                    for (i in direction.second.x..direction.first.x) {
                        vents[direction.first.y][i]++
                    }
                }
            }
        }
        var twoPoint = 0
        for (i in vents) {
            for (j in i) {
                if (j >= 2) twoPoint++
            }
        }
        return twoPoint
    }


    fun part2(input: List<String>): Int {
        val directions = parseInput(input)
        val maxSize = maxSize(input) + 1
        val vents = Array(maxSize) { IntArray(maxSize) }
        directions.forEach { direction ->
            if (direction.first.x == direction.second.x) {
//                vertical line
                if (direction.first.y < direction.second.y) {
                    for (i in direction.first.y..direction.second.y) {
                        // ALL following matrix access is mat[col][row] coz thats how the PS has it
                        vents[i][direction.first.x]++
                    }
                } else {
                    for (i in direction.second.y..direction.first.y) {
                        vents[i][direction.first.x]++
                    }
                }
            } else if (direction.first.y == direction.second.y) {
//                horizontal line
                if (direction.first.x < direction.second.x) {
                    for (i in direction.first.x..direction.second.x) {
                        vents[direction.first.y][i]++
                    }
                } else {
                    for (i in direction.second.x..direction.first.x) {
                        vents[direction.first.y][i]++
                    }
                }
            } else {
                val diff = abs(direction.second.y - direction.first.y)
                if (direction.first.y < direction.second.y && direction.first.x > direction.second.x) {
                    // upward slope
                    for (i in 0..diff) {
                        vents[direction.first.y + i][direction.first.x - i]++
                    }
                } else if (direction.first.y > direction.second.y && direction.first.x > direction.second.x) {
                    // upward slope
                    for (i in 0..diff) {
                        vents[direction.first.y - i][direction.first.x - i]++
                    }
                } else if (direction.first.x < direction.second.x && direction.first.y < direction.second.y) {
                    // downward slope
                    for (i in 0..diff) {
                        vents[direction.first.y + i][direction.first.x + i]++
                    }
                } else if (direction.first.x < direction.second.x && direction.first.y > direction.second.y) {
                    // downward slope
                    for (i in 0..diff) {
                        vents[direction.first.y - i][direction.first.x + i]++
                    }
                }

            }
        }
        var twoPoint = 0
        for (i in vents) {
            for (j in i) {
                if (j >= 2) twoPoint++
            }
        }
        return twoPoint
    }


    val input = readInput("Day05")
    val testInput = readInput("Day05test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)
    println(part1(input))
    println(part2(input))
}

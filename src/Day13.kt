fun main() {

    fun constructGrid(input: List<String>): Array<BooleanArray> {
        val coords = input.filter { it.contains(",") }
        val maxR = coords.map { it.split(",")[1].toInt() }.maxOf { it }
        val maxC = coords.map { it.split(",")[0].toInt() }.maxOf { it }
        val grid = Array(maxR + 1) {
            BooleanArray(maxC + 1) {
                false
            }
        }
        coords.forEach {
            val (i, j) = it.split(",")
            grid[j.toInt()][i.toInt()] = true
        }
        return grid
    }

    fun part1(input: List<String>): Int {
        var (axis, value) = input.first { it.contains("fold") }.split("=")
        val grid = constructGrid(input)
        var rows = grid.size - 1
        var columns = grid[0].size - 1
        axis = axis.split(" ")[2]
        if (axis == "y") {
            // horizontal divider
            for (i in rows downTo value.toInt()) {
                for (j in 0..columns) {
                    if (grid[i][j]) {
                        grid[i][j] = false
                        val mirX = value.toInt() + (value.toInt() - i)
                        val mirY = j
                        grid[mirX][mirY] = true
                    }
                }
            }
        } else {
            // vertical divider
            for (i in 0..rows) {
                for (j in columns downTo value.toInt()) {
                    if (grid[i][j]) {
                        grid[i][j] = false
                        val mirX = i
                        val mirY = value.toInt() + (value.toInt() - j)
                        grid[mirX][mirY] = true
                    }
                }
            }
        }

        var visible = 0
        for (i in 0..rows) {
            for (j in 0..columns) {
                if (grid[i][j]) {
                    visible++
                }
            }
        }
        return visible
    }

    fun part2(input: List<String>): Int {
        var visible = 0
        val folds = input.filter { it.contains("fold") }
        val grid = constructGrid(input)
        var rows = grid.size - 1
        var columns = grid[0].size - 1

        for (fold in folds) {
            var (axis, value) = fold.split("=")
            axis = axis.split(" ")[2]
            if (axis == "y") {
                // horizontal divider
                for (i in rows downTo value.toInt()) {
                    for (j in 0..columns) {
                        if (grid[i][j]) {
                            grid[i][j] = false
                            val mirX = value.toInt() + (value.toInt() - i)
                            val mirY = j
                            grid[mirX][mirY] = true
                        }
                    }
                }
                rows = value.toInt() - 1
            } else {
                // vertical divider
                for (i in 0..rows) {
                    for (j in columns downTo value.toInt()) {
                        if (grid[i][j]) {
                            grid[i][j] = false
                            val mirX = i
                            val mirY = value.toInt() + (value.toInt() - j)
                            grid[mirX][mirY] = true
                        }
                    }
                }
                columns = value.toInt() - 1
            }
        }
        for (i in 0..rows) {
            for (j in 0..columns) {
                if (grid[i][j]) {
                    print("#")
                    visible++
                } else {
                    print(" ")
                }
            }
            println()
        }
        return visible
    }

    val input = readInput(
        "Day13"
    )


    val testInput = readInput("Day13test")
    check(part1(testInput) == 17)
    println(part1(input))
    check(part2(testInput) == 16)
    println(part2(input))
}


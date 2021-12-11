import java.util.*

fun main() {
    // offsets for above, right, down, left, topRight, bottomRight, bottomLeft, topLeft of a cell
    val dirX = intArrayOf(-1, 0, 1, 0, -1, 1, 1, -1)
    val dirY = intArrayOf(0, 1, 0, -1, 1, 1, -1, -1)

    data class Cell(val x: Int, val y: Int, var value: Int)

    fun constructGrid(input: List<String>): Array<IntArray> {
        val grid = Array(input.size) { IntArray(input[0].length) }
        for ((i, r) in input.withIndex()) {
            for (j in r.indices) {
                grid[i][j] = r[j].digitToInt()
            }
        }
        for ((i, r) in input.withIndex()) {
            for (j in r.indices) {
                grid[i][j] = r[j].digitToInt()
            }
        }
        return grid
    }

    fun part1(input: List<String>, steps: Int): Int {
        val grid = constructGrid(input)
        val rows = grid.size
        val columns = grid[0].size
        var totalFlash = 0

        for (step in 0 until steps) {
            var flash = 0
            var flashed = Array(rows) { BooleanArray(columns) { false } }
            val flashQueue: Queue<Cell> = LinkedList()

            // increment all energy levels by 1
            for (i in 0 until rows) {
                for (j in 0 until columns) {
                    grid[i][j]++
                    if (grid[i][j] > 9) {
                        flashed[i][j] = true
                        flashQueue.add(Cell(x = i, y = j, value = grid[i][j]))
                    }
                }
            }

            // pop flash
            while (flashQueue.size > 0) {
                val cell = flashQueue.poll()
                for (i in dirX.indices) {
                    val nX = cell.x + dirX[i]
                    val nY = cell.y + dirY[i]
                    val legal = nX in 0 until rows && nY in 0 until columns
                    if (legal) {
                        grid[nX][nY]++
                        if (grid[nX][nY] > 9 && !flashed[nX][nY]) {
                            flashQueue.add(Cell(nX, nY, grid[nX][nY]))
                            flashed[nX][nY] = true
                        }
                    }
                }
            }

            // reset popped flashes to 0
            for (i in 0 until rows) {
                for (j in 0 until columns) {
                    if (flashed[i][j]) {
                        grid[i][j] = 0
                        flash++
                    }
                }
            }

            totalFlash += flash
        }

        return totalFlash
    }

    fun part2(input: List<String>): Int {
        val grid = constructGrid(input)
        val rows = grid.size
        val columns = grid[0].size
        var step = 1

        while (true) {
            var flash = 0
            var flashed = Array(rows) { BooleanArray(columns) { false } }
            val flashQueue: Queue<Cell> = LinkedList()

            // increment all energy levels by 1
            for (i in 0 until rows) {
                for (j in 0 until columns) {
                    grid[i][j]++
                    if (grid[i][j] > 9) {
                        flashed[i][j] = true
                        flashQueue.add(Cell(x = i, y = j, value = grid[i][j]))
                    }
                }
            }

            // pop flash
            while (flashQueue.size > 0) {
                val cell = flashQueue.poll()
                for (i in dirX.indices) {
                    val nX = cell.x + dirX[i]
                    val nY = cell.y + dirY[i]
                    val legal = nX in 0 until rows && nY in 0 until columns
                    if (legal) {
                        grid[nX][nY]++
                        if (grid[nX][nY] > 9 && !flashed[nX][nY]) {
                            flashQueue.add(Cell(nX, nY, grid[nX][nY]))
                            flashed[nX][nY] = true
                        }
                    }
                }
            }

            // reset popped flashes to 0
            for (i in 0 until rows) {
                for (j in 0 until columns) {
                    if (flashed[i][j]) {
                        grid[i][j] = 0
                        flash++
                    }
                }
            }
            if (flash == (rows * columns)) {
                break
            }
            step++
        }

        return step
    }

    val input = readInput(
        "Day11"
    )
    val testInput = readInput("Day11test")
    check(part1(testInput, 100) == 1656)
    println(part1(input, 100))
    check(part2(testInput) == 195)
    println(part2(input))
}
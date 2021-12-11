import java.util.*

fun main() {
    data class Point(val x: Int, val y: Int, val value: Int)

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

    fun getLowPoints(grid: Array<IntArray>): List<Point> {
        // changes in (X,Y) in clockwise direction ( ie to go up, right, down, left)
        val dirX = intArrayOf(-1, 0, 1, 0)
        val dirY = intArrayOf(0, 1, 0, -1)

        val rows = grid.size
        val columns = grid[0].size

        val lowPoints = mutableListOf<Point>()
        for (i in 0 until rows) {
            for (j in 0 until columns) {
                val point = grid[i][j]
                var isLowPoint = true
                for (d in 0..3) {
                    val nX = i + dirX[d]
                    val nY = j + dirY[d]
                    val legal = nX in 0 until rows && nY in 0 until columns
                    if (legal && point >= grid[nX][nY]) {
                        isLowPoint = false
                        break
                    }
                }
                if (isLowPoint) {
                    lowPoints.add(Point(x = i, y = j, value = point))
                }
            }
        }
        return lowPoints
    }

    fun part1(input: List<String>): Int {
        val grid = constructGrid(input)
        val lowPoints = getLowPoints(grid)
        return lowPoints.sumOf { it.value } + lowPoints.size
    }

    fun part2(input: List<String>): Int {
        val grid = constructGrid(input)
        val rows = grid.size
        val columns = grid[0].size
        val visitedGrid = Array(rows) { BooleanArray(columns) { false } }

        val lowPoints = getLowPoints(grid)
        val basinSizes = mutableListOf<Int>()
        for (lowPoint in lowPoints) {
            val queue: Queue<Point> = LinkedList()
            queue.add(lowPoint)
            visitedGrid[lowPoint.x][lowPoint.y] = true
            var basinSize = 0
            while (queue.size != 0) {
                val p = queue.poll()
                basinSize++
                val dirX = intArrayOf(-1, 0, 1, 0)
                val dirY = intArrayOf(0, 1, 0, -1)
                for (d in 0..3) {
                    val nX = p.x + dirX[d]
                    val nY = p.y + dirY[d]
                    val legal = nX in 0 until rows && nY in 0 until columns
                    if (legal && !visitedGrid[nX][nY] && grid[nX][nY] < 9) {
                        queue.add(Point(x = nX, y = nY, value = grid[nX][nY]))
                        visitedGrid[nX][nY] = true
                    }
                }
            }
            basinSizes.add(basinSize)
        }
        basinSizes.sortDescending()
        return basinSizes[0] * basinSizes[1] * basinSizes[2]
    }

    val input = readInput(
        "Day09"
    )


    val testInput = readInput("Day09test")
    check(part1(testInput) == 15)
    println(part1(input))
    check(part2(testInput) == 1134)
    println(part2(input))
}


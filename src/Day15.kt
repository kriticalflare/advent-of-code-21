import java.util.*
import kotlin.collections.HashSet
import kotlin.math.abs
import kotlin.math.min

fun main() {

    data class Coord(val x: Int, val y: Int)

    val dirX = intArrayOf(-1, 0, 1, 0)
    val dirY = intArrayOf(0, 1, 0, -1)

    fun constructGrid(input: List<String>): Array<IntArray> {
        val grid = Array(input.size) { IntArray(input[0].length) }
        for (i in input.indices) {
            for (j in input[0].indices) {
                grid[i][j] = input[i][j].digitToInt()
            }
        }
        return grid
    }

    fun explode(og: Array<IntArray>): Array<IntArray> {
        val times = 5
        val grid = Array(og.size * times) { IntArray(og[0].size * times) }
        val offsetY = og.size
        val offsetX = og[0].size

        for (i in grid.indices) {
            for (j in grid[0].indices) {
                if (i in og.indices && j in og[0].indices) {
                    grid[i][j] = og[i][j]
                    continue
                }
                if (j in og[0].indices) {
                    grid[i][j] = grid[i - offsetY][j] + 1
                    if (grid[i][j] > 9) {
                        grid[i][j] = 1
                    }
                    continue
                }
                grid[i][j] = grid[i][j - offsetX] + 1
                if (grid[i][j] > 9) {
                    grid[i][j] = 1
                }
            }
        }

        return grid
    }


    fun part1(input: List<String>): Int {
        val grid = constructGrid(input)

        val dist = Array(grid.size) { IntArray(grid[0].size) { Int.MAX_VALUE } }
        dist[0][0] = 0

        val rows = grid.size
        val columns = grid[0].size

        val compareCoord: Comparator<Coord> = compareBy { dist[it.x][it.y] }
        val pq = PriorityQueue(compareCoord)
        pq.add(Coord(0, 0))

        val sptSet = HashSet<Coord>()

        while (sptSet.size != rows * columns) {
            val u = pq.poll()
            sptSet.add(u)

            for (i in dirX.indices) {
                val nX = u.x + dirX[i]
                val nY = u.y + dirY[i]
                val legal = nX in 0 until rows && nY in 0 until columns
                if (legal && !sptSet.contains(
                        Coord(
                            nX, nY
                        )
                    ) && dist[u.x][u.y] != Int.MAX_VALUE && dist[u.x][u.y] + grid[nX][nY] < dist[nX][nY]
                ) {
                    dist[nX][nY] = dist[u.x][u.y] + grid[nX][nY]
                    pq.add(Coord(nX, nY))
                }
            }
        }
        return dist[rows - 1][columns - 1]
    }


    fun part2(input: List<String>): Int {
        val grid = explode(constructGrid(input))

        val dist = Array(grid.size) { IntArray(grid[0].size) { Int.MAX_VALUE } }
        dist[0][0] = 0

        val rows = grid.size
        val columns = grid[0].size

        val compareCoord: Comparator<Coord> = compareBy { dist[it.x][it.y] }
        val pq = PriorityQueue(compareCoord)
        pq.add(Coord(0, 0))

        val sptSet = HashSet<Coord>()

        while (sptSet.size != rows * columns) {
            val u = pq.poll()
            sptSet.add(u)

            for (i in dirX.indices) {
                val nX = u.x + dirX[i]
                val nY = u.y + dirY[i]
                val legal = nX in 0 until rows && nY in 0 until columns
                if (legal && !sptSet.contains(
                        Coord(
                            nX, nY
                        )
                    ) && dist[u.x][u.y] != Int.MAX_VALUE && dist[u.x][u.y] + grid[nX][nY] < dist[nX][nY]
                ) {
                    dist[nX][nY] = dist[u.x][u.y] + grid[nX][nY]
                    pq.add(Coord(nX, nY))
                }
            }
        }
        return dist[rows - 1][columns - 1]
    }

    val input = readInput(
        "Day15"
    )


    val testInput = readInput("Day15test")
    val testInput2 = readInput("Day15test2")
    check(part1(testInput) == 40)
    println(part1(input))
    println(part1(testInput2))
    check(part2(testInput) == 315)
    println(part2(input))
}




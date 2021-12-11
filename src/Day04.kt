fun main() {

    data class Cell(var value: Int, var marked: Boolean)

    fun cleanInput(input: List<String>): List<String> {
        val cInp = input.toMutableList()
        cInp.removeAt(0)
        cInp.removeIf { it.isEmpty() }
        return cInp
    }

    fun unmarkedSum(x: Int, bingoSet: Array<Array<Cell>>): Int {
        var sum = 0
        var startRow = (x / 5) * 5
        for (i in startRow until startRow + 5) {
            for (j in 0 until 5) {
                if (!bingoSet[i][j].marked) {
                    sum += bingoSet[i][j].value
                }
            }
        }
        return sum
    }


    fun createBingoSet(cInp: List<String>): Array<Array<Cell>> {
        val bingoSet = Array(cInp.size) { Array(5) { Cell(-1, false) } }
        for (i in cInp.indices) {
            val row = cInp[i].split(" ").toMutableList()
            row.removeIf { it.isEmpty() }
            for ((idx, ele) in row.withIndex()) {
                bingoSet[i][idx] = Cell(value = ele.toInt(), marked = false)
            }
        }
        return bingoSet
    }

    fun part1(input: List<String>): Int {
        val bingoInput = input[0].split(",").map { it.toInt() }
        val cInp = cleanInput(input)
        val bingoSet = createBingoSet(cInp)
        var boardFound = false
        var x = -1
        var y = -1
        var winNumber = -1
        for (ele in bingoInput) {
            if (boardFound) {
                break
            }
            for (i in bingoSet.indices) {
                for (j in 0 until 5) {
                    if (bingoSet[i][j].value == ele) {
                        bingoSet[i][j].marked = true

                        // check row and column of the cell for bingo
                        // check row
                        var rowB = true
                        for (c in 0 until 5) {
                            if (!bingoSet[i][c].marked) {
                                rowB = false
                            }
                        }
                        // check column
                        var colB = true
                        var startIdx = (i / 5) * 5
                        for (r in startIdx until startIdx + 5) {
                            if (!bingoSet[r][j].marked) {
                                colB = false
                            }
                        }
                        if (rowB || colB) {
                            boardFound = true
                            x = i
                            y = j
                            winNumber = ele
                        }
                    }
                }
            }
        }

        var sum = unmarkedSum(x, bingoSet)

        return sum * winNumber
    }

    fun part2(input: List<String>): Int {
        val bingoInput = input[0].split(",").map { it.toInt() }
        val cInp = cleanInput(input)
        val noOfBoards = cInp.size / 5
        val bingoSet = createBingoSet(cInp)
        var boards = BooleanArray(noOfBoards) { false }
        var x = -1
        var y = -1
        var winNumber = -1
        for (ele in bingoInput) {
            if (boards.all { it }) {
                break
            }
            for (i in bingoSet.indices) {
                for (j in 0 until 5) {
                    if (bingoSet[i][j].value == ele) {
                        bingoSet[i][j].marked = true

                        // check row and column of the cell for bingo
                        // check row
                        var rowB = true
                        for (c in 0 until 5) {
                            if (!bingoSet[i][c].marked) {
                                rowB = false
                            }
                        }
                        // check column
                        var colB = true
                        var startIdx = (i / 5) * 5
                        for (r in startIdx until startIdx + 5) {
                            if (!bingoSet[r][j].marked) {
                                colB = false
                            }
                        }
                        if (rowB || colB) {
                            val boardNo = i / 5
                            if (!boards[boardNo]) {
                                boards[i / 5] = true
                                x = i
                                y = j
                                winNumber = ele
                            }
                        }
                    }
                }
            }
        }

        var sum = unmarkedSum(x, bingoSet)
        return sum * winNumber;
    }

    val input = readInput("Day04")
    val testInput = readInput("Day04test")
    check(part2(testInput) == 1924)
    println(part1(input))
    println(part2(input))

}

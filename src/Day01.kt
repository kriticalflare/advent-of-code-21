fun main() {

    fun part1(input: List<Int>): Int {
        var increase = 0
        for (curr in input.indices) {
            if (curr != 0) {
                val prev = curr - 1
                if (input[curr] > input[prev]) {
                    increase++
                }
            }
        }
        return increase
    }

    fun part2(input: List<Int>): Int {
        var window = 0
        var increases = 0
        for (i in 0..2) {
            window += input[i]
        }
        for (i in 3 until input.size) {
            val prev = window
            val curr = window - input[i-3] + input[i]
            if(curr > prev){
                increases++
            }
        }
        return increases
    }

    val input = readInt("Day01")
    println(part1(input))
    println(part2(input))
}

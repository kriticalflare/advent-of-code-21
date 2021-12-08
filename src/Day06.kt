fun main() {


    fun cleanInput(input: List<String>): List<Int> {
        return input.flatMap { it.split(",") }.map { it.toInt() }
    }

    fun part1(input: List<String>, days: Int): Int {
        val fishes = cleanInput(input).toMutableList()
        for (currDay in 0 until days) {
            var newAdd = 0
            for ((idx, fish) in fishes.withIndex()) {
                if (fish != 0) {
                    fishes[idx]--
                } else {
                    fishes[idx] = 6
                    newAdd++
                }
            }
            for (i in 0 until newAdd) {
                fishes.add(8)
            }
        }
        return fishes.size
    }

    fun part2(input: List<String>, days: Int): Long {
        val fishes = cleanInput(input).toMutableList()
        var size = 0L
        val noOfFishAtTimerStage = LongArray(9)
        fishes.forEach {
            noOfFishAtTimerStage[it]++
        }
        for (currDay in 0 until days) {
            // rotate array left
            val day0 = noOfFishAtTimerStage[0]
            for (i in 1 until 9) {
                noOfFishAtTimerStage[i - 1] = noOfFishAtTimerStage[i]
            }
            // add newborns from day 0 to day 8 and their parents to day 6
            noOfFishAtTimerStage[6] += day0
            noOfFishAtTimerStage[8] = day0
        }
        for (i in 0..8) {
            size += noOfFishAtTimerStage[i]
        }
        return size
    }

    val input = readInput(
        "Day06"
    )
    val testInput = readInput("Day06test")
    check(part1(testInput, 18) == 26)
    check(part1(testInput, 80) == 5934)
    check(part2(testInput, 18) == 26L)
    check(part2(testInput, 80) == 5934L)
    check(part2(testInput, 256) == 26984457539L)
    println(part1(input, 80))
    println(part2(input, 256))

}
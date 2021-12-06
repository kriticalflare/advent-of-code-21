fun main() {

    fun part1(input: List<String>): Int {
        var position = 0
        var depth = 0
        for (curr in input) {
            val (command, magnitude) = curr.split(" ")
            when(command){
                "forward" -> position += magnitude.toInt()
                "up" -> depth -= magnitude.toInt()
                "down" -> depth += magnitude.toInt()
            }
        }
        return position * depth
    }

    fun part2(input: List<String>): Int {
        var position = 0
        var depth = 0
        var aim = 0
        for (curr in input) {
            val (command, magnitude) = curr.split(" ")
            when(command){
                "forward" -> {
                    position += magnitude.toInt()
                    depth += (aim * magnitude.toInt())
                }
                "up" -> aim -= magnitude.toInt()
                "down" -> aim += magnitude.toInt()
            }
        }
        return position * depth
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

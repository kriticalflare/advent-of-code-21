import java.util.*
import kotlin.collections.HashMap

fun main() {

    fun part1(input: List<String>, steps: Int): Long {
        var template = input[0]
        var rules = input.filter { it.contains("->") }
        val sb = StringBuilder(template)
        val ruleMap = HashMap<String, String>()
        for (rule in rules) {
            var (pattern, out) = rule.split(" -> ")
            ruleMap[pattern] = out
        }
        for (step in 0 until steps) {
            val prev = sb.toString()
            var prevCounter = 0
            var sbCounter = 0
            while (prevCounter < prev.length - 1) {
                val slice = prev.substring(prevCounter, prevCounter + 2)
                if (ruleMap.containsKey(slice)) {
                    sb.insert(++sbCounter, ruleMap[slice])
                }
                sbCounter++
                prevCounter++
            }
        }
        val out = sb.toString()
        val alphabets = HashMap<Char, Long>()
        for (char in out) {
            alphabets[char] = alphabets.getOrDefault(char, 0) + 1
        }
        var max = Long.MIN_VALUE
        var min = Long.MAX_VALUE
        for ((_, value) in alphabets) {
            if (value > max) {
                max = value
            }
            if (value < min) {
                min = value
            }
        }
        return max - min
    }

    fun part2(input: List<String>, steps: Int): Long {
        var template = input[0]
        val start = template.first()
        val end = template.last()
        var rules = input.filter { it.contains("->") }
        val ruleMap = HashMap<String, Pair<String, String>>()
        for (rule in rules) {
            var (pattern, out) = rule.split(" -> ")
            ruleMap[pattern] = Pair(pattern[0] + out, out + pattern[1])
        }
        val charCount = HashMap<Char, Long>()
        val duoMap = HashMap<String, Long>()
        for (i in 0 until template.length - 1) {
            val duo = template.substring(i, i + 2)
            duoMap[duo] = duoMap.getOrDefault(duo, 0) + 1
        }

        for (step in 0 until steps) {
            val queue: Queue<Pair<String, Long>> = LinkedList()
            for (duo in duoMap) {
                if (duo.value > 0) {
                    queue.add(Pair(duo.key, duo.value))
                }
            }
            while (queue.isNotEmpty()) {
                val duo = queue.poll()
                duoMap[duo.first] = duoMap.getOrDefault(duo.first, 1) - duo.second
                val (out1, out2) = ruleMap[duo.first]!!
                duoMap[out1] = duoMap.getOrDefault(out1, 0) + duo.second
                duoMap[out2] = duoMap.getOrDefault(out2, 0) + duo.second
            }
        }
        for (duo in duoMap) {
            val firstChar = duo.key[0]
            val secondChar = duo.key[1]
            charCount[firstChar] = charCount.getOrDefault(firstChar, 0) + duo.value
            charCount[secondChar] = charCount.getOrDefault(secondChar, 0) + duo.value
        }
        charCount.forEach {
            if (it.key == start || it.key == end) {
                charCount[it.key] = ((it.value - 1) / 2) + 1
            } else {
                charCount[it.key] = it.value / 2
            }
        }
        var maxChar = Long.MIN_VALUE
        var minChar = Long.MAX_VALUE
        for ((_, value) in charCount) {
            if (value > maxChar) {
                maxChar = value
            }
            if (value < minChar) {
                minChar = value
            }
        }
        return maxChar - minChar
    }

    val input = readInput(
        "Day14"
    )


    val testInput = readInput("Day14test")
    check(part1(testInput, 10) == 1588L)
    println(part1(input, 10))
    check(part2(testInput, 10) == 1588L)
    check(part2(testInput, 40) == 2188189693529)
    println(part2(input, 40))
}


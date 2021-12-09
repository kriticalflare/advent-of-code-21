import kotlin.math.pow

fun main() {

    fun part1(input: List<String>): Int {
        val outputs = input.map {
            it.split("|")[1]
        }.flatMap {
            it.split(" ")
        }
        var uniqueSegments = 0
        outputs.forEach {
            when (it.trim().length) {
                2, 3, 4, 7 -> {
                    uniqueSegments++
                }
            }
        }
        return uniqueSegments
    }


    fun parseInput(input: List<String>): List<Pair<String, String>> {
        return input.map {
            val (l, r) = it.split("|")
            Pair(l, r)
        }
    }

    fun part2(input: List<String>): Int {
        val parsedInput = parseInput(input)
        var sum = 0
        for (inp in parsedInput) {

            val encounteredChar = BooleanArray(7) { false }

            val scrambled = inp.first.split(" ").toMutableList()
            scrambled.removeIf { it.isEmpty() }
            val scrambledOutputs = inp.second.split(" ").toMutableList()
            scrambledOutputs.removeIf { it.isEmpty() }

            // step 1 find probables for representing '1'
            val probable1 = HashSet<Char>()
            scrambled.forEach {
                if (it.length == 2) {
                    it.toCharArray().forEach { c ->
                        probable1.add(c)
                        // to mark these chars as visited atleast once
                        encounteredChar[c - 'a'] = true
                    }
                }
            }

            // step 2 find confirmed for slot i
            // find scrambled with len 3
            // and take the Complement of intersection of set from step 1 and aforementioned set
            var i = ' '
            val probable7 = HashSet<Char>()
            scrambled.forEach {
                if (it.length == 3) {
                    it.toCharArray().forEach { c ->
                        if (!probable1.contains(c)) {
                            i = c
                            encounteredChar[c - 'a'] = true
                        }
                        probable7.add(c)
                    }
                }
            }

            // step 3 find scrambled with len 4 and find probables for slot vi and vii
            val probable4 = HashSet<Char>()
            scrambled.forEach {
                if (it.length == 4) {
                    it.toCharArray().forEach { c ->
                        probable4.add(c)
                        encounteredChar[c - 'a'] = true
                    }
                }
            }

            // step 4 find encoded string for "9"
            // need to find a string with length 6 which has all
            // 5 of the encountered chars until now and 1 un-encountered char
            // which will be slot iv confirm

            var iv = ' '
            scrambled.forEach {
                if (it.length == 6) {
                    if (it.toHashSet().intersect(probable7.union(probable4)).size == 5) {
                        it.forEach { c ->
                            if (!probable7.union(probable4).contains(c)) {
                                iv = c
                                encounteredChar[c - 'a'] = true
                            }
                        }
                    }
                }
            }

            // step 5 the one char we havent encountered yet is the slot v confirmed
            var v = ' '
            for ((idx, b) in encounteredChar.withIndex()) {
                if (!b) {
                    v = 'a' + idx
                }
            }

            // step 6 find a len 5 word with
            // exactly 1 char each from probable1 and probable4
            // this would give slots ii and vii
            // which would inturn confirm slots iii and vi

            var ii = ' '
            var vii = ' '
            var iii = ' '
            var vi = ' '

            scrambled.forEach {
                if (it.length == 5) {
                    val set = it.toHashSet()
                    if (set.intersect(probable1).size == 1 &&
                        set.intersect(probable4.subtract(probable1)).size == 1
                    ) {
                        set.intersect(probable1).forEach { c ->
                            ii = c
                        }
                        set.intersect(probable4.subtract(probable1)).forEach { c ->
                            vii = c
                        }
                        probable1.forEach { c ->
                            if (c != ii) {
                                iii = c
                            }
                        }
                        probable4.subtract(probable1).forEach { c ->
                            if (c != vii) {
                                vi = c
                            }
                        }
                    }
                }
            }

            // step 7 create sets corresponding to 0 .. 9

            val zero = hashSetOf(i, ii, iii, iv, v, vi)
            val one = hashSetOf(ii, iii)
            val two = hashSetOf(i, ii, vii, v, iv)
            val three = hashSetOf(i, ii, vii, iii, iv)
            val four = hashSetOf(vi, vii, ii, iii)
            val five = hashSetOf(i, vi, vii, iii, iv)
            val six = hashSetOf(i, vi, vii, iii, iv, v)
            val seven = hashSetOf(i, ii, iii)
            val eight = hashSetOf(i, ii, iii, iv, v, vi, vii)
            val nine = hashSetOf(vii, vi, i, ii, iii, iv)

            val digits = listOf(zero, one, two, three, four, five, six, seven, eight, nine)

            // step 8
            var numericalValue = 0
            scrambledOutputs.forEachIndexed { index, out ->
                val outHash = out.toHashSet()
                var digit = 0
                digits.forEachIndexed { idx, d ->
                    if (outHash == d) {
                        digit = idx
                    }
                }
                numericalValue += (digit * (10.0.pow(3 - index)).toInt())
            }

            // step 9 add every numerical value to sum
            sum += numericalValue
        }
        return sum
    }


    val input = readInput("Day08")
    val testInput = readInput("Day08test")
    val testInput2 = readInput("Day08test2")
    check(part1(testInput) == 26)
    check(part2(testInput2) == 5353)
    check(part2(testInput) == 61229)
    println(part1(input))
    println(part2(input))
}
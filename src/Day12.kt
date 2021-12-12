import kotlin.collections.ArrayList
import kotlin.collections.HashMap

fun main() {


    data class Node(val position: Int, val label: String)

    fun dfsPart1(
        nodes: HashMap<String, Int>, visitedCount: IntArray, graph: ArrayList<ArrayList<Node>>, root: String
    ): Int {
        var paths = 0
        if (root == "end") {
            return 1
        }
        visitedCount[nodes[root]!!]++
        for (adj in graph[nodes[root]!!]) {
            if (adj.label[0].isLowerCase() && visitedCount[nodes[adj.label]!!] < 1) {
                paths += dfsPart1(nodes, visitedCount.clone(), graph, adj.label)
            } else if (adj.label[0].isUpperCase()) {
                paths += dfsPart1(nodes, visitedCount.clone(), graph, adj.label)
            }
        }
        return paths
    }

    fun constructNodes(input: List<String>): HashMap<String, Int> {
        var nodeCount = 0
        val nodes = HashMap<String, Int>()
        input.flatMap {
            it.split("-")
        }.forEach {
            if (!nodes.containsKey(it)) {
                nodes[it] = nodeCount
                nodeCount++
            }
        }
        return nodes
    }

    fun constructGraph(input: List<String>, nodes: HashMap<String, Int>): ArrayList<ArrayList<Node>> {
        val graph = ArrayList<ArrayList<Node>>()
        val nodeCount = nodes.size
        for (i in 0..nodeCount) {
            graph.add(ArrayList())
        }
        input.forEach { edge ->
            val (src, dst) = edge.split("-")
            graph[nodes[src]!!].add(Node(position = nodes[dst]!!, label = dst))
            graph[nodes[dst]!!].add(Node(position = nodes[src]!!, label = src))
        }
        return graph
    }

    fun part1(input: List<String>): Int {

        val nodes = constructNodes(input)
        val nodeCount = nodes.size
        val graph = constructGraph(input, nodes)
        val visitedCount = IntArray(nodeCount + 1) { 0 }

        return dfsPart1(nodes, visitedCount, graph, "start")
    }


    fun dfsPart2(
        nodes: HashMap<String, Int>, visitedCount: IntArray, graph: ArrayList<ArrayList<Node>>, root: String
    ): Int {
        var paths = 0
        if (root == "end") {
            return 1
        }
        visitedCount[nodes[root]!!]++
        for (adj in graph[nodes[root]!!]) {
            if (adj.label == "start") {
                continue
            }
            if (adj.label[0].isLowerCase()) {
                // cases

                val lowerCaseVisitedTwice = nodes.any {
                    it.key[0].isLowerCase() && visitedCount[it.value] > 1
                }
                // case1  no valid lowercase has visitedCount more than 1
                // case2  1 lowercase has visitedCount 2
                if (lowerCaseVisitedTwice) {
                    if (visitedCount[adj.position] < 1) {
                        paths += dfsPart2(nodes, visitedCount.clone(), graph, adj.label)
                    }
                } else {
                    if (visitedCount[adj.position] < 2) {
                        paths += dfsPart2(nodes, visitedCount.clone(), graph, adj.label)
                    }
                }
            }
            if (adj.label[0].isUpperCase()) {
                paths += dfsPart2(nodes, visitedCount.clone(), graph, adj.label)
            }
        }
        return paths
    }

    fun part2(input: List<String>): Int {
        val nodes = constructNodes(input)
        val nodeCount = nodes.size
        val graph = constructGraph(input, nodes)
        val visitedCount = IntArray(nodeCount) { 0 }

        return dfsPart2(nodes, visitedCount, graph, "start")
    }

    val input = readInput(
        "Day12"
    )


    val testInput1 = readInput("Day12test1")
    val testInput2 = readInput("Day12test2")
    val testInput3 = readInput("Day12test3")
    check(part1(testInput1) == 10)
    check(part1(testInput2) == 19)
    check(part1(testInput3) == 226)
    println(part1(input))
    check(part2(testInput1) == 36)
    check(part2(testInput2) == 103)
    check(part2(testInput3) == 3509)
    println(part2(input))
}


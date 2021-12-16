import java.io.File
import java.util.*

fun main() {
    val lines: List<String> = File("day15.txt").readLines()

    val expandedStr = expandList(lines)

    println(expandedStr.joinToString("\n"))

    data class Node(val coord: Pair<Int, Int>, val cost: Int) : Comparable<Node> {
        val row = coord.first
        val col = coord.second
        override fun compareTo(other: Node): Int {
            return this.cost - other.cost
        }
    }

    val expanded: List<List<Int>> = expandedStr.map { s -> s.toCharArray().map { c -> c.toString().toInt() } }

    val visited = mutableSetOf<Pair<Int, Int>>()

    val minCosts: MutableList<MutableList<Node>> = expanded.mapIndexed { row, ints ->
        List(ints.size) { col ->
            Node(row to col, Integer.MAX_VALUE)
        }.toMutableList()
    }.toMutableList()

    fun List<List<Int>>.neighborsToQueue(node: Node, pq: PriorityQueue<Node>) {
        val neighbours = listOf(
            node.row to node.col + 1, // Right
            node.row + 1 to node.col, // Down
            node.row - 1 to node.col, // Up
            node.row to node.col - 1  // Left
        )
        for ((row, col) in neighbours) {
            getOrNull(row)?.getOrNull(col)?.let { neighbourCost ->
                val totalCost = node.cost + neighbourCost
                if (totalCost < minCosts[row][col].cost) {
                    minCosts[row][col] = minCosts[row][col].copy(cost = totalCost)

                    if (!visited.contains(row to col)) {
                        pq.add(Node(row to col, minCosts[row][col].cost))
                    }
                }
            } ?: println("$row,$col outside range")
        }
    }

    minCosts[0][0] = minCosts[0][0].copy(cost = 0)

    val pq = PriorityQueue<Node>()
    pq.add(Node(0 to 0, 0))

    while (pq.isNotEmpty()) {

        // Remove the node with the least cost
        val node = pq.remove()

        if (visited.contains(node.coord))
            continue
        visited.add(node.coord)

        expanded.neighborsToQueue(node, pq)
    }

    println("Min cost = ${minCosts[expanded.size - 1][expanded.first().size - 1]}")

}

private fun expandList(lines: List<String>): MutableList<String> {
    fun Int.incWrap(): Int {
        val newVal = this + 1
        return if (newVal == 10) 1 else newVal
    }

    fun Int.incTimes(times: Int): Int {
        var newValue = this
        for (i in 0 until times) newValue = newValue.incWrap()
        return newValue
    }

    val newLines = mutableListOf<String>()

    val times = 5
    for (row in 0 until times) {
        newLines.addAll(lines.map { line ->
            // Map original string (char array) in row and column direction
            val charArray = line.toCharArray()
            val sb = StringBuilder()
            for (col in 0 until times) {
                val newString = charArray.map {
                    it.toString().toInt().incTimes(row + col)
                }.joinToString("")
                sb.append(newString)
            }
            sb.toString()
        })
    }
    return newLines
}

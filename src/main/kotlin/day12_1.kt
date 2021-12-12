import java.io.File

fun String.isLower() = this == this.toLowerCase()
fun String.isUpper() = this == this.toUpperCase()

class Day12(fileName: String) {
    data class Node(
        val name: String,
        val next: MutableList<String> = mutableListOf(),
        val prev: MutableList<String> = mutableListOf()
    ) {
        override fun toString(): String = this.name
    }

    val nodes = mutableMapOf<String, Node>()
    private fun getNode(curr: String) = nodes.getOrPut(curr) { Node(curr) }

    operator fun get(str: String) = nodes[str]!!

    fun startNode() = nodes["start"]!!

    private val lines = File(fileName).readLines()

    init {
        lines.forEach {
            val (curr, next) = it.split("-")
            val leftNode = getNode(curr)
            val toNode = getNode(next)
            leftNode.next.add(toNode.name)
            if (leftNode.name != "start" && toNode.name != "end") {
                toNode.prev.add(leftNode.name)
            }
        }
        println(nodes.entries.joinToString("\n"))
    }
}

fun main() {
    val day12 = Day12("day12.txt")

    fun canVisit(nodeName: String, visitedNode: MutableList<String>): Boolean =
        nodeName == "end" || nodeName.isUpper() || !visitedNode.contains(nodeName)

    fun visit(
        node: Day12.Node,
        visitedNodes: MutableList<String>,
        onResult: (MutableList<String>) -> Unit
    ) {
        if (!canVisit(node.name, visitedNodes)) {
            return
        }
        visitedNodes.add(node.name)
        if (node.name == "end") {
            onResult(visitedNodes)
            return
        } else {
            node.next.forEach {
                visit(day12[it], visitedNodes.toMutableList(), onResult)
            }
            node.prev.forEach {
                visit(day12[it], visitedNodes.toMutableList(), onResult)
            }
        }
    }

    val startNode = day12.startNode()
    var count = 0
    startNode.prev.forEach {
        val visitedNodes = mutableListOf<String>().apply { add("start") }
        visit(day12[it], visitedNodes) {
            count++
        }
    }
    startNode.next.forEach {
        val visitedNodes = mutableListOf<String>().apply { add("start") }
        visit(day12[it], visitedNodes) {
            count++
        }
    }
    println("Found $count paths")
}

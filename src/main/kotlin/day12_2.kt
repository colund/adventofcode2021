fun main() {
    val day12 = Day12("day12.txt")

    fun canVisit(nodeName: String, visitedNode: MutableList<String>): Boolean {
        if (nodeName == "start") return false
        return nodeName == "end" || nodeName.isUpper() ||
                !visitedNode.contains(nodeName) ||
                (visitedNode.count { it == nodeName } == 1 &&
                        (visitedNode.filter { it.isLower() }.groupingBy { it }.eachCount().values.maxOrNull() ?: 0) < 2)
    }

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
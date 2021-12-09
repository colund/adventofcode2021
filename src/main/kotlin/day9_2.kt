import java.io.File

fun main() {
    val lines = File("day9.txt").readLines() // array of strings per line
    val numbers: List<List<Int>> = lines.map { line -> line.toCharArray().map { it.toString().toInt() } }

    val visitedCoords = mutableListOf<Pair<Int, Int>>()

    fun visitCoord(row: Int, col: Int, list: MutableList<Int>) {
        val num = numbers.getOrNull(row)?.getOrNull(col) ?: return
        if (visitedCoords.contains(row to col)) {
            return
        }
        visitedCoords.add(row to col)
        if (num != 9) {
            list.add(num)
            visitCoord(row - 1, col, list)
            visitCoord(row, col + 1, list)
            visitCoord(row, col - 1, list)
            visitCoord(row + 1, col, list)
        }
    }

    val list = mutableListOf<List<Int>>()

    for (i in numbers.indices) {
        for (j in numbers[i].indices) {
            val list2 = mutableListOf<Int>()
            visitCoord(i, j, list2)
            if (list2.isNotEmpty()) {
                list.add(list2)
            }
        }
    }

    // Results
    println(list.map { it.size }.sortedDescending().take(3).foldRight(1) { i, acc -> i * acc })
}
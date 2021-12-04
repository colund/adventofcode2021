import java.io.File

val day4lines = File("day4.txt").readLines()

fun main() {
    val input = day4lines[0].split(",").map { it.toInt() }

    val tables = mutableListOf<List<Int>>()
    val markedPositions = mutableListOf<MutableSet<Int>>()

    var current = mutableListOf<Int>()

    for (i in 2 until day4lines.size) {
        val currentLine = day4lines[i]
        if (currentLine.isEmpty()) {
            continue
        }

        val regex = """[^\d]*(\d+)[^\d]+(\d+)[^\d]+(\d+)[^\d]+(\d+)[^\d]+(\d+)""".toRegex().matchEntire(currentLine)
        val numbers = regex?.groupValues?.takeLast(5)?.map { it.toInt() } ?: continue
        current.addAll(numbers)

        if (current.size == 25) {
            tables.add(current)
            current = mutableListOf()
            markedPositions.add(mutableSetOf())
        }
    }

    for (i in input) {
        for (t in tables.indices) {
            val index = tables[t].indexOf(i)
            if (index >= 0) {
                markedPositions[t].add(index)
            }
            if (hasBingo(markedPositions[t])) {
                // First table to win
                val sum = tables[t].filterIndexed { idx, _ -> !markedPositions[t].contains(idx) }.sum()
                val score = i * sum
                println(score) // Result <-------------------------
                return
            }
        }
    }
}


val bingoLines = listOf(
    (0 until 5).toSet(),
    (5 until 10).toSet(),
    (10 until 15).toSet(),
    (15 until 20).toSet(),
    (20 until 25).toSet(),
    (0 until 20 step 5).toSet(),
    (1 until 22 step 5).toSet(),
    (2 until 23 step 5).toSet(),
    (3 until 24 step 5).toSet(),
    (4 until 25 step 5).toSet(),
)

fun hasBingo(markedPositions: Set<Int>): Boolean {
    return bingoLines.any { markedPositions.containsAll(it) }
}

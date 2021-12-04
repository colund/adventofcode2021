fun main() {
    val input = day4lines[0].split(",").map { it.toInt() }

    val tables = mutableListOf<List<Int>>()
    val markedPositions = mutableListOf<MutableSet<Int>>()

    var current = mutableListOf<Int>()

    val bingoTables = mutableSetOf<Int>()

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
            if (t in bingoTables) continue

            val index = tables[t].indexOf(i)
            if (index >= 0) {
                markedPositions[t].add(index)
            }
            if (hasBingo(markedPositions[t])) {
                val sum = tables[t].filterIndexed { idx, _ -> !markedPositions[t].contains(idx) }.sum()
                val score = i * sum
                bingoTables.add(t)
                if (bingoTables.size == tables.size) {
                    println(score) // Result <-------------------------
                    return
                }
            }
        }
    }
}

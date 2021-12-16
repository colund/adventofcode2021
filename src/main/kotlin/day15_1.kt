import java.io.File

fun main() {
    val lines = File("day15.txt").readLines()

    val risks = mutableListOf<Int>()

    var minResult = Int.MAX_VALUE
    lines.walkDay15Recursion(0, 0, risks) {
        minResult = minResult.coerceAtMost(it)
    }

    println("Min result=$minResult")
}

internal val minByCoord: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()

fun List<String>.walkDay15Recursion(row: Int, col: Int, risk: MutableList<Int>,
                                    result: (Int) -> Unit
) {
    val currentRisk = this.getOrNull(row)?.getOrNull(col)?.toString()?.toInt() ?: return

    val lastElement = row == size - 1 && col == first().length - 1
    if (lastElement) {
        val firstRisk = this[0][0].toString().toInt()
        val sum = currentRisk - firstRisk
        val total = risk.fold(sum) { acc, i -> acc + i }
        result(total)
    } else  {

        val currMinAtCoord = minByCoord[row to col]
        val sum = risk.sum()
        if (currMinAtCoord != null && currMinAtCoord <= sum) return println("$row,$col Not smaller so not progressing")
        minByCoord[row to col] = sum

        risk.add(currentRisk)

        // down
        this@walkDay15Recursion.walkDay15Recursion(row + 1, col, risk.toMutableList(), result)

        // right
        this@walkDay15Recursion.walkDay15Recursion(row, col + 1, risk.toMutableList(), result)

        // left
        this@walkDay15Recursion.walkDay15Recursion(row, col - 1, risk.toMutableList(), result)
    }

}

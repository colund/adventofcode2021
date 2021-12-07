import java.io.File
import kotlin.math.abs

fun main() {
    val day6 = File("day7.txt").readText().split(",").map { it.toInt() }
    val costMap = cost(day6) { v0, v1 -> abs(v0 - v1) }
    val lowestCost = costMap.values.minOrNull()
    println(lowestCost)
}

fun cost(day6: List<Int>, costFn: (Int, Int) -> Int): MutableMap<Int, Int> {
    val sortedSet = day6.toSortedSet()

    val costMap = mutableMapOf<Int, Int>()
    val min = sortedSet.first()
    val max = sortedSet.last()

    (min..max).forEach {
        val cost = day6.sumOf { curr -> costFn(curr, it) }
        costMap[it] = cost
    }
    return costMap
}

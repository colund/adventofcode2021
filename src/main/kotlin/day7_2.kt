import java.io.File
import kotlin.math.abs

fun main() {
    val day6 = File("day7.txt").readText().split(",").map { it.toInt() }
    val costMap = cost(day6) { v0, v1 -> onePlus2Plus3ToN(abs(v0 - v1)) }

    val lowestCost = costMap.values.minOrNull()
    println("lowestCost = $lowestCost")
}

fun onePlus2Plus3ToN(n: Int): Int = (n * (n + 1)) / 2

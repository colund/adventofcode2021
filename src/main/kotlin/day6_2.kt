import java.io.File

fun main() {
    val day6line = File("day6.txt").readText()
    val numbers = day6line.split(",").map { it.toInt() }.toMutableList()

    var numCount: MutableList<Long> = (0..8).map { num ->
        numbers.count { it == num }
    }.map { it.toLong() }.toMutableList()

    var fishCount: Long = numbers.count().toLong()

    repeat(256) {
        val zeroCount = numCount[0]
        numCount = numCount.drop(1).toMutableList()
        numCount.add(zeroCount)
        if (zeroCount > 0) {
            fishCount += zeroCount
            numCount[6] += zeroCount
        }
    }

    println(fishCount)
}
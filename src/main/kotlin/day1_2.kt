import java.io.File

fun main() {
    val lines: List<Int> = File("day1.txt").readLines().map { it.toInt() }
    var count = 0
    var lastSum = 0
    for (i in 0 until lines.size - 2) {
        val current = lines[i] + lines[i + 1] + lines[i + 2]
        if (i > 0 && current > lastSum) {
            count++
        }
        lastSum = current
    }
    println(count)
}


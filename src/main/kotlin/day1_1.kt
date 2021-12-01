import java.io.File

fun main() {
    val lines = File("day1.txt").readLines()
    var count = 0
    val numbers = lines.map { it.toInt() }
    for (i in 1 until numbers.size) {
        if (numbers[i] > numbers[i - 1]) {
            count++
        }
    }
    println(count)
}


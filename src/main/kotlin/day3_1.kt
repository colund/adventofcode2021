import java.io.File

fun main() {
    val strings = File("day3.txt").readLines()
    val digits = strings.first().length
    val numbers: List<Int> = strings.map { it.toInt(2) }
    val halfSize = numbers.size / 2
    var gamma = 0
    var epsilon = 0

    for (bit in digits - 1 downTo 0) {
        val num = 1 shl bit
        val list = numbers.map { it and num }
        val ones = list.count { it > 0 }

        if (ones == 0) continue

        if (ones > halfSize) {
            gamma += num
        } else {
            epsilon += num
        }
    }
    println(gamma * epsilon)
}

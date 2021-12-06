import java.io.File

fun main() {
    val day6line = File("day6.txt").readText()
    var numbers = day6line.split(",").map { it.toInt() }.toMutableList()

    repeat(80) {
        var fishToAdd = 0
        val newList = numbers.map {
            if (it == 0) {
                fishToAdd++
                6
            } else it - 1
        }
        numbers = newList.toMutableList().also { list ->
            repeat(fishToAdd) { list.add(8) }
        }
    }

    println(numbers.count())
}

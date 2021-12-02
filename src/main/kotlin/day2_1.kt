import java.io.File

fun main() {
    val numbers: List<String> = File("day2.txt").readLines()
    var position = 0
    var depth = 0
    numbers.forEach {
        val (command, intString) = it.split(" ")
        val value = intString.toInt()
        when (command) {
            "forward" -> position += value
            "down" -> depth += value
            "up" -> depth -= value
        }
    }

    println(position * depth)
}


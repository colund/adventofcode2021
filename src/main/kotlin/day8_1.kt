import java.io.File
import java.util.*

enum class Letter {
    a, b, c, d, e, f, g
}

val definitions = listOf(
    // 0
    EnumSet.of(Letter.a, Letter.b, Letter.c, Letter.e, Letter.f, Letter.g),
    // 1 <---
    EnumSet.of(Letter.c, Letter.f),
    // 2
    EnumSet.of(Letter.a, Letter.c, Letter.d, Letter.e, Letter.g),
    // 3
    EnumSet.of(Letter.a, Letter.c, Letter.d, Letter.f, Letter.g),
    // 4 <---
    EnumSet.of(Letter.b, Letter.c, Letter.d, Letter.f),
    // 5
    EnumSet.of(Letter.a, Letter.b, Letter.d, Letter.f, Letter.g),
    // 6
    EnumSet.of(Letter.a, Letter.b, Letter.d, Letter.e, Letter.f, Letter.g),
    // 7 <---
    EnumSet.of(Letter.a, Letter.c, Letter.f),
    // 8 <---
    EnumSet.of(Letter.a, Letter.b, Letter.c, Letter.d, Letter.e, Letter.f, Letter.g),
    // 9
    EnumSet.of(Letter.a, Letter.b, Letter.c, Letter.d, Letter.f, Letter.g),
)

fun main() {
    val sizes = definitions.map { it.size }

    val readLines = File("day8.txt").readLines()
    val sum = readLines.sumOf { line ->

        val right = line.split("|").map { it.trim() }[1]

        val rightStrings = right.split(" ")

        val oneCount = rightStrings.count { it.length == sizes[1] }
        val fourCount = rightStrings.count { it.length == sizes[4] }
        val sevenCount = rightStrings.count { it.length == sizes[7] }
        val eightCount = rightStrings.count { it.length == sizes[8] }

        oneCount + fourCount + sevenCount + eightCount
    }

    println(sum)
}

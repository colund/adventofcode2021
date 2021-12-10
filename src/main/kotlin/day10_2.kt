import java.io.File
import java.util.*

fun main() {
    val lines = File("day10.txt").readLines()
    val scores = mutableListOf<Long>()
    lines.forEach { line ->
        val charArray = line.toCharArray()
        val stack = Stack<Char>()
        for (i in charArray.indices) {
            if (matchParens(charArray, i, stack) != 0) {
                return@forEach
            }
        }
        var score = 0L
        while (stack.isNotEmpty()) {
            score *= 5
            val next = stack.pop()
            score += leftParens.indexOf(next) + 1 // same index for left and right matching chars
        }
        scores.add(score)
    }
    val sortedScores = scores.sorted()
    println(sortedScores[sortedScores.size / 2])
}
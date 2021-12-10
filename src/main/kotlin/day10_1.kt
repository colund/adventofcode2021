import java.io.File
import java.util.*

const val leftParens = "([{<"
const val rightParens = ")]}>"
val points = listOf(3, 57, 1197, 25137)

fun main() {
    val lines = File("day10.txt").readLines()
    var sum = 0L
    lines.forEach { line ->
        val charArray = line.toCharArray()
        val stack = Stack<Char>()
        for (i in charArray.indices) {
            sum += matchParens(charArray, i, stack)
        }
    }
    println("sum = $sum")
}

fun matchParens(charArray: CharArray, i: Int, stack: Stack<Char>): Int {
    val char = charArray[i]
    val leftIndex = leftParens.indexOf(char)
    if (leftIndex > -1) {
        stack.push(charArray[i])
    } else {
        val rightIndex = rightParens.indexOf(char)
        val prevChar = stack.pop()
        val prevCharIndex = leftParens.indexOf(prevChar)
        if (rightIndex != prevCharIndex) {
            return points[rightIndex]
        }
    }
    return 0
}
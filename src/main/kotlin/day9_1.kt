import java.io.File

fun main() {
    val lines = File("day9.txt").readLines() // array of strings per line
    val numbers: List<List<Int>> = lines.map { line -> line.toCharArray().map { it.toString().toInt() } }

    val minima = mutableListOf<Int>()

    numbers.forEachIndexed { lineIndex, line: List<Int> ->
        line.forEachIndexed { index, num ->
            val leftNum = line.getOrNull(index - 1) ?: 100
            val rightNum = line.getOrNull(index + 1) ?: 100
            val upNum = numbers.getOrNull(lineIndex - 1)?.getOrNull(index) ?: 100
            val downNum = numbers.getOrNull(lineIndex + 1)?.getOrNull(index) ?: 100

            if (num < leftNum && num < rightNum && num < upNum && num < downNum) {
                minima.add(num)
            }
        }
    }

    println(minima.sum() + minima.count())
}

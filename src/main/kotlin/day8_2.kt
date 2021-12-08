import java.io.File
import java.util.*


fun String.sorted() = toCharArray().sorted().joinToString("")
fun String.toLetters(): List<Letter> = this.toCharArray().map { Letter.valueOf(it.toString()) }

fun main() {
    // Frequency of each letter, i.e. {a=8, b=6, c=8, d=7, e=4, f=9, g=7}
    val letterFrequencies: Map<Letter, Int> = Letter.values().associateWith { letter ->
        val count = definitions.count { it.contains(letter) }
        count
    }

    // [[4, 6, 7, 8, 8, 9], [8, 9], ...]
    val freqsByNumList: List<List<Int>> =
        definitions.map { defn: EnumSet<Letter> ->
            defn.map { letterFrequencies[it] }
        }.map { it -> it.sortedBy { it }.filterNotNull() }
    println(freqsByNumList)

    // {0=[4, 6, 7, 8, 8, 9], 1=[8, 9] ... }
    val numberToFreqs: Map<Int, List<Int?>> = freqsByNumList.mapIndexed { index, freqs -> index to freqs }.toMap()

    val readLines = File("day8.txt").readLines()
    val sum = readLines.sumOf { line ->

        val (left, right) = line.split("|").map { it.trim() }

        val leftStrings = left.split(" ")
        val rightStrings = right.split(" ")

        val allLetters = leftStrings.flatMap { it.toLetters() }

        // Get frequency of letters in left string, e.g. {a=7, b=9, c=4, d=8, e=6, f=8, g=7}
        val frequencies: Map<Letter, Int> = Letter.values().associateWith { letter ->
            allLetters.count { it == letter }
        }

        // E.g. [[4, 6, 7, 7, 8, 8, 9], [7, 7, 8, 8, 9], ... ]
        val currFreqs: List<List<Int?>> =
            leftStrings.map { s -> s.toLetters().map { frequencies[it] }.sortedBy { it } }


        // E.g. [[5], [1], [9], [8], [7], [4], [3], [0], [2], [6]]
        val numbers = currFreqs.map { f -> numberToFreqs.filter { it.value == f }.map { it.key } }

        // E.g. {abcdeg=[0], abcefg=[6], ade=[7], ...}
        val stringToNumber = leftStrings.mapIndexed { index, s -> s.sorted() to numbers[index] }.toMap()

        // E.g. [[0], [8], [5], [3]]
        val digits: List<Int> = rightStrings.map { it.sorted() }.mapNotNull { stringToNumber[it] }.map { it.first() }

        // E.g. 1000 * 0 + 100 * 8 + 10 * 5 + 3
        digits[0] * 1000 + digits[1] * 100 + digits[2] * 10 + digits[3] * 1
    }

    // Result
    println(sum)
}

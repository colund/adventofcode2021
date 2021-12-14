import java.io.File

fun main() {
    val day14 = Day14()
    val string = day14.doRounds(10)
    val freq = mutableMapOf<Char, Long>()
    string.forEach {
        freq[it] = freq[it]?.plus(1) ?: 1
    }
    println("freq is $freq")
    val diff = freq.values.maxOrNull()!! - freq.values.minOrNull()!!
    println("diff is $diff")
}

class Day14 {
    val lines = File("day14.txt").readLines()

    /**
     * Part 1, simple calculation
     */
    fun doRounds(maxRounds: Int): String {
        var chars: MutableList<Char> = lines[0].toCharArray().toMutableList()

        val rules = createRules()

        for (i in 1..maxRounds) {
            val newChars = mutableListOf<Char>()
            for (c in 0 until chars.size - 1) {
                val left = chars[c].toString()
                val right = chars[c + 1].toString()
                val pair = left + right
                val rule = rules[pair]
                newChars.add(chars[c])
                if (rule != null) {
                    newChars.add(rule)
                }
                if (c == chars.size - 2) {
                    newChars.add(chars[c + 1])
                }
            }
            chars = newChars
        }
        return chars.joinToString("")
    }

    /**
     * Part two uses frequencies of pairs where new pairs are born from rulesl
     */
    fun doRoundsV2(rounds: Int) {
        val chars: MutableList<Char> = lines[0].toCharArray().toMutableList()
        println(lines[0])

        // Create all pairs from first line
        var freq = mutableMapOf<Pair<Char, Char>, Long>()
        for (c in 0..chars.size - 2) {
            val key = chars[c] to chars[c + 1]
            freq[key] = freq[key]?.plus(1) ?: 1
        }

        // Create all rules from remaining lines
        val rules: MutableMap<Pair<Char, Char>, Char> = createRulesV2()

        val firstChar = chars.first()
        val lastChar = chars.last()

        // Loop max times and update frequencies
        for (i in 1..rounds) {
            println("Start round $i! ")
            println("freq=$freq")

            val newFreqs = mutableMapOf<Pair<Char, Char>, Long>()

            freq.forEach { (pair, count) ->
                val ch = rules[pair]
                if (ch != null) {
                    val firstPairKey = pair.first to ch
                    val secondPairKey = ch to pair.second

                    newFreqs[firstPairKey] = count + (newFreqs[firstPairKey] ?: 0)
                    newFreqs[secondPairKey] = count + (newFreqs[secondPairKey] ?: 0)
                } else newFreqs[pair] = count
            }
            freq = newFreqs
            println("ROUND $i freqs is now $freq")
            printLetterCount(freq, firstChar, lastChar)

        }
        println("freq=$freq")

        val result = printLetterCount(freq, firstChar, lastChar)

        println(result.entries.sortedBy { it.value })

        val max = result.values.maxOrNull()!!
        println("max = $max")
        val min = result.values.minOrNull()!!
        println("min=$min")
        val diff = result.values.maxOrNull()!! - result.values.minOrNull()!!
        println("diff=$diff")
    }

    private fun printLetterCount(
        freq: MutableMap<Pair<Char, Char>, Long>,
        firstChar: Char,
        lastChar: Char
    ): Map<Char, Long> {
        val letterCountFromPairs = mutableMapOf<Char, Long>()
        freq.forEach { (pair: Pair<Char, Char>, count: Long) ->
            letterCountFromPairs[pair.first] = (letterCountFromPairs[pair.first] ?: 0) + count
            letterCountFromPairs[pair.second] = (letterCountFromPairs[pair.second] ?: 0) + count
        }

        val letterCount: Map<Char, Long> = letterCountFromPairs.map {
            // First and last letter are part of single pairs, all others are part of two pairs
            it.key to (if (it.key == firstChar || it.key == lastChar) {
                it.value + 1
            } else it.value) / 2L
        }.toMap()

        return letterCount
    }

    private fun createRules(): MutableMap<String, Char> {
        // In the first step I was using String keys
        val rules = mutableMapOf<String, Char>()
        for (i in 2 until lines.size) {
            val (left, right) = lines[i].split(" -> ")
            rules[left] = right[0]
        }
        return rules
    }

    private fun createRulesV2(): MutableMap<Pair<Char, Char>, Char> {
        // In the second step I was using Pair<Char, Char> keys
        val rules = mutableMapOf<Pair<Char, Char>, Char>()
        for (i in 2 until lines.size) {
            val (left, right) = lines[i].split(" -> ")
            val leftChars = left.toCharArray().let { it.first() to it[1] }
            rules[leftChars] = right[0]
        }
        return rules
    }
}

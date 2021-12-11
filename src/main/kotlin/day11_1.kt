import java.io.File
import java.lang.IllegalArgumentException

class Day11 {
    private var lines = File("day11.txt").readLines().map {
        it.toCharArray().map { it.toString().toInt() }
    }
    private val rows = lines.size
    private val cols = lines.first().size
    private val nums = lines.flatten().toMutableList()

    /**
     * Part 1
     */
    fun part1(): Int {
        val steps = 100
        var flashCount = 0

        repeat(steps) { step ->
            flashCount += doStep(step)
        }
        return flashCount
    }

    /**
     * Part 2
     */
    fun part2(): Int {
        var step = 0
        while (true) {
            val flashCount = doStep(step)
            step++
            if (flashCount == nums.size) {
                break
            }
        }
        return step
    }

    private fun validCoord(i: Int, j: Int): Boolean = i in 0 until rows && j in 0 until cols

    operator fun get(i: Int, j: Int): Int? = if (validCoord(i, j)) nums.getOrNull(i * cols + j) else null

    operator fun set(i: Int, j: Int, value: Int) {
        if (validCoord(i, j)) {
            nums[i * cols + j] = value
        } else {
            throw IllegalArgumentException("Invalid: ($i,$j)")
        }
    }

    override fun toString(): String {
        val sb = StringBuilder()
        nums.forEachIndexed { index, i ->
            sb.append((i % 10).toString())
            if (index % cols == cols - 1) {
                sb.append("\n")
            }
        }
        return sb.toString()
    }

    private inline fun forEach(crossinline fn: (i: Int, j: Int) -> Unit) {
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                fn(i, j)
            }
        }
    }

    private inline fun runAdjacent(i: Int, j: Int, crossinline fn: (Int, Int) -> Unit) {
        for (r in i - 1..i + 1) {
            for (c in j - 1..j + 1) {
                fn(r, c)
            }
        }
    }

    private fun flashElem(i: Int, j: Int, flashes: MutableList<Pair<Int, Int>>) {
        if (this[i, j] == 10) return
        val num = increase(i, j) ?: return
        if (num == 10) {
            flashToAdjacent(i, j, flashes)
        }
    }

    private fun flashToAdjacent(i: Int, j: Int, flashes: MutableList<Pair<Int, Int>>) {
        val invalidCoord = i !in 0 until rows || j !in 0 until cols
        if (invalidCoord) {
            throw IllegalArgumentException()
        }
        if (i to j in flashes) return
        println("flashing $i,$j")
        flashes.add(i to j)

        runAdjacent(i, j) { r, c ->
            flashElem(r, c, flashes)
        }
    }

    private fun increase(i: Int, j: Int): Int? {
        val num = this[i, j] ?: return null
        this[i, j] = num + 1
        return this[i, j]
    }

    private fun doStep(step: Int): Int {
        val flashes = mutableListOf<Pair<Int, Int>>()
        println("step=" + (step + 1))
        forEach { i, j ->
            increase(i, j)
        }
        forEach { i, j ->
            if (this[i, j] == 10) {
                flashToAdjacent(i, j, flashes)
            }
        }
        // Set all 10 to 0
        forEach { i, j ->
            this[i, j] = this[i, j]!! % 10
        }

        println("after step ${step + 1}:\n$this")
        return flashes.size
    }
}

fun main() {
    val day11 = Day11()
    val flashCount = day11.part1()
    println("total flash count=$flashCount")
}

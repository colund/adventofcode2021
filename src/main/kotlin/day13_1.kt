import java.io.File

private val file = File("day13.txt")

private enum class XorY { X, Y }

private data class Coord(val x: Int, val y: Int) : Comparable<Coord> {
    override fun compareTo(other: Coord): Int {
        return if (this.y == other.y) this.x - other.x
        else this.y - other.y
    }
}

private fun List<Coord>.maxX() = maxOf { it.x }
private fun List<Coord>.maxY() = maxOf { it.y }

private data class FoldAlong(val value: Int, val along: XorY)

fun main() {
    doFolds(1)
}

internal fun doFolds(maxFolds: Int) {
    val lines = file.readLines()

    val coords: MutableList<Coord> = mutableListOf()

    val folds = mutableListOf<FoldAlong>()

    val coordRegex = """([\d]+),([\d]+)""".toRegex()
    val foldRegex = """[\w]+ [\w]+ (\w)=(\d+)""".toRegex()

    parseStuff(lines, coordRegex, coords, foldRegex, folds)


    val table = coords.table()

    println("table:\n$table")

    val newList: MutableList<Coord> = coords.doFolds(folds, maxFolds)

    val tableAfter = newList.table()
    println("table after:\n$tableAfter")
    println("Count # = " + tableAfter.count { it == '#' })
}

private fun MutableList<Coord>.doFolds(
    folds: MutableList<FoldAlong>,
    maxFolds: Int
): MutableList<Coord> {
    val newList = this
    val maxIndex = minOf(maxFolds - 1, folds.size - 1)
    for (i in 0..maxIndex) {
        val foldAlong = folds[i]
        when (foldAlong.along) {
            XorY.X -> {
                newList.replaceAll {
                    if (it.x > foldAlong.value) {
                        val diff = it.x - foldAlong.value
                        it.copy(x = it.x - diff - diff)
                    } else it
                }
            }
            XorY.Y -> {
                newList.replaceAll {
                    if (it.y > foldAlong.value) {
                        val diff = it.y - foldAlong.value
                        it.copy(y = it.y - diff - diff)
                    } else it
                }
            }
        }
    }
    return newList
}

private fun MutableList<Coord>.table(): StringBuilder {
    sort()
    val sb = StringBuilder()
    for (y in 0..maxY()) {
        for (x in 0..maxX()) {
            if (any { it.x == x && it.y == y }) {
                sb.append("#")
            } else {
                sb.append(".")
            }
        }
        sb.append("\n")
    }
    return sb
}

private fun parseStuff(
    lines: List<String>,
    coordRegex: Regex,
    coords: MutableList<Coord>,
    foldRegex: Regex,
    folds: MutableList<FoldAlong>
) {
    lines.forEach { line ->
        coordRegex.matchEntire(line)?.destructured?.let {
            val (x, y) = it
            coords.add(Coord(x.toInt(), y.toInt()))
            return@forEach
        }

        foldRegex.matchEntire(line)?.destructured?.let {
            val (along, value) = it
            when (along) {
                "x" -> folds.add(FoldAlong(value.toInt(), XorY.X))
                "y" -> folds.add(FoldAlong(value.toInt(), XorY.Y))
            }
        }
    }
}

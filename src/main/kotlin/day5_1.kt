import java.io.File
import kotlin.math.abs

val day5lines = File("day5.txt").readLines()

data class Vec2(val x: Int, val y: Int) {
    constructor(x: String, y: String) : this(x.toInt(), y.toInt())
}

data class Line(val p0: Vec2, val p1: Vec2)

fun main() {
    val overLappingPoints = overlappingPoints { p0, p1 ->
        // Horizontal or vertical
        p0.x == p1.x || p0.y == p1.y
    }
    // Result
    println(overLappingPoints.count().toString())
}

inline fun overlappingPoints(crossinline includePoints: (p0: Vec2, p1: Vec2) -> Boolean): Set<Vec2> {
    val regex = """([\d]+)[^\d]+([\d]+)[^\d]+([\d]+)[^\d]+([\d]+)""".toRegex()

    val lines: List<Line> = day5lines.mapNotNull {
        val (x0, y0, x1, y1) = regex.matchEntire(it)?.destructured ?: return@mapNotNull null
        val p0 = Vec2(x0, y0)
        val p1 = Vec2(x1, y1)
        if (includePoints(p0, p1)) {
            Line(p0, p1)
        } else null
    }

    val allPoints: List<Vec2> = lines.flatMap { lineToPoints(it) }

    val eachCount: Map<Vec2, Int> = allPoints.groupingBy { it }.eachCount()
    return eachCount.filter { it.value >= 2 }.keys
}

// Supports horizontal, vertical and diagonal lines
fun lineToPoints(line: Line): List<Vec2> {
    val minX = minOf(line.p0.x, line.p1.x)
    val maxX = maxOf(line.p0.x, line.p1.x)
    val minY = minOf(line.p0.y, line.p1.y)
    val maxY = maxOf(line.p0.y, line.p1.y)

    // We know it's one step horizontally, vertically or diagonally
    val xDiff = line.p1.x - line.p0.x
    val xStep = if (xDiff == 0) 0 else xDiff / abs(xDiff)
    val yDiff = line.p1.y - line.p0.y
    val yStep = if (yDiff == 0) 0 else yDiff / abs(yDiff)

    val result = mutableListOf<Vec2>()

    var x = line.p0.x
    var y = line.p0.y
    while (x in (minX..maxX) && y in (minY..maxY)) {
        result.add(Vec2(x, y))
        x += xStep
        y += yStep
    }
    return result
}

import kotlin.math.abs

fun main() {
    val overLappingPoints = overlappingPoints { p0, p1 ->
        // Horizontal, vertical or diagonal
        p0.x == p1.x || p0.y == p1.y ||
                (abs(p0.x - p1.x) == abs(p0.y - p1.y))
    }
    // Result
    println(overLappingPoints.count().toString())
}
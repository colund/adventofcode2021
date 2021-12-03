import java.io.File

fun main() {
    val day3lines = File("day3.txt").readLines()
    val length = day3lines.first().length

    val oxygenList = day3lines.map { it.toInt(2) }.toMutableList()
    val co2List = day3lines.map { it.toInt(2) }.toMutableList()

    fun oxygenNumberToKeep(ones: Int): Int = if (ones >= oxygenList.size / 2.0) 1 else 0
    fun co2NumberToKeep(ones: Int): Int = if (ones < co2List.size / 2.0) 1 else 0

    for (i in length - 1 downTo 0) {
        val num = 1 shl i
        if (oxygenList.size > 1) {
            val oxygenNum = oxygenNumberToKeep(oxygenList.count { it and num > 0 })
            removeItemsFromList(oxygenNum, oxygenList, num)
        }

        if (co2List.size > 1) {
            val co2Num = co2NumberToKeep(co2List.count { it and num > 0 })
            removeItemsFromList(co2Num, co2List, num)
        }
    }
    println(oxygenList.first() * co2List.first())
}

private fun removeItemsFromList(
    oneOrZero: Int,
    list: MutableList<Int>,
    num: Int
) {
    if (oneOrZero == 1) {
        list.removeIf { it and num == 0 }
    } else {
        list.removeIf { it and num > 0 }
    }
}

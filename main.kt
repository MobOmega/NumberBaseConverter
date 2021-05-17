import java.math.RoundingMode
import java.math.BigDecimal
import java.math.BigInteger

fun main() {
    val char: CharArray = charArrayOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
        'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z')
    home(char)
}

fun home(char: CharArray) {
    print("Enter two numbers in format: {source base} {target base} (To quit type /exit) ")
    val input = readLine()!!
    if (input == "/exit") return else if (input == "") home(char) else {
        val bases = input.split(" ").map { it.toInt() }
        convert(bases, char)
    }
}

fun convert(bases: List<Int>, char: CharArray) {
    print("Enter number in base ${bases[0]} to convert to base ${bases[1]} (To go back type /back) ")
    val input = readLine()!!
    if (input == "/back") home(char) else if (input == "") convert(bases, char) else {
        var x: BigInteger?
        var str = ""
        if (input.contains('.', true)) {
            x = input.substringBefore(".").toBigIntegerOrNull(bases[0])
            var zero = BigDecimal("0.00000")
            var br = true
            var y = input.substringAfter(".")
            var list = y.split("").filter { it != "" }
            var m: BigDecimal
            var n = BigDecimal.ONE.setScale(5, RoundingMode.UNNECESSARY)
            if (list.size > 0) {
                for (i in 0 until list.size) {
                    n /= bases[0].toBigDecimal().setScale(5, RoundingMode.UNNECESSARY)
                    if (list[i] !in char.map { it.toString() }) m = list[i].toBigDecimal().setScale(5, RoundingMode.UNNECESSARY)
                    else m = char.indexOfFirst { it.toString() == list[i] }.toBigDecimal().setScale(5, RoundingMode.UNNECESSARY) + BigDecimal("10").setScale(5, RoundingMode.UNNECESSARY)
                    zero += m * n
                }
            }
            zero.setScale(5, RoundingMode.HALF_UP)
            if (y.length < 1) br = false
            val bd = bases[1].toBigDecimal().setScale(5, RoundingMode.UNNECESSARY)
            var nm: Int
            var one = BigDecimal.ONE.setScale(5, RoundingMode.UNNECESSARY)
            var add = ""
            if (br) {
                str = "."
                for (i in 1..5) {
                    nm = 0
                    add = ""
                    zero *= bd
                    while (zero >= one) {
                        zero -= one
                        nm++
                    }
                    if (nm >= 10) {
                        nm -= 10
                        add += char[nm]
                    } else {
                        add += nm.toString()
                    }
                    if (str.length < 6) str += add
                }
            }

        } else {
            x = input.toBigIntegerOrNull(bases[0])
        }
        val num = x!!.toString(bases[1])
        println("Conversion result: $num$str")
        convert(bases, char)
    }
}

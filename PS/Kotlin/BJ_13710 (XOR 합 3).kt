import java.io.*
import java.util.*
/*
13710번 XOR 합 3

배열의 모든 연속 부분 배열의 XOR의 합을 구해보자.

어제 한거랑 같은 문제인데 오늘은 일이 있어서 비상식량 섭취
*/
val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun ini(): Int = br.readLine().toInt()
fun lni(): Long = br.readLine().toLong()
fun ins(): List<Int> = br.readLine().split(" ").map { it.toInt() }
fun lns(): List<Long> = br.readLine().split(" ").map { it.toLong() }
val inf = Double.POSITIVE_INFINITY

fun main() {
    val N = ini()
    val A = ins()
    var ret = 0L
    (0 until 30).forEach{len -> 
        var cnt = 0L
        var bit = longArrayOf(1, 0)
        var cur = 0
        A.forEach {i ->
            cur = cur xor (i and (1 shl len))
            val idx = if ((cur and (1 shl len)) != 0) 1 else 0
            cnt += bit[idx xor 1]
            bit[idx]++
        }
        ret += cnt*(1 shl len)
    }
    println(ret)

    bw.flush()
}

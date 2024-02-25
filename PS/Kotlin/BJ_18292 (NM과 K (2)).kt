import java.io.*
import java.util.*
/*
18292번 NM과 K (2)

N*M보드에서 K개의 수를 골라 합이 최대가 되도록 만들어보자.
단, 선택한 수끼리 인접해있으면 안된다.

백트래킹인가? dp인가? 고민 많이 했던 문제.
그런데 dp[row][prvColSelect][K]로 dp식 세우면 되겠더라고요.
처음엔 rowSelect*colSelect*K인가 했는데..
이러다보니까 잘 풀었네요. 빠르게.
*/
val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun ini(): Int = br.readLine().toInt()
fun lni(): Long = br.readLine().toLong()
fun ins(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun lns(): List<Long> = br.readLine().split(" ").map {it.toLong()}

lateinit var M: Array<IntArray>
lateinit var dp: Array<Array<IntArray>>

fun loop(curR: Int, prvC: Int, K: Int, R: Int, C: Int): Int {
    if (curR == R) return if (K == 0) 0 else -INFI
    if (dp[curR][prvC][K] != -INFI) return dp[curR][prvC][K]
    (0 until (1 shl C)).forEach { curC ->
        var canSelect = true
        var get = 0
        var cnt = 0
        (0 until C).forEach { i ->
            if (curC and (1 shl i) == 0) return@forEach
            if (i-1 >= 0 && (curC and (1 shl (i-1))) != 0) canSelect = false
            if (curC and (1 shl i+1) != 0) canSelect = false
            if (prvC and (1 shl i) != 0) canSelect = false
            get += M[curR][i]
            cnt++
        }
        if (!canSelect) return@forEach
        if (cnt > K) return@forEach
        dp[curR][prvC][K] = Math.max(dp[curR][prvC][K], loop(curR+1, curC, K-cnt, R, C)+get)
    }
    return dp[curR][prvC][K]
}

fun main() {
    val (R, C, K) = ins()
    M = Array(R) { ins().toIntArray() }
    dp = Array(R) { Array(1 shl C) { IntArray(K+1) { -INFI } } }
    println(loop(0, 0, K, R, C))

    bw.flush()
}

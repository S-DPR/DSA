import java.io.*
import java.util.*
/*
13439번 팩토리얼과 점화식

S(n, k) = S(n-1, k) * S(n, k-1)
S(0, k) = 1
S(n, 0) = k
N, K가 주어질 때 S(N, K)의 약수의 개수를 1_000_000_009로 나눈 값을 구해보자.

그냥 dp문제인데 왜 플레4?
약수의 개수 구하는 방법만 알면 그냥 순수하게 dp문제입니다.
3차원이긴 한데 뭐 말이좋아 3차원이지 이건 그냥..
골드1로 기여했습니다.
*/
val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun ini(): Int = br.readLine().toInt()
fun lni(): Long = br.readLine().toLong()
fun ins(): List<Int> = br.readLine().trim().split(" ").map { it.toInt() }
fun lns(): List<Long> = br.readLine().trim().split(" ").map { it.toLong() }

lateinit var dp: Array<Array<LongArray>>
lateinit var vis: Array<BooleanArray>
val mod = 1_000_000_009L

fun loop(n: Int, k: Int): LongArray {
    if (vis[n][k]) return dp[n][k]
    vis[n][k] = true
    if (n == 0) {
        dp[n][k][1] = 1
        return dp[n][k]
    }
    if (k == 0) {
        dp[n][k][n] = 1
        return dp[n][k]
    }
    loop(n-1, k).forEachIndexed { idx, i ->
        dp[n][k][idx] = (dp[n][k][idx] + i) % mod
    }
    loop(n, k-1).forEachIndexed { idx, i ->
        dp[n][k][idx] = (dp[n][k][idx] + i) % mod
    }
    return dp[n][k]
}

fun main() {
    val (N, K) = ins()
    dp = Array(N+1) { Array(K+1) { LongArray(N+1) } }
    vis = Array(N+1) { BooleanArray(K+1) }
    val factos = loop(N, K)
    val divisor = LongArray(N+1)
    var ret = 1L
    (2..N).forEach {
        var x = it
        for (i in 2..it) {
            if (x == 1) break
            while (x%i == 0) {
                divisor[i] = (divisor[i] + factos[it]) % mod
                x /= i
            }
        }
    }
    (2..N).forEach {
        ret = (ret*(divisor[it]+1))%mod
    }
    println(ret)

    bw.flush()
}

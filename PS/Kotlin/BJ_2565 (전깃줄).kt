import java.io.*
import java.util.*
import kotlin.math.min
/*
2565번 전깃줄

왼쪽 i번쨰 전깃줄에서 오른쪽 j번째 전깃줄을 잇는 선들이 N개 있다.
이 선 중 최소개수를 잘라 서로 교차하지 않게 하려한다.
적어도 몇 개를 잘라야할까?

이미 너무 유명한 문제지만, 너무 피곤해서 이미 틀려있는김에 푼 문제.
그냥 LIS 긁으면 됩니다..
피곤해..
*/
val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun spi(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun spl(): List<Long> = br.readLine().split(" ").map {it.toLong()}

fun main() {
    val N = br.readLine().toInt()
    val items = Array(N) { Pair(0, 0) }
    repeat(N) {
        val (u, v) = spi()
        items[it] = Pair(u, v)
    }
    items.sortWith(compareBy { it.first })
    val dp = IntArray(N)
    repeat(N) { i ->
        var max = 0
        repeat(i) { j ->
            if (items[i].second > items[j].second)
                max = Math.max(max, dp[j])
        }
        dp[i] = max+1
    }
    println(N-dp.max())
}
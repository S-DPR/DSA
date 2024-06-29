import java.io.*
import java.util.*
/*
29200번 문제 수 줄이기

수열 A를 부분연속배열로 쪼개자. 단, 인접한 부분배열은 길이가 달라야한다.
모든 부분배열을 xor한 뒤 더한 값의 최댓값을 구하시오.

N이 20만이면서 dp인거도 살짝 억울하긴한데,
더 억울한건 생각보다 음..
답지를 본 이상 잊을 수 없는.. 그런느낌..

일단 에디토리얼의 깨달음 1)
인접한 수가 다르도록 더할 때 필요한 최댓값은 4.
솔직히 2인거같은데 왠지는 모르겠어요. 커뮤니티에 질문글 올리긴했는데 답이 달릴지는..
아몰라..

그리고..
어렵다..
애드혹느낌 나는데 애드혹이 안달려있네..
*/
val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun ini(): Int = br.readLine().toInt()
fun lni(): Long = br.readLine().toLong()
fun ins(): List<Int> = br.readLine().trim().split(" ").map { it.toInt() }
fun lns(): List<Long> = br.readLine().trim().split(" ").map { it.toLong() }

fun main() {
    val N = ini()
    val A = longArrayOf(0) + lns()
    val dp = Array(N+1) { LongArray(4) }
    var vl = 0L
    (1..minOf(N, 2)).forEach {
        vl = vl xor A[it]
        dp[it][it-1] = vl
    }
    (1..N).forEach {
        var vl = 0L
        (1..4).forEach { i ->
            if (it < i) return@forEach
            vl = vl xor A[it-i+1]
            if (it == 2 && i == 1) return@forEach
            var mx = 0L
            (1..4).forEach { j ->
                if (i != j) mx = maxOf(mx, dp[it-i][j-1])
            }
            dp[it][i-1] = mx+vl
        }
    }
    println(dp[N].max())

    bw.flush()
}

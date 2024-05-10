import java.io.*
import java.util.*

val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun ini(): Int = br.readLine().toInt()
fun lni(): Long = br.readLine().toLong()
fun ins(): List<Int> = br.readLine().split(" ").map { it.toInt() }
fun lns(): List<Long> = br.readLine().split(" ").map { it.toLong() }
val inf = Double.POSITIVE_INFINITY
/*
2682번 최대 사이클 1

P(x)은 수열에서 1번째에 있는 값이다.
P(P(P(P(...P(x))))) 이런식으로 충분히 겹칠 때, 어느 순간 사이클을 돌게된다.
그 사이클에서 최댓값을 최대사이클이라고 하자.
제일 내부가 P(1)로 시작할 때, 길이가 N이고 최대사이클이 K인 순열의 개수를 구해보자.

조합론
그냥 대충 생각해보다가 풀었습니다.
의외로 간단한데 이게 왜 골드2지..
*/
fun main() {
    val fac = LongArray(21) { 1 }
    for (i in 1..20) {
        fac[i] = fac[i - 1] * i
    }

    repeat(ini()) {
        val (n, k) = ins()
        if (k == 1) {
            println(fac[n - 1])
            return@repeat
        }
        var ret = 0L
        for (sz in 2..k) {
            ret += fac[sz - 1] * fac[n - sz] * comb(k - 2, sz - 2)
        }
        println(ret)
    }

    bw.flush()
}

fun comb(n: Int, k: Int): Long {
    if (n < k) return 0
    var res = 1L
    for (i in 0 until k) {
        res *= (n - i)
        res /= (i + 1)
    }
    return res
}

import java.io.*
import java.util.*
import kotlin.math.sqrt
/*
26601번 가장 작은 수

약수가 2^N개인 수 중 가장 작은 수를 구해보자.

문제 내용이 심플하고 재밌어서 푼 문제
대충 1부터 8까지는 코드 돌려서 맞춰봤는데, 규칙이 없어서 생각 좀 해보다가..
소인수분해 하고 각 소수의 지수+1을 곱한거라는걸 생각하고.
조금 맞춰보다가 우선순위큐에 각 소수 넣고 이짓저짓 하면 되더라고요.
그래서 구현을 했더니 AC. MOD질 잘못해서 조금 문제가 발생했긴 한데, 조금 바꾸니 됐네요.
처음엔 에라토스테네스의 체 크기를 몇으로하나.. 해서 1000만으로 했는데,
의외로 문제에서 주어진 2000003까지만 해도 되는걸 보고 충격받았습니다..
*/
val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun spi(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun spl(): List<Long> = br.readLine().split(" ").map {it.toLong()}

fun main() {
    val sqrtMAX = sqrt(Long.MAX_VALUE.toDouble()).toLong()
    val MOD = 2_000_003L
    val MAX = MOD
    val A = LongArray(MAX.toInt()+1) {it.toLong()}
    val P = mutableListOf<Long>()
    (2..MAX).forEach {  i ->
        if (A[i.toInt()] == i)
            P.add(i)
        for (j in P) {
            if (i*j > MAX) break
            A[(i*j).toInt()] = j
            if (i%j == 0L) break
        }
    }
    val N = br.readLine().toInt()
    val Q = PriorityQueue(P)
    var C = 1L
    repeat(N) {
        val nxt = Q.poll()
        C = (C*nxt)%MOD
        if (nxt < sqrtMAX)
            Q.add(nxt*nxt)
    }
    println(C%MOD)

    br.close()
    bw.flush()
    bw.close()
}

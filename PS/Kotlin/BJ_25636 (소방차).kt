import java.io.*
import java.util.*
/*
25636번 소방차

S에서 E로 갈 때, 최단경로로 가면서 물의 양을 최대로 하려 한다.
최단거리와 최대 물의양을 구해보자.

대충 다익dp 굴리면 AC.
간단한 문제였네요. 주말이라 풀기싫어서 이런거풀고있네..
*/
val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun nxti(): Int = br.readLine().toInt()
fun nxtl(): Long = br.readLine().toLong()
fun spi(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun spl(): List<Long> = br.readLine().split(" ").map {it.toLong()}

lateinit var G: Array<MutableList<Pair<Int, Long>>>
lateinit var A: LongArray

fun dij(s: Int, e: Int): Pair<Long, Long> {
    val N = G.size - 1
    val dist = LongArray(N+1) { INFL }
    val dp = LongArray(N+1)
    val pq = PriorityQueue<Pair<Int, Long>>(compareBy{it.second})
    dist[s] = 0
    dp[s] = A[s]
    pq.add(Pair(s, 0))
    while (pq.isNotEmpty()) {
        val (curN, curW) = pq.poll()
        if (dist[curN] < curW) continue
        G[curN].forEach{ (nxtN, nxtW) ->
            if (curW+nxtW <= dist[nxtN]) {
                if (curW+nxtW < dist[nxtN]) {
                    dist[nxtN] = curW+nxtW
                    pq.add(Pair(nxtN, dist[nxtN]))
                    dp[nxtN] = dp[curN]+A[nxtN]
                }
                dp[nxtN] = Math.max(dp[nxtN], dp[curN]+A[nxtN])
            }
        }
    }
    return Pair(dist[e], dp[e])
}

fun main() {
    val N = nxti()
    A = longArrayOf(0) + spl()
    G = Array(N+1) { mutableListOf() }
    val K = nxti()
    repeat(K) {
        val (u, v, w) = spi()
        G[u].add(Pair(v, w.toLong()))
        G[v].add(Pair(u, w.toLong()))
    }
    val (S, E) = spi()
    val (ed, ew) = dij(S, E)
    if (ed == INFL) {
        println("-1")
    } else {
        println("${ed} ${ew}")
    }

    bw.flush()
}

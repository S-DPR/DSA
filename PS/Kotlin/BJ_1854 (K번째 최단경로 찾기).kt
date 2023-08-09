import java.io.*
import java.util.*

val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun spi(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun spl(): List<Long> = br.readLine().split(" ").map {it.toLong()}
/*
1854번 K번째 최단경로 찾기

그래프가 주어진다.
1번 노드에서 출발해, 모든 노드로 가는 최단경로 중, K번째 최단거리를 출력해보자.
K번째가 없다면, -1을 출력하자.

키야
이전에 푼 문제중 dist배열을 비튼문제가 있었는데, 그거의 아종.
그때는 간선을 0으로 어쩌구 했던거같았는데, 이번에는 각각을 PQ로 만들어서 관리하면 됩니다.
한번 당했던 맛이라 그런가 1시간만에 풀었네..
*/
lateinit var G: Array<MutableList<Pair<Int, Long>>>

fun dij(N: Int, K: Int): Array<PriorityQueue<Long>> {
    val dp = Array(N+1) {PriorityQueue<Long>(K+1)}
    val pq = PriorityQueue<Pair<Int, Long>>(compareBy { it.second })
    pq.add(Pair(1, 0))
    dp[1].add(0)
    while (!pq.isEmpty()) {
        val cur = pq.poll()
        val curN = cur.first
        val curW = cur.second
        G[curN].forEach { (nxtN, nxtW) ->
            if (dp[nxtN].size < K || nxtW+curW < -dp[nxtN].peek()) {
                if (dp[nxtN].size == K)
                    dp[nxtN].poll()
                dp[nxtN].add(-(nxtW+curW))
                pq.add(Pair(nxtN, nxtW+curW))
            }
        }
    }
    return dp
}

fun main() {
    val (N, M, K) = spi()
    G = Array(N+1) { mutableListOf() }
    repeat(M) {
        val (u, v, w) = spi()
        G[u].add(Pair(v, w.toLong()))
    }
    val ret = dij(N, K)
    (1..N).forEach {
        bw.write("${if (ret[it].size == K) -ret[it].peek() else -1}\n")
    }

    br.close()
    bw.flush()
    bw.close()
}

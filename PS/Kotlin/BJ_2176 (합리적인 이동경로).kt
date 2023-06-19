import java.io.*
import java.util.*
/*
2176번 합리적인 이동경로

1번 노드에서 2번 노드로 이동하려 한다.
이 때, 2번 노드로 가는 동안 2번 노드와의 거리가 짧아지길 원한다.
경로의 개수를 구해보자.

그냥 다익스트라 쓰고 dp굴리면 되겠네~ 하는 마인드.
단, dp굴릴때도 우선순위큐에 잘 넣고 순서대로 돌려야합니다.
난이도? 쉽죠. 이정도면..

다만, dp에 불순물 섞어버린거에 익숙해지지 못했다면 어렵다고 생각할법합니다.
*/
val INF = 1 shl 30

fun dij(G: Array<MutableList<Pair<Int, Int>>>): IntArray {
    val N = G.size
    val dist = IntArray(N) {INF}
    val pq = PriorityQueue<Pair<Int, Int>>(compareBy { it.first })
    pq.add(Pair(0, 2))
    dist[2] = 0
    while (!pq.isEmpty()) {
        val cur = pq.poll()
        val curN = cur.second
        val curW = cur.first
        if (dist[curN] < curW) continue
        G[curN].forEach { (nxtN, nxtW) ->
            if (curW+nxtW < dist[nxtN]) {
                dist[nxtN] = curW+nxtW
                pq.add(Pair(dist[nxtN], nxtN))
            }
        }
    }
    return dist
}

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val (N, K) = br.readLine().split(" ").map {it.toInt()}
    val G = Array(N+1) {mutableListOf<Pair<Int, Int>>()}
    repeat(K) {
        val (u, v, w) = br.readLine().split(" ").map {it.toInt()}
        G[u].add(Pair(v, w))
        G[v].add(Pair(u, w))
    }
    val dist = dij(G)
    val dp = LongArray(N+1) {0}
    dp[1] = 1
    val Q = PriorityQueue<Int>(compareBy { -dist[it] })
    val V = BooleanArray(N+1)
    Q.add(1)
    while (!Q.isEmpty()) {
        val curN = Q.poll()
        if (V[curN]) continue
        V[curN] = true
        G[curN].forEach { (nxtN, _) ->
            if (dist[nxtN] < dist[curN]) {
                dp[nxtN] += dp[curN]
                Q.add(nxtN)
            }
        }
    }
    println(dp[2])

    br.close()
    bw.flush()
    bw.close()
}

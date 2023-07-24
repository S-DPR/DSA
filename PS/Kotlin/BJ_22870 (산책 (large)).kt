import java.io.*
import java.util.*
/*
22870번 산책 (large)

정점 S에서 E를 찍고, E에서 다시 S로 오려한다.
단, S에서 E로 갈 때 지나온 정점은 지나지 않으려한다.
S에서 E로 갈 때는 번호가 가장 작은 것을 선호한다 할 떄, 최단거리를 구해보자.

또 문제 제대로 안읽어서 멸망에 이를뻔한 문제
다행히 30분만에 다시 읽어서 다시 읽고 30분만에 풀었습니다.
이렇게 문제 빠르게 풀 수 있으면 백준 참 재밌는데..

거의 최단경로처럼 풀면 됩니다. 그냥 다익스트라 -> bfs -> 다익스트라.
첫 다익스트라는 그냥 돌리고, bfs굴릴떄는 방문한 이전 정점 잡아내고,
set에다가 s에서 e 갈 때 방문한 정점 저장하고.
마지막으로 그 정점 방문 안하는상태로 e에서 s로 가는 다익스트라 굴리고.
틀에 박힌 그래프문제는 역시 할만해요.
*/
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun spi(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun spl(): List<Long> = br.readLine().split(" ").map {it.toLong()}

fun dij(G: Array<MutableList<Pair<Int, Long>>>, x: Int, vis: TreeSet<Int>): LongArray {
    val size = G.size
    val dist = LongArray(size) {1L shl 62}
    val pq = PriorityQueue<Pair<Long, Int>>(compareBy { it.first })
    dist[x] = 0
    pq.add(Pair(0, x))
    while (!pq.isEmpty()) {
        val cur = pq.poll()
        val curN = cur.second
        val curW = cur.first
        if (dist[curN] < curW) continue
        G[curN].forEach { (nxtN, nxtW) ->
            if (nxtW+curW < dist[nxtN] && !vis.contains(nxtN)) {
                dist[nxtN] = nxtW+curW
                pq.add(Pair(dist[nxtN], nxtN))
            }
        }
    }
    return dist
}

fun main() {
    val (N, K) = spi()
    val G = Array(N+1) { mutableListOf<Pair<Int, Long>>()}
    repeat(K) {
        val (u, v, w) = spi()
        G[u].add(Pair(v, w.toLong()))
        G[v].add(Pair(u, w.toLong()))
    }
    val (s, e) = spi()
    val dijS = dij(G, s, TreeSet<Int>())
    val trace = IntArray(N+1) {N+1}
    val Q = ArrayDeque<Int>()
    Q.add(e)
    while (!Q.isEmpty()) {
        val cur = Q.removeFirst()
        G[cur].forEach { (nxtN, nxtW) ->
            if (dijS[cur]-dijS[nxtN] == nxtW && cur < trace[nxtN]) {
                trace[nxtN] = cur
                Q.add(nxtN)
            }
        }
    }
    val vis = TreeSet<Int>()
    var cur = s
    while (cur != e) {
        cur = trace[cur]
        vis.add(cur)
    }
    val dijE = dij(G, e, vis)
    println(dijS[e] + dijE[s])

    br.close()
    bw.flush()
    bw.close()
}

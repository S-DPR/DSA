import java.io.*
import java.util.*
/*
23087번 최단최단경로

그래프가 주어진다. x부터 y까지 이동하는데 최소거리, 건너야하는 최소 노드 수, 그 경로 수를 구해보자.

이게 골드2? 진짜? 어려운데?

저기 어디냐, 플레 5 거의 최단경로 아이디어 빌려서 다익스트라+BFS로 푼 문제.
보통은 다익스트라+dp로 푸나봅니다. 전 모르겠어요.
여하튼, 91%에 다익스트라 잘못된 구현 저걱 데이터가 있어서 5번정도 거기서 틀렸습니다.
이상하게 계속 까먹네..

다익스트라 한번 가볍게 돌리고, dist[nxt]-dist[cur]을 이용해서 다시 굴려주면 됩니다.
물론 그럴려면 현재 몇번만에 도착했는지에 관한 배열, 그 경로 수를 저장하는 배열도 있어야겠죠.
*/
const val INF = 1L shl 62
const val MOD = 1_000_000_009
lateinit var G: Array<MutableList<Pair<Int, Long>>>

fun dij(x: Int): LongArray {
    val N = G.size-1
    val dist = LongArray(N+1) {INF}
    val pq = PriorityQueue<Pair<Int, Long>>(compareBy { it.second })
    dist[x] = 0
    pq.add(Pair(x, 0))
    while (!pq.isEmpty()) {
        val cur = pq.poll()
        val curN = cur.first
        val curW = cur.second
        if (dist[curN] < curW) continue
        G[curN].forEach { (nxtN, nxtW) ->
            if (nxtW+curW < dist[nxtN]) {
                dist[nxtN] = curW+nxtW
                pq.add(Pair(nxtN, dist[nxtN]))
            }
        }
    }
    return dist
}

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val (N, M, x, y) = br.readLine().split(" ").map {it.toInt()}
    G = Array(N+1) { mutableListOf() }
    repeat(M) {
        val (u, v, w) = br.readLine().split(" ").map {it.toInt()}
        G[u].add(Pair(v, w.toLong()))
    }
    val dist = dij(x)
    if (dist[y] != INF) {
        val Q = ArrayDeque<Int>()
        val cnt = LongArray(N+1)
        val pass = LongArray(N+1) { INF }
        val vis = BooleanArray(N+1)
        cnt[x] = 1
        pass[x] = 0
        vis[x] = true
        Q.add(x)
        while (!Q.isEmpty()) {
            val cur = Q.removeFirst()
            G[cur].forEach { (nxtN, nxtW) ->
                if (dist[nxtN]-dist[cur] != nxtW) return@forEach
                if (pass[nxtN] < pass[cur]+1) return@forEach
                if (pass[nxtN] == pass[cur]+1)
                    cnt[nxtN] += cnt[cur]
                if (pass[nxtN] > pass[cur]+1) {
                    cnt[nxtN] = cnt[cur]
                    pass[nxtN] = pass[cur]+1
                }
                cnt[nxtN] = cnt[nxtN]%MOD
                if (!vis[nxtN]) {
                    Q.add(nxtN)
                    vis[nxtN] = true
                }
            }
        }
        bw.write("${dist[y]}\n${pass[y]}\n${cnt[y]%MOD}")
    } else bw.write("-1")

    br.close()
    bw.flush()
    bw.close()
}

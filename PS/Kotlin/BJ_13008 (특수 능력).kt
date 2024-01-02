import java.io.*
import java.util.*
/*
13008번 특수 능력

방향그래프가 주어진다. 1에서 N으로 가려고 한다.
최대 C번 능력을 사용해 이번에 이동할 간선의 가중치에 *-1 연산을 할 수 있다.
연산을 하고 이동한 뒤에는 다시 원래 가중치가 된다.
이 때, N으로 가는 최단경로를 구해보자.

음수? 생각하면서 벨만포드인가 했는데,
이거 아무리봐도 가중치가 낮아져도 curC가 높아지는데.. 하면서 다익스트라.
그리고 언제나 그랬듯이 dp.

그런데, 이제 그냥 다익스트라 내에서 dp를 다 굴리는게 아니라,
C는 한칸씩 올라가면서 처리해줘야합니다.
이거 생각하는데 어려웠어요. 이게 진짜 골드1수준 생각 아닐까 싶어요..
*/
val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun spi(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun spl(): List<Long> = br.readLine().split(" ").map {it.toLong()}

lateinit var G: Array<MutableList<Pair<Int, Long>>>
lateinit var dist: Array<LongArray>
val pq =  PriorityQueue<Triple<Long, Int, Int>>(compareBy{it.first})

fun dij(c: Int) {
    while (pq.isNotEmpty()) {
        val (curW, curN, curC) = pq.poll()
        if (dist[curN][curC] < curW) continue
        G[curN].forEach{(nxtN, nxtW) ->
            if (curC != c && curW-nxtW < dist[nxtN][curC+1]) {
                dist[nxtN][curC+1] = curW-nxtW
            }
            if (curW+nxtW < dist[nxtN][curC]) {
                dist[nxtN][curC] = curW+nxtW
                pq.add(Triple(dist[nxtN][curC], nxtN, curC))
            }
        }
    }
}

fun main() {
    val (N, K, C) = spi()
    G = Array(N+1) { mutableListOf() }
    dist = Array(N+1) { LongArray(C+1) { INFL } }
    dist[1][0] = 0
    repeat(K) {
        val (u, v, w) = spi()
        G[u].add(Pair(v, w.toLong()))
    }
    (0..C).forEach { c ->
        (1..N).forEach { n ->
            if (dist[n][c] != INFL) pq.add(Triple(dist[n][c], n, c))
        }
        dij(C)
    }
    bw.write("${dist[N].min()}")

    bw.flush()
}

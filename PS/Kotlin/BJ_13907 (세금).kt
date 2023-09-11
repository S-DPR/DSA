import java.io.*
import java.util.*
/*
13907번 세금

그래프가 주어진다. S번 노드에서 E번 노드로 가려 한다.
그런데 여기에서, 모든 간선의 가중치를 k만큼 올려보는 행위를 K번 하려한다.
하지 않았을때를 포함해 한 번 행위를 할 때마다 S에서 E로 가는 최단거리를 구해보자.

오늘도 평화로운 다익스트라에 dp얹은 다익스트라 활용 문제
첨엔 이게 왜 플레4인가 했는데 예제 보고 아 간선의 수 저장해야해서 플레4구나, 했습니다.
다익스트라 활용은 항상 흥미로워요.

그냥 다익스트라에서 dp 잘 적용해주고 dist[E][j]에서 잘 긁어주면 됩니다.
다익스트라 큐가 Pair가 아니라 Triple이 된게 핵심.
딱 그정도..
*/
val INFI = 1 shl 29
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun spi(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun spl(): List<Long> = br.readLine().split(" ").map {it.toLong()}

fun dij(N: Int, G: Array<MutableList<Pair<Int, Int>>>, S: Int): Array<IntArray> {
    val dist = Array(N+1) { IntArray(N+1) {INFI} }
    val pq = PriorityQueue(compareBy<Triple<Int, Int, Int>>{it.third}.thenBy{it.first})
    dist[S][0] = 0
    pq.add(Triple(0, S, 0))
    while (pq.isNotEmpty()) {
        val cur = pq.poll()
        val curW = cur.first
        val curN = cur.second
        val curK = cur.third
        if (curK == N) continue
        if (dist[curN][curK] < curW) continue
        G[curN].forEach { (nxtW, nxtN) ->
            if (curW+nxtW < dist[nxtN][curK+1]) {
                dist[nxtN][curK+1]= curW+nxtW
                pq.add(Triple(curW+nxtW, nxtN, curK+1))
            }
        }
    }
    return dist
}

fun main() {
    val (N, M, K) = spi()
    val (S, E) = spi()
    val G = Array(N+1) {mutableListOf<Pair<Int, Int>>()}
    val V = mutableListOf(0)
    repeat(M) {
        val (u, v, w) = spi()
        G[u].add(Pair(w, v))
        G[v].add(Pair(w, u))
    }
    repeat(K) {
        val x = br.readLine().toInt()
        V.add(x+V.last())
    }
    val dist = dij(N, G, S)
    (0..K).forEach { i ->
        val ret = (0..N).minOfOrNull { dist[E][it] + it*V[i] }
        bw.write("${ret}\n")
    }
    bw.flush()
}
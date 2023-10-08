import java.io.*
import java.util.*
/*
13308번 주유소

각 노드에 주유소가 있다. 최소가격으로 N번 노드로 갈 때, 그 가격을 구해보자.
초기에 자동차에는 0L의 연료가 있다.

쉬운 P5 다익스트라. dist[i][j] = i번 노드에서 최소비용이 j일 때 가능한 최소비용.
저거 잡고 풀면 됩니다. 처음엔 2500*2500이면 메모리초과 안날까 했는데 의외로 50MB밖에 안먹는다네요..?
오랜만에 문제 보고 한번에 딱 풀었습니다. 진짜 30분도 안걸린거같은데..
*/
val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun spi(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun spl(): List<Long> = br.readLine().split(" ").map {it.toLong()}

lateinit var G: Array<MutableList<Pair<Int, Int>>>
lateinit var W: IntArray

fun dij(N: Int): Array<LongArray> {
    val dist = Array<LongArray>(N+1) {LongArray(2501) {INFL}}
    val pq = PriorityQueue<Triple<Int, Long, Int>>(compareBy{it.second})
    dist[1][W[1]] = 0
    pq.add(Triple(1, 0, W[1]))
    while (pq.isNotEmpty()) {
        val (curN, curW, curM) = pq.poll()
        if (dist[curN][curM] < curW) continue
        G[curN].forEach { (nxtN, nxtW) ->
            val nxtM = Math.min(curM, W[nxtN])
            val w = curW + curM*nxtW
            if (w < dist[nxtN][nxtM]) {
                dist[nxtN][nxtM] = w
                pq.add(Triple(nxtN, w, nxtM))
            }
        }
    }
    return dist
}

fun main() {
    val (N, K) = spi()
    W = intArrayOf(0) + spi().toIntArray()
    G = Array(N+1) { mutableListOf() }
    repeat(K) {
        val (u, v, w) = spi()
        G[u].add(Pair(v, w))
        G[v].add(Pair(u, w))
    }
    val R = dij(N)
    println((0..2500).map{R[N][it]}.min())
    bw.flush()
}

import java.io.*
import java.util.*
/*
12763번 지각하면 안 돼

1번 정점에서 N번 정점까지 시간 T 이하로 가려한다.
드는 최소비용은 몇일까?

평범하게 상태가 2개인 다익스트라.
대충 dist변형 때려주면 됩니다. 간단하죠.
그런데 깊이우선탐색도 된다네요.. N이 너무 작아서그런가봅니다.
*/
val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun spi(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun spl(): List<Long> = br.readLine().split(" ").map {it.toLong()}

lateinit var G: Array<MutableList<Triple<Int, Int, Int>>>

fun dij(T: Int, M: Int): Array<IntArray> {
    val N = G.size-1
    val dist = Array<IntArray>(N+1) {IntArray(T+1) {INFI}}
    val pq = PriorityQueue<Triple<Int, Int, Int>>(compareBy{it.first})
    dist[1][0] = 0
    pq.add(Triple(0, 0, 1))
    while (pq.isNotEmpty()) {
        val (curM, curT, curN) = pq.poll()
        if (dist[curN][curT] < curM) continue
        G[curN].forEach{(nxtN, nxtT, nxtM) ->
            if (T < curT+nxtT) return@forEach
            if (M < curM+nxtM) return@forEach
            if (curM+nxtM < dist[nxtN][curT+nxtT]) {
                dist[nxtN][curT+nxtT] = curM+nxtM
                pq.add(Triple(dist[nxtN][curT+nxtT], curT+nxtT, nxtN))
            }
        }
    }
    return dist
}

fun main() {
    val N = br.readLine().toInt()
    val (T, M) = spi()
    val L = br.readLine().toInt()
    G = Array(N+1) {mutableListOf()}
    repeat(L) {
        val (u, v, t, w) = spi()
        G[u].add(Triple(v, t, w))
        G[v].add(Triple(u, t, w))
    }
    val dist = dij(T, M)
    val ret = dist[N].min()
    println(if (ret == INFI) -1 else ret)
}

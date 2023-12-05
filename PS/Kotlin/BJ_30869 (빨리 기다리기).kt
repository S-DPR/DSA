import java.io.*
import java.util.*

val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun spi(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun spl(): List<Long> = br.readLine().split(" ").map {it.toLong()}
/*
30869번 빨리 기다리기

정점이 N개인 그래프가 있다.
간선이 있는데, 각 간선은 G시간의 배수에만 사용할 수 있으며 거리는 T만큼이다.
1에서 N으로 가는데 얼마의 시간이 걸릴까?

지금까지 푼 dp다익스트라 문제와는 다르게, 조금 더 다익스트라스러움을 사용한 문제.
이유? 간단해, 시간초과나서..

다익스트라에서는 우선순위 큐에서 빼낸다면, 그 의미는 해당 노드는 이걸로 오는게 최소! 라는 의미입니다.
그래서 거기에 visit 걸어주고..
.. 0부터 K까지 반복하면서 모든 간선을 다 뒤적뒤적했습니다.
솔직히 무슨 의미가 있는진 모르겠는데.. 되긴 하네요?
평소 다익dp처럼하면 같은 정점이 두 번 이상 들어가서 시간손해나서 터지는건가?
*/
lateinit var G: Array<MutableList<Triple<Int, Long, Long>>>

fun dij(K: Int): Array<LongArray> {
    val N = G.size
    val dist = Array<LongArray>(N) {LongArray(K+1) {INFL}}
    val pq = PriorityQueue<Pair<Long, Int>>(compareBy{it.first})
    val V = BooleanArray(N)
    dist[1][0] = 0
    pq.add(Pair(0, 1))
    while (pq.isNotEmpty()) {
        val (_, curN) = pq.poll()
        if (V[curN]) continue
        V[curN] = true
        (0..K).forEach { curK -> 
            val curW = dist[curN][curK]
            if (curW == INFL) return@forEach
            G[curN].forEach{ (nxtN, nxtW, nxtT) ->
                if (V[nxtN]) return@forEach
                val nxt = (curW+nxtT-1)/nxtT*nxtT+nxtW
                if (nxt < dist[nxtN][curK])
                    dist[nxtN][curK] = nxt
                if (curK == K) return@forEach
                val nxtK = curW+nxtW
                if (nxtK < dist[nxtN][curK+1])
                    dist[nxtN][curK+1] = nxtK
            }
        }
        G[curN].forEach { (nxtN, _, _) ->
            val min = (0..K).map{dist[nxtN][it]}.min()
            pq.add(Pair(min, nxtN))
        }
    }
    return dist
}

fun main() {
    val (N, M, K) = spi()
    G = Array(N+1) { mutableListOf<Triple<Int, Long, Long>>() }
    repeat(M) {
        val (s, e, t, g) = spi()
        G[s].add(Triple(e, t.toLong(), g.toLong()))
    }
    val ret = dij(K)[N].min()
    bw.write("${if (ret == INFL) -1 else ret}")
    bw.flush()
}

import java.io.*
import java.util.*
/*
1702번 고속도로

각 도로마다 (걸리는 시간, 내는 비용)이 걸려있다.
s에서 e로 가려 한다. 이 때, 가는 방법의 수를 구해보자.
단, 걸리는 시간과 내는 비용이 모두 작은 방법이 있다면, 그것은 방법으로 세지 않는다.

처음에는 진짜 이게 뭐지 싶은 문제고, 태그 보니까 다익스트라라서 놀랐는데,
의외로 그냥 직관적으로 짜보니까 풀려서 오히려 당황한 문제였습니다.
노드의 개수와 간선의 개수가 100, 300으로 매우 작습니다.
그래서 그냥 다익스트라 내에서 더 나은 방법이 있는지 전수조사 한 뒤,
없으면 힙에 넣고 아니면 넣지 않는 방식으로 했습니다.
간단하게 풀렸네요.
*/
val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun spi(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun spl(): List<Long> = br.readLine().split(" ").map {it.toLong()}

data class Info(val n: Int, val p: Int, val r: Int)
lateinit var G: Array<MutableList<Triple<Int, Int, Int>>>
lateinit var W: IntArray

fun dij(N: Int, s: Int): Array<TreeMap<Int, Int>> {
    val dist = Array<TreeMap<Int, Int>>(N+1) { TreeMap() }
    val pq = PriorityQueue<Info>(compareBy{it.p})
    dist[s].put(0, 0)
    pq.add(Info(s, 0, 0))
    while (pq.isNotEmpty()) {
        val (curN, curP, curR) = pq.poll()
        if (dist[curN][curP]!! < curR) continue
        G[curN].forEach { (nxtN, nxtP, nxtR) ->
            var fail = false
            dist[nxtN].forEach{ (k, v) ->
                fail = fail or (k <= curP+nxtP && v <= curR+nxtR)
            }
            if (!fail) {
                dist[nxtN].put(curP+nxtP, curR+nxtR)
                pq.add(Info(nxtN, curP+nxtP, curR+nxtR))
            }
        }
    }
    return dist
}

fun main() {
    val (N, K, s, e) = spi()
    G = Array(N+1) { mutableListOf() }
    repeat(K) {
        val (u, v, p, r) = spi()
        G[u].add(Triple(v, p, r))
        G[v].add(Triple(u, p, r))
    }
    val R = dij(N, s)
    var ret = 0
    R[e].forEach { (k, v) ->
        var fail = false
        R[e].forEach { (k2, v2) ->
            fail = fail or (k2 < k && v2 <= v)
        }
        if (!fail) ret++
    }
    bw.write("${ret}")
    bw.flush()
}

import java.io.*
import java.util.*
/*
11952번 좀비

노드가 N개인 정점이 있다.
이중 특수 정점이 K개 있다. 이 정점으로는 이동할 수 없다.
특수 정점으로부터 거리가 S 이하인 곳은 q만큼의 가중치를 가진다.
그렇지 않은 곳은 p만큼의 가중치를 가진다.
이 때, 1번에서 N번 노드로 가는 최단거리를 구해보자.

그냥 BFS+다익 순서대로 박으면 AC
딱히 역추적도없고 문제도 쉬워보이고 졸려서 그냥 풀었습니다.
좀비에 감염된 최초 지역은 갈 수 없다는 문장을 늦게봐서 늦게풀었네..
*/
val INFI = 1 shl 30
val INFL = 1L shl 55
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun ini(): Int = br.readLine().toInt()
fun lni(): Long = br.readLine().toLong()
fun ins(): List<Int> = br.readLine().trim().split(" ").map { it.toInt() }
fun lns(): List<Long> = br.readLine().trim().split(" ").map { it.toLong() }

fun main() {
    val (N, M, K, S) = ins()
    val (p, q) = lns()
    val Q = ArrayDeque<Pair<Int, Int>>()
    val G = Array(N+1) { mutableListOf<Int>() }
    val W = LongArray(N+1) { p }
    val O = BooleanArray(N+1)
    repeat(K) {
        val x = ini()
        Q.add(Pair(x, 0))
        W[x] = q
        O[x] = true
    }
    repeat(M) {
        val (u, v) = ins()
        G[u].add(v)
        G[v].add(u)
    }
    while (Q.isNotEmpty()) {
        val (curN, curW) = Q.pollFirst()
        if (curW == S) continue
        G[curN].forEach{nxtN ->
            if (W[nxtN] == p) {
                W[nxtN] = q
                Q.add(Pair(nxtN, curW+1))
            }
        }
    }
    fun dij(s: Int): LongArray {
        val dist = LongArray(N+1) { INFL }
        val pq = PriorityQueue<Pair<Long, Int>>(compareBy{it.first})
        pq.add(Pair(0L, s))
        dist[s] = 0
        while (pq.isNotEmpty()) {
            val (curW, curN) = pq.poll()
            if (dist[curN] < curW) continue
            G[curN].forEach{nxtN ->
                val nxtW = W[nxtN]
                if (!O[nxtN] && curW+nxtW < dist[nxtN]) {
                    dist[nxtN] = curW+nxtW
                    pq.add(Pair(dist[nxtN], nxtN))
                }
            }
        }
        return dist
    }
    println(dij(1)[N]-W[N])
}

import java.io.*
import java.util.*
/*
23807번 두 단계 최단 경로 3

정점이 N개인 양방향 그래프가 주어진다.
N개의 정점 중, 특별한 정점이 K개 있다.
S에서 출발해 K개중 3개를 거친 후 E에 오는 최단거리를 구해보자.

어떻게 정해가 다익스트라 100번돌리기 ㅋㅋ
다돌리고 100C3으로 브루트포스 갖다때리면 끝납니다.
입력이 꽤 돼서그런가 코틀린은 StringTokenizer써야 겨우 맞네요..
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
    val (N, K) = ins()
    val G = Array(N+1) { mutableListOf<Pair<Int, Long>>() }
    repeat(K) {
        val (u, v, w) = ins()
        G[u].add(Pair(v, w.toLong()))
        G[v].add(Pair(u, w.toLong()))
    }
    val (S, E) = ins()
    val X = ini()
    val P = ins()
    fun dij(s: Int): LongArray {
        val dist = LongArray(N+1) { INFL }
        val pq = PriorityQueue<Pair<Long, Int>>(compareBy{it.first})
        pq.add(Pair(0L, s))
        dist[s] = 0
        while (pq.isNotEmpty()) {
            val (curW, curN) = pq.poll()
            if (dist[curN] < curW) continue
            G[curN].forEach{(nxtN, nxtW) ->
                if (curW+nxtW < dist[nxtN]) {
                    dist[nxtN] = curW+nxtW
                    pq.add(Pair(dist[nxtN], nxtN))
                }
            }
        }
        return dist
    }
    val dists = HashMap<Int, LongArray>()
    P.forEach {i -> 
        dists[i] = dij(i)
    }
    var ret = INFL
    P.forEach { i ->
        P.forEach { j ->
            P.forEach { k ->
                if (mutableSetOf(i, j, k).size < 3) return@forEach
                ret = minOf(ret, dists[i]!![S]+dists[i]!![j]+dists[j]!![k]+dists[k]!![E])
            }
        }
    }
    println(if (ret == INFL) -1 else ret)
}

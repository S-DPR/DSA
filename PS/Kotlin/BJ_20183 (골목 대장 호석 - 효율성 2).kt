import java.io.*
import java.util.*
/*
20183번 골목 대장 호석 - 효율성 2

가중치가 있는 그래프가 주어진다.
S에서 E로 갈 때, 지나는 가중치의 최댓값의 최솟값을 구해보자.

간단한 매개변수+다익스트라.
보자마자 바로 알 수 있을수준이죠..
너무 많이 당했어..

lateinit 쓰기 귀찮아서 전부 main안에 때려박았습니다.
편하긴 하네
*/
val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun ini(): Int = br.readLine().toInt()
fun lni(): Long = br.readLine().toLong()
fun ins(): List<Int> = br.readLine().trim().split(" ").map { it.toInt() }
fun lns(): List<Long> = br.readLine().trim().split(" ").map { it.toLong() }

fun main() {
    val (N, M, S, E, C) = lns()
    val G = Array(N.toInt()+1) { mutableListOf<Pair<Int, Long>>() }
    repeat(M.toInt()) {
        val (ul, vl, w) = lns()
        val (u, v) = Pair(ul.toInt(), vl.toInt())
        G[u].add(Pair(v, w))
        G[v].add(Pair(u, w))        
    }

    var (lo, hi) = Pair(0.toLong(), C+1)
    while (lo < hi) {
        val mid = (lo + hi) shr 1

        val pq = PriorityQueue<Pair<Long, Int>>(compareBy{it.first})
        val dist = LongArray(N.toInt()+1) { INFL }
        pq.add(Pair(0, S.toInt()))
        dist[S.toInt()] = 0
        while (pq.isNotEmpty()) {
            val (curW, curN) = pq.poll()
            if (dist[curN] < curW) continue
            G[curN].forEach { (nxtN, nxtW) ->
                if (nxtW <= mid && curW+nxtW < dist[nxtN]) {
                    dist[nxtN] = curW+nxtW
                    pq.add(Pair(dist[nxtN], nxtN))
                }
            }
        }

        if (dist[E.toInt()] <= C) {
            hi = mid
        } else {
            lo = mid + 1
        }
    }
    println(if (hi <= C) hi else -1)

    bw.flush()
}

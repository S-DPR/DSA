import java.io.*
import java.util.*

val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun nxti(): Int = br.readLine().toInt()
fun nxtl(): Long = br.readLine().toLong()
fun spi(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun spl(): List<Long> = br.readLine().split(" ").map {it.toLong()}
/*
23975번 정훈이는 민트초코맛 짜장라면이 먹고 싶다

각 노드에 몇 개의 상품이 있는지 주어진다.
이후 Q개의 질문이 주어진다. 각 질문의 의미는 아래와 같다.
x : x번 노드에서 출발해 1번 노드로 갈 때, 가장 먼저 만나는 상품이 있는 노드를 출력하라.
단, 항상 최단 경로로 이동하며, 최단 경로가 여러개라면 사전순으로 뒤에 오는 방법으로 이동한다.
각 쿼리를 통해 만난 노드의 상품은 1 줄어들고, 이는 누적된다.

으아악 민트초코 짜장라면은 좀..

어떻게할까 하다가.. 다익스트라 결과가 트리로 나온다는 점 사용했습니다.
1에서 다익스트라 쓰고, 그거로 트리를 구축합니다.
이제 각 쿼리마다 루트노드로 가면서 먼저 만나는 상품이 있는 노드를 출력하면 됩니다.
이 때, 0인거를 빠르게 넘기기 위해 유니온파인드까지 써줍니다.
휴..
*/
lateinit var G: Array<MutableList<Pair<Int, Long>>>
lateinit var P: IntArray
lateinit var U: IntArray

fun dij(): LongArray {
    val N = G.size-1
    val dist = LongArray(N+1) {INFL}
    val pq = PriorityQueue<Pair<Int, Long>> { i, j ->
        if (i.second != j.second)
            i.second.compareTo(j.second)
        else i.first.compareTo(j.first)
    }
    dist[1] = 0
    pq.add(Pair(1, 0))
    while (pq.isNotEmpty()) {
        val (curN, curW) = pq.poll()
        if (dist[curN] != curW) continue
        G[curN].forEach{ (nxtN, nxtW) ->
            if (curW+nxtW < dist[nxtN]) {
                dist[nxtN] = curW+nxtW
                pq.add(Pair(nxtN, dist[nxtN]))
            }
        }
    }
    return dist
}

fun constructTreeG(dist: LongArray) {
    val Q = ArrayDeque<Triple<Int, Long, Int>>()
    Q.add(Triple(1, 0, 0))
    while (Q.isNotEmpty()) {
        val (curN, curW, curP) = Q.poll()
        if (P[curN] != curP) continue
        G[curN].forEach{ (nxtN, nxtW) ->
            if (curW+nxtW == dist[nxtN] && P[nxtN] < curN) {
                P[nxtN] = curN
                Q.add(Triple(nxtN, dist[nxtN], curN))
            }
        }
    }
}

fun union(x: Int, y: Int): Boolean {
    val xp = find(x)
    val yp = find(y)
    U[yp] = U[xp]
    return xp != yp
}

fun find(x: Int): Int {
    if (U[x] != x) U[x] = find(U[x])
    return U[x]
}

fun main() {
    val (N, M, Q) = spi()
    val R = intArrayOf(0) + spi()
    U = IntArray(N+1) {it}
    G = Array(N+1) {mutableListOf()}
    P = IntArray(N+1)
    repeat(M) {
        val (u, v, w) = spi()
        G[u].add(Pair(v, w.toLong()))
        G[v].add(Pair(u, w.toLong()))
    }
    val dist = dij()
    constructTreeG(dist)
    repeat(Q) {
        var x = find(nxti())
        if (dist[x] == INFL) {
            bw.write("-1\n")
            return@repeat
        }
        while (R[x] == 0 && P[x] != x) {
            val pp = find(P[x])
            if (R[x] == 0)
                union(pp, x)
            x = pp
        }
        if (x == 0) {
            bw.write("-1\n")
        } else {
            R[x]--
            bw.write("${x}\n")
        }
    }

    bw.flush()
}

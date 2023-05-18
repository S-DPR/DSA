import java.io.*
import java.util.*
/*
20313번 출퇴근

그래프가 주어지고 A에서 B로 가려고 한다.
당신은 모든 간선에 대해 그 가중치를 바꿀 수 있다.
즉, 간선만 그대로 두고 모든 간선의 가중치만 바꾼다는 이야기이다.
이를 K번 할 수 있다. A에서 B로 가는 최단경로를 구해보자.

문제 잘못읽어서 날린 시간? "120분"
..이라고는 하지만 10의 4승을 1000이라고 생각해서 180분을 날린 기억이 있어서,
이정도는 웃어넘길 수 있...나..?

어쨌든 저는 dist를 2차원으로 해서 dist[i][j] = i번 마법을 사용했을 때 j정점에서 최단거리를 했고,
그렇게 다익스트라를 돌렸습니다. 그러면 풀려요.
앞으론 문제 잘 읽어보는거로..
*/
fun dij(G: Array<IntArray>, C: Array<MutableList<Int>>, E:MutableList<LongArray>, S: Int): Array<LongArray> {
    val N = G.size
    val edgeCount = E.size
    val dist = Array(edgeCount) {LongArray(N) { Long.MAX_VALUE }}
    val pq = PriorityQueue<Triple<Long, Int, Int>>(compareBy{it.first})
    dist[0][S] = 0
    pq.add(Triple(0, S, 0))
    while (!pq.isEmpty()) {
        val (curW, curN, curK) = pq.poll()
        if (dist[curK][curN] < curW) continue
        C[curN].forEach { nextN ->
            val nextC = G[curN][nextN]
            (curK until edgeCount).forEach { nextK ->
                val nextW = E[nextK][nextC]
                if (curW + nextW < dist[nextK][nextN]) {
                    dist[nextK][nextN] = curW + nextW
                    pq.add(Triple(dist[nextK][nextN], nextN, nextK))
                }
            }
        }
    }
    return dist
}

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val (N, M, A, B) = br.readLine().split(" ").map {it.toInt()}
    val E = mutableListOf(LongArray(M) { -1 })
    val G = Array(N+1) { IntArray(N+1) { -1 } }
    val C = Array(N+1) { mutableListOf<Int>() }
    repeat(M) {
        val (U, V, T)= br.readLine().split(" ").map {it.toInt()}
        E[0][it] = T.toLong()
        G[U][V] = it
        G[V][U] = it
        C[U].add(V)
        C[V].add(U)
    }
    val K = br.readLine().toInt()
    repeat(K) {
        val items = br.readLine().split(" ").map {it.toLong()}.toLongArray()
        E.add(items)
    }
    println(dij(G, C, E, A).minOfOrNull { it[B] })

    br.close()
    bw.flush()
    bw.close()
}

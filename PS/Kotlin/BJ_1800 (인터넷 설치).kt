import java.io.*
import java.util.PriorityQueue

const val inf = 1_000_000_000
/*
1800번 인터넷 설치

가중치가 있는 그래프가 주어진다.
1부터 N까지 가려고 하는데, 가는 도중 건너는 간선의 집합을 S라고 하자.
이 S를 오름차순으로 정렬한 후, 가장 큰 K개의 간선들은 무시하고, K-1번째 간선의 길이만 최소로 하고싶다.
만약 S의 원소가 정확히 K개거나 K개보다 적다면 0을 출력하고,
1부터 N까지 갈 수 없다면 -1을,
그게 아니라면 K-1번째로 긴 간선을 출력해보자.

빅-데이터의 승리
며칠전에 BFS+매개변수탐색이라는 해괴한 플레4문제에 맞고와서 아직 쓰라린데,
그냥 오늘 암거나 잡아서 푸니까 딱 다익스트라+매개변수탐색이 걸렸습니다.
이게 이렇게 푸는게 맞나.. 하면서 일단 틀리면 생각해보자, 하고 제출했는데,
맞아서 오히려 정말 놀랐습니다. 태그도 정확히 다익스트라+매개변수탐색.

휴.. 30분내로 풀어서 다행이다..
*/
fun dij(G: Array<MutableMap<Int, Int>>, K: Int, limit: Int) : Int {
    val visit = IntArray(G.size) { inf }
    val Q = PriorityQueue<IntArray> (compareBy { it[0] })
    Q.add(intArrayOf(0, 1))
    visit[1] = K
    while (Q.isNotEmpty()) {
        val (curL, curN) = Q.remove()
        if (visit[curN] < curL) continue
        G[curN].forEach{ (nextN, nextW) ->
            if (curL < K && visit[nextN] > curL+1 && nextW > limit) {
                Q.add(intArrayOf(curL + 1, nextN))
                visit[nextN] = curL+1
            }
            if (visit[nextN] > curL && nextW <= limit) {
                Q.add(intArrayOf(curL, nextN))
                visit[nextN] = curL
            }
        }
    }
    return visit[G.size-1]
}

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val (N, M, K) = br.readLine().split(" ").map {it.toInt()}
    val G = Array(N+1) {mutableMapOf<Int, Int>()}
    repeat(M) {
        val (u, v, w) = br.readLine().split(" ").map {it.toInt()}
        if (G[u].containsKey(v)) {
            G[u][v] = minOf(G[u][v]!!, w)
            G[v][u] = minOf(G[v][u]!!, w)
        } else {
            G[u][v] = w
            G[v][u] = w
        }
    }
    var (lo, hi) = intArrayOf(0, 1_000_001)
    while (lo < hi) {
        val mid = (lo + hi) shr 1
        val res = dij(G, K, mid)
        if (res in 0 until inf) hi = mid
        else lo = mid + 1
    }
    println(if (hi == 1_000_001) -1 else hi)

    br.close()
    bw.flush()
    bw.close()
}

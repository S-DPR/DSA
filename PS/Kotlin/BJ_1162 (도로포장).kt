import java.io.*
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.min
/*
1162번 도로포장

그래프가 주어진다. 1에서 N으로 갈건데, K개의 도로까지는 거리 0으로 이동할 수 있다.
1에서 N까지 가는데 최단거리를 출력해보자.

옛날에 야 그냥 이거 대충 우선순위큐도 같이 넣어주면 되는거 아니냐~? 쉽네~ 하고 북마크에 박았다가,
한번 풀어보려니 어..어어..어ㅓ/?? 이러고 도망친 문제
갑자기 생각나서 풀었습니다.

문제가 문제다보니 다익스트라는 다익스트라인데, 거기에 2차원을 섞은 문제입니다.
BFS로 다 뒤진다음 최소값 찾으려면 100% 시간초과죠 뭐..
솔직히 아이디어는 첫번째 트라이부터 바로 성공했습니다. 그런데 메모리초과 엄청 맞았습니다..

내가 로직이 틀렸나? 하지만 내 로직은 완벽한데? 라는 생각으로 파이썬으로 구현해본 결과 AC를 맞고,
코틀린 유저들의 코드를 본 결과 저는 힙에 넣을때 longArrayOf를 썼는데 이사람들은 Triple을 쓰더라고요?
그래서 저도 Triple을 써봤다? 그런데 AC인거에요 이거 얼마나 억울해

후.. 어쨌든.. 좋은문제긴 합니다.. 태그에 dp도 있는데 솔직히 visit배열을 dp라곤 안하잖아요?
애초에 그게 dp면 벽 부수고 이동하기도 dp인거고.. 하니까요.
저는 위 이유로 그냥 다익스트라 활용문제라고 생각하고 있습니다.
*/
fun dij(G: Array<HashMap<Int, Long>>, K: Int): Array<LongArray> {
    val N = G.size
    val INF = 1.toLong() shl 62
    val dist = Array(N) {LongArray(K+1) {INF} }
    val pq = PriorityQueue<Triple<Int, Long, Int>>(compareBy { it.second })
    dist[1][0] = 0
    pq.add(Triple(1, 0, 0)) // where, dist, K
    while (pq.isNotEmpty()) {
        val (curN, curW, curK) = pq.poll()
        if (dist[curN][curK] < curW) continue
        G[curN].forEach { (nextN, nextW) ->
            if (curW+nextW < dist[nextN][curK]) {
                dist[nextN][curK] = curW+nextW
                pq.add(Triple(nextN, dist[nextN][curK], curK))
            }
            if (curK+1 <= K && curW < dist[nextN][curK+1]) {
                dist[nextN][curK+1] = curW
                pq.add(Triple(nextN, dist[nextN][curK+1], curK+1))
            }
        }
    }
    return dist
}

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val INF = 1.toLong() shl 62
    val (N, M, K) = br.readLine().split(" ").map {it.toInt()}
    val G = Array(N+1) {HashMap<Int, Long>()}
    repeat(M) {
        val (u, v, w) = br.readLine().split(" ").map {it.toInt()}
        G[u][v] = min(G[u].getOrDefault(v, INF), w.toLong())
        G[v][u] = min(G[v].getOrDefault(u, INF), w.toLong())
    }
    bw.write("${dij(G, K)[N].min()}")

    br.close()
    bw.flush()
    bw.close()
}
/*
건성건성 쓴 파이썬 코드입니다.
import sys, heapq
from collections import defaultdict
input = sys.stdin.readline

def dij():
    visit = [[INF]*(k+1) for _ in ' '*(n+1)]
    pq = [[0, 1, 0]]
    while pq:
        curW, curN, curK = heapq.heappop(pq)
        if visit[curN][curK] < curW: continue
        for nextN, nextW in G[curN].items():
            if curW+nextW < visit[nextN][curK]:
                visit[nextN][curK] = curW+nextW
                heapq.heappush(pq, [visit[nextN][curK], nextN, curK])
            if curK+1 <= k and curW < visit[nextN][curK+1]:
                visit[nextN][curK+1] = curW
                heapq.heappush(pq, [curW, nextN, curK+1])
    return min(visit[-1])

INF = 1<<63
n, m, k = map(int, input().split())
G = [defaultdict(lambda: INF) for _ in ' '*(n+1)]
for _ in ' '*m:
    u, v, w = map(int, input().split())
    G[u][v] = min(G[u][v], w)
    G[v][u] = min(G[v][u], w)
print(dij())
*/
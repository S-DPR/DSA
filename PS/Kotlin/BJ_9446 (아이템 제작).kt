import java.io.*
import java.util.*
/*
9446번 아이템 제작

i번 아이템을 만드는 데는 P[i]의 비용이 든다.
그리고 M개의 조합법이 있다. 조합법은 x a b로 이루어져있는데, a와 b 아이템을 사용해 x를 만들 수 있다는 의미이다.
1번 아이템을 만들려 한다. 최소비용을 구해보자.

진짜 충격과 공포다 와
답지보고 풀었는데 발상이 놀랍네

일단.. 너무 당연해서 생각도 안한 부분인 P[a]+P[b] <= P[x].
이 성질때문에 P[i]중 가장 작은 녀석을 먼저 처리하는 것이 가능해집니다.
그상태로 다익스트라를 굴려서 맞추기..

1. 그래프가 이런 모양으로도 나올 수 있구나
2. 이거 문제 위상정렬인지알았는데 아니었구나
등등.. 많은 것을 느꼈습니다.
*/
val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun spi(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun spl(): List<Long> = br.readLine().split(" ").map {it.toLong()}

fun main() {
    val (N, M) = spi()
    val P = spl().toMutableList()
    val G = Array(N+1) {mutableListOf<Pair<Int, Int>>()}
    val pq = PriorityQueue<Pair<Int, Long>>(compareBy{it.second})
    val V = BooleanArray(N+1)
    P.add(0, -1)
    repeat(N) {
        pq.add(Pair(it+1, P[it+1]))
    }
    repeat(M) {
        val (x, u, v) = spi()
        G[u].add(Pair(x, v))
        G[v].add(Pair(x, u))
    }
    while (pq.isNotEmpty()) {
        val cur = pq.poll()
        val curN = cur.first
        val curW = cur.second
        if (V[curN]) continue
        V[curN] = true
        G[curN].forEach{(obj, other) ->
            if (V[other] && curW+P[other] < P[obj]) {
                P[obj] = curW+P[other]
                pq.add(Pair(obj, P[obj]))
            }
        }
    }
    println(P[1])
}

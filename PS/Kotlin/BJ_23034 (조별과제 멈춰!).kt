import java.io.*
import java.util.*

val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun spi(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun spl(): List<Long> = br.readLine().split(" ").map {it.toLong()}
/*
23034번 조별과제 멈춰!

연결그래프가 주어진다. 모든 점을 최소비용으로 연결한다 할 때, 두 정점을 시작점으로 하려한다.
이 때 간선 가중치 합의 최소를 구하시오.

이야 MST 얼마만이냐
진짜 몇달만에 다시만났다

다익스트라 그렸다가 어어 망했네 싶어서 누워서 생각 좀 하니 MST가 나온 문제
누워서 이거저거 아는거 꺼내보니 MST가 나왔습니다. 휴..

먼저 그래프를 MST로 만듭니다.
그리고 MST에서 두 정점이 선택되면, 두 정점 사이에 있는 가장 가중치가 큰 간선을 잘라냅니다.
이러면 AC. 와 간단해!
*/
lateinit var G: Array<MutableList<Pair<Int, Long>>>
lateinit var E: MutableList<Triple<Int, Int, Long>>
lateinit var U: IntArray

fun dfs(V: BooleanArray, x: Int, d: Int, m: Long): Pair<Boolean, Long> {
    if (V[x]) return Pair(false, 0)
    if (x == d) return Pair(true, m)
    V[x] = true
    G[x].forEach { (nxtN, nxtW) ->
        val (ret, dst) = dfs(V, nxtN, d, Math.max(m, nxtW))
        if (ret) return Pair(true, dst)
    }
    return Pair(false, 0)
}

fun union(x: Int, y: Int) {
    U[find(x)] = U[find(y)]
}

fun find(x: Int): Int {
    if (U[x] != x)
        U[x] = find(U[x])
    return U[x]
}

fun main() {
    val (N, K) = spi()
    G = Array(N+1) { mutableListOf() }
    E = mutableListOf()
    U = IntArray(N+1) { it }
    repeat (K) {
        val (u, v, w) = spi()
        E.add(Triple(u, v, w.toLong()))
    }
    var weight = 0L
    E.sortBy { it.third }
    E.forEach { (u, v, w) ->
        if (find(u) != find(v)) {
            G[u].add(Pair(v, w))
            G[v].add(Pair(u, w))
            union(u, v)
            weight += w
        }
    }
    val Q = br.readLine().toInt()
    repeat(Q) {
        val (u, v) = spi()
        bw.write("${weight-dfs(BooleanArray(N+1), u, v, 0).second}\n")
    }

    br.close()
    bw.flush()
    bw.close()
}

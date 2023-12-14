import java.io.*
import java.util.*

val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun spi(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun spl(): List<Long> = br.readLine().split(" ").map {it.toLong()}
/* 
13016번 내 왼손에는 흑염룡이 잠들어 있다

가중치가 있는 트리가 주어진다.
모든 노드에 대해, 가장 먼 노드와의 거리를 구해보자.

dp 어떻게 잘 굴리면되겠다 하면서 3시간동안 열심히 짜서 성공했는데,
알고보니 트리의 지름 변형문제라길래..
진짜 세상이 너무 싫다.. 그런 생각이 막 들고..
*/
lateinit var G: Array<MutableList<Pair<Int, Long>>>
lateinit var M: LongArray
lateinit var R: LongArray

fun dfs(x: Int): Long {
    M[x] = 0
    G[x].forEach{ (nxtN, nxtW) ->
        if (M[nxtN] != -1L) return@forEach
        M[x] = Math.max(M[x], dfs(nxtN)+nxtW)
    }
    return M[x]
}

fun loop(x: Int, pdist: Long) {
    val mx = mutableListOf<Pair<Int, Long>>()
    mx.add(Pair(0, pdist))
    G[x].forEach { (nxtN, nxtW) ->
        if (R[nxtN] == -1L) {
            mx.add(Pair(nxtN, M[nxtN]+nxtW))
        }
    }
    mx.sortWith(compareBy{-it.second})
    G[x].forEach{ (nxtN, nxtW) ->
        if (R[nxtN] == -1L) {
            R[x] = Math.max(R[x], M[nxtN]+nxtW)
            val pdist_ = if (mx[0].first == nxtN) mx[1].second else mx[0].second
            loop(nxtN, pdist_+nxtW)
        } else {
            R[x] = Math.max(R[x], pdist)
        }
    }
}

fun main() {
    val N = br.readLine().toInt()
    G = Array(N+1) {mutableListOf()}
    M = LongArray(N+1) { -1 }
    R = LongArray(N+1) { -1 }
    repeat(N-1) {
        val (u, v, w) = spi()
        G[u].add(Pair(v, w.toLong()))
        G[v].add(Pair(u, w.toLong()))
    }
    dfs(1)
    loop(1, 0)
    (1..N).forEach{bw.write("${R[it]}\n")}
    bw.flush()
}

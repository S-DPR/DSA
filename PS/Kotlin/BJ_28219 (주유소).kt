import java.io.*
import java.util.*
/*
28219번 주유소

트리가 주어진다. 거리가 K인 모든 정점 쌍 (i, j)에 대해, R개의 정점중 하나는 지나치게 하려 한다.
R의 최솟값은 몇일까?

그치..
트리 문제 그동안 너무 쉽다 히히 하고 살았지..
잊은게 너무 많았지 그지..

25123번 (좋은 단순 경로)을 잊어서 멸망해버린 문제.
아이디어는 바로 냈는데 계속 틀려서 꺾어버리고,
태그봐도 그리디여서 아니 내가 생각한건 그리디가 아니었나? 혼동오다가,

며칠 후에 답지 보고 아 내가 생각했던게 맞았구나.
그런데 내가 자손노드의 정보를 합쳐서 처리 안해줘서 망했구나..

.. 이제 못잊을거같네..
*/
val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun spi(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun spl(): List<Long> = br.readLine().split(" ").map {it.toLong()}

lateinit var G: Array<MutableList<Int>>
lateinit var V: IntArray
var ret = 0

fun dfs(x: Int, k: Int): Int {
    V[x] = 0
    var child = mutableListOf<Int>()
    G[x].forEach {
        if (V[it] != -2) return@forEach
        val nxt = dfs(it, k)+1
        V[x] = Math.max(V[x], nxt)
        child.add(nxt)
    }
    child.sort()
    child.reverse()
    if (child.size > 1 && child[0]+child[1] >= k) V[x] = k
    if (V[x] == k) {
        V[x] = -1
        ret++
    }
    return V[x]
}

fun main() {
    val (N, K) = spi()
    G = Array(N+1) {mutableListOf()}
    V = IntArray(N+1) {-2}
    repeat(N-1) {
        val (u, v) = spi()
        G[u].add(v)
        G[v].add(u)
    }
    dfs(1, K)
    println(Math.max(ret, 1))

    bw.flush()
}

import java.io.*
import java.util.*
/*
1272번 특별 노드

루트가 주어진 트리가 주어지고, 루트는 특별 노드로 고정이다.
특별노드가 아닌 노드는 부모노드 중 가장 가까운 특별노드만큼 가중치가 줄어든다.
특별노드를 원하는대로 선택할 수 있다. 트리 전체의 최소 가중치를 구하시오.

음..
그리디인줄알았고 트리dp는 그냥 자식개수 세는거때문에 있는줄알았는데..
N^2트리dp는 처음이네..
개수 잘 보고 잘해야겠네..
*/
val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun ini(): Int = br.readLine().toInt()
fun lni(): Long = br.readLine().toLong()
fun ins(): List<Int> = br.readLine().trim().split(" ").map { it.toInt() }
fun lns(): List<Long> = br.readLine().trim().split(" ").map { it.toLong() }

lateinit var A: IntArray
lateinit var G: Array<MutableList<Int>>
lateinit var V: BooleanArray
lateinit var dp: Array<IntArray>

fun dfs(x: Int, isSelect: Int, parent: Int): Int {
    if (dp[x][isSelect] != -1) return dp[x][isSelect]
    dp[x][isSelect] = if (x == isSelect) A[x] else A[x]-A[isSelect]
    G[x].forEach {i ->
        if (parent != i) dp[x][isSelect] += Math.min(dfs(i, i, x), dfs(i, isSelect, x))
    }
    return dp[x][isSelect]
}

fun main() {
    val (N, R) = ins()
    A = intArrayOf(0) + ins()
    G = Array(N+1) { mutableListOf() }
    V = BooleanArray(N+1)
    dp = Array(N+1) { IntArray(N+1) { -1 } }
    repeat(N-1) {
        val (u, v) = ins()
        G[u].add(v)
        G[v].add(u)
    }
    println(dfs(R, R, -1))

    bw.flush()
}

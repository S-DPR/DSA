import java.io.*
import java.util.*
/*
14432번 우물

트리가 주어진다. 각 노드는 필요한 가중치가 있는데, 가중치는 자신과 연결된 모든 정점의 가중치 + 자신의 가중치이다.
각각의 노드가 가져야하는 가중치가 주어진다.
각 정점에 가중치를 부여할 때, 그 합을 최소로 하여 부여해보자.

트리 아닌척 간선의 수 m도 입력받게 만드는 문제.
매우 자연스럽게 '모든 정점은 연결되어있고 정점 A에서 B로 이동하는 경로는 정확히 하나 존재한다..'
가 써져있어서 트리 정의 모르면 못풀게 해버렸네요.

대충 dfs 한번 굴리는데,
특성상 부모노드에 무조건 가중치를 부여하는게 좋습니다.
dp가 맞나 모르겠지만 dp를 정의하자면,
dp[i] = i번째 노드가 자식노드의 모든 가중치를 처리하는데 필요한 최소 가중치
로 해서 마지막에 dp값 모두 더하면 됩니다.

특성상 1번이 최상단이 되면 제대로 작동을 안하기에,
가상노드 0번을 만들어서 0-1을 잇고 0에서 출발을 했습니다.
*/
val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun spi(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun spl(): List<Long> = br.readLine().split(" ").map {it.toLong()}

lateinit var G: Array<MutableList<Int>>
lateinit var A: LongArray
lateinit var dp: LongArray

fun dfs(x: Int, p: Int): Long {
    dp[x] = 0
    G[x].forEach {
        if (dp[it] != -1L) return@forEach
        val nxt = dfs(it, x)
        dp[x] = Math.max(dp[x], A[it]-nxt)
    }
    if (p != -1) {
        A[p] = Math.max(0, A[p]-dp[x])
    }
    return dp[x]
}

fun main() {
    val (N, _) = spi()
    G = Array(N+1) {mutableListOf<Int>()}
    A = longArrayOf(0) + spl().toLongArray()
    dp = LongArray(N+1) {-1}
    G[0].add(1)
    repeat(N-1) {
        val (u, v) = spi()
        G[u].add(v)
        G[v].add(u)
    }
    dfs(0, -1)
    println(dp.sum())
    bw.flush()
}

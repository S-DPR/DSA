import java.io.*
import java.util.*
/*
1949번 우수 마을

트리가 있다. 각 노드에는 점수가 있다.
노드를 적절하게 고르려한다. 이 때, 고른 노드는 서로 인접해서는 안된다.
노드를 적절히 골랐을 때 최대 점수를 구해보자.

어..? 왜됐지..?
그냥 던져보니까 됐네..?
이딴게.. 트리DP 기초..?

이게 머.. 그리디적인 자명함 증명.. 어쩌구.. 이러는데..
이게 그니까 3번조건에 뭐가있었냐면, [ 선택받지 못한 노드는 선택받은 노드와 적어도 한 개 이상 인접해야한다. ] 라는게 있었습니다.
난이도 올리는 요인이 저거라는데 아직도 이해가 안가네.. 저게 의미가 없는 조건이라고?
좀 찾아봐야겠는데 이거..
*/
lateinit var depth: IntArray
lateinit var dp: Array<LongArray>
lateinit var arr: LongArray
lateinit var G: Array<MutableList<Int>>
fun dfs(V: BooleanArray, cur: Int, dep: Int) {
    if (V[cur]) return
    V[cur] = true
    depth[cur] = dep
    G[cur].forEach {
        dfs(V, it, dep+1)
    }
    dp[cur][1] = arr[cur]
    G[cur].forEach {
        if (depth[it] == dep+1) {
            dp[cur][0] += dp[it].max()
            dp[cur][1] += dp[it][0]
        }
    }
}

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val N = br.readLine().toInt()
    arr = longArrayOf(0) + br.readLine().split(" ").map { it.toLong() }
    G = Array(N+1) { mutableListOf() }
    depth = IntArray(N+1) { -1 }
    dp = Array(N+1) {LongArray(2) { 0 } }
    repeat(N-1) {
        val (u, v) = br.readLine().split(" ").map {it.toInt()}
        G[u].add(v)
        G[v].add(u)
    }
    dfs(BooleanArray(N+1), 1, 1)
    println(dp.map{it.max()}.max())

    br.close()
    bw.flush()
    bw.close()
}

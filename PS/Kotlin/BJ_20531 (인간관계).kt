import java.io.*
import java.util.*
/*
20531번 인간관계

1, 2, ..., N개의 수가 있다. 그리고, 아래 조건중 하나라도 부합하면 수들은 친구관계라고 할 수 있다.
1. 자기 자신은 자기 자신과 친구이다.
2. X와 Y가 친구라면 Y도 X의 친구이다.
3. X와 Y가 친구고, Y와 Z가 친구면 X와 Z도 친구이다.
아직 X, Y가 친구가 아니다라는 뜻은, X와 Y는 친구일수도있고 아닐수도 있다는 뜻이다.
M개의 쿼리가 주어진다. 각 쿼리는 X, Y로 이루어져있으며 X, Y는 서로 친구가 되었다는 뜻이다.
각 쿼리마다 가능한 친구관계의 수를 출력하라.

오묘한 DP문제
일단 문제를 이렇게 바꾸는게 좋습니다. 
1~N을 집합 K개로 만들어 넣을 수 있는 방법의 수의 합 (1 <= K <= N) 쿼리 처리.

약간 피보나치느낌이 나는 재귀 메모이제이션 방식을 사용하며,
각 쿼리가 주어졌을 때 둘이 이미 친구인지 확인하고, 친구가 아니면 N을 1 줄여버리는 방식으로 처리하면 됩니다.
1과 2가 친구라면, 1과 2를 하나의 수로 취급한다는 느낌으로.
그렇게해서 각 수마다 dp[N]을 출력하면 되겠죠. 

스털링수를 구하는것에서 DP를, 둘이 이미 친구인지 확인하는 점에서 유니온파인드를 사용하는 문제였습니다.
*/
const val MOD = 1_000_000_007

fun stirling(dp: Array<LongArray>, n:Int, k:Int): Long {
    if (dp[n][k] == -1L)
        dp[n][k] = stirling(dp, n-1, k-1) + stirling(dp, n-1, k) * k
    return dp[n][k] % MOD
}

fun union(G: IntArray, x: Int, y: Int) {
    G[find(G, x)] = G[find(G, y)]
}

fun find(G: IntArray, x: Int): Int {
    if (G[x] != x)
        G[x] = find(G, G[x])
    return G[x]
}

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val (N, M) = br.readLine().split(" ").map {it.toInt()}
    val dp = Array(N+1) {LongArray(N+1) {-1} }
    for (i in 0..N) {
        dp[0][i] = 0
        dp[i][0] = 0
    }
    dp[1][1] = 1
    for (i in 1..N)
        for (j in 1..i)
            stirling(dp, i, j)
    val dp_ = LongArray(N+1) { dp[it].sum()%MOD }

    val G = IntArray(N+1) {it}
    var cur = N

    repeat(M) {
        val (u, v) = br.readLine().split(" ").map {it.toInt()}
        if (u != v && find(G, u) != find(G, v)) {
            union(G, u, v)
            cur--
        }
        bw.write("${dp_[cur]}\n")
    }

    br.close()
    bw.flush()
    bw.close()
}
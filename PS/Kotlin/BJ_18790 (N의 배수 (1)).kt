import java.io.*
import java.util.*
/*
18790번 N의 배수 (1)

2N-1개의 수가 주어질 때, 합이 N으로 나누어 떨어지는 수 N개를 구해보자.

옛날옛날에 문제 제목이 너무 멋져보여서 들어왔다가 이게뭐여 이러고 도망쳤던 문제.
대충 dp 긁으니까 되긴 하네요.
dp[i][j][k] = i번째 수를 고르는데, 현재까지 더하고 모듈러N한 값은 j이고, k=0에는 자기 조상을, k=1에는 자기 수를 넣기.
배낭활용이라고 생각하는데 배낭은 없네요..

그나저나 이게 N이 지금 500이하인데 골드2인거고, 50000까지 가면 루비5까지 가네.. 무섭다 무서워..
*/
fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val N = br.readLine().toInt()
    val dp = Array(N+1) {Array(N) {Pair(-1, 0)} }
    // dp[i][j] : i번 더했고 현재 값은 j%N
    // Pair(i, j) : i번째를 조상으로 하고현재 더한 값은 j; i가 -1이면 업데이트 안된 것
    val arr = br.readLine().split(" ").map {it.toInt()}
    arr.forEach { cur ->
        (N-1 downTo 1).forEach { i ->
            (N-1 downTo 0).forEach { j ->
                if (dp[i][j].first != -1) {
                    val newV = (j+cur)%N
                    if (dp[i+1][newV].first == -1)
                        dp[i+1][newV] = Pair(j, cur)
                }
            }
        }
        dp[1][cur] = Pair(0, cur)
    }
    var idx = N
    var retA = 0
    if (dp[N][0].first == -1) println(-1)
    else while (idx != 0) {
        val retV = dp[idx][retA].second
        bw.write("$retV ")
        retA = dp[idx--][retA].first
    }

    br.close()
    bw.flush()
    bw.close()
}

import java.io.*
import kotlin.math.max
/*
17845번 수강 과목

투자할 수 있는 시간 N, 과목의 수 M이 주어진다.
이후 M개의 줄에 k번째 과목의 중요도 I와 걸리는 시간 T가 주어진다.
N시간 이하로 투자해 얻을 수 있는 최대 중요도를 구해보자.

냅색냅색
그냥 문제 뒤적이다가 발견해서, 이번엔 2차원으로 풀어보았습니다.
근본적으로 1차원 냅색이랑 같은 원리네요.
*/
fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val (N, M) = br.readLine().split(" ").map {it.toInt()}
    val DP = Array(M+1) {IntArray(N+1)}
    for (i in 1..M) {
        val (I, T) = br.readLine().split(" ").map {it.toInt()}
        for (j in 0..N) {
            if (j < T)
                DP[i][j] = DP[i-1][j]
            else
                DP[i][j] = max(DP[i-1][j-T]+I, DP[i-1][j])
        }
    }
    var answer = 0
    DP.forEach{
        it.forEach { res ->
            answer = max(answer, res)
        }
    }
    bw.write("${answer}")

    br.close()
    bw.flush()
    bw.close()
}

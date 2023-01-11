import java.io.*
/*
18132번 내 이진트리를 돌려줘!!!

무한한 노드와 E개의 간선이 있을 때, E개의 간선으로 이진트리를 만드는 경우의 수를 구해보자.
노드는 모두 같은 노드라고 가정한다.

DPDP
체감은 G4로했는데 한칸이나 더 높네
카탈란수라고 합니다. 또 나만 모르지 이거..
전에 본적은 있는데 "이게 얼마나 나오겠어~"하는데 진짜 간혹 나오긴하네요.
참..

루비로 풀려다가 재귀스택 제한한테 "꺼져~" 소리 듣고 그냥 코틀린 켰습니다.

점화식은 이렇게 구했습니다.
이진트리니까 양쪽에 다리를 A, B개 단다고 칩시다.
일단 AB = 0인 경우를 생각하지 않으면 i개, E-i개 이렇게 달릴텐데,
그러면 왼쪽에서 i개를 달았을 때와 오른쪽에서 E-i개를 달았을 때 경우의 수를 곱해줍시다.
이제 다음으로 AB = 0인경우인데, 왼쪽이나 오른쪽에 하나를 달았으므로,
다음에 나올 수 있는 트리는 E-1개의 간선을 가진 트리입니다.
그 경우를 더해주는데, 왼쪽과 오른쪽에 달 수 있으므로 2를 곱해 더해줍니다.
*/
val dp = LongArray(5001) {0}
val MOD = 1_000_000_007L

fun loop(x:Int):Long {
    if (dp[x] != 0L) return dp[x]
    for (i in 1 until x) {
        val left = loop(i-1) % MOD
        val right = loop(x-i-1) % MOD
        dp[x] += (left*right) % MOD
    }
    dp[x] += loop(x-1) * 2
    dp[x] %= MOD
    return dp[x]
}

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    dp[0] = 1
    val T = br.readLine().toInt()
    repeat(T) {
        val x = br.readLine().toInt()
        bw.write("${loop(x)}\n")
    }

    br.close()
    bw.flush()
    bw.close()
}

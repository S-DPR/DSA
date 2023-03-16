import java.io.*
/*
18866번 젊은 날의 생이여

수열의 길이 N과 그 길이의 수열 두 개가 주어진다. 이를 각각 A, B라고 하자.
어떤 수 i에 대하여,
min(A[0], ..., A[i]) > max(A[i+1], ..., A[N-1])
max(B[0], ..., B[i]) < min(B[i+1], ..., B[N-1])
을 충족하는 최대 i에 1을 더한 값을 구해보자. (0 <= i < N-1)
그런게 없다면 -1을 출력하자.
참고로, 수열 A, B와 어떤 i에 대해 i번째 인덱스가 0이라면 그 값은 어떤 수라도 될 수 있다.

아..
이렇게 틀릴 문제 아니었는데..
왼쪽, 오른쪽에서 각각 누적 최대/최소를 구하고 N-2부터 내려오면서 저 조건을 충족할 경우 i를 출력하면 됩니다.
참.. 한번은 오른쪽에서만 구해서 틀리질않나.. 그다음은 0부터 N-2까지 굳이 올라오면서 해서 틀리질않나..
총체적난국이네..
*/
fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val N = br.readLine().toInt()
    val happy = IntArray(N)
    val tired = IntArray(N)
    for (i in 0 until N) {
        val (u, v) = br.readLine().split(" ").map {it.toInt()}
        happy[i] = u
        tired[i] = v
    }
    val prefixHappy = IntArray(N)
    val prefixTired = IntArray(N)
    var curHappy = if (happy[N-1] != 0) happy[N-1] else -1
    var curTired = if (tired[N-1] != 0) tired[N-1] else 1_000_000_001
    for (i in N-1 downTo 0) {
        if (happy[i] == 0) {
            prefixHappy[i] = curHappy
            happy[i] = 1_000_000_001
        } else {
            curHappy = maxOf(happy[i], curHappy)
            prefixHappy[i] = curHappy
        }
        if (tired[i] == 0) {
            prefixTired[i] = curTired
            tired[i] = -1
        } else {
            curTired = minOf(tired[i], curTired)
            prefixTired[i] = curTired
        }
    }
    for (i in 1 until N) {
        happy[i] = minOf(happy[i-1], happy[i])
        tired[i] = maxOf(tired[i-1], tired[i])
    }
    for (i in N-2 downTo 0) {
        if (happy[i] > prefixHappy[i+1] && tired[i] < prefixTired[i+1]) {
            println(i+1)
            return
        }
    }
    println(-1)

    br.close()
    bw.flush()
    bw.close()
}

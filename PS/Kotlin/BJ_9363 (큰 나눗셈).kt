import java.io.*
/*
9363번 큰 나눗셈

테스트케이스의 개수 T가 주어진다.
이후 분자에 있을 수의 개수 N, 분모에 있을 수의 개수 M이 주어진다.
다음줄에 분자에 있을 수가 N개 주어진다.
실제로 분자에 있을 수는 이 N개의 곱이다.
다음줄에 분모에 있을 수가 M개 주어진다.
역시, 분모에 있을 수는 이 M개의 곱이다.
당신은, 이 분수를 기약분수로 나타내면 된다.
(1 <= N, M <= 110_000)
(0 < 그 외에 주어지는 수 < 1_000_000)
(최종 답안은 int범위 내로 나타낼 수 있다.)

Linear Sieve는 골드 소수문제 학살도구입니다.
그냥 뭐.. 사실 굳이 Linear가 아니라 에라토스꺼여도 상관없지만.
그래도 이 문제는 소인수분해를 해야해서 Linear가 더 좋죠.

주어지는 수를 모두 소인수분해해서 배열에 저장한 뒤,
소수들 다 확인해서 약분해준 뒤 곱해주면 됩니다.
*/
val MAX = 1_000_000

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val P = mutableListOf<Int>()
    val sieve = IntArray(MAX+1) { it }
    for (i in 2..MAX) {
        if (sieve[i] == i) P.add(i)
        for (j in P) {
            if (i*j > MAX) break
            sieve[i*j] = j
            if (i%j == 0) break
        }
    }
    val upP = IntArray(MAX+1) {0}
    val downP = IntArray(MAX+1) {0}

    val T = br.readLine().toInt()
    for (TC in 1..T) {
        br.readLine()
        val up = br.readLine().split(" ").map {it.toInt()}
        val down = br.readLine().split(" ").map {it.toInt()}

        for (i in up) {
            var cur = i
            while (cur > 1) {
                upP[sieve[cur]]++
                cur /= sieve[cur]
            }
        }
        for (i in down) {
            var cur = i
            while (cur > 1) {
                downP[sieve[cur]]++
                cur /= sieve[cur]
            }
        }
        var upRes = 1
        var downRes = 1
        for (i in P) {
            val tmp = minOf(upP[i], downP[i])
            upP[i] -= tmp
            downP[i] -= tmp
            while (upP[i] > 0) {
                upRes *= i
                upP[i]--
            }
            while (downP[i] > 0) {
                downRes *= i
                downP[i]--
            }
        }
        bw.write("Case #${TC}: ${upRes} / ${downRes}\n")
    }

    br.close()
    bw.flush()
    bw.close()
}

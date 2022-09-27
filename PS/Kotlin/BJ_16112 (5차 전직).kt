import java.io.*

/*
16112번 5차 전직

이건 뭐..
하다가 "이건 코틀린으로 안되나보다~" 하고 포기할뻔했습니다.
틀렸다하길래 파이썬으로 같은 로직 돌려보니 되고..
BigInteger라는 자료형을 새로 알게되었습니다.
이거로 돌리니까 시간초과만 나던데..
어떻게 해서 992ms로 간신히 통과했습니다.

문제는 간단합니다.
"크기가 최대 i개인 수열 K를 만들 수 있다.
이 수열에 인덱스를 추가하기 위해선, 주어진 수열 arr에서 하나를 삭제하여야 한다.
주어진 수열 arr에서 하나를 삭제하면, 현재 K에 있는 모든 인덱스에 삭제한 arr의 값을 더하고,
이후 크기가 아직 i가 아니라면 값이 0인 인덱스를 하나 추가할 수 있다.
이 때, arr을 적절하게 삭제하여 K배열에 있는 원소의 합이 가장 클 떄의 값을 구하여라."

.. 어째 스토리텔링 다 빼고 쿼리문제처럼 만들었더니 더 딱딱하고 이해하기 어려워진거같긴 한데,
문제를 읽고 느껴지는 그대로 풀면 AC를 맞습니다.
*/


// 그 방법은 작은수부터 없애면서 K를 채워가는겁니다.
// 탐욕법이죠. 증명은 생략하겠습니다.

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val (_, k) = br.readLine().split(" ").map {it.toInt()}
    var ans = 0.toBigInteger() // 이 BigInteger는 처음써봤습니다.
    val arr = br.readLine().split(" ").map {it.toInt()}.toIntArray()
    arr.sort()
    arr.forEachIndexed{ idx, i ->
        ans += minOf(idx, k).toBigInteger().multiply(i.toBigInteger())
    }
    bw.write("$ans")

    br.close()
    bw.flush()
    bw.close()
}
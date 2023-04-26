import java.io.*
import java.util.PriorityQueue
/*
25606번 장마

수열 A가 주어진다. 이것은 i번째 날에 받아지는 물의 양이다.
즉, i번째 날에는 A[1..i]만큼의 물이 쌓여야 한다.
하지만 물은 하루마다 M만큼 증발한다. 예를들어, 두번째 날에는 max(0, A[1]-M) + A[2]만큼의 물이 쌓인다.
일반화하면, max(0, A[1]-M*1) + max(0, A[2]-M*2) + ... + max(0, A[i-1]-1) + A[i]만큼의 물이 쌓이게 된다.
증발하는 물의 양은, Sigma(j = 1 -> i) A[j]-max(0, A[j]-M*(i-j)) 일 것이다.
두 종류의 쿼리가 Q개 주어진다. 쿼리에 적절하게 답을 해보자.
1 t : t일까지 쌓인 물의 양을 구해보자.
2 t : t일에 증발할 물의 양을 구해보자.

이딴게.. 골드1..?
내가 그동안 봤던 골드1은....

t를 기준으로 정렬하여 오프라인 쿼리로 풀까 생각했는데,
그냥 우선순위큐 누적합 전처리로 풀었습니다.
좀 더 생각해보니 오프라인 쿼리, 우선순위큐 안쓰는 방법도 있던거같긴한데.. 뭐 어때요.

sum에는 i번째 날에 쌓인 빗물의 양을, reduce에는 감소한 빗물의 양을 기록합시다.
sum에는 매일 A[i]를 더하고, 배열에 저장한 뒤 (M 이상 있는 원소)*M과 (오늘 증발이 끝나는 원소 합)을 모두 빼줍니다.
reduce는 뭐.. sum에서 뺀 값 저장하고 다음날에 배열에 저장하면 됩니다.
그냥 그렇게하면 됩니다. 코드보는게 더 이해하기 쉽겠네요.

와 근데 딴사람 보니까 우선순위큐 없이 깡수학느낌으로 푼사람도 있네요. 대단한데..
*/
data class Info(val lastDay: Int, val lastValue: Int): Comparable<Info> {
    override fun compareTo(other: Info): Int {
        return this.lastDay - other.lastDay
    }
}

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val (N, M, Q) = br.readLine().split(" ").map { it.toInt() }
    val arr = br.readLine().split(" ").map { it.toInt() }
    val end = PriorityQueue<Info>()
    (0 until N).forEach {end.add(Info(it+arr[it]/M, arr[it]%M))}
    val res = IntArray(Q)
    var reduce = 0
    var sum = 0
    var enough = 0
    val queryA = IntArray(N)
    val queryB = IntArray(N)
    (0 until N).forEach {
        enough++
        queryB[it] = reduce
        var item = 0
        while (!end.isEmpty() && end.peek().lastDay <= it) {
            item += end.poll().lastValue
            enough--
        }
        sum += arr[it]
        queryA[it] = sum
        sum -= enough*M + item
        reduce = enough*M + item
    }
    (0 until Q).forEach {kth ->
        val (q, t) = br.readLine().split(" ").map { it.toInt() }
        when (q) {
            1 -> bw.write("${queryA[t-1]}\n")
            2 -> bw.write("${queryB[t-1]}\n")
        }
    }

    br.close()
    bw.flush()
    bw.close()
}
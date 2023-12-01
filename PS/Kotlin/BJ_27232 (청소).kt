import java.io.*
import java.util.*
/*
27232번 청소

길이가 N인 배열 A에서 길이가 K인 연속된 모든 부분 수열을 가져오자.
각 부분정렬에서 가장 큰 수부터 시작해 다음으로 가장 큰 수로 이동하려한다.
그렇게 K개의 수를 모두 이동하면 끝나는데, 이 거리가 가장 짧은 배열은 어느정도의 이동거리를 가질까?

크으
BBST, 다시말해 트리셋/맵 쓰지 않으면 난이도가 플레 이상으로 치솟는 문제
파이썬같은건 무슨 제곱근분할법이나 세그먼트트리 써야한다네요.

약간 연결리스트를 트리맵으로 쓰는 느낌입니다.
연결리스트의 단점인 탐색시 선형 시간복잡도를 트리맵 써서 logN으로 바꾸는느낌.
그래서 중간에 노드를 지우고, 정보를 갱신하는 작업이 매 부분배열마다 이루어집니다.
재미있는 문제였네요.
*/
val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun spi(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun spl(): List<Long> = br.readLine().split(" ").map {it.toLong()}

fun main() {
    val (N, K) = spi()
    val A = spi()
    val T = TreeMap<Int, Long>() // 값, 인덱스
    var (ret, local) = Pair(INFL, 0L)
    A.forEachIndexed { idx, i ->
        if (idx-K >= 0) {
            val key = A[idx-K]
            val less = T.lowerEntry(key)?.value
            val more = T.higherEntry(key)?.value
            if (less != null) local -= Math.abs(less-(idx-K))
            if (more != null) local -= Math.abs(more-(idx-K))
            if (less != null && more != null)
                local += Math.abs(less-more)
            T.remove(key)
        }
        val less = T.lowerEntry(i)?.value
        val more = T.higherEntry(i)?.value
        if (less != null) local += Math.abs(less-idx)
        if (more != null) local += Math.abs(more-idx)
        if (less != null && more != null)
            local -= Math.abs(less-more)
        T.put(i, idx.toLong())
        if (idx+1 >= K) ret = Math.min(ret, local)
    }
    println(ret)
}

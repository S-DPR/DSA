import java.io.*
import java.util.*
/*
21778번 가희와 프로세스 2

N개의 프로세스가 있다.
각 프로세스는 id, 실행단위시간, 우선순위가 있다.
우선순위가 큰 순서로 선택하며, 우선순위가 같다면 id가 작은 순서로 선택한다.
선택된 프로세스는 실행단위시간이 1 감소하며, 다른 모든 프로세스의 우선순위를 1씩 올린다.
Q개의 쿼리가 주어진다. 각 쿼리는 자연수 T로 이루어져있다.
시간이 T일 때 실행되고있는 프로세스의 id를 출력하자.
CPU는 1초에 첫 처리를 시작한다.

히히 재미따
오프라인쿼리 쓴거 까먹고 그대로 출력해서 한번 틀리고, 이거 깨닫고 다시 고치니 AC.
그런데 이게 이분탐색으로도 풀린다고..?

즐거운 우선순위큐 문제입니다.
어떻게해야할까~ 를 많이 고민했는데, 일단 생각난 아이디어.
1. 다른 모든 우선순위를 1 올리는 대신, 선택된 프로세스의 우선순위를 1 내리자.
2. 반복되는걸 하나로 묶어버리자. 어차피 주기에서 선택될테니.
일단 이 두 아이디어는 끝까지 끌고간 아이디어입니다.
휴.. 힘들어따
*/
val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun spi(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun spl(): List<Long> = br.readLine().split(" ").map {it.toLong()}

fun main() {
//    System.setIn(FileInputStream(File("./input.txt")))
//    System.setOut(PrintStream(File("./output.txt")))
    val (Q, N) = spi()
    val items = Array(N) {Triple(0L, 0L, 0L)}
    val queries = Array(Q) {Pair(0L, 0)}
    val ret = Array(Q) {0L}
    repeat(N) {
        val (u, v, w) = spl()
        items[it] = Triple(u, v, w)
    }
    repeat(Q) {
        val item = br.readLine().toLong()
        queries[it] = Pair(item, it)
    }
    items.sortWith(compareBy<Triple<Long, Long, Long>> { -it.third }.thenBy { it.first })
    queries.sortWith(compareBy{it.first})

    var curPriority = items[0].third
    var curTime = 0L
    var curRepeat = 0L
    var idx = 0
    var queryIdx = 0
    val pq = PriorityQueue(compareBy<Triple<Long, Long, Long>>{it.second})
    while (!pq.isEmpty() || idx < N) {
        while (idx < N && (curPriority <= items[idx].third || pq.isEmpty())) {
            val (u, v, w) = items[idx++]
            pq.add(Triple(u, v+curRepeat, w))
            curPriority = w
        }
        var nextTime = curTime
        if (idx < N)
            nextTime += ((pq.peek().second-curRepeat).coerceAtMost(curPriority-items[idx].third))*pq.size
        else
            nextTime += (pq.peek().second-curRepeat)*pq.size
        while (queryIdx < Q && curTime < queries[queryIdx].first && queries[queryIdx].first <= nextTime) {
            val content = pq.toTypedArray().sortedBy { it.first }
            val (time, qidx) = queries[queryIdx++]
            val contentIdx = (time-curTime-1) % pq.size
            ret[qidx] = content[contentIdx.toInt()].first
        }
        val nextRepeat = (nextTime-curTime)/pq.size
        curPriority -= nextRepeat
        curTime = nextTime
        curRepeat += nextRepeat
        while (!pq.isEmpty() && curRepeat >= pq.peek().second)
            pq.poll()
    }
    ret.forEach {
        bw.write("${it}\n")
    }

    br.close()
    bw.flush()
    bw.close()
}

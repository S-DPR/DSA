import java.io.*
import java.util.*
/*
13334번 철로

수직선 상에 [S, E] 선분이 N개 있다.
길이가 D인 선분이 있을 때, 최대 몇 개의 선분을 겹칠 수 있을까?

음..
그냥.. PQ 두개 딱 썼는데.
솔직히 왜 된지 모르겠어요.
데이터가 부족한거 아닐까..
*/
val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun ini(): Int = br.readLine().toInt()
fun lni(): Long = br.readLine().toLong()
fun ins(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun lns(): List<Long> = br.readLine().split(" ").map {it.toLong()}

fun main() {
    val N = ini()
    val pq = PriorityQueue<Pair<Long, Long>>(compareBy{it.second})
    val A = Array(N) {
        val (u, v) = lns().sorted()
        pq.add(Pair(u, v))
        Pair(u, v)
    }
    val L = lni()
    A.sortBy{it.first}
    val retPq = PriorityQueue<Pair<Long, Long>>(compareBy{it.first})
    var ret = 0
    (0 until N).forEach {
        val (u, v) = A[it]
        while (!retPq.isEmpty() && retPq.peek().first < u) {
            retPq.poll()
        }
        while (!pq.isEmpty() && pq.peek().second <= u+L) {
            retPq.add(pq.poll())
        }
        while (!retPq.isEmpty() && retPq.peek().first < u) {
            retPq.poll()
        }
        ret = Math.max(ret, retPq.size)
    }
    println(ret)

    bw.flush()
}

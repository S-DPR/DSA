import java.io.*
import java.util.*
/*
14461번 소가 길을 건너간 이유 7

N*N크기 맵이 있다. 상하좌우 한 칸으로 이동하는데는 T의 시간이 필요하다.
또, 이 맵에서 3번 걸을때마다 그 칸에 써있는 숫자만큼의 시간이 추가된다.
좌측 상단에서 우측 하단으로 가는 최단시간을 구해보자.

피곤해서 그냥 쉬운 골드2 잡아 풀었습니다.
그냥 다익스트라 굴리면 끝나는..
*/
val INFI = 1 shl 30
val INFL = 1L shl 61
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun ini(): Int = br.readLine().toInt()
fun lni(): Long = br.readLine().toLong()
fun ins(): List<Int> = br.readLine().trim().split(" ").map { it.toInt() }
fun lns(): List<Long> = br.readLine().trim().split(" ").map { it.toLong() }

fun main() {
    val (N, T) = ins()
    val pq = PriorityQueue<LongArray>(compareBy{it[0]})
    val M = Array(N) {lns()}
    val V = Array(N) {Array(N) { LongArray(3) {INFL} }}
    val dr = intArrayOf(1, -1, 0, 0)
    val dc = intArrayOf(0, 0, 1, -1)
    pq.add(longArrayOf(0, 0, 0, 0))
    while (pq.isNotEmpty()) {
        val cur = pq.poll()
        val curW = cur[0]
        val curR = cur[1].toInt()
        val curC = cur[2].toInt()
        val curT = cur[3].toInt()
        if (V[curR][curC][curT] < curW) continue
        repeat(4) {
            val nxtR = curR+dr[it]
            val nxtC = curC+dc[it]
            val nxtT = (curT+1)%3
            if (!(0 <= nxtR && nxtR < N)) return@repeat
            if (!(0 <= nxtC && nxtC < N)) return@repeat
            var nxtW = T.toLong()
            if (nxtT == 0) nxtW += M[nxtR][nxtC]
            if (V[nxtR][nxtC][nxtT] <= curW+nxtW) return@repeat
            V[nxtR][nxtC][nxtT] = curW+nxtW
            pq.add(longArrayOf(curW+nxtW, nxtR.toLong(), nxtC.toLong(), nxtT.toLong()))
        }
    }
    println(V[N-1][N-1].min())
}

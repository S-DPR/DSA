import java.io.*
import java.util.*
/*
10217번 KCM Travel

비용을 M 이하로 사용해 1에서 N까지 가려고 한다.
최단시간을 구해보자. 갈 수 없다면 Poor KCM을 출력하자.

아니 님들아 진짜 이건 좀
각 간선을 정렬하는 아이디어??
와 무쳤다 진짜..
그치.. 각 간선 정렬하면 다익스트라는 느려질래야 느려질수가없지..
특히 같은 간선이 여러번 나온다면 더더욱..

하아
100인거 보고 인접리스트가 아니라 인접행렬로 했어야했다..
*/
val INFI = 1 shl 29
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun ini(): Int = br.readLine().toInt()
fun lni(): Long = br.readLine().toLong()
fun ins(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun lns(): List<Long> = br.readLine().split(" ").map {it.toLong()}

lateinit var G: Array<MutableList<Triple<Int, Int, Int>>>

fun dij(M: Int): Array<IntArray> {
    val N = G.size-1
    val dist = Array(N+1) { IntArray(M+1) { INFI } }
    val pq = PriorityQueue(compareBy<Triple<Int, Int, Int>>{it.first}.thenBy{it.second})
    dist[1][0] = 0
    pq.add(Triple(0, 0, 1))
    while (pq.isNotEmpty()) {
        val (curT, curC, curN) = pq.poll()
        if (dist[curN][curC] < curT) continue
        G[curN].forEach{ (nxtN, nxtC, nxtT) ->
            if (curC+nxtC <= M && curT+nxtT < dist[nxtN][curC+nxtC]) {
                for (i in curC+nxtC..M) {
                    if (dist[nxtN][i] <= curT+nxtT) break
                    dist[nxtN][i] = curT+nxtT
                }
                pq.add(Triple(dist[nxtN][curC+nxtC], curC+nxtC, nxtN))
            }
        }
    }
    return dist
}

fun main() {
    val T = ini()
    repeat(T) {
        val (N, M, K) = ins()
        G = Array(N+1) { mutableListOf() }
        repeat(K) {
            val (u, v, c, d) = ins()
            G[u].add(Triple(v, c, d))
        }
        G.forEach { it.sortBy{it.third} }
        val ret = dij(M)[N].min()
        bw.write(if (ret == INFI) "Poor KCM" else "${ret}")
    }

    bw.flush()
}

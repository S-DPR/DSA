import java.io.*
import java.util.*
/*
16118번 달빛 여우

그래프와 1번 노드에서 출발하는 A, B가 있다. A, B는 다음과 같은 방법으로 달린다.
A : 항상 정속도로 달린다.
B : 1번째 움직임은 두배속으로, 2번째 움직임은 1/2배속으로, 3번째는 1번째와 같이.. 이런식으로 움직인다.
A가 B보다 빠르게 도착하는 노드의 개수를 구해보자.

다익스트라 하나 잘 굴리면 되는 문제
1/2로 하면 소숫점이 나올 수 있으므로 애초 가중치를 2배로 한다음 처리해줍시다.
그렇게한다음 잘 처리해주면 AC.
*/
val INFI = 1 shl 30
val INFL = 1L shl 61
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))

fun main() {
    val st = StringTokenizer(br.readLine())
    val N = st.nextToken().toInt()
    val M = st.nextToken().toInt()
    val G = Array(N + 1) { mutableListOf<Pair<Int, Long>>() }
    
    repeat(M) {
        val edgeInput = StringTokenizer(br.readLine())
        val u = edgeInput.nextToken().toInt()
        val v = edgeInput.nextToken().toInt()
        val w = edgeInput.nextToken().toLong() * 2
        G[u].add(Pair(v, w))
        G[v].add(Pair(u, w))
    }
    
    val pq = PriorityQueue<Triple<Long, Int, Int>>(compareBy { it.first })
    val dist = Array(N + 1) { LongArray(3) { INFL } }
    
    pq.add(Triple(0, 0, 1)) // type 0/1 = 두배 빠름/느림
    pq.add(Triple(0, 2, 1)) // type 2 = 일반속도
    dist[1][0] = 0
    dist[1][2] = 0
    
    while (pq.isNotEmpty()) {
        val (curW, type, curN) = pq.poll()
        if (dist[curN][type] < curW) continue
        G[curN].forEach { (nxtN, nxtW) ->
            val trueW = when (type) {
                0 -> nxtW / 2
                1 -> nxtW * 2
                else -> nxtW
            }
            val nxtType = if (type == 2) 2 else (type xor 1)
            if (curW + trueW < dist[nxtN][nxtType]) {
                dist[nxtN][nxtType] = curW + trueW
                pq.add(Triple(dist[nxtN][nxtType], nxtType, nxtN))
            }
        }
    }
    
    println((1..N).count { i -> Math.min(dist[i][0], dist[i][1]) > dist[i][2] })
}

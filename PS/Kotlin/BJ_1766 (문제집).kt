import java.io.*
import java.util.PriorityQueue
/*
1766번 문제집

그래프를 위상정렬하려한다.
그런데, 가능하면 노드가 작은수를 먼저 뽑아내면서 하려고 한다.
그래프가 주어질 때, 뽑히는 노드를 순서대로 구해보자.

「우선순위큐가 내장구현이 되어있는 언어는 얼마나 아름다운가..」

헤헤 골드 4나 높아봐야 3일거같은 위상정렬이다 하면서 풀었더니 골드2
확실히 그래프류는 좀 난이도가 쓸데없이 높게 책정되네요..
DP 골드2에비하면 엄청 쉬운거같은데..

그냥 PriorityQueue 만들어서 거기서 노드 관리하면 됩니다.
*/

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val (N, M) = br.readLine().split(" ").map {it.toInt()}
    val G = Array(N+1) { mutableListOf<Int>() }
    val dependent = IntArray(N+1) { 0 }
    repeat(M) {
        val (x, y) = br.readLine().split(" ").map {it.toInt()}
        dependent[y]++
        G[x].add(y)
    }

    val PQ = PriorityQueue<Int>()
    for (i in 1..N) {
        if (dependent[i] == 0) PQ.add(i)
    }
    while (!PQ.isEmpty()) {
        val cur = PQ.poll()
        bw.write("$cur ")
        for (i in G[cur]) {
            if (--dependent[i] == 0) PQ.add(i)
        }
    }

    br.close()
    bw.flush()
    bw.close()
}

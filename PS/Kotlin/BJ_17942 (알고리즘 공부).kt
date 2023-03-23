import java.io.*
import java.util.*
/*
17942번 알고리즘 공부

N개의 알고리즘이 있다. 각 알고리즘은 x만큼의 내공이 있어야 배울 수 있다. 그 배열이 두번째 줄에 주어진다.
알고리즘 A를 배울 때 필요한 내공을 i라고 하고,
알고리즘 B를 배울 때 필요한 내공을 j라고 하면
알고리즘 A, B를 배우는데 필요한 내공은 max(i, j)이다.
그리고, 알고리즘 A를 배울 때 다른 알고리즘 K를 배우는데 필요한 내공이 T만큼 줄어들 수도 있다.
위 관계가 R개 주어진다. 알고리즘 M개를 배우는데 필요한 최소 내공을 구해보자.

그래프가 들어간 우선순위큐인데 거기에 그리디도 첨가한 문제.
자료구조를 어떻게 활용해야할지, 어떻게 풀어야할지 생각해볼만한 문제였습니다.

먼저 처음에 주는 필요한 내공을 모두 받습니다.
그리고 단방향그래프로 A와 K와 T를 받습니다.
이제 우선순위큐에 1부터 N까지 모두 넣고, arr[i]의 값도 같이 넣습니다.
우선순위큐는 입력받은 arr[i]의 값을 토대로 힙을 구성하게 됩니다.

이제 우선순위큐에서 서로 다른 M개의 수가 나올때까지 빼냅니다.
한개를 뺄 때마다 그래프에 있는 값을 업데이트해준뒤, 다시 우선순위큐에 넣습니다.
M개가 나올떄까지 정답을 갱신하고, 출력하면 AC입니다.
*/
data class Node(val dst:Int, val weight:Int)
data class QNode(val nth:Int, val comp:Int)

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    var (N, M) = br.readLine().split(" ").map {it.toInt()}
    val arr = (listOf(0)+br.readLine().split(" ").map {it.toInt()}).toIntArray()
    val G = Array(N+1) { mutableListOf<Node>() }
    val R = br.readLine().toInt()
    repeat(R) {
        val (u, v, w) = br.readLine().split(" ").map {it.toInt()}
        G[u].add(Node(v, w))
    }
    val Q = PriorityQueue<QNode>(compareBy{it.comp})
    val visit = BooleanArray(N+1) { false }
    for (i in 1..N)
        Q.add(QNode(i, arr[i]))
    var res = 0
    while (M > 0) {
        val cur = Q.poll().nth
        if (visit[cur]) continue
        visit[cur] = true
        res = maxOf(res, arr[cur])
        G[cur].forEach {
            arr[it.dst] -= it.weight
            Q.add(QNode(it.dst, arr[it.dst]))
        }
        M--
    }
    bw.write("$res")

    br.close()
    bw.flush()
    bw.close()
}

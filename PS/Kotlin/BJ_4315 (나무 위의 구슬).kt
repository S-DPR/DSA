import java.io.*
import java.util.*
/*
4315번 나무 위의 구슬

트리가 주어진다. 그리고 각 노드에 구슬이 주어진다.
트리상에 있는 구슬은 트리의 노드 수와 같다.
구슬 하나를 간선을 태워 다른 노드로 하나를 이동하는 행위를 최소화하여, 모든 노드에 1개의 구슬이 있게 하려 한다.
행위의 최솟값을 구하시오.

히히 쉬운 플레5
는무슨 그냥 여기저기 쳐맞다가 그나마 만만한놈 만난거지..

그래도 푸는데까지 1시간 15분정도 걸렸습니다.
45분이 수업시간이었기 때문에 1시간이라고 봐도되..려나?

기본적으로 구슬의 개수를 -1로 하고 구슬의 개수를 받아줍니다.
이유는, 해당 노드에 남는 구슬의 개수만 남기려고. -1이 된다면 1개가 필요하다는 뜻이겠죠.
자식노드에서 부모노드로 남는 구슬의 수와 이 노드까지 이동하는데 걸린 거리를 보내줍니다.
부모노드는 자식의 구슬 수를 자신의 구슬 수와 더하고,
거리도 더하긴 핟네 거기에 자식들의 남는 구슬의 수의 절댓값도 더해줍니다.
부모로 옮기는데 그정도의 추가 이동이 필요하니까요.
그러면 AC가 나옵니다.

아, 루트노드는 그냥 1로 대충 잡으면 됩니다. 중요하지 않아요.
*/
lateinit var G: Array<MutableList<Int>>
lateinit var B: LongArray
lateinit var D: LongArray
lateinit var V: BooleanArray

fun dfs(x: Int) {
    if (V[x]) return
    V[x] = true
    G[x].forEach {
        if (V[it]) return@forEach
        dfs(it)
        B[x] += B[it]
        D[x] += Math.abs(B[it])+D[it]
    }
}

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    while (true) {
        val N = br.readLine().toInt()
        if (N == 0) break
        G = Array(N+1) { mutableListOf() }
        B = LongArray(N+1) {-1}
        D = LongArray(N+1) {0}
        V = BooleanArray(N+1)
        repeat(N) {
            val I = br.readLine().split(" ").map {it.toInt()}
            val cur = I[0]
            val ball = I[1]
            B[cur] += ball.toLong()
            (3 until I.size).forEach {
                G[cur].add(I[it])
                G[I[it]].add(cur)
            }
        }
        dfs(1)
        bw.write("${D[1]}\n")
    }

    br.close()
    bw.flush()
    bw.close()
}

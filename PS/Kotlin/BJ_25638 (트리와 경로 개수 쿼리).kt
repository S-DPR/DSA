import java.io.*
import java.util.*
/*
25638번 트리와 경로 개수 쿼리

트리가 있다. 각 노드는 색이 R, B중 하나로 칠해져있다.
다음을 쿼리를 처리해보자.
 - x가 주어집니다. 모든 색이 다른 두 노드 i, j가 x번째 노드를 "지나가는" 횟수해주세요.
참고로, 해당 노드에서 시작하거나 도착하는 것은 지나가는 것이 아니다.

보자마자 방법이 떠오른 문제. 대충 dfs긁어서 구현하면 됩니다. 언제나, 트리dp가 그랬듯이.
자기 아래 서브트리에서 만나는거 처리해주고, 그리고 자기 자식들이랑 서브트리 밖의 노드랑 만나는거 처리해주고.
전처리하는데 O(N+M), 쿼리처리에 O(Q). 쉬운 플레5였네요.
*/
val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun spi(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun spl(): List<Long> = br.readLine().split(" ").map {it.toLong()}

lateinit var G: Array<MutableList<Int>>
lateinit var V: BooleanArray
lateinit var R: LongArray
lateinit var B: LongArray
lateinit var A: LongArray
lateinit var C: IntArray

fun dfs(c: Int): Pair<Long, Long> {
    val child = mutableListOf<Pair<Long, Long>>()
    G[c].forEach {
        if (V[it]) return@forEach
        V[it] = true
        val (r, b) = dfs(it)
        child.add(Pair(r, b))
        R[c] += r
        B[c] += b
    }
    var (AR, AB) = Pair(R[c], B[c])
    child.forEach {
        AR -= it.first
        AB -= it.second
        A[c] += AB*it.first
        A[c] += AR*it.second
    }
    if (C[c-1] == 1) R[c]++ else B[c]++
    return Pair(R[c], B[c])
}

fun main() {
    val N = br.readLine().toInt()
    C = spi().toIntArray()
    G = Array<MutableList<Int>>(N+1){mutableListOf<Int>()}
    R = LongArray(N+1)
    B = LongArray(N+1)
    A = LongArray(N+1)
    V = BooleanArray(N+1)
    V[1] = true
    repeat(N-1) {
        val (u, v) = spi()
        G[u].add(v)
        G[v].add(u)
    }
    dfs(1)
    (2..N).forEach {
        A[it] += (R[1]-R[it])*B[it]
        A[it] += (B[1]-B[it])*R[it]
        A[it] -= if (C[it-1] == 1) B[1]-B[it] else R[1]-R[it]
    }
    val Q = br.readLine().toInt()
    repeat(Q) {
        val x = br.readLine().toInt()
        bw.write("${A[x]}\n")
    }
    bw.flush()
}

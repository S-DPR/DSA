import java.io.*
import java.util.*
/*
20156번 기왕 이렇게 된 거 암기왕이 되어라

정점 N개로 만든 1개 이상의 그래프가 주어진다. 몇몇 노드는 부모를 갖고있다.
M개의 라운드가 있다. i번째 노드를 부모와 분리시킬 것이다.
단, 이미 부모와 분리됐거나 없다면 아무일도 일어나지 않는다.
K개의 쿼리가 있다. R A B로 주어지며, R번째 라운드에서 A B가 같은 그래프에 있는지 구해보자.

히히 할만한 플레4
그냥 분리집합 긁어주면 됍니다.
그런데 거기애 오프라인쿼리를 곁들인.
확실히 옛날에 쿼리문제 많이 푼게 이런데 도움이 크게 되는거같아요.
*/
val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun spi(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun spl(): List<Long> = br.readLine().split(" ").map {it.toLong()}

data class Query(val u: Int, val a: Int, val b: Int, val idx: Int)

lateinit var G: Array<MutableList<Int>>
lateinit var U: IntArray

fun union(x: Int, y: Int) {
    U[find(x)] = U[find(y)]
}

fun find(x: Int): Int {
    if (U[x] != x) U[x] = find(U[x])
    return U[x]
}

fun main() {
    val (N, M, K) = spi()
    val P = intArrayOf(0) + spi()
    val O = P.clone()
    val lazy = mutableListOf<Int>()
    U = IntArray(N+1) {it}
    repeat(M) {
        val x = br.readLine().toInt()
        if (P[x] != -1)
            lazy.add(x)
        else
            lazy.add(-1)
        P[x] = -1
    }
    (1..N).forEach {
        if (P[it] == -1) return@forEach
        union(it, P[it])
    }
    val Q = Array<Query>(K) {
        val (u, a, b) = spi()
        Query(u, a, b, it)
    }
    Q.sortBy { -it.u }
    val ret = BooleanArray(K)
    Q.forEach { (u, a, b, idx) ->
        while (lazy.size > u) {
            val x = lazy.removeLast()
            if (x == -1 || O[x] == -1) continue
            union(x, O[x])
        }
        ret[idx] = find(a) == find(b)
    }
    ret.forEach {
        bw.write(if (it) "Same Same;\n" else "No;\n")
    }

    bw.flush()
}

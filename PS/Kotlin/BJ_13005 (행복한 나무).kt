import java.io.*
import java.util.*
/*
13005번 행복한 나무

루트가 1인 트리가 있다.
어떤 정점 i와 그 서브트리에 있는 정점 j에 대해,
dist(i, j) > A[j]인 모든 정점을 없애려 한다.
리프노드만 자를 수 있다고 할 때, 최소 몇 개의 정점을 제거해야할까?

멍청하게 모든 함정에 걸려주고 푼 문제.
사실 플레4 2개 건들다가 멸망하고 도망온거긴한데..
생각해보니 플레4 문제 두개 다 서울대 경진대회꺼네..

가중치에 음수가 있다는게 무슨 의민지 생각 안하고 그냥 했다가 모든 함정에 걸렸습니다.
덕분에 5번 틀렸네요..
어렵진 않은 문제였는데..
*/

val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun spi(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun spl(): List<Long> = br.readLine().split(" ").map {it.toLong()}

lateinit var G: Array<MutableList<Pair<Int ,Long>>>
lateinit var A: LongArray
var ret = 0

fun dfs(x: Int, pf: Long, valid: Int) {
    var isValid = valid
    if (isValid == 0 || A[x] < pf) {
        ret++
        isValid = 0
    }
    G[x].forEach { (nxtN, nxtW) ->
        dfs(nxtN, Math.max(nxtW, pf+nxtW), isValid)
    }
}

fun main() {
    val N = br.readLine().toInt()
    A = longArrayOf(0) + spl().toLongArray()
    G = Array(N+1) {mutableListOf()}
    repeat(N-1) { it ->
        val (p, v) = spi()
        G[p].add(Pair(it+2, v.toLong()))
    }
    dfs(1, 0, 1)
    println(ret)
    bw.flush()
}

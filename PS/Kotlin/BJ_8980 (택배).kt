import java.io.*
import java.util.*

val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun nxti(): Int = br.readLine().toInt()
fun nxtl(): Long = br.readLine().toLong()
fun spi(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun spl(): List<Long> = br.readLine().split(" ").map {it.toLong()}
/*
8980번 택배

1에서 N으로 가면서 택배를 배달하려 한다.
택배를 보내는 곳, 택배를 받는 곳, 택배의 개수가 주어질 때,
택배를 최대 몇 개 보낼 수 있나 구해보자.
단, 모든 경우에 대해 트럭에 있는 택배 용량이 C를 넘기면 안된다.

택배는 다 받으면서, C를 넘기면 가장 먼곳을 버리기.
저는 트리맵으로 구현했는데 단순하게 정렬로만 풀 수도 있었나봅니다.
다들 택배를 받는곳을 기준으로 정렬해서 풀었다고 하네요.
처음에 N 크기 보고 DP인줄 알았는데 아무리생각해도 각이 안나와서 그리디로 전환하니 AC..
*/
fun main() {
    val (N, C) = spi()
    val M = nxti()
    val edge = Array(M) {
        val (u, v, w) = spi()
        Triple(u, v, Math.min(C, w))
    }
    edge.sortBy { it.first }
    val tree = TreeMap<Int, Int>()
    var score = 0
    var sum = 0
    var idx = 0
    (1..N).forEach { i ->
        if (tree.containsKey(i)) {
            val get = tree.get(i)!!
            score += get
            sum -= get
            tree.remove(i)
        }
        while (idx < M && edge[idx].first == i) {
            val (_, v, w) = edge[idx++]
            tree.put(v, tree.getOrDefault(v, 0)+w)
            sum += w
            while (C < sum) {
                val mx = tree.lastKey()
                val get = tree.get(mx)!!
                val newV = get - Math.min(sum-C, get)
                sum -= Math.min(sum-C, get)
                if (newV > 0)
                    tree.put(mx, newV)
                else
                    tree.remove(mx)
            }
        }
    }
    println(score)

    bw.flush()
}

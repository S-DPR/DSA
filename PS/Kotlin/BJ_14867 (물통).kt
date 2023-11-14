import java.io.*
import java.util.*
/*
14867번 물통

상한이 a인 물통 A와 상한이 b인 물통 B가 있다.
아래 세 연산을 하려한다. 첫 물통에 c, 두번째 물통에 d만큼 물을 채울 수 있을까?
1. A에 물을 a만큼 채운다. 흘러넘치는 물은 못받는다.
2. B에 물을 b만큼 채운다. 흘러넘치는 물은 못받는다.
3. A나 B의 물을 비운다.
4. A->B나 B->A로 물을 이동시킨다. 한쪽이 비거나 찰때까지 이동하게된다.

그냥 딱 보고 '머야 이거 상태 생각보다 얼마 안되겠는데'.
그리고 이거는 문제를 푸는데 핵심적입니다. 상태가 몇개인지 정확히 몰라도, 적어도 상태가 많지 않을것이라는 점.
그래서 그냥 BFS굴리면 맞을거같다.
이런느낌으로..

그런데 풀고보니 이걸 코틀린으로 푼건 너무 아까운거같기도하고..
30분만에 풀었습니다.
*/
val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun spi(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun spl(): List<Long> = br.readLine().split(" ").map {it.toLong()}

fun main() {
    val (a, b, c, d) = spi()
    val deq = ArrayDeque<Pair<Int, Int>>();
    val vis = Array<TreeMap<Int, Int>>(a+1) {TreeMap<Int, Int>()};
    deq.add(Pair(0, 0))
    vis[0][0] = 0
    while (deq.isNotEmpty()) {
        val (f, s) = deq.removeFirst()
        if (!vis[a].containsKey(s)) {
            vis[a][s] = vis[f][s]!! + 1
            deq.add(Pair(a, s))
        }
        if (!vis[f].containsKey(b)) {
            vis[f][b] = vis[f][s]!! + 1
            deq.add(Pair(f, b))
        }
        if (!vis[f].containsKey(0)) {
            vis[f][0] = vis[f][s]!! + 1
            deq.add(Pair(f, 0))
        }
        if (!vis[0].containsKey(s)) {
            vis[0][s] = vis[f][s]!! + 1
            deq.add(Pair(0, s))
        }
        val leftToRight = Math.min(f, b-s)
        val leaveLeft = f-leftToRight
        val leaveRight = s+leftToRight
        if (!vis[leaveLeft].containsKey(leaveRight)) {
            vis[leaveLeft][leaveRight] = vis[f][s]!! + 1
            deq.add(Pair(leaveLeft, leaveRight))
        }
        val rightToLeft = Math.min(s, a-f)
        val leaveLeft_ = f+rightToLeft
        val leaveRight_ = s-rightToLeft
        if (!vis[leaveLeft_].containsKey(leaveRight_)) {
            vis[leaveLeft_][leaveRight_] = vis[f][s]!! + 1
            deq.add(Pair(leaveLeft_, leaveRight_))
        }
    }
    println(if (vis[c][d] == null) -1 else vis[c][d])
}

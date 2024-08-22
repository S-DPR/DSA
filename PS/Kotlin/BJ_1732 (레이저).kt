import java.io.*
import java.util.*
/*
1732번 레이저

2차원 좌표평면이 있고, (x, y)에 높이가 w인 기둥이 N개 있다.
기둥이 모두 (0, 0)을 향해 레이저를 쏜다.
가다가 높이가 같거나 높은 기둥을 만날경우 레이저가 막힌다고 할때,
(0, 0)에는 총 몇 개의 레이저가 지나갈까?

대충 기울기 통일하고 풀어내기.
통일이 되면 이제 나머지 처리가 모두 쉬워집니다.
여기에 CCW나 그런거 뭐 하라고하나본데..
음.. 그냥 y를 x로 나누면 되는데.. 이걸 기하학 타이틀까지 줘야하나..
*/
val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun ini(): Int = br.readLine().toInt()
fun lni(): Long = br.readLine().toLong()
fun ins(): List<Int> = br.readLine().trim().split(" ").map { it.toInt() }
fun lns(): List<Long> = br.readLine().trim().split(" ").map { it.toLong() }

fun main() {
    val N = ini()
    val M = mutableMapOf<Double, MutableList<Triple<Int, Int, Int>>>()
    repeat(N) {
        val (x, y, w) = ins()
        val slope = y.toDouble()/x
        M.getOrPut(slope) { mutableListOf() }.add(Triple(x, y, w))
    }
    M.forEach{ (k, v) -> v.sortWith(compareBy({Math.abs(it.first)}, {Math.abs(it.second)})) }
    var ret = mutableListOf<Pair<Int, Int>>()
    M.forEach { (k, v) ->
        var curW = -1
        v.forEach { (x, y, w) ->
            if (curW < w) {
                curW = w
            } else {
                ret.add(Pair(x, y))
            }
        }
    }
    ret.sortWith(compareBy({it.first}, {it.second}))
    ret.forEach { (x, y) -> bw.write("$x $y\n")}

    bw.flush()
}

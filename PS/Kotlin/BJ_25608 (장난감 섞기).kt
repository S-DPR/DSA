import java.io.*
import java.util.*
/*
25608번 장난감 섞기

수열이 N개 있다.
이 수열을 원하는대로 이어붙인 뒤, 그 중 가능한 연속최대합을 구해보자.

N이 10이니까..
그냥 누적합 잘쓰면 끝.
야.. 골드1에 있을만한 인재는 아니다.
*/
val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))

fun ini(): Int = br.readLine().toInt()
fun lni(): Long = br.readLine().toLong()
fun ins(): List<Int> = br.readLine().trim().split(" ").map { it.toInt() }
fun lns(): List<Long> = br.readLine().trim().split(" ").map { it.toLong() }

fun getMx(A: List<Int>): Int {
    var pf = A[0]
    var ret = maxOf(A[0], 0)
    for (i in 1 until A.size) {
        pf += A[i]
        ret = maxOf(ret, pf)
    }
    return ret
}

fun kadane(A: List<Int>): Int {
    var (ret, cur) = Pair(0, 0)
    for (i in 0 until A.size) {
        ret = maxOf(ret, cur)
        cur = maxOf(cur+A[i], A[i], 0)
    }
    return ret
}

fun main() {
    val (N, K) = ins()
    val A = mutableListOf<List<Int>>()
    for (i in 0 until N) {
        A.add(ins())
    }
    val info = mutableListOf<List<Int>>()
    for (i in 0 until N) {
        val lmx = getMx(A[i])
        val rmx = getMx(A[i].reversed())
        val pf = A[i].sum()
        info.add(listOf(lmx, rmx, pf))
    }
    val vis = BooleanArray(N)
    val trace = mutableListOf<Int>()

    fun calc(): Int {
        var ret = maxOf(info[trace[0]][1], 0)
        var pf = info[trace[0]][1]
        for (i in 1 until N) {
            ret = maxOf(ret, pf + info[trace[i]][0])
            pf += info[trace[i]][2]
        }
        return ret
    }

    fun loop(): Int {
        if (trace.size == N) {
            return calc()
        }
        var ret = 0
        for (i in 0 until N) {
            if (vis[i]) continue
            trace.add(i)
            vis[i] = true
            ret = maxOf(ret, loop())
            vis[i] = false
            trace.removeAt(trace.size - 1)
        }
        return ret
    }

    var mx = (0 until N).map { kadane(A[it]) }.max()
    bw.write("${maxOf(loop(), mx)}\n")
    bw.flush()
}

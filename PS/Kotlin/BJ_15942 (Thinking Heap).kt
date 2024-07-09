import java.io.*
import java.util.*
/*
15942번 Thinking Heap

1부터 N까지 수를 빈 이진 힙에 삽입하려 한다.
수 k가 p번째 노드에 가게 삽입 할 수 있을까?

대충 아이디어는 금방 짰는데,
구현에서 결국 BBST님을 가져온..
어렵진않은데..
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
    val (k, p) = ins()
    val R = IntArray(N+1)
    val T = TreeSet<Int>()
    (1..N).forEach { T.add(it) }
    T.remove(k)
    R[p] = k
    var idx = p shr 1
    while (idx > 0) {
        R[idx] = -1
        idx = idx shr 1
    }
    var fail = false
    (1..N).forEach {
        if (R[it] == -1) {
            val put = T.first()!!
            R[it] = put
            T.remove(put)
        }
    }
    R[p] = k
    if (k < R[p/2]) fail = true
    (1..N).forEach {
        if (R[it] == 0) {
            val put = T.higher(R[it/2])
            if (put == null) fail = true
            else {
                R[it] = put!!
                T.remove(put!!)
            }
        }
    }
    if (fail) bw.write("-1")
    else (1..N).forEach { bw.write("${R[it]}\n") }

    bw.flush()
}

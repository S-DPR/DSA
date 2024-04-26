import java.io.*
import java.util.*
/*
4195번 친구 네트워크

N개의 친구 연결 순서가 주어진다.
그 친구가 연결되었을 때, 그 친구를 통해 연락할 수 있는 친구들의 수를 구해보자.

하..
P4 풀다가 결국 못풀고 도망온문제
근데 이게 왜 골드2지?
대충 사이즈 재는 유니온파인드 쓰면 AC입니다. 쩝..
*/
val INFI = 1 shl 29
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun ini(): Int = br.readLine().toInt()
fun lni(): Long = br.readLine().toLong()
fun ins(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun lns(): List<Long> = br.readLine().split(" ").map {it.toLong()}

lateinit var U: IntArray
lateinit var S: IntArray

fun union(u: Int, v: Int): Boolean {
    val up = find(u)
    val vp = find(v)
    if (up == vp) return false
    S[up] += S[vp]
    U[vp] = U[up]
    return true
}

fun find(u: Int): Int {
    if (U[u] != u) U[u] = find(U[u])
    return U[u]
}

fun main() {
    val T = ini()
    repeat(T) {
        val N = ini()
        U = IntArray(N*2) { it }
        S = IntArray(N*2) { 1 }
        val H = HashMap<String, Int>()
        repeat(N) {
            val str = br.readLine().split(" ")
            val l = str[0]
            val r = str[1]
            if (!H.containsKey(l)) H.put(l, H.size)
            if (!H.containsKey(r)) H.put(r, H.size)
            val ln = H.get(l)!!
            val rn = H.get(r)!!
            union(ln, rn)
            bw.write("${S[find(ln)]}\n")
        }
    }
    bw.flush()
}

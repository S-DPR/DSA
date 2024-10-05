import java.io.*
import java.util.*
/*
15440번 Vera And LCS

길이가 N인 문자열 A, B가 있다.
A가 주어지고, A와 B의 LCS가 K일 때 가능한 B를 구해보자.

으음
애드혹인데 아이디어를 봐버리니 의미가없네
아이디어는 참신했다.. 정도로만 생각해야지..
근데 dp만 생각했는데 애드혹일줄은 몰랐다
*/
val INFI = 1 shl 30
val INFL = 1L shl 61
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun ini(): Int = br.readLine().toInt()
fun lni(): Long = br.readLine().toLong()
fun ins(): List<Int> = br.readLine().trim().split(" ").map { it.toInt() }
fun lns(): List<Long> = br.readLine().trim().split(" ").map { it.toLong() }

fun main() {
    val (N, K) = ins()
    val S = br.readLine().trim()
    val R = IntArray(26)
    S.forEach { R[it-'a']++ }
    var mn = R.indices.minByOrNull { R[it] }!!
    val ret = mutableListOf<Char>()
    repeat(R[mn]) {
        ret.add('a'+mn)
    }
    var idx = 0
    S.forEach {
        if (it-'a' == mn) {
            idx++
            return@forEach
        }
        if (ret.size < K) {
            ret.add(idx++, it)
        }
    }
    println(if (ret.size == K) ret.joinToString("") + (1..N-K).map { 'a'+mn }.joinToString("") else "WRONGANSWER")
}

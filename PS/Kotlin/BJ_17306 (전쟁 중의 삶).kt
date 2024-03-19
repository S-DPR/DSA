import java.io.*
import java.util.*
/*
17306번 전쟁 중의 삶

무한한 길이의 포화이진트리가 있다. 노드의 번호는 위에서부터 1 2 3.. 이렇게 내려간다.
N개의 노드 번호가 주어졌을 때, 이들이 한 점에서 만나기 위해 지나야 하는 노드의 개수를 구해보자.
모이는 한 점도 지나오는 노드이고, 첫 노드도 지나오는 경로이다.

아마 비트로 나눠서 트라이..쓰는 문제같은데.
.. 음, 저는 그냥 우선순위큐로 깔끔하게.
만나는 경우는 제외하고, 한 점에 이미 모인경우도 제외하고.
그런식으로 제외를 잘 넣어서 AC.
데이터가 약하다기보단 문제 조건이 이걸 생각 못한거같네요..?
*/
val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun ini(): Int = br.readLine().toInt()
fun lni(): Long = br.readLine().toLong()
fun ins(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun lns(): List<Long> = br.readLine().split(" ").map {it.toLong()}

fun main() {
    val N = ini()
    val A = lns()
    val pq = PriorityQueue<Long>()
    A.forEach {
        pq.add(-it)
    }
    var ret = 0
    while (pq.isNotEmpty()) {
        val cur = -pq.poll()
        if (cur == 0L) continue
        if (pq.isNotEmpty() && cur == -pq.peek()) continue
        ret++
        if (pq.isNotEmpty()) pq.add(-(cur shr 1))
    }
    println(ret)

    bw.flush()
}

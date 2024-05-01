import java.io.*
import java.util.*
/*
28072번 K512에서 피자 먹기

합이 K의 배수인 수열 A가 주어진다. A는 앞과 뒤가 연결되어있다.
이 때, 부분 연속 수열의 합이 K의 배수가 되도록 A를 최대한 나누려 한다.
최대 몇 조각으로 나눌 수 있을까?

아..
원형이라는거에 당했다..

배열 모듈러때리는거까진 이해 가고 누적합도 이해는 갔는데,
이거 원형인데?? 하면서 하 어떡하나.. 보고있었습니다.
아니 근데 답지 보니까 아!! 원형은 함정이었구나!!
그냥 최빈값만 구해서 하면 되는구나.. A 합이 K의 배수니까..
그리고 원형으로 보면 결국 빠진게 다시 들어오는 형태니까..
..음.. 답지 좀 늦게봤으면 맞출 수 있었으려나..?
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
    val (N, K) = ins()
    val A = ins()
    var last = 0
    val cnt = IntArray(K)
    A.forEach {
        last = (last+it)%K
        cnt[last]++
    }
    println(cnt.max())

    bw.flush()
}

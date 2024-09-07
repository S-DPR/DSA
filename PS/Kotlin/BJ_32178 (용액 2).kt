import java.io.*
import java.util.*

val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun ini(): Int = br.readLine().toInt()
fun lni(): Long = br.readLine().toLong()
fun ins(): List<Int> = br.readLine().trim().split(" ").map { it.toInt() }
fun lns(): List<Long> = br.readLine().trim().split(" ").map { it.toLong() }
/* 
32178번 용액 2

수열이 주어진다.
L, R을 잡아 그 사이 인덱스를 가진 요소를 모두 더한 값의 최솟값이 최소가 되게 하려 한다.
그 최솟값과 L, R을 구해보자.

오랜만에 푼 한글문제
"누적합이야 어떤식으로든 쓰일테고.."로 접근했습니다.

저는 트리맵을 썼는데 훨씬 섹시한 그리디 풀이가 있더라고요.
트리맵 풀이는
'어라? 옛날 두 용액 문제에서 이제 이분탐색을 못하네? 그럼 트리맵으로 우회하지 뭐'
라는 마인드로 했고, 성공은 했네요.

그리디는 이제 누적합을 정렬합니다!
그러면 모든 요소에 대해 인접한 요소가 최솟값을 이루는(!) 엄청난 현상이 일어납니다.
거기서 고르면 되죠. 이야..
*/
fun main() {
    val N = ini()
    val A = lns()
    val map = TreeMap<Long, Int>()
    var pf = 0L
    A.forEachIndexed{ idx, i ->
        pf += i
        map[pf] = idx+1
    }
    pf = 0L
    var ret = map.keys.map { i -> Math.abs(i) }.min()
    if (!map.containsKey(ret)) ret = -ret
    var lo = 1
    var hi = map[ret]
    A.forEachIndexed{ idx, i ->
        if (Math.abs(i) < Math.abs(ret)) {
            ret = i
            lo = idx+1
            hi = idx+1
        }
        pf += i
        if (map.containsKey(pf) && map[pf] == idx+1) map.remove(pf)
        val up = map.ceilingKey(pf)
        val down = map.floorKey(pf-1)
        if (up != null && Math.abs(pf-up) < Math.abs(ret)) {
            ret = up-pf
            lo = idx+2
            hi = map[up]
        }
        if (down != null && Math.abs(pf-down) < Math.abs(ret)) {
            ret = down-pf
            lo = idx+2
            hi = map[down]
        }
    }
    println(ret)
    println("${lo} ${hi}")

    bw.flush()
}

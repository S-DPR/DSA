import java.io.*
import java.util.*
/*
28079번 배 옮기기

왼쪽에 있는 배를 모두 오른쪽으로 옮기려한다.
어떤 배의 무게가 i라면, 옮길 때 i의 시간이 들고 i 미만의 무게를 갖는 배를 태우고 옮길 수 있다.
배의 무게가 주어질 떄, 옮기는 최단시간을 구해보자.
모두 옮길 수 없다면 -1을 출력하자.

진짜 아무리 봐도 수상함 그 자체인 "N은 15 이하의 양의 정수" 조건.
아하.. 15는 어중간한 수..
범위가 어중간함의 극치를 달리는 알고리즘에는 대표적으로 두개.. MITM, 비트dp..
아무리봐도 MITM 각은 보이지 않으니, 비트dp.

비트를 볼 때, 0은 왼쪽에 있음을, 1은 오른쪽에 있음을 의미합니다.
그리고 배가 같은 상황이어도 내가 왼쪽인지, 오른쪽인지 여부도 매우 중요합니다.
그러므로 dp[2][1<<N]으로 배열을 만들어주고, -1로 초기화한뒤,
방문시 dp[pos][i]를 INFI로 초기화하고 자기 위치에 맞게 알고리즘을 돌린뒤,
dp[pos][i]를 return하면 됩니다.

그래도 비트dp 익숙해진거같기도?
*/
val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun spi(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun spl(): List<Long> = br.readLine().split(" ").map {it.toLong()}

var N = 0
lateinit var dp: Array<IntArray>
lateinit var arr: List<Int>

fun loop(v: Int, pos: Int): Int {
    if (v+1 == 1 shl N) return 0
    if (dp[pos][v] != -1) return dp[pos][v]
    dp[pos][v] = INFI
    if (pos == 0) { // 왼쪽이면
        repeat(N) { i ->
            if ((v and (1 shl i)) != 0) return@repeat
            // i번쨰를 들고 오른쪽으로 가고샆은데,
            repeat(N) { j ->
                if (i == j) return@repeat           // 같은건 들면 안되고,
                if (arr[i] <= arr[j]) return@repeat // i가 항상 커야하고,
                if ((v and (1 shl j)) != 0) return@repeat // 왼쪽에 있어야해.
                var newV = v
                newV = newV or (1 shl i)
                newV = newV or (1 shl j)
                dp[pos][v] = dp[pos][v].coerceAtMost(arr[i] + loop(newV, 1))
            }
            dp[pos][v] = dp[pos][v].coerceAtMost(arr[i] + loop(v or (1 shl i), 1))
            // 그리고, i번째만 보내는 경우도 있겠지
        }
    }
    else { // 오른쪽일때
        repeat(N) { i ->
            if ((v and (1 shl i)) == 0) return@repeat
            // i번째를 들고 왼쪽으로 갈거야
            dp[pos][v] = dp[pos][v].coerceAtMost(arr[i]+loop(v and (1 shl i).inv(), 0))
        }
    }
    return dp[pos][v]
}

fun main() {
//    System.setIn(FileInputStream(File("./input.txt")))
//    System.setOut(PrintStream(File("./output.txt")))
    N = br.readLine().toInt()
    arr = spi().sorted()
    dp = Array(2) {IntArray(1 shl N) {-1}}
    val ret = loop(0, 0)
    bw.write("${if (ret == INFI) -1 else ret}")

    br.close()
    bw.flush()
    bw.close()
}

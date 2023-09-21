import java.io.*
import java.util.*
/*
27315번 틀리는건 싫으니까 쉬운 문제에 올인하려고 합니다

한별이가 문제를 풀려한다. 각 문제는 아이디어 난이도 d, 구현 난이도 p, 데이터 유무 t, 에디토리얼 유무 e로 이루어져있다.
한별이는 기본적으로 자신의 아이디어 수치보다 높은 아이디어 난이도를 가진 문젠 손도 못댄다.
그게 아니고 구현난이도가 한별이의 구현 수치보다 높다면 max(0, (구현난이도)-(구현수치))만큼 WA를 받고 풀게된다.
데이터가 있을 경우 한별이는 단 한번의 WA도 받지 않는다.
에디토리얼은 (한별이의 아이디어 수치*2) > (문제의 아이디어 난이도)를 만족하면 구현난이도와 아이디어 난이도 모두 반으로 떨어지게 된다.

한별이는 M개의 문제를 WA를 최소한으로 받으며 풀려고 한다.
한별이가 받게 될 WA의 개수를 구해보자.

그리디랑 우선순위큐인걸 옛날에 실수로 보기는 했는데..
이건 뭐 그냥 문제 대충 봐도 그리디는 맞고 우선순위큐는 좀 생각하면 나올거같고..
그런데 좀 더 보다보니 느낀건데 이거 골드3에서 골드2되더니 골드1되더라고요?

데이터여부와 에디토리얼 여부를 미리 우선순위큐에 넣기 전에 계산해두어야합니다.
'한번도 WA를 받지 않는다'는 곧 문제의 구현 난이도가 0이 된다와 동치이고,
에디토리얼 여부는 (문제아이디어난이도+1)/2, 문제구현난이도/2 로 문제를 바꿈과 같은 의미입니다.
그상태로 난이도로 정렬하고 우선순위큐로 구현난이도 낮은거순서대로 처리해주면 AC입니다.
*/
val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun spi(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun spl(): List<Long> = br.readLine().split(" ").map {it.toLong()}

fun main() {
    var (N, M) = spi()
    val items = Array<Pair<Long, Long>>(N) {
        var (d, p, t, e) = spl()
        if (t == 1L) p = 0
        if (e == 1L) {
            d = (d+1) / 2
            p /= 2
        }
        Pair(d, p)
    }
    items.sortBy{it.first}
    var idx = 0
    var (cd, cp) = spl()
    var wa = 0L
    val pq = PriorityQueue<Long>()
    while (idx < N && items[idx].first <= cd)
        pq.add(items[idx++].second)
    while (M > 0 && pq.isNotEmpty()) {
        val p = pq.poll()
        wa += Math.max(0L, p-cp)
        cd++
        cp++
        while (idx < N && items[idx].first <= cd)
            pq.add(items[idx++].second)
        M--
    }
    println(if (M == 0) wa else -1)
}

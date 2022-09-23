import java.io.*

/*
15889번 호 안에 수류탄이야!!

간단한 문제를 어렵게 풀어버렸네요. 네..
사람들 코드 보다가 어떻게 빨리했나 알게되었지만, 일단 제가 푼대로 써봅니다.
*/

// 저는 dfs로 풀었습니다. 일단 가능한 방법이더라구요.
lateinit var coor:IntArray
lateinit var range:IntArray
lateinit var visited:BooleanArray

// dfs를 써줍니다. x가 이미 목적지라면 true를 리턴하는 dfs입니다.
fun dfs(x:Int = 0) : Int{
    if (x == coor.size-1) return 1
    if (visited[x]) return 0
    visited[x] = true
    var res = 0
    for (i in coor.size-1 downTo x+1){
        if (coor[i] <= coor[x] + range[x]) res = dfs(i)
		// 나보다 앞에있는 사람중, 가장 멀리 있는사람부터 나한테 가까운 사람 순으로 던져봅니다.
        if (res == 1) return res // res가 1로 결정되었다면 바로 res를 내보냅니다.
    }
    return res
}

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val n = br.readLine().toInt()
    coor = IntArray(n)
    br.readLine().split(" ").map {it.toInt()}.forEachIndexed { index, i ->
        coor[index] = i
    }
    range = IntArray(n-1)
    if (n > 1) {
        br.readLine().split(" ").map { it.toInt() }.forEachIndexed { index, i ->
            range[index] = i
        }
    }
    visited = BooleanArray(n) {false}

    println(if (dfs() == 1) "권병장님, 중대장님이 찾으십니다" else "엄마 나 전역 늦어질 것 같아")

    br.close()
    bw.flush()
    bw.close()
}
// 실버문제라 그렇게 설명할 건 없는데..
// 그리디로 풀면 훨씬 쉬워집니다.
// 내가 앞으로 던질 수 있는 사람중, 사거리+그 사람의 좌표가 제일 큰 값으로 계속 갱신하면
// 빠르게 풀 수 있게 됩니다.
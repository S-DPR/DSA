import java.io.*
/*
9944번 NxM 보드 완주하기

북마크가 하나씩 사라져가고있습니다, 아주 좋아요.

파이썬류는 시간제한이 30초로 설정되고, C는 3초인 특이한 문제입니다.
자바 및 코틀린은 2배에 1초를 더 받으니 시간제한이 7초겠죠.
이하 코드는 2296ms만에 통과했는데, 코틀린으로 푼 다른분 보니 356ms..
코틀린에 아직도 익숙해지지 않아서 더 줄이질 못하겠습니다..

문제는 뭐.. 그냥 구슬로 맵 전부 탐방하기..입니다.
백트래킹 문제입니다. 문제에 보이는거보다 할만해요.
*/

// 파이썬에서 하던것처럼 vec을 정의했는데, 여기선 안될것같습니다. 길이가 이게 머에요..
val vec = arrayOf(intArrayOf(0, 1), intArrayOf(0, -1), intArrayOf(1, 0), intArrayOf(-1, 0))
var ans = 1000000

// 이 문제는 백트래킹이라고 했지만..
// dfs가..뭔가..더..친숙하고..어..음..그런기분?
fun dfs(x:Int, y:Int, M:Array<String>, n:Int, m:Int, cnt:Int = 0):Int{
    if (ans < cnt) return ans // 이미 도출된 최적의 답보다 더 많이 시도하고있다면 무의미하겠죠.
    var blocked = 0
    vec.forEach{(dx, dy) ->
        val C = M.copyOf() // 아마 이 부분에서 시간이 많이 걸려버린듯 합니다. copy는 꽤 큰 작업이니까요.
        C[y] = StringBuilder(C[y]).also {it.setCharAt(x, '*')}.toString() // 자바류로 string다루기는.. 참..
        var nx = dx+x; var ny = dy+y
        if (!(nx in 0 until m && ny in 0 until n)) {blocked++; return@forEach}
        if (C[ny][nx] == '*') {blocked++; return@forEach}
        while (nx in 0 until m && ny in 0 until n && C[ny][nx] == '.'){ // 지나온 길을 다 *처리 해줍시다.
            C[ny] = StringBuilder(C[ny]).also {it.setCharAt(nx, '*')}.toString()
            nx += dx; ny += dy
        }
        ans = minOf(ans, dfs(nx-dx, ny-dy, C, n, m, cnt+1)) // 그리고 백트래킹을 해 도출된 값중 ans에 더 작은 값을 넣어줍시다.
    }
    var check = 0
    if (blocked == 4){ // 현재 좌표에서 상하좌우 다 막힌 경우입니다. 이 경우에는
        M.forEachIndexed{ cy, it ->
            it.forEachIndexed{cx, k->
                if (k == '*' || (cy == y && cx == x)) check++ // 모든 타일을 지나왔나 테스트를 해보고,
            }
        }
        return if (check == n*m) cnt else 1000000 // 다 지나왔으면 cnt를, 아니면 1000000을 return합니다.
    }
    return ans // 그게 아니면 그냥 ans 리턴합니다.
}

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    var tc = 1
    while (true){ // 이 문제는 테스트케이스의 수를 따로 안주기에,
        val tmp = br.readLine() ?: break // 라인을 읽고 이게 null이면 break를 해야합니다.
        val (n, m) = tmp.split(" ").map {it.toInt()}
        val M = Array<String>(n) {br.readLine()}
        val testCoor = mutableListOf<IntArray>()
        M.forEachIndexed{ y, i ->
            i.forEachIndexed{ x, j ->
                if (j == '.') testCoor.add(intArrayOf(x, y))
            }
        }
        var res = 1000000; ans = 1000000
        testCoor.forEach{(x, y) ->
            res = minOf(dfs(x, y, M, n, m), res)
			// 둘 수 있는 모든 경우에 대해서 테스트를 해봅시다.
        }
        bw.write("Case $tc: ${if (res==1000000) -1 else res}\n"); tc++
		// res가 1000000이면 -1을, 아니면 res를 출력합니다.
    }

    br.close()
    bw.flush()
    bw.close()
}

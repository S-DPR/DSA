import java.io.*
import java.util.*
/*
1207번 종이 자르기

N*N크기의 종이를 자른 조각 5개가 주어진다.
이걸 이제 다시 붙여보자. 이 때, 어느 조각이 어디에 붙어있는지 숫자로 표시한다.
경우가 여러개라면, 사전순으로 가장 먼저 오는걸 출력하자.

ㅋㅋ 40회정도 제출했나..

사전순 먼저오는거 출력 아니었으면.. 아마 실버쯤이 아니었을까..
백트래킹 기본 실력이 있다면, 여기서 추가로 필요한 발상은..
'다 못채우면 어차피 gg출력' 이라는 점.
그걸 사용해서, 가장 먼저 empty부분인 요소를 찾아 그 부분을 어떻게 채울지 고민해야한다는 점.
이걸 못해서.. 40회제출..
아..
*/
val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun ini(): Int = br.readLine().toInt()
fun lni(): Long = br.readLine().toLong()
fun ins(): List<Int> = br.readLine().trim().split(" ").map { it.toInt() }
fun lns(): List<Long> = br.readLine().trim().split(" ").map { it.toLong() }

var N = 0
lateinit var M: Array<MutableList<CharArray>>
lateinit var map: Array<IntArray>
lateinit var ret: Array<IntArray>
lateinit var use: BooleanArray

fun isMin(): Boolean {
    repeat(N) { r ->
        repeat(N) { c -> 
            if (map[r][c] == ret[r][c]) return@repeat
            return map[r][c] < ret[r][c]
        }
    }
    return false
}

fun cpy() {
    repeat(N) { r ->
        repeat(N) { c ->
            ret[r][c] = map[r][c]
        }
    }
}

fun loop(cnt: Int) {
    if (cnt == 5) {
        if (isMin()) cpy()
        return
    }
    var (r, c) = Pair(-1, -1)
    for (rr in 0 until N) {
        if (r != -1) break
        for (cc in 0 until N) {
            if (map[rr][cc] == 0) {
                r = rr
                c = cc
                break
            }
        }
    }

    for (idx in 0 until 5) {
        if (use[idx]) continue
        use[idx] = true
        val R = M[idx].size
        val C = M[idx][0].size
        val pad = M[idx][0].indexOf('#')
        val (r, c) = Pair(r, c-pad)
        var can = 0 <= r && r+R-1 < N && 0 <= c && c+C-1 < N
        for (dr in 0 until R) {
            if (!can) break
            for (dc in 0 until C) {
                if (!can) break
                if (M[idx][dr][dc] == '.') continue
                can = can && map[r+dr][c+dc] == 0
            }
        }
        if (can) {
            for (dr in 0 until R) {
                for (dc in 0 until C) {
                    if (M[idx][dr][dc] == '.') continue
                    map[r+dr][c+dc] = idx+1
                }
            }
            if (isMin()) loop(cnt+1)
            for (dr in 0 until R) {
                for (dc in 0 until C) {
                    if (M[idx][dr][dc] == '.') continue
                    map[r+dr][c+dc] = 0
                }
            }
        }
        use[idx] = false
    }
}

fun main() {
    N = ini()
    M = Array(5) { mutableListOf() }
    use = BooleanArray(5)
    var cnt = 0
    repeat(5) { i ->
        val (R, C) = ins()
        repeat(R) { r ->
            M[i].add(br.readLine().toCharArray())
            repeat(C) { c ->
                if (M[i][r][c] == '#') cnt++
            }
        }
    }
    map = Array(N) { IntArray(N) }
    ret = Array(N) { IntArray(N) { INFI } }
    if (cnt == N*N) loop(0)
    if (ret[0][0] != INFI) {
        repeat(N) { r ->
            repeat(N) { c ->
                bw.write("${ret[r][c]}")
            }
            bw.write("\n")
        }
    } else {
        bw.write("gg")
    }

    bw.flush()
}

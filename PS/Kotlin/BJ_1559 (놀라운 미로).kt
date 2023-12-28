import java.io.*
import java.util.*
/*
1559번 놀라운 미로

N W E S로 이루어진 미로가 있다. 각 알파벳은 해당 칸에서 그 방향으로 이동할 수 있음을 나타낸다.
이 때, 모든 칸은 매 분마다 시계방향으로 한번씩 이동한다.
그리고 몇몇 칸에는 보물이 있다. 이 보물을 다 모을 생각이다.
가만히 있거나 이동할 수 있는 곳으로 이동할 수 있을 때, 모든 보물을 갖고 우측 최하단로 가는 최단시간을 구해보자.

그냥 평범한 BFS+비트마스킹 문제.
대충 맵 만들고, t로 이동가능한 방향 잡고, 이동할수있는지 보면 됩니다.
이정도 플레5면 뭐.. 정말 쉬운축..
개인적으로 아직도 생각해봐도 회전미로탈출이 제일 지옥이었어서..
*/
val INFI = 1 shl 30
val INFL = 1L shl 60
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun spi(): List<Int> = br.readLine().split(" ").map {it.toInt()}
fun spl(): List<Long> = br.readLine().split(" ").map {it.toLong()}

data class Info(val r: Int, val c: Int, val t: Int, val v: Int)

fun main() {
    val dr = intArrayOf(-1, 0, 1, 0)
    val dc = intArrayOf(0, 1, 0, -1)
    val M = Array(100) { IntArray(100) }
    val P = Array(100) { IntArray(100) }
    val V = Array(100) { Array(100) { Array(4) { BooleanArray(256) } } }
    while (true) {
        val (R, C) = spi()
        if (R == 0) break
        repeat(R) { r ->
            val inp = br.readLine()
            repeat(C) { c ->
                P[r][c] = -1
                M[r][c] = when (inp[c]) {
                    'N' -> 0
                    'E' -> 1
                    'S' -> 2
                    else -> 3
                }
            }
        }
        val K = br.readLine().toInt()
        repeat(K) {
            val (r, c) = spi()
            P[r-1][c-1] = it
        }
        repeat(R) { r ->
            repeat(C) { c ->
                repeat(4) { t ->
                    repeat(1 shl K) { k ->
                        V[r][c][t][k] = false
                    }
                }
            }
        }
        var Q = ArrayDeque<Info>()
        Q.add(Info(0, 0, 0, 0)) // r, c, t, vis
        while (!Q.isEmpty()) {
            val cur = Q.removeFirst()
            val (r, c, t, v) = intArrayOf(cur.r, cur.c, cur.t, cur.v)
            if (r == R-1 && c == C-1 && v+1 == 1 shl K) {
                bw.write("${t}\n")
                break
            }
            if (!V[r][c][(t+1)%4][v]) {
                V[r][c][(t+1)%4][v] = true
                Q.add(Info(r, c, t+1, v))
            }
            val direc = (M[r][c] + t) % 4
            val (nr, nc) = Pair(r+dr[direc], c+dc[direc])
            if (!(0 <= nr && nr < R)) { continue }
            if (!(0 <= nc && nc < C)) { continue }
            var newV = v
            if (P[nr][nc] != -1) {
                newV = newV or (1 shl P[nr][nc])
            }
            if (V[nr][nc][(t+1)%4][newV]) { continue }
            V[nr][nc][(t+1)%4][newV] = true
            Q.add(Info(nr, nc, t+1, newV))
        }
    }
    bw.flush()
}

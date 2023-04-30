import java.io.*
/*
26009번 험난한 등굣길

맵이 주어진다.
그리고, K개의 r, c, d가 주어진다.
(r, c)를 중심으로 거리가 d 이하인 모든 칸에 장애물이 있다는 뜻이다.
좌측 상단에서 우측 하단으로 갈 수 있는지, 갈 수 있다면 몇 번을 거쳐 가는지 구해보자.

「속도」의 차이가 느껴지십니까? 를 경험한 문제.
코틀린도 *2+1초 받고 루비도 *2+1초 받는데 루비로는 못푸는..

그냥 중심좌표에서 d칸 어떻게 떨어뜨린 다음, 테두리만 둘러싸주면 되는 문제입니다.
저거 bfs로 되나 해서 해봤더니 호기심=천국 당했습니다.
그리고 그냥 bfs 두르기.
아.. 이런 쉬운문제에 시간을 박아야했다니...
*/
fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val (M, N) = br.readLine().split(" ").map {it.toInt()}
    val map = Array(M) { IntArray(N) {0} }
    val dx = intArrayOf(1, -1, 0, 0)
    val dy = intArrayOf(0, 0, 1, -1)
    repeat (br.readLine().toInt()) {
        val (r, c, d) = br.readLine().split(" ").map {it.toInt()}
        var curX = c-1
        var curY = r+d-1
        repeat (d) {
            if (0 <= curX && curX < N && 0 <= curY && curY < M)
                map[curY][curX] = 1
            curY--
            curX++
        }
        repeat (d) {
            if (0 <= curX && curX < N && 0 <= curY && curY < M)
                map[curY][curX] = 1
            curY--
            curX--
        }
        repeat (d) {
            if (0 <= curX && curX < N && 0 <= curY && curY < M)
                map[curY][curX] = 1
            curY++
            curX--
        }
        repeat (d) {
            if (0 <= curX && curX < N && 0 <= curY && curY < M)
                map[curY][curX] = 1
            curY++
            curX++
        }
        if (0 <= curX && curX < N && 0 <= curY && curY < M)
            map[curY][curX] = 1
    }

    val deque = ArrayDeque<IntArray>()
    deque.add(intArrayOf(0, 0))
    while (!deque.isEmpty()) {
        val (x, y) = deque.first(); deque.removeFirst()
        if (map[M-1][N-1] != 0) break
        (0 until 4).forEach {
            val nx = x + dx[it]
            val ny = y + dy[it]
            if (!(0 <= nx && nx < N)) return@forEach
            if (!(0 <= ny && ny < M)) return@forEach
            if (map[ny][nx] != 0) return@forEach
            map[ny][nx] = map[y][x] + 1
            deque.add(intArrayOf(nx,ny))
        }
    }
    println(if (map[M-1][N-1] != 0) "YES\n${map[M-1][N-1]}" else "NO")

    bw.close()
}

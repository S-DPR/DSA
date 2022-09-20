import java.io.*
/*
7576번 토마토
코틀린 재활치료 3
사실 코틀린으로 그래프 문제를 푼 적은 없습니다. 코틀린에 흥미 느꼈을 때엔 그래프랑 등져서..
그냥 간단한 BFS 문제를 풀어봅시다.
*/

// d는 덱, M은 맵입니다. bfs에서도 쓰고 main에서도 쓰기때문에 전역변수로 설정했습니다.
val d = ArrayDeque<IntArray>()
lateinit var M:Array<IntArray>

// 평범한 bfs입니다. 평소 파이썬과 다른점이라면 vec을 안에서 설정했다는점?
fun bfs(x:Int, y:Int, t:Int){
    val vec = arrayOf(intArrayOf(1, 0), intArrayOf(-1, 0), intArrayOf(0, 1), intArrayOf(0, -1))
    vec.forEach{ (dx, dy) ->
        val nx = dx+x; val ny = dy+y
        if (!(0 <= nx && nx < M[0].size && 0 <= ny && ny < M.size)) return@forEach
        if (M[ny][nx] != 0) return@forEach
        M[ny][nx] = 1
        d.addLast(intArrayOf(nx, ny, t+1))
    }
}

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))
	// m은 실질적으로 C, Java를 위해 들어간거라 받을 이유가 없습니다.
    val (_, n) = br.readLine().split(" ").map {it.toInt()}
	// 맵을 init해주고
    M = Array(n) {br.readLine().split(" ").map {it.toInt()}.toIntArray()}
    M.forEachIndexed{ y, i ->
        i.forEachIndexed{x, j ->
            if (j == 1) d.addLast(intArrayOf(x, y, 0)) //토마토가 있는곳을 d에 다 넣어줍니다.
        }
    }
    var ans = -1
    while (d.isNotEmpty()){ // d가 비지 않았다면
        val (x, y, t) = d.first(); d.removeFirst() // d를 터뜨리면서 bfs에 돌립니다.
        ans = t // t는 while 안에서 선언되었기 때문에 밖으로 못나갑니다. ans에 t를 계속 저장해줍시다.
        bfs(x, y, t)
    }

    var flg = 1
    M.forEach{i->
        i.forEach{j->
			// 만약에 익지 않은 토마토가 있다면 flg를 0으로 만들고
            if (j == 0) flg = 0
        }
    }
    if (flg == 1) println(ans) // flg여부에 따라 답을 출력합니다.
    else println(-1) // 어차피 1줄출력이라 bw.write는 쓰지 않았습니다.

    br.close()
    bw.flush()
    bw.close()
}
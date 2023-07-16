import java.io.*
import java.util.*
/*
14427번 수열과 쿼리 15

수열이 주어지고, 쿼리가 Q개 주어진다. 쿼리는 아래와 같다.
1 x y : 수열의 x번째 수를 y로 바꾼다.
2     : 수열에서 가장 작은 수의 인덱스를 출력한다. 그게 여러개라면 가장 작은 인덱스를 출력한다.
모든 쿼리에 답해보자.

비트dp에 잡혀서 죽어가다가 수열과 쿼리를 푸니 마치 PS의 고향에 온 기분
그래 이맛이지..

세그트리아십니까정말갓갓자료구조입니다
를 외치기 전에, PQ로 가볍게 풀 수 있는 맵. 좀 더 나아가면 트리맵까지?
난이도가 어렵지 않습니다. 그냥 pq 업데이트 깔끔하게 해주면 AC.

그래.. 이런느낌.. 좋아..
*/
fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val N = br.readLine().toInt()
    val arr = br.readLine().split(" ").map {it.toInt()}
    val U = IntArray(N+1)
    val pq = PriorityQueue(compareBy<Triple<Int, Int, Int>>{it.first}.thenBy{it.second})
    repeat(N) {
        pq.add(Triple(arr[it], it+1, 0))
    }
    val Q = br.readLine().toInt()
    repeat(Q) {
        val q = br.readLine().split(" ").map {it.toInt()}
        when (q[0]) {
            1 -> pq.add(Triple(q[2], q[1], ++U[q[1]]))
            2 -> {
                while (pq.peek().third != U[pq.peek().second])
                    pq.poll()
                bw.write("${pq.peek().second}\n")
            }
        }
    }

    br.close()
    bw.flush()
    bw.close()
}

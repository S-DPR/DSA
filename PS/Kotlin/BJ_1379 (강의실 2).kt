import java.io.*
import java.util.*
/*
1379번 강의실 2

N개의 강의가 s에 시작해 e에 끝날 때, 강의실을 최소로 사용하려 한다.
어떻게 할당해야할지 정해보자.

머리는 정렬 그리디를 외치지만 내 손은 이미 스위핑으로 가있다
스위핑 재밌잖아요. 그리디보다.
그냥 시작과 끝을 적어두고, 우선순위 큐로 대충 어디에 뭘 할당할지 관리해주면 됩니다.
쉬운 골드3이죠?
*/
fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val N = br.readLine().toInt()
    val start = HashMap<Int, MutableList<Int>>()
    val end = HashMap<Int, MutableList<Int>>()
    val pq = PriorityQueue<Int>()
    pq.addAll(1..N)
    repeat(N) {
        val (i, s, e) = br.readLine().split(" ").map {it.toInt()}
        if (start[s] == null) start[s] = mutableListOf()
        if (end[e] == null) end[e] = mutableListOf()
        start[s]!!.add(i)
        end[e]!!.add(i)
    }
    val ret = IntArray(N+1)
    start.plus(end).keys.sorted().forEach {
        end[it]?.forEach {
            pq.add(ret[it])
        }
        start[it]?.forEach {
            ret[it] = pq.poll()
        }
    }

    bw.write("${ret.max()}\n")
    (1..N).forEach {
        bw.write("${ret[it]}\n")
    }
    br.close()
    bw.flush()
    bw.close()
}

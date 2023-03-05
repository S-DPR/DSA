import java.io.*
import java.util.PriorityQueue
/*
19640번 화장실의 규칙

N명의 사람을 M개의 줄로 서게 하였다. 1번사람은 1번줄, 2번사람은 2번줄, ..., M+1번사람은 1번줄, ... 이런 식이다.
선두란, 어떤 줄에서 가장 먼저 와서, 가장 앞에 선 사람을 말한다.
1. M개의 줄의 선두 중 근무 일수 Di가 가장 높은 선두가 화장실을 이용한다.
2. M개의 줄의 선두 중 근무 일수 Di가 가장 높은 선두가 둘 이상인 경우, 해당 선두들 중 화장실이 급한 정도 Hi가 가장 높은 선두가 화장실을 이용한다.
3. M개의 줄의 선두 중 근무 일수 Di가 가장 높은 선두가 둘 이상이며, 해당 선두들의 화장실이 급한 정도 Hi도 모두 같다면, 해당 선두 중 줄의 번호가 가장 낮은 줄에 선 선두가 화장실을 이용한다.
K번째 사람은 몇 명의 사람이 화장실을 이용하고 난 뒤 화장실을 이용할 수 있을까?

상당히 재밌는 PQ문제.
대충 생각해도 우선순위큐겠거니 합니다.
문제를 제대로 안읽어서 한 10분은 지체한거같네요.

그냥 pq 정렬을 잘 설정해주면 끝나는 문제입니다.
*/
fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val (N, M, K) = br.readLine().split(" ").map {it.toInt()}
    val comp = compareBy<IntArray?> { -it!![0] }.thenBy { -it!![1] }.thenBy { it!![3] }
    val arr = Array<ArrayDeque<IntArray>>(M) { ArrayDeque() }
    for (i in 0 until N) {
        val (D, H) = br.readLine().split(" ").map {it.toInt()}
        arr[i%M].add(intArrayOf(D, H, i, i%M))
    }
    val cur = PriorityQueue(comp)
    arr.forEach {
        if (!it.isEmpty()) cur.add(it.removeFirst())
    }

    var res = 0
    while (!cur.isEmpty()) {
        val current = cur.poll()!!
        if (current[2] == K) break
        if (!arr[current[3]].isEmpty()) cur.add(arr[current[3]].removeFirst())
        res++
    }
    println(res)

    br.close()
    bw.flush()
    bw.close()
}

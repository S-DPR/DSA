import java.io.*
import java.util.*
/*
21399번 장난감 묶음 할인

수열 arr이 주어진다.
3의 배수 i에 대하여, arr[i]를 기준으로 좌/우로 나누도록 하자.
그러면 좌/우는 각각 원소를 3의 배수개를 가지게 될 것이다.
이를 j개라고 하자. 이중 가장 큰 j/3개의 원소를 제거한 뒤 그 합을 구해보자.
이 때, 이 합의 최소값과 그 값이 나오게 하는 최소의 i를 구해보자.

이틀동안 고민해서 겨우 풀어낸 문제
우선순위큐 << 알았음
그리디 << 알았음
문제 << 모름
이상태를 이틀동안 지속했습니다.

원래는 오른쪽에서 어떤 수를 어떻게 빼야하는가에 대하여 이틀동안 고민했는데,
생각을 바꾸니 문제가 확 진전되고 풀렸습니다.
역시 생각하는 방법을 바꾸어야합니다.

우쪽에서 '수를 추가하면서' 최댓값들을 얻습니다.
좌측에서 '수를 추가하면서' 최댓값들을 얻습니다.
이를 구현하고, 그 합이 최대가 된다면 그게 답이 됩니다.
*/
fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val T = br.readLine().toInt()
    repeat(T) {
        val N = br.readLine().toInt()
        val arr = br.readLine().split(" ").map {it.toLong()}
        val sum = arr.sum()

        val rightCut = LongArray((N/3)+1) { 0 }
        val rightSmallPQ = PriorityQueue<Long>(reverseOrder())
        val rightBigPQ = PriorityQueue<Long>()
        var rightSum = 0L
        arr.reversed().forEachIndexed { idx, i ->
            rightSmallPQ.add(i)
            if ((idx+1) % 3 == 0) {
                val item = rightSmallPQ.poll()
                rightBigPQ.add(item)
                rightSum += item
                while (!rightSmallPQ.isEmpty() && !rightBigPQ.isEmpty() && rightSmallPQ.peek() > rightBigPQ.peek()) {
                    val pop = rightBigPQ.poll()
                    val push = rightSmallPQ.poll()
                    rightSum += push - pop
                    rightBigPQ.add(push)
                    rightSmallPQ.add(pop)
                }
                rightCut[idx/3+1] = rightSum
            }
        }

        val leftCut = LongArray((N/3)+1) { 0 }
        val leftSmallPQ = PriorityQueue<Long>(reverseOrder())
        val leftBigPQ = PriorityQueue<Long>()
        var leftSum = 0L
        arr.forEachIndexed { idx, i ->
            leftSmallPQ.add(i)
            if ((idx+1) % 3 == 0) {
                val item = leftSmallPQ.poll()
                leftBigPQ.add(item)
                leftSum += item
                while (!leftSmallPQ.isEmpty() && !leftBigPQ.isEmpty() && leftSmallPQ.peek() > leftBigPQ.peek()) {
                    val pop = leftBigPQ.poll()
                    val push = leftSmallPQ.poll()
                    leftSum += push - pop
                    leftBigPQ.add(push)
                    leftSmallPQ.add(pop)
                }
                leftCut[N/3-1-idx/3] = leftSum
            }
        }

        var resIdx = -1
        var res = -1L
        for (j in 0..N/3) {
            val item = rightCut[j] + leftCut[j]
            if (res <= sum - item) {
                resIdx = N-1-j*3
                res = sum - item
            }
        }
        bw.write("$resIdx $res\n")
    }

    br.close()
    bw.flush()
    bw.close()
}
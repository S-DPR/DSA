import java.io.*
import kotlin.math.abs
/*
23792번 K번째 음식 찾기 2

세 정렬된 배열 arr, brr, crr이 주어진다.
이후, a, b, c, k가 Q번 주어진다.
arr의 a번째 원소까지, brr의 b번째 원소까지, crr의 c번째 원소까지 합친 배열을 A라고 할 때
A에서 k번째로 작은 원소를 구하시오. arr, brr, crr의 모든 원소는 다르다.
(k <= a+b+c)

K번째 음식 찾기 1과 다를거 없는 문제입니다.
다만 이 문제는 살짝 더 신경써주어야합니다. 그니까 구현에 신경쓴다는게 아니라..
조금 더 편하게 짜자~ 하지 않으면 귀찮아진다.. 는 그런 느낌입니다.
저같은경우 arr, brr, crr과 a, b, c를 각각 배열로 관리하여 모듈러연산을 이용해 구현했습니다.
개뜬금없긴한데 코틀린 `binarySearch`는 인덱스를 -로 돌려준다는게 참...
*/
fun solve(arrays: Array<List<Int>>, a: Int, b: Int, c: Int, k: Int): Pair<Int, Int> {
    val rightIdx = intArrayOf(a, b, c)
    for (standard in 0..2) {
        var left = 0
        var right = rightIdx[standard]
        val next = (standard + 1) % 3
        val nnext = (standard + 2) % 3
        while (left < right) {
            val mid = (left + right) shr 1
            val key = arrays[standard][mid]
            var keyIsKth = mid+1
            keyIsKth += (abs(arrays[next].binarySearch(key))-1).coerceAtMost(rightIdx[next])
            keyIsKth += (abs(arrays[nnext].binarySearch(key))-1).coerceAtMost(rightIdx[nnext])
            if (keyIsKth == k) {
                return Pair(standard+1, mid+1)
            } else if (keyIsKth > k) {
                right = mid
            } else {
                left = mid + 1
            }
        }
    }
    return Pair(-1, -1)
}

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val n = br.readLine().toInt()
    val arr = br.readLine().split(" ").map {it.toInt()}
    val brr = br.readLine().split(" ").map {it.toInt()}
    val crr = br.readLine().split(" ").map {it.toInt()}
    val arrays = arrayOf(arr, brr, crr)
    val Q = br.readLine().toInt()
    repeat(Q) {
        val (a, b, c, k) = br.readLine().split(" ").map {it.toInt()}
        val res = solve(arrays, a, b, c, k)
        bw.write("${res.first} ${res.second}\n")
    }

    br.close()
    bw.flush()
    bw.close()
}

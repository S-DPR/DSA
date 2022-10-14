import java.io.*
import kotlin.math.ceil
import kotlin.math.log2
/*
10868번 최솟값

최솟값 세그먼트트리를 만들면 AC를 맞는 쉬운 문제입니다.
쉬운문제이다못해, 그냥 진짜 구현만 하면 되는 기초문제입니다.
*/
var nums : LongArray? = null
var segment : LongArray? = null

fun makeSegment(n:Int, start:Int, end:Int){
    if (start == end) {segment!![n] = nums!![start]}
    else {
        val mid: Int = (start + end) / 2
        makeSegment(n * 2, start, mid)
        makeSegment(n * 2 + 1, mid + 1, end)
        segment!![n] = segment!![n*2].coerceAtMost(segment!![n*2+1])
    }
}

fun findMin(n:Int, start:Int, end:Int, left:Int, right:Int): Long{
    if (left > end || right < start) return Long.MAX_VALUE
    if (left <= start && end <= right) return segment!![n]

    val mid:Int = (start + end) / 2
    val leftResult = findMin(n*2, start, mid, left, right)
    val rightResult = findMin(n*2+1, mid+1, end, left, right)
    return leftResult.coerceAtMost(rightResult)
}

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (n, m) = br.readLine().split(" ").map{it.toInt()}

    nums = LongArray(n) {br.readLine().toLong()}
    segment = LongArray(1 shl ceil(log2(n.toFloat())+1).toInt()) {Long.MAX_VALUE}
    makeSegment(1, 0, n-1)

    val result = mutableListOf<Long>()
    repeat(m){
        val (a, b) = br.readLine().split(" ").map{it.toInt()}
        result.add(findMin(1,1,n,a,b))
    }

    result.forEach{println(it)}
    br.close()
}
import java.io.*
/*
12899번 데이터 구조

제목이 자료 스트럭처인 특이한 쿼리문제입니다.
1번 쿼리는 배열에 수 X를 추가,
2번 쿼리는 배열에 있는 수중 X번째 수 출력 후 제거.
평범한 이진탐색 세그트리문제입니다.

제가 처음에 풀 때는 동적 세그트리 적용해보겠다고 낭비를 많이 했길래..
이번에 새로 코드를 짰습니다.
펜윅트리 코드에 대한 설명은 생략하겠습니다.
*/
private class Fenwick{
	// 펜윅트리는 공간복잡도가 O(N)입니다. N이 200만이면 200만의 공간만 배정해주면 딱 맞아요!
    private val F = IntArray(2000001) {0}
    private val size = 2000000
    private fun internal_query(idx_:Int):Int{
        var idx = idx_; var res = 0
        while (idx > 0){
            res += F[idx]
            idx -= idx and -idx
        }
        return res
    }

    fun query(l:Int, r:Int):Int{
        return internal_query(r) - internal_query(l-1)
    }

    fun update(idx_:Int, value:Int){
        var idx = idx_
        while (idx < size){
            F[idx] += value
            idx += idx and -idx
        }
    }
}

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val T = Fenwick()
    repeat(br.readLine().toInt()){
        var (c, v) = br.readLine().split(" ").map{it.toInt()}
		//쿼리를 입력받습니다. 1번, 2번 모두 2개의 자연수가 들어와서 이렇게 쓸 수 있습니다.
        when(c){
            1 -> T.update(v, 1) // c가 1이면 v를 업데이트하고,
            2 -> {
                var l = 1; var r = 2000000
				// 2면 이분탐색을 합니다.
				// 개인적으로 펜윅트리 이분탐색을 처음 해봤습니다.
                while (l < r) {
                    val m = (l + r) shr 1
                    val mValue = T.query(l, m)
                    if (mValue >= v) r = m
                    else {l = m+1; v -= mValue}
                }
                bw.write("$r\n")
                T.update(l, -1)
            }
        }
    }

    br.close()
    bw.flush()
    bw.close()
}
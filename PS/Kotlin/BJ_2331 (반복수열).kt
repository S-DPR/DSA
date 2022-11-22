import java.io.*
/*
2331번 반복수열

다음과 같은 수열이 있다.
D[1] = A
D[n] = D[n-1]의 각 자리 숫자를 P번 곱한 수의 합
이 수열을 반복하다보면 어느순간 반복하는 순간이 나타난다.
그 부분 이후를 제외했을 때 수열에 남는 수의 개수를 구하여라.

완전탐색인줄알았는데 완전탐색 태그는 없네요..?
그냥 반복해주다가 나타난 순간 인덱스를 보고 그 인덱스를 출력하면 됩니다.
*/
fun pow(x:Int, a:Int):Int{
    var res = x
    repeat(a-1) { res *= x }
    return res
}

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    var (A, P) = br.readLine().split(" ").map {it.toInt()}
    val arr = mutableListOf<Int>()
    while (!arr.contains(A)){
        arr.add(A)
        var tmp = 0
        while (A > 0) {
            tmp += pow(A%10, P)
            A /= 10
        }
        A = tmp
    }
    println(arr.indexOf(A))
    br.close()
    bw.flush()
    bw.close()
}

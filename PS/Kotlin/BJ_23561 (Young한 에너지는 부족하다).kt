import java.io.*
/*
23561번 Young한 에너지는 부족하다

수 N과 3N의 길이를 갖는 수열 A가 주어진다.
A에서 원소를 3개씩 묶고, 그 중간값으로 N의 길이를 갖는 수열 B를 만들자.
B의 최댓값과 최솟값 차의 최소를 구하시오.

음..
보면 바로 느낌은 오지만..
왜 그렇게되는지는 알기 힘든.. 그런 문제입니다.
수열 A를 정렬합시다.
그리고 인덱스 N과 2N-1번째 인덱스 원소의 차를 구하여 출력합시다.
그러면 AC를 맞습니다.
*/
fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val n = br.readLine().toInt()
    val arr = br.readLine().split(" ").map {it.toInt()}.toIntArray()
    arr.sort()
    bw.write("${arr[arr.size-n-1]-arr[n]}")

    br.close()
    bw.flush()
    bw.close()
}

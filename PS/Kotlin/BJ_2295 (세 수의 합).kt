import java.io.*
/*
2295번 세 수의 합

수열이 주어졌을 때, 수열 내의 세 수를 더해 수열 내의 수를 만들려 한다.
만들 수 있는 가장 큰 수를 구해보자.
수열 내의 세 수를 중복해 골라도 된다.
(5 <= 수열의 길이 <= 1000)

수열의 길이를 보면, O(N^2)까지는 되겠구나 하고 거기에 착안해 푼 문제.
코틀린의 Set이 트리기반인지 해시기반인지 모르겠습니다. 트리형식이면 O(N^2(lgN))이겠네요.

먼저, 배열의 두 수의 합을 모두 구해 set안에 넣습니다.
이제 배열의 두 수를 골라 그 차를 구한 뒤, 그 값이 set안에 있나 확인합니다.
만약에 있다면 그 수는 수열 내의 세 수를 더해 만들 수 있는 수가 됩니다.
*/
const val inf = 1_000_000_000L

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val T = br.readLine().toInt()
    val arr = LongArray(T) {br.readLine().toLong()}
    val plus = mutableSetOf<Long>()
    arr.sort()

    for (i in 0 until T)
        for (j in 0 until T)
            plus.add(arr[i]+arr[j])

    var result = -inf
    for (i in 0 until T) {
        for (j in 0 until T) {
            if (!plus.contains(arr[i] - arr[j])) continue
            result = maxOf(result, arr[i])
        }
    }
    bw.write("$result")

    br.close()
    bw.flush()
    bw.close()
}

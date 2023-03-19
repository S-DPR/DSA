import java.io.*
/*
1083번 소트

수열이 주어진다. S번 이하로 인접한 것과 스왑하여 사전순으로 가장 나중인 수열을 만들어보자.

과제하기싫다..
어쨌든 문제 자체는 그냥 대충 풀면 되는 수준입니다.
확실히 그리디가 빠르게 풀고 끝내기 좋아요.

그냥 S번 이내로 앞으로 끌고올 수 있는 가장 큰 수를 찾아내고, 맨 앞으로 끌고오면 됩니다.
S에선 그 수만큼 빼고요, 기준인덱스 1 올려서 이제 그 기준인덱스부터 계속 반복하고요.
이제 의미 없어지거나 S가 0이 됐다면 바로 탈출하면 됩니다.
*/
fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val N = br.readLine().toInt()
    val arr = br.readLine().split(" ").map {it.toInt()}.toMutableList()
    var K = br.readLine().toInt()

    var repeatCnt = 0
    while (K > 0) {
        var (idx, max) = intArrayOf(-1, -1)
        for (i in repeatCnt until minOf(repeatCnt+K+1, N)) {
            if (max < arr[i]) {
                idx = i
                max = arr[i]
            }
        }
        if (idx == -1) break
        arr.removeAt(idx)
        arr.add(repeatCnt, max)
        K -= idx-repeatCnt
        repeatCnt++
    }
    arr.forEach{bw.write("$it ")}

    br.close()
    bw.flush()
    bw.close()
}

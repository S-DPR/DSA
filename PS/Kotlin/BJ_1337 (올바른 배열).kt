import java.io.*
/*
1337번 올바른 배열

"배열이 주어진다. 이 배열에 수를 최대한 적게 추가해서, 5개의 원소를 뽑았을 때
연속된 자연수 5개가 될 수 있는 경우의수가 하나라도 있게 하려고 한다.
최소한 몇개의 원소를 추가해야하는지 구하시오."

.. 문제를 잘못 이해해서 세번정도 쉐도우복싱 했습니다.
*/
fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val arr = IntArray(br.readLine().toInt()) {br.readLine().toInt()}
    arr.sort() // 저는 sort를 한번 해줄겁니다.
    var ans = 4
    arr.forEachIndexed{ idx, i ->
        if (idx == arr.size-1) return@forEachIndexed // idx가 마지막이면 무조건 4개 추가해야하니, 패스.
        var k = idx+1 // k에는 다음 수를,
        var now = i // now에는 현재 수를,
        var cnt = 0 // cnt에는 추가한 개수를 적어주고.
        repeat(4){ // 연속된 자연수가 5개만 있으면 되니 4번만 테스트해봅시다.
            if (k < arr.size && arr[k] - now == 1) k++ // k가 범위 내고, arr[k] - now가 1이면 arr안에 필요한 수가 있는거고요,
            else cnt++ // 아니면 수를 추가해야겠죠.
            now++ // 그리고 현재 수는 1을 증가시켜줍니다. 제가 N을 테스트하고있었으면, 적어도 이번 트라이에선 N+1을 추가한거니까요.
        }
        ans = minOf(cnt, ans) // 답은 cnt와 ans중 더 작은 값이겠죠.
    }
    println(ans) // 출력합니다.

    br.close()
    bw.flush()
    bw.close()
}

/*
다른사람의 풀이를 보다가 정말 똑똑한 풀이를 보았습니다.
numberList.forEachIndexed { index, num ->
        val continuous = numberList.count { it <= num + 4 && it >= num }
        max = max(max, continuous)
	}
이건데요, count안에 lambda를 쓸 수 있단점, 현재 수 기준으로 num <= it <= num+4의 개수를 세면 된다는 점.
생각도 못했는데 이런 깔끔한 방법이 있을줄은 몰랐습니다..
*/
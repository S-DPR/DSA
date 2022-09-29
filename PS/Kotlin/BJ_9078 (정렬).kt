import java.io.*
/*
9078번 정렬

정렬문제는 많죠.
이도 그중 하나지만, 이유는 몰라도 G2라는 난이도를 책정받았습니다.
(물론, 기여한 사람이 적어서 그닥 정확하진 않습니다. 전 G4로 기여했습니다.)
어쨌든 문제는..
"길이가 n인 배열이 있는데,
여기서 이웃하는 두 수를 묶어서 어떤 수 사이나 수열의 맨 앞, 뒤에 배치할 수 있어.
이 방식으로 이 수열을 정렬상태로 만들 수 있을까?"
입니다.

언뜻 보면 이게 먼소린가 싶지만..
정해는 나중에 끝에서 설명하고요,
저는 테스트케이스가 20이고 N은 최대 100이란점을 이용해 그냥 O(n)시간복잡도 가진거 많이 써서 풀었습니다.
네, 덱으로 풀었습니다.
*/

/*
코드만 보면 알고리즘 이해가 어려우니, 
예시를 들어봅시다.

먼저, 저는 now=1의 인덱스를 찾습니다.
그리고 이 인덱스의 값을 보는데요,

1. 이 인덱스가 0이면 이미 정렬이 된 상태이므로 그냥 왼쪽을 pop해줍니다.
그 뒤 now를 1 올립니다.
1 2 3 4 5 ->  2 3 4 5

2. 이 인덱스가 1~n-2라면, 이 수와 뒤에 있는 수를 묶어 맨 앞으로 옮겨줍니다.
그러면 반드시 현재 맨 앞에 와야할 수가 맨 앞에 오게됩니다.
그러니, 찾은 수 뒤에 있는 수를 맨 앞에 옮기고, 찾는 수와 그 뒤에 있는 수를 모두 지웁시다.
이후, now를 1 올립니다.
2 1 3 4 5 -> 1 3 2 4 5 -> 3 2 4 5

3. 이 인덱스가 n-1이라면 이 수와 앞에 있는 수를 묶어 맨 앞으로 옮겨줍니다.
그 뒤 now를 올리지 않고, 현재 now에서 이 과정을 반복하면 2번 과정으로 넘어가게 됩니다.

이 방법을 채택했습니다. 바로 떠오르는게 이거밖에 없더라구요.
*/
fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    repeat(br.readLine().toInt()) {
        val n = br.readLine().toInt()
        val arr = ArrayDeque(br.readLine().split(" ").map { it.toInt() })
        var now = 1 // 이 수는 현재 찾는 수입니다.
        while (arr.size > 2) { // arr의 size가 2 초과라면 이 반복을 계속 돌아줍시다.
            val i = arr.indexOf(now) // now의 인덱스를 찾고..
            if (i == 0){ // i가 0이면 그냥 맨 앞만 터뜨리고 now를 올립시다.
                arr.removeFirst()
                now++ // 찾으려는 수는 정렬이 되었으니 찾으려는 수를 1 더해줍니다.
            }
            else if (i < arr.size - 1) { // 그 외에, i가 맨 끝이 아니라면요,
                arr.addFirst(arr[i + 1]) // 맨 앞에 i+1을 추가해주고요.
                arr.removeAt(i+1) // 추가하기 전 i번째 인덱스를 삭제해줍시다.
                arr.removeAt(i+1) // 이제 삭제 됐으니 원래 인덱스 사이즈입니다. i+1번 인덱스를 제거해줍시다.
                now++ // 찾으려는 수는 정렬이 되었으니 찾으려는 수를 1 더해줍니다.
            } else { // 맨 마지막이면 바로 못터뜨립니다.
                val tmp1 = arr[i] // 찾으려는 수와
                val tmp2 = arr[i-1] // 찾으려는 수 앞의 수를 잡고
                arr.removeAt(i) // 맨 뒤 수를 제거해줍니다. arr.removeLast()와 같아서, O(1)입니다.
                arr.removeAt(i-1) // 이거도 마찬가지입니다.
                arr.addFirst(tmp1) // 그리고 앞에 수를
                arr.addFirst(tmp2) // 천천히 추가해줍니다.
            }
        }
        if (n == 1) bw.write("YES\n") // n이 1이면 무조건 정렬이 되어있는거고요,
        else bw.write(if (arr[0] < arr[1]) "YES\n" else "NO\n") // 그게 아니라면 첫 원소보다 두번째 원소가 더 크면 정렬이 된거겠죠.
    }

    br.close()
    bw.flush()
    bw.close()
}
/*
이 문제의 정해는 Inverse Counting이라고 합니다.
궁금하신분은 '수열의 홀짝성'을 검색해봅시다.
*/
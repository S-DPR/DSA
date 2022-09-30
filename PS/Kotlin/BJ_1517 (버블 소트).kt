import java.io.*
/*
1517번 버블 소트

제가 처음 푼 플래티넘 문제였습니다.
어.. 좀 더 정확히는, 한 90%의 힌트를 받고 10%는 제가 풀었습니..다?
이유는, 알고리즘 분류를 봤던걸로 기억합니다.
뭐 세그먼트트리랑 머지소트트리 있었던것 같아요.
요즘은 끄고해서 모르겠지만..
여하튼, 문제를 풀고부터 PS에 흥미를 붙였습니다.

태그를 본 덕분에 '머지소트가 먼데?' 로 들어갔고,
'병합정렬이란게 있구나~~'까지 알았고.
'근데 이게 왜쓰이는데?'로 들어가서
이게 여러 테스트케이스를 만들어서 몇 시간 누워서 실험해보니 규칙이 있었고,
'아 그럼 이거 이렇게 쓰면 되는게 아닐까?' 해서 풀었던것 같습니다.
그리고 제 방식을 검색해보니 Inversion Counting이라고 합니다.

이 문제의 정해는 'Inversion Counting'을 세는것입니다.
이거 모르면 이 문제 못풉니다.
저게 생각보다 여기저기 박혀있는데 눈치채기는 힘들어서..

이 문제를 풀 때는 세그먼트트리를 제대로 활용하지 못했기때문에,
병합정렬로 Inversion Counting을 계산했습니다.
아마 제가 한것중 유일하게 병합정렬로 한게 아닐까..
*/
var cnt:Long = 0

// 병합정렬입니다.
fun merge(arr:LongArray):LongArray{
    if (arr.size == 1) return arr
    val mid = arr.size / 2
    val arr1 = merge(arr.slice(0 until mid).toLongArray())
    val arr2 = merge(arr.slice(mid until arr.size).toLongArray())
    return mergesort(arr1, arr2)
}

/*
병합정렬을 실행할 떄, 왼쪽에서 오른쪽으로 보내는걸 주목해봅시다.
[1,3,5] 와 [2,4,6]을 병합정렬 한다면,
2를 집어넣을 때 [3,5]와 [2,4,6]이 남는데.
다시생각해보면 저 두번째 주석을 정렬한다는건 [1,3,5,2,4,6]을 정렬한다는 뜻입니다.
1은 자기자리니 그렇다 치는데 2는 자기자리로 가려면 두 번 스왑하게되죠.
그럼 이제 4 차례를 보면, [1,2,3,5,4,6]이 됩니다. 그러면 4는 한번 스왑하겠죠.
그러면, 왼쪽은 애초에 왼쪽에서 정렬이 되어있으니 문제 없고, 문제는 오른쪽인건데..

결론부터 말하면,
(왼쪽 배열 크기) - (현재 이동 준비중인 왼쪽 인덱스) 만큼, Inversion Counting이 일어납니다.
병합정렬은 위 예시에서 나온 버블소트를 '한번에' 하는 역할로 이해하면 좋습니다.
이 점을 이용해 빠르게 Inversion Counting을 계산할 수 있습니다.
*/
fun mergesort(arr1:LongArray, arr2:LongArray):LongArray{
    val result = LongArray(arr1.size+arr2.size)
    var idx1 = 0; var idx2 = 0; var idxr = 0
    while (arr1.size > idx1 && arr2.size > idx2) {
        result[idxr++] = if (arr1[idx1] > arr2[idx2]){
            cnt += arr1.size - idx1; arr2[idx2++]
        }else arr1[idx1++]
    }
    while (arr1.size > idx1) result[idxr++] = arr1[idx1++]
    while (arr2.size > idx2) result[idxr++] = arr2[idx2++]
    return result
}

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    br.readLine()
    val t = br.readLine().split(" ").map {it.toLong()}.toLongArray()
    merge(t) // 그러면 입력받은 배열을 바로 머지소트 해서 답을 만들어낼 수 있겠죠.
    println(cnt) // 답은 int범위를 넘어갈 수 있습니다. 주의합시다.
}
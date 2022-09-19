import java.io.*
import kotlin.math.*
/*
2106번 통계학
개인적으로 수를 다루는 문제중에선 가장 괜찮게 여러 활용을 해보는 문제라고 생각해서,
코틀린 재활훈련 겸 한번 해보았습니다.

문제는 정말 간단한합니다.
수열을 줄테니, 그 평균(소숫점 첫째자리에서 반올림), 중간값, 최빈값, 범위를 구해보아라..
풀면서 파이썬 쓰고싶어지는 문제가 하나둘이 아닌데 솔직히 이 문제가 제일 심합니다.
*/
fun main(){
	// 코틀린의 빠른 입출력입니다. PS에선 필수죠.
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))
	
	// arr을 받아줍니다.
    val arr = IntArray(br.readLine().toInt()) {br.readLine().toInt()}

	// 평균은 바로 구할 수 있죠. 개인적으로 이부분은 파이썬이 수입좀 해야한다고 생각합니다. 아님말고~
    bw.write("${round(arr.average()).toInt()}\n")
	
	// 카운팅정렬로 최빈값과 중간값을 구할건데요,
    val cnt = IntArray(8001) {0}
    arr.forEach{cnt[it+4000]++} // 이렇게 해줍니다. 문제에서 원소는 절댓값 4000을 넘지 않는다고 했습니다.

    var mid = (arr.size+1)/2 // 중간값을 구할건데,
    run loop@{
        cnt.forEachIndexed { idx, i ->
            mid -= i // i를 계속 빼다보면..
            if (mid <= 0) { // 언젠가는 이 값이 0 이하가 됩니다. 이때를 캐치해서
                bw.write("${idx - 4000}\n") // 그 인덱스에서 4000을 뺀걸 넣고
                return@loop // 반복을 돌아봅시다.
            }
        }
    }
	
	// 다음은 최빈값입니다. most는 나오는 횟수, most_nums는 그 숫자들입니다.
    var most = 0; val most_nums = mutableListOf<Int>()
    cnt.forEachIndexed{idx, i -> // 역시 cnt에서 반복을 돌거고요.
        if (most == i) most_nums.add(idx-4000) // 같으면 그냥 most_nums에 추가하고
        if (most < i) { // i가 더 크다면
            most_nums.clear() // 다 비우고
            most_nums.add(idx-4000) // 이 수만 추가하고
            most = i // most를 갱신합니다.
        }
    }
	// most_nums에 두 개 이상의 수가 있다면 두번째 수를 넣고, 아니면 첫번째 수를 넣습니다.
    bw.write("${if (most_nums.size >= 2) most_nums[1] else most_nums[0]}\n")
	// 범위는 바로 구할 수 있습니다.
    bw.write("${abs(arr.maxOf{it}-arr.minOf{it})}")

    br.close()
    bw.flush()
    bw.close()
}
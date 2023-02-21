package main

import (
	"bufio"
	"fmt"
	"os"
)

var arr []bool

/*
4241번 소수 없는 수열

수 N, M, K가 주어진다.
N부터 M까지 있는 수열중, 연속된 2, 3, ..., K개의 합이 소수가 아닌 수열 중 사전순으로 제일 앞에 오는 수열을 출력하자.
입력의 마지막에는 0 0 0이 주어진다.

꺄아아아앙아아ㅏㄱ
구현은 지옥이다

오랜만에 푼 백트래킹 문제입니다.
그런데 이런식으로 만나고싶진 않았는데...
여하튼, N, M이 1이상 1000이하라는 작은 범위,
아무리 생각해도 이분탐색, DP, 그리디, .. 등 뭐로 가도 나올것같지 않은 답을 종합해 백트래킹을 맞췄습니다.

시간복잡도는 매우 파멸적입니다. 답이없어요 엄청 클텐데 못구하겠어요.
코틀린으로 하려했었는데 메모리초과맞고 그냥 Go로 옮겨왔습니다.
이런문제면 뭐 딴사람 코드 볼 필요도 없겠네요.. 실력향상이고머고 이건 그냥 파멸이야..
*/
func loop(min, max, d int, exist []bool, prefix, cur []int) (bool, []int) {
	if len(cur) == max-min+1 {
		for i := 2; i <= d; i++ {
			if arr[prefix[i]] {
				return false, cur
			}
		}
		return true, cur
	}
	for i := 2; i <= d && len(cur)-i >= 0; i++ {
		prefix[i] -= cur[len(cur)-i]
	}
	for i := min; i <= max; i++ {
		if !exist[i-min] {
			pref := make([]int, len(prefix))
			copy(pref, prefix)
			isPrime := false
			for j := 2; j <= d; j++ {
				pref[j] += i
				if arr[pref[j]] {
					isPrime = true
				}
			}
			if !isPrime {
				exist[i-min] = true
				res, ary := loop(min, max, d, exist, pref, append(cur, i))
				if res {
					return true, ary
				}
				exist[i-min] = false
			}
		}
	}
	return false, cur
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	MAX := 1_000_000
	arr = make([]bool, MAX+1)
	for i := 2; i <= MAX; i++ {
		arr[i] = true
	}
	for i := 0; i <= MAX; i++ {
		if arr[i] {
			for j := i + i; j <= MAX; j += i {
				arr[j] = false
			}
		}
	}

	for {
		var min, max, d int
		fmt.Fscan(br, &min, &max, &d)
		if min+max+d == 0 {
			break
		}
		res, cur := loop(min, max, d, make([]bool, max-min+1), make([]int, d+1), make([]int, 0))
		if res {
			fmt.Fprintf(bw, "%d", cur[0])
			for i := 1; i < len(cur); i++ {
				fmt.Fprintf(bw, ",%d", cur[i])
			}
			fmt.Fprintln(bw)
		} else {
			fmt.Fprintln(bw, "No anti-prime sequence exists.")
		}
	}

	bw.Flush()
}

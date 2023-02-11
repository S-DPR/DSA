package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
23759번 비슷한 문자열

문자열 두 개가 같은 위치에서 같은 문자가 나온다면 비슷한 문자열이라고 한다.
길이가 K로 고정된 문자열이 N개 주어진다.
주어진 문자열 순서대로 두고, 이중 T개를 제거하여 인접한 문자열끼리 비슷하게 만들려고 한다.
T의 최솟값을 구해보자.

음...
신기한문제.
일단 dp라고는 하는데 저는 map으로 풀었습니다. 근데 따지고보면 dp긴하네요.

dp에는 map을 K개 만들어둡시다.
다음으로, 입력받은 문자열을 거꾸로 돕시다. (어쩌면 그대로 돌아도 괜찮을지도 모르겠지만)
문자열을 하나씩 살펴보는데, dp[문자열인덱스][문자]가 존재하지 않는다면 N으로 초기화해줍니다.
이후 있는 문자중 min값을 구해줍니다.

이제 문자열에 존재하는 모든 문자에 대해 min값으로 넣어줍니다.
이를 모든 문자열에 대해 반복하고, 그중 최솟값을 구하면 정답이 됩니다.

난이도 기여를 보니 결국 LIS문제라고도 하고.. 위상정렬로도 풀었다고하고..
신기하네요.

근데 검색 좀 해보면 결국 이런 방식으로 푼 사람이 있네요. 나쁘지 않은 방법같아요.
dp인건 바로 알아챘는데 구현을 어떻게할까 고민을 한 문제였습니다.
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	var N, K int
	fmt.Fscan(br, &N, &K)
	arr := make([]string, N)
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &arr[i])
	}
	dp := make([]map[rune]int, K)
	for i := 0; i < K; i++ {
		dp[i] = make(map[rune]int)
	}

	for i := N - 1; i >= 0; i-- {
		min := N
		for k := 0; k < K; k++ {
			char := rune(arr[i][k])
			if dp[k][char] == 0 {
				dp[k][char] = N
			}
			if dp[k][char]-1 < min {
				min = dp[k][char] - 1
			}
		}
		for k := 0; k < K; k++ {
			char := rune(arr[i][k])
			if min < dp[k][char] {
				dp[k][char] = min
			}
		}
	}

	result := N
	for i := 0; i < K; i++ {
		for _, val := range dp[i] {
			if val < result {
				result = val
			}
		}
	}
	fmt.Fprintln(bw, result)

	bw.Flush()
}

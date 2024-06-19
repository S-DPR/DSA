package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
23891번 타이어 끌기

사람이 p명 배치되어있는 점수가 s인 타이어가 N개 있다.
타이어를 뺏으려면 p명보다 많은 사람을, 소유를 무산시키려면 p명을 보내야한다.
사람이 현재 M명 있을 때 상대보다 더 많은 점수를 얻을 수 있을까?

냅-색
dp[0]에 타이어 전부 뺏겼다 해둔 뒤 처리해주면 됩니다.
이런 냅색은 처음이네요. 간단해.
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N, M int
	fmt.Fscan(br, &N, &M)
	dp := make([]int, M+1)
	A := make([][2]int, N)
	for i := 1; i <= M; i++ {
		dp[i] = -1 << 30
	}
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &A[i][0], &A[i][1])
		dp[0] -= A[i][0]
	}
	for _, n := range A {
		nw_dp := make([]int, M+1)
		for i := 0; i <= M; i++ {
			nw_dp[i] = dp[i]
		}
		s, p := n[0], n[1]
		for i := M; i-p >= 0; i-- { // 비기는경우
			if dp[i-p] != -1 {
				nw_dp[i] = max(nw_dp[i], dp[i-p]+s)
			}
		}
		for i := M; i-p-1 >= 0; i-- { // 이기는 경우
			if dp[i-p-1] != -1 {
				nw_dp[i] = max(nw_dp[i], dp[i-p-1]+s*2)
			}
		}
		dp = nw_dp
	}
	ret := -1
	for _, i := range dp {
		if i > 0 {
			ret = max(ret, 1)
		}
		if i == 0 {
			ret = max(ret, 0)
		}
	}
	if ret == 1 {
		fmt.Println("W")
	}
	if ret == 0 {
		fmt.Println("D")
	}
	if ret == -1 {
		fmt.Println("L")
	}
}

package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
)

/*
2031번 이 쿠키 달지 않아!

수직선상에 점이 N개 있다.
여기에 길이가 D인 선을 K개 그을 수 있다.
그은 선에 점을 최대 몇 개 포함할 수 있을까?

그냥 dp+이분탐색.
이런 조합 꽤 많이 봐왔던거같네요.
*/
var (
	T, N, D, K int
	A          []int
)

func bisect(A []int, x int) int {
	lo, hi := 0, len(A)
	for lo < hi {
		mid := (lo + hi) >> 1
		if A[mid] >= x {
			hi = mid
		} else {
			lo = mid + 1
		}
	}
	return hi
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	fmt.Fscan(br, &T, &N, &D, &K)
	A := make([]int, N)
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &A[i])
	}
	sort.Ints(A)
	dp := make([][]int, N+1)
	for i := 0; i <= N; i++ {
		dp[i] = make([]int, K+1)
		for j := 1; j <= K; j++ {
			dp[i][j] = -1
		}
	}
	ret := 0
	for i := 0; i < N; i++ {
		idx := bisect(A, A[i]+D)
		cnt := idx - i
		for j := 0; j < K; j++ {
			if i > 0 {
				dp[i][j] = max(dp[i][j], dp[i-1][j])
			}
			if dp[i][j] != -1 {
				dp[idx][j+1] = max(dp[idx][j+1], dp[i][j]+cnt)
				ret = max(ret, dp[idx][j+1])
			}
		}
	}
	fmt.Println(ret)
}

package main

import (
	"bufio"
	"fmt"
	"os"
)
/*
12019번 동아리방 청소!

N일동안 동아리원이 올 계획이 있다.
동아리방에 만약 K명이 오면 동아리원들이 나가고 더러움이 K만큼 상승한다.
동아리방에 K명이 오면 들어올 당시의 더러움만큼 불쾌감을 느낀다.
민규는 귀찮아서 단 M일만 청소를 할 것이다. 청소를 하면 더러움이 0이 된다.
언제 청소해야 전체 불쾌감이 최소가 되고, 그 불쾌감은 몇일까?

아..
너무오래시간박았다..
진짜 dp 너무싫다..

4중for문 굴려서 어떻게든 푸는 문제..
dp[i][j][k] = i번째 날에 청소를 하는데, 그게 j번째 청소고, 그 상태로 k일까지 보는 경우의 수.
하루종일 풀었네요. 이런 dp 잘 푸는 방법을 알아봐야지..
대충 O(N^3*K).. 그래도.. 최대한 열심히했다...
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	var N, K int
	fmt.Fscan(br, &N, &K)
	items := make([]int64, N)
	pf := make([]int64, N+1)
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &items[i])
		pf[i+1] = pf[i] + items[i]
	}
	dp := make([][][]int64, N+1)
	trace := make([][][]int, N+1)
	for i := 0; i <= N; i++ {
		dp[i] = make([][]int64, K+1)
		trace[i] = make([][]int, K+1)
		for j := 0; j <= K; j++ {
			dp[i][j] = make([]int64, N+1)
			trace[i][j] = make([]int, N+1)
			for k := 0; k <= N; k++ {
				dp[i][j][k] = 1 << 60
				trace[i][j][k] = 0
			}
		}
	}
	traceBack := N
	ret := int64(1 << 60)
	dp[0][0][0] = 0
	for i := 0; i < N; i++ {
		for j := 0; j <= K; j++ {
			dirty := int64(0)
			for k := i + 1; k <= N; k++ {
				for t := 0; j != 0 && t < k; t++ {
					tmp := dirty * items[k-1]
					if dp[t][j-1][k-1]+tmp < dp[i][j][k] {
						dp[i][j][k] = dp[t][j-1][k-1] + tmp
						trace[i][j][k] = t
					}
				}
				tmp := dp[i][j][k-1] + dirty*items[k-1]
				if tmp < dp[i][j][k] {
					dp[i][j][k] = tmp
					trace[i][j][k] = trace[i][j][k-1]
				}
				if k == N && dp[i][j][k] < ret {
					ret = dp[i][j][k]
					traceBack = i
				}
				dirty += items[k-1]
			}
		}
	}
	fmt.Fprintln(bw, ret)
	stk := make([]int, 0)
	curDay := N
	for i := K; i > 0; i-- {
		stk = append(stk, traceBack)
		prvDay := curDay
		curDay = traceBack
		traceBack = trace[traceBack][i][prvDay]
	}
	for i := len(stk) - 1; i >= 0; i-- {
		fmt.Fprintf(bw, "%d ", stk[i])
	}

	bw.Flush()
}

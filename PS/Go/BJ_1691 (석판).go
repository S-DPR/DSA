package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
1691번 석판

R*C 크기의 석판에서, r*c크기의 석판모양을 떼어낼 것이다.
떼어낼 크기의 석판은 N개가 있으며, 다 떼어냈을 때 남는 석판의 크기를 최소화하려한다.
초기 석판을 가로나 세로로 완전히 원하는대로 찢을 수 있을 때, 어떻게해야 남는 석판을 최소화할 수 있을까?

이야 이게 구간dp네..
솔직히 생각도 못했다..

문장 읽으면서 당연하지 하고 넘겼던 부분이 힌트였다고 합니다.
문제 잘읽어야겠어요..
어쨌든.. 그래서 구간dp.
아 근데 절반만 치니까 600도 O(N^3)이 되긴하네
이건 배워간다
*/
var (
	R, C, N, r, c int
	dp            [601][601]int
)

func loop(r, c int) int {
	if dp[r][c] != 1<<30 {
		return dp[r][c]
	}
	dp[r][c] = r * c
	for i := 1; i <= r/2; i++ {
		dp[r][c] = min(dp[r][c], loop(r-i, c)+loop(i, c))
	}
	for i := 1; i <= c/2; i++ {
		dp[r][c] = min(dp[r][c], loop(r, c-i)+loop(r, i))
	}
	return dp[r][c]
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	fmt.Fscan(br, &C, &R)
	for i := 0; i <= R; i++ {
		for j := 0; j <= C; j++ {
			dp[i][j] = 1 << 30
		}
	}
	fmt.Fscan(br, &N)
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &c, &r)
		dp[r][c] = 0
	}
	fmt.Println(loop(R, C))
}

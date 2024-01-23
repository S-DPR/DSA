package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
)

/*
30896번 두 팀으로 나누기

각 사람마다 협동력 A와 실력 B가 존재한다.
한 팀의 총 실력은 그 팀에 있는 협동력의 최하 * 실력의 합 일 때,
두 팀을 구성했을 때 두 팀의 실력 차이를 최소화해보자.
단, 모든 사람은 한 팀에 속해야한다.

ㅋㅋ 오랜만에 한 페이지 꽉 WA맞네

처음에 dp[i][j] = 팀 A의 협동력 최하가 i, 팀 B의 협동력 최하가 j일 때 차이가 가장 최소가 되는 팀 A의 실력의 합
으로 했다가.. 멸망.

해설 보고 와.. 그렇네.. 했습니다.
문제 관찰을 너무 짧게했어요. 그게 패인같네요..
*/
var (
	S int64
)

func abs(i int64) int64 {
	if i < 0 {
		return -i
	}
	return i
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N int
	var a, b int64
	fmt.Fscan(br, &N)
	A := make([][]int64, N)
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &a, &b)
		A[i] = []int64{a, b}
		S += b
	}
	sort.SliceStable(A, func(i, j int) bool {
		if A[i][0] != A[j][0] {
			return A[i][0] < A[j][0]
		}
		return A[i][1] < A[j][1]
	})
	ret := int64(1 << 60)
	dp := make([]bool, S+1)
	mn := A[0][0]
	for i := N - 1; i > 0; i-- {
		nmn := A[i][0]
		x := A[i][1]
		for k := S; k-x >= 0; k-- {
			if dp[k-x] {
				ret = min(ret, abs(k*nmn-(S-k)*mn))
				dp[k] = true
			}
		}
		dp[x] = true
		ret = min(ret, abs(x*nmn-(S-x)*mn))
	}
	fmt.Println(ret)
}

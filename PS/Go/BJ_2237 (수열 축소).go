package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
2237번 수열 축소

F(A, x)는 A[x] = A[x] - A[x+1] 을 수행하고 A[x+1]을 제거하는 연산이다.
길이가 N인 수열 A가 주어진다. F를 N-1번 수행했을 때 마지막에 남은 수가 T이길 원한다.
x에 들어가야 하는 수를 순서대로 구하시오.

플1 승급문제
1000문제 100기여.

dp역추적이구나 했는데 하다하다 안돼서 내가 잘못했나 하고 태그 까니까 dp는맞고 배낭?
배낭? 배낭??? 이러고있었는데,
ㅋㅋ 내 코드 다시보니까 진짜 배낭모양 ㅋㅋㅋㅋㅋㅋㅋ

수열의 마지막에서 두번째부터 시작합니다.
마지막은 그냥 넣어주고, 이제 나머질 넣을건데.
먼저 이전에 가능했던 요소를 자신에게 빼는 연산을 배낭으로 구현합니다.
이후 내 요소도 뒤쪽 요소를 빼도록 하는 연산을 배낭으로 구현합니다.
그리고 역추적합니다.

저같은경우 use를 이번에 -를 몇 번 썼는지, merge를 이게 지금 몇 번 -연산을 해야하는지로 정의했습니다.
merge가 +때 증가하는 이유는 간단한데, -A-B는 -(A+B)니까.
어쩄든.. 역추적 아니었으면 간단했는데, 역추적이 어려웠네요. 순수배낭 어렵다..
*/
var dp, use, merge [][]int

func loop(n, x int) {
	if dp[n][x] == -1 {
		return
	}
	// fmt.Println(n, x-55, dp[n][x]-55, use[n][x])
	loop(n+1, dp[n][x])
	for i := 0; i < use[n][x]; i++ {
		fmt.Println(n + 1)
	}
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N, T int
	fmt.Fscan(br, &N, &T)
	MX := 20000
	dp = make([][]int, N)
	use = make([][]int, N)
	merge = make([][]int, N)
	A := make([]int, N)
	for i := 0; i < N; i++ {
		dp[i] = make([]int, MX+1)
		use[i] = make([]int, MX+1)
		merge[i] = make([]int, MX+1)
		fmt.Fscan(br, &A[i])
		for j := 0; j <= MX; j++ {
			dp[i][j] = 1 << 30
			use[i][j] = -1
		}
	}
	dp[N-1][A[N-1]+MX/2] = -1
	for i := N - 2; i >= 0; i-- {
		item := A[i] + MX/2
		for j := 0; j <= MX; j++ {
			if dp[i+1][j] == 1<<30 {
				continue
			}
			val := j - MX/2
			if dp[i][item-val] == 1<<30 || use[i][item-val] == -1 {
				dp[i][item-val] = j
				use[i][item-val] = merge[i+1][j] + 1
			}
		}
		for j := 0; j <= MX; j++ {
			if dp[i+1][j] == 1<<30 {
				continue
			}
			val := j - MX/2
			if dp[i][item+val] == 1<<30 {
				dp[i][item+val] = j
				merge[i][item+val] = merge[i+1][j] + 1
			}
		}
	}
	loop(0, T+MX/2)
}

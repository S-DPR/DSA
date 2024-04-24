package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
12975번 트라이슬

1~255를 원소로 갖는 길이가 N인 배열 A를 세 부분으로 나누고,
각 부분을 모두 XOR한 뒤 그 합의 최대를 구하려 한다.
가능한 최대를 구해보자.

옛날에 문제 보고 와 어캐풀지 하면서 답지봤었는데 너무 간결해서 놀랐던 문제
문제는.. 그 코드가 잊히지 않았다는거.. 너무 깔끔해서..
그래서 그냥 오늘 시간도 없고 하면서 풀었습니다.

핵심 아이디어는, 세 부분으로 나누는 대신 두 부분으로 나누자.
XOR의 성질을 이용해, A를 모두 XOR한부분과 각 두 부분의 모든 XOR과 XOR을 하면,
남은 C가 나온다. 그렇게해서 차원 하나를 줄여서 공간복잡도를줄이자!
이야..
*/
var (
	N, pf int
	A     []int
	dp    [][256][256]int
)

func loop(idx, a, b int) int {
	if idx == N {
		return a + b + (pf ^ a ^ b)
	}
	if dp[idx][a][b] != 0 {
		return dp[idx][a][b]
	}
	dp[idx][a][b] = max(
		loop(idx+1, a^A[idx], b),
		loop(idx+1, a, b^A[idx]),
		loop(idx+1, a, b))
	return dp[idx][a][b]
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	fmt.Fscan(br, &N)
	A = make([]int, N)
	dp = make([][256][256]int, N)
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &A[i])
		pf ^= A[i]
	}
	fmt.Println(loop(0, 0, 0))
}

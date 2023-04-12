package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
2879번 코딩은 예쁘게

N개의 줄이 있는데, 각 줄은 cur[i]만큼 현재 들여쓰기가 되어있다.
각 들여쓰기를 dst[i]로 늘리거나 줄이려는데, i를 포함하는 연속한 범위를 원하는 만큼 잡아 들여쓰기를 넣거나 줄일 수 있다.
최소 몇 번 이 행위를 하여야 하는지 구해보자.

N이 1000 이하라고해서 분류가 dp인줄 알았는데 하다보니 그리디 분할정복..
어떻게 하면 스택느낌으로 풀 수 있을거같긴한데 그냥 그리디분할정복이 편한 것 같습니다.
실제로 숏코딩은 스택방식으로 풀고요. 숏코딩 방식이 좀 더 간단한 방법인거같네요.
그냥 오랜만에 분할정복 연습했단 생각이나 하렵니다..
질문글에 dp로 어캐풀어요?? 하는거 보고 낚였다.. 아..
*/
func abs(n int) int {
	if n < 0 {
		return -n
	}
	return n
}

func loop(cur, dst []int, l, r, extraIndent int) int {
	if l > r {
		return 0
	}
	if l == r {
		return abs(dst[l] - cur[l] - extraIndent)
	}
	minIdx := l
	diff := dst[l] - cur[l] - extraIndent
	for i := l; i <= r; i++ {
		if abs(diff) > abs(dst[i]-cur[i]-extraIndent) {
			minIdx = i
			diff = dst[i] - cur[i] - extraIndent
		}
	}
	left := loop(cur, dst, l, minIdx-1, diff+extraIndent)
	right := loop(cur, dst, minIdx+1, r, diff+extraIndent)
	return left + right + abs(diff)
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	var N int
	fmt.Fscan(br, &N)
	cur := make([]int, N)
	dst := make([]int, N)
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &cur[i])
	}
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &dst[i])
	}

	result := 0
	left := 0
	sign := dst[0] - cur[0]
	for i := 1; i < N; i++ {
		if sign*(dst[i]-cur[i]) < 0 {
			sign = dst[i] - cur[i]
			result += loop(cur, dst, left, i-1, 0)
			left = i
		}
	}
	result += loop(cur, dst, left, N-1, 0)
	fmt.Fprintln(bw, result)

	bw.Flush()
}

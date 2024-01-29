package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
24536번 정원장어

문자와 수로 이루어진 배열이 주어진다.
문자가 L일 경우 이 배열 왼쪽에 이 수와 같거나 큰 수가 없어야 하고.
문자가 R일 경우 이 배열 오른쪽에 이 수와 같거나 큰 수가 없어야 한다.
이 규칙을 지키기 위해 최소 몇개의 원소를 제거해야할까?

진짜 보자마자 '어어 이거 lis아니냐'
그래서 열심히 lis짜고.. AC.
저번에 먹이사슬이라는 문제에 '와 이게 lis네' 당한적이 있어서..
관찰 성공적으로 한 다음 풀었습니다. 휴..
*/
func bisect(A []int, x int) int {
	left, right := 0, len(A)
	for left < right {
		mid := (left + right) >> 1
		if A[mid] >= x {
			right = mid
		} else {
			left = mid + 1
		}
	}
	return right
}

func lis(H []int, C []rune, obj rune) []int {
	N := len(H)
	dp := make([]int, N+1)
	lis := []int{-1 << 30}
	for i := 1; i < N; i++ {
		if C[i] != obj {
			dp[i] = dp[i-1]
			continue
		}
		idx := bisect(lis, H[i])
		if len(lis) == idx {
			lis = append(lis, H[i])
		} else {
			lis[idx] = H[i]
		}
		dp[i] = len(lis) - 1
	}
	return dp
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N int
	fmt.Fscan(br, &N)
	C := make([]rune, N+1)
	H := make([]int, N+1)
	var S string
	fmt.Fscan(br, &S)
	C = []rune(S)
	C = append([]rune{' '}, C...)
	for i := 1; i <= N; i++ {
		fmt.Fscan(br, &H[i])
	}
	left := lis(H, C, 'L')
	for i := 1; i <= (N+1)/2; i++ {
		C[i], C[(N+1)-i] = C[(N+1)-i], C[i]
		H[i], H[(N+1)-i] = H[(N+1)-i], H[i]
	}
	right := lis(H, C, 'R')
	for i := 1; i <= (N+1)/2; i++ {
		right[i], right[(N+1)-i] = right[(N+1)-i], right[i]
	}
	mx := 0
	for i := 0; i <= N; i++ {
		mx = max(mx, left[i]+right[i+1])
	}
	fmt.Println(N - mx)
}

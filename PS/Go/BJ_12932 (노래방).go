package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
12932번 노래방

수열 arr이 주어진다.
arr을 두 수열로 적당히 나눈 새 수열을 A, B라고 하자.
|A[i]-A[i+1]| (0 < i < A.length-1)
|B[i]-B[i+1]| (0 < i < B.length-1)
의 최솟값을 구해보자.
단, 수열을 나눌 때는 반드시 arr의 맨 처음 숫자부터 하나씩 빼면서 만들어야한다.

전에 푼 '피아노' 문제랑 비슷해서 푼 문제
피아노는 조금 오묘하게(와 이게 되네 느낌) 풀었는데 얘는 골드와 플레의 차이를 보여주듯이 까다롭더라고요.
파아노처럼 했다가 메모리 시간초과 한번씩 맞고 ruby에서 go로 옮겨 풀었습니다. ruby에서 재귀를 못쓰니까..

처음엔 해시를 이용해서 했는데 덕분에 3500ms라는 기적적인 속도가 나왔습니다.
그래서 시간 좀 들여서 180ms까지 줄였습니다.
난이도가 좀 많이 있는거같고, 이렇게 할 수 있구나를 생각해볼 수 있었습니다.

dp[l][r]은 left가 arr[l], right가 arr[r]에 있을 때 최솟값입니다.
어.. 네, 그게 끝이에요. 네..
어쩄든 루비 재귀나 늘어나면 좋겠네요. 한 10만~100만으로.
*/
func abs(i int) int {
	if i < 0 {
		return -i
	}
	return i
}

func min(i, j int) int {
	if i < j {
		return i
	}
	return j
}

func loop(dp [][]int, arr []int, idx, N, l, r, total int) int {
	if idx == N+1 {
		return total
	}
	cur := arr[idx]
	if dp[l][r] != -1 {
		return dp[l][r]
	}
	left := loop(dp, arr, idx+1, N, idx, r, total)
	if l != 0 {
		left += abs(arr[l] - cur)
	}
	right := loop(dp, arr, idx+1, N, l, idx, total)
	if r != 0 {
		right += abs(arr[r] - cur)
	}
	dp[l][r] = min(left, right)
	return dp[l][r]
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	var N int
	fmt.Fscan(br, &N)
	arr := make([]int, N+1)
	for i := 1; i <= N; i++ {
		fmt.Fscan(br, &arr[i])
	}
	dp := make([][]int, N+1)
	for i := 0; i <= N; i++ {
		dp[i] = make([]int, N+1)
		for j := 0; j <= N; j++ {
			dp[i][j] = -1
		}
	}
	fmt.Fprintln(bw, loop(dp, arr, 1, N, 0, 0, 0))

	bw.Flush()
}

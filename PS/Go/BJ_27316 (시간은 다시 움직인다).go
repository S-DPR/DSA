package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
27316번 시간은 다시 움직인다

길이가 N인 수열이 주어진다.
0에서 시작해, 점프를 해서 수열에 있는 숫자를 피하려 한다.
점프는 처음에 1밖에 못하지만, 점프를 하면 이번에 점프한 길이+1만큼 점프하게된다.
또, 점프를 하면 점프한 거리만큼 이동하기 전까지는 다시 점프할 수 없다.
이동은 앞으로만 할 수 있다. 수열의 수를 모두 피하는게 가능한지 구해보자.

아니 boolean이긴해도 배열 1억개 만드는데 100메가정도밖에 안드네..
놀랍다 진짜

처음에 메모리 고려해서 []map[int]int로 했는데 시간초과나서 아 왜나지.. 하다가,
어차피 curL이 500넘기 힘들어보이는데 [200000][500]boolean 해볼까? 해서 했더니 진짜 됐네요.
그나저나 여기서 배운건, 처음엔 그리디인가 했는데 하면 할수록 중복계산 나오는 부분이 보이고,
결국 dp까지 생각해낸거. 좀 값지네요.
*/
var (
	N   int
	A   []int
	dp  [][]bool
	vis [][]bool
)

func min(x, y int64) int64 {
	if x < y {
		return x
	}
	return y
}

func max(i, j int) int {
	if i < j {
		return j
	}
	return i
}

func bisect(arr []int, x int) int {
	left, right := 0, N
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

func loop(curT, curL int) bool {
	if curT > A[N-1] {
		return true
	}
	if vis[curT][curL] {
		return dp[curT][curL]
	}
	flg := false
	idx := bisect(A, curT)
	restIdx := bisect(A, curT+curL)
	if A[idx] != curT {
		flg = flg || loop(max(curT+1, A[idx]-curL), curL)
	}
	if restIdx == N || A[restIdx] >= curT+curL*2 {
		flg = flg || loop(curT+curL*2, curL+1)
	}
	dp[curT][curL] = flg
	vis[curT][curL] = true
	return flg
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	fmt.Fscan(br, &N)
	A = make([]int, N)
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &A[i])
	}
	dp = make([][]bool, A[N-1]+1)
	vis = make([][]bool, A[N-1]+1)
	for i := 0; i <= A[N-1]; i++ {
		dp[i] = make([]bool, 500)
		vis[i] = make([]bool, 500)
	}
	ret := loop(0, 1)
	if ret {
		fmt.Println("YES")
	} else {
		fmt.Println("NO")
	}
}

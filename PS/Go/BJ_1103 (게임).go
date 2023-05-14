package main

import (
	"bufio"
	"fmt"
	"os"
)
/*
1103번 게임

한 자리 숫자와 H로 이루어진 보드판이 있다.
한 자리 숫자에 있을 때는 상하좌우로 그 숫자만큼 이동가능하다.
그리고 판 밖으로 나가거나 H로 움직이면 게임이 종료된다.
좌측 상단에서 시작할 때, 최대 몇 번 이동 가능한지 구해보자.
단, 무한번 움직일 수 있다면 -1을 출력하자.

dp인가 그래프인가 그것이 문제로다 하고 있을 때,
그래프만 고르고 반례 찾으러 질문게시판 가니까,
짜잔 dp와 그래프 모두였네요~ 가 된 문제.
안되면 dp도 섞어야지 뭐.. 이러고 있었긴한데..

제가 내리막길인가 골드3문제에 데인 이후로 DFS/BFS+dp는 참 싫어하는데,
결국엔 오늘 마주쳤습니다. 네..
dfs는 '이 칸에서 판 밖으로 나갈때까지 둘 수 있는 최대 횟수'를 뜻합니다.
물론 사이클도 판별합니다. 뭐 이거야 당연한거고..

어쨌든 덕분에 dp[i][j]는 i번행 j번열에서 갈 수 있는 최대 횟수가 되고,
dp로 중복계산 잘라내버리면서 가면 됩니다.
요즘 골드2는 쉬운거만 걸리네요.. 절대 이런거만 있는게 아닐텐데..
*/
var (
	N, K int
	INF  = 1 << 30
	dx   = []int{1, -1, 0, 0}
	dy   = []int{0, 0, 1, -1}
)

func max(i, j int) int {
	if i < j {
		return j
	}
	return i
}

func dfs(x, y int, M []string, V [][]bool, dp [][]int) int {
	if !(0 <= x && x < K && 0 <= y && y < N) {
		return 0
	}
	if M[y][x] == 'H' {
		return 0
	}
	if dp[y][x] != 0 {
		return dp[y][x]
	}
	if V[y][x] {
		return INF
	}
	V[y][x] = true
	getM := 0
	for i := 0; i < 4; i++ {
		cur := int(M[y][x] & 0xF)
		nx, ny := x+cur*dx[i], y+cur*dy[i]
		getM = max(getM, dfs(nx, ny, M, V, dp)+1)
	}
	dp[y][x] = getM
	V[y][x] = false
	return dp[y][x]
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	fmt.Fscan(br, &N, &K)
	M := make([]string, N)
	dp := make([][]int, N)
	V := make([][]bool, N)
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &M[i])
		dp[i] = make([]int, K)
		V[i] = make([]bool, K)
	}
	ret := dfs(0, 0, M, V, dp)
	if ret >= INF {
		fmt.Fprintln(bw, -1)
	} else {
		fmt.Fprintln(bw, ret)
	}
}

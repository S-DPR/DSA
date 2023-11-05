package main

/*
1649번 택시

사이클이 없는 방향그래프가 주어진다.
s에서 출발하고, C개의 정점을 모두 방문해 e로 도착하는 경우의 수를 구해보자.

풀고보니까 입력형식 오류로 인해 레이팅 못받은 문제..
머 어떻습니까. 오랜만에 위상정렬이었네.

위상정렬 긁어주면서 dp로 싹 처리해주면 됩니다.
DAG인지 확인하는데 시간 좀 걸렸고,
dp만 써서 어떻게 해보려다가 DAG면 위상정렬도 될거같아서 써보니 됐네요.
*/
import (
	"bufio"
	"fmt"
	"os"
)

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N, M int
	fmt.Fscan(br, &N, &M)
	G := make([][]int, N+1)
	V := make([]int, N+1)
	nvis := make([]int, N+1)
	indep := make([]int, N+1)
	for i := 0; i <= N; i++ {
		G[i] = make([]int, 0)
	}
	for i := 0; i < M; i++ {
		var u, v int
		fmt.Fscan(br, &u, &v)
		G[u] = append(G[u], v)
		indep[v]++
	}
	var s, e, c int
	fmt.Fscan(br, &s, &e, &c)
	for i := 0; i < c; i++ {
		var x int
		fmt.Fscan(br, &x)
		V[x] = 1
	}
	Q := make([]int, 0)
	dp := make([]int64, N+1)
	dp[s] = 1
	for i := 1; i <= N; i++ {
		if indep[i] == 0 {
			Q = append(Q, i)
		}
	}
	for len(Q) > 0 {
		x := Q[0]
		Q = Q[1:]
		for _, nxt := range G[x] {
			indep[nxt]--
			if nvis[x]+V[x] >= nvis[nxt] {
				if nvis[x]+V[x] > nvis[nxt] {
					dp[nxt] = 0
				}
				dp[nxt] += dp[x]
				nvis[nxt] = nvis[x] + V[x]
			}
			if indep[nxt] == 0 {
				Q = append(Q, nxt)
			}
		}
	}
	if nvis[e] == c {
		fmt.Fprintln(bw, dp[e])
	} else {
		fmt.Fprintln(bw, 0)
	}
}

package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
)

/*
2026번 소풍

그래프에서 정점의 개수가 K개인 부분 그래프를 뽑아내자.
그러면서, 완전 그래프를 구성하도록 골라보자.
그런 그래프가 여러개라면 사전순으로 가장 앞에 오도록 출력하자.

1154번 팀 편성.. 토요일에 푼거 풀기 전에,
"어 이 문제 풀면 팀 편성 풀수있을거같은데" 하고 저장해둔 문제.
그런데 팀 편성 풀고 그 아이디어 들고와서 이걸 풀어버렸네

팀 편성은 둘로 나누기떄문에 결국 완탐이 되지만,
이거는 안되면 걍 버리기때문에 백트래킹.
답은 나오긴 한데 시간초과 안나려나.. 하고 해봤는데 되네요.
다행..
*/
var (
	K, N, F int
	R       []int
	G       [][]int
)

func loop(x int) bool {
	if x == N+1 {
		return len(R) >= K
	}
	flg := true
	for _, i := range R {
		flg = flg && G[x][i] == 1
	}
	if flg {
		R = append(R, x)
		if loop(x + 1) {
			return true
		}
		R = R[:len(R)-1]
	}
	return loop(x + 1)
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	fmt.Fscan(br, &K, &N, &F)
	R = make([]int, 0)
	G = make([][]int, N+1)
	for i := 0; i <= N; i++ {
		G[i] = make([]int, N+1)
	}
	for i := 0; i < F; i++ {
		var u, v int
		fmt.Fscan(br, &u, &v)
		G[u][v] = 1
		G[v][u] = 1
	}
	ret := loop(1)
	if ret {
		sort.Ints(R)
		for i := 0; i < K; i++ {
			fmt.Fprintln(bw, R[i])
		}
	} else {
		fmt.Println(-1)
	}
}

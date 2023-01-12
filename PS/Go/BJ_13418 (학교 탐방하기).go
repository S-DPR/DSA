package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
)
/*
13418번 학교 탐방하기

그래프가 주어진다.
최대 스패닝 트리와 최소 스패닝 트리를 구한 뒤,
각 가중치의 값을 제곱한 뒤 그 차를 구해보자.

사실 이거 어떻게푸냐.. 하고있다가 그냥 질문글 봤는데,
MST 관련 내용이 있길래 그냥 Ruby로 크루스칼 알고리즘 구현해본 뒤,
바로 적용해봤습니다. 오랜만에 새로운 알고리즘을 공부했네요.

그런데 Ruby로 하니까 시간초과나길래 그냥 Go로 했습니다..
*/
type V struct {
	cost, start, dest int
}

func union(u []int, x int, y int) {
	u[find(u, x)] = u[find(u, y)]
}

func find(u []int, x int) int {
	if u[x] != x {
		u[x] = find(u, u[x])
	}
	return u[x]
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	var N, M int
	fmt.Fscan(br, &N, &M)
	G := make([]V, 0)
	for i := 0; i <= M; i++ {
		var a, b, c int
		fmt.Fscan(br, &a, &b, &c)
		G = append(G, V{c ^ 1, a, b})
	}

	MAX_UNION := make([]int, N+1)
	MIN_UNION := make([]int, N+1)
	for i := 0; i <= N; i++ {
		MAX_UNION[i] = i
		MIN_UNION[i] = i
	}
	MAX_R := 0
	MIN_R := 0

	sort.Slice(G, func(i, j int) bool {
		return G[i].cost > G[j].cost
	})

	for i := 0; i < len(G); i++ {
		if find(MAX_UNION, G[i].start) != find(MAX_UNION, G[i].dest) {
			union(MAX_UNION, G[i].start, G[i].dest)
			MAX_R += G[i].cost
		}
	}

	for i := len(G) - 1; i >= 0; i-- {
		if find(MIN_UNION, G[i].start) != find(MIN_UNION, G[i].dest) {
			union(MIN_UNION, G[i].start, G[i].dest)
			MIN_R += G[i].cost
		}
	}

	fmt.Fprintln(bw, MAX_R*MAX_R-MIN_R*MIN_R)

	bw.Flush()
}

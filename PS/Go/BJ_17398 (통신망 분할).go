package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
17398번 통신망 분할

그래프가 주어진다.
여기서 간선을 Q개 제거하려 하는데, 제거 후 그래프가 둘로 나눠진다면 각 그래프 노드 개수의 곱만큼 비용이 든다.
제거하려는 간선들이 순서대로 주어진다. 비용이 얼마나 나오는지 알아보자.

이야 P5에 -0.49면 그냥 머 곧 골드1되겠네
그래도 세그트리에 좀 절였다고 오프라인쿼리에 익숙해서 금방 풀었습니다.
유니온파인드 구현미스로 다섯번 틀린거 빼고..

그냥 다 받고, 선택받은 간선 제외하고 다 합치고, 선택한 간선은 거꾸로 합치면서 처리하면 됩니다.
그리 어려운 일도 아니죠?
*/
type info struct {
	s, e int
}

var (
	U []int
	S []int64
)

func union(x, y int) int64 {
	x, y = find(x), find(y)
	ret := S[x] * S[y]
	S[x] += S[y]
	U[y] = U[x]
	return ret
}

func find(x int) int {
	if U[x] != x {
		U[x] = find(U[x])
	}
	return U[x]
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N, K, Q int
	fmt.Fscan(br, &N, &K, &Q)
	U = make([]int, N+1)
	S = make([]int64, N+1)
	E := make([]info, K+1)
	C := make([]bool, K+1)
	queries := make([]int, Q+1)
	for i := 0; i <= N; i++ {
		U[i] = i
		S[i] = 1
	}
	for i := 1; i <= K; i++ {
		fmt.Fscan(br, &E[i].s, &E[i].e)
	}
	for i := 1; i <= Q; i++ {
		fmt.Fscan(br, &queries[i])
		C[queries[i]] = true
	}
	for i := 1; i <= K; i++ {
		if !C[i] && find(E[i].s) != find(E[i].e) {
			union(E[i].s, E[i].e)
		}
	}
	ret := int64(0)
	for i := Q; i > 0; i-- {
		j := queries[i]
		if find(E[j].s) != find(E[j].e) {
			ret += union(E[j].s, E[j].e)
		}
	}
	fmt.Fprintln(bw, ret)
}

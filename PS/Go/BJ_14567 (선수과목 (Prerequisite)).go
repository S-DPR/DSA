package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
14567번 선수과목 (Prerequisite)

정점의 개수 N과 간선의 개수 M이 주어진다.
위상정렬하시오.

위상정렬 기본문제입니다. DP나 일반 탐색으로도 풀린다지만..
해보진 않았지만 어떻게하면 위상정렬 안해도 될지는 아이디어가 떠오르긴 하네요.

저는 그냥 위상정렬로 풀었습니다.
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	var n, m int
	fmt.Fscan(br, &n, &m)
	G := make([][]int, n+1)
	V := make([]int, n+1)
	A := make([]int, n+1)
	for i := 0; i <= n; i++ {
		G[i] = make([]int, n+1)
	}
	for i := 0; i < m; i++ {
		var x, y int
		fmt.Fscan(br, &x, &y)
		G[x] = append(G[x], y)
		V[y]++
	}

	deque := make([]int, 0)
	for i := 1; i <= n; i++ {
		if V[i] == 0 {
			deque = append(deque, i)
			A[i] = 1
		}
	}

	for len(deque) > 0 {
		x := deque[0]
		deque = deque[1:]
		for _, i := range G[x] {
			V[i]--
			if V[i] == 0 {
				deque = append(deque, i)
				A[i] = A[x] + 1
			}
		}
	}

	for i := 1; i <= n; i++ {
		fmt.Fprintf(bw, "%d ", A[i])
	}

	bw.Flush()
}

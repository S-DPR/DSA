package main

import (
	"bufio"
	"fmt"
	"math"
	"os"
)

/*
26615번 다오의 행사 계획하기

R*C모양의 맵이 주어진다. 상하좌우로 움직일 수 있으며, 임의의 두 점을 가는 경로는 유일하다.
K개의 쿼리가 주어진다.
S E a b c d V : (a, b)에서 (c, d)로 가는 가중치를 S부터 E시간까지 V만큼 늘린다.
모든 쿼리를 보낸 이후 1부터 T까지 맵의 총 가중치를 구해보자.

쭉 생각하다가..
어.. 이거 LCA구나. 하고 푼 문제.
그냥 LCA 긁기고 두 지점의 최단거리만 구하면 다 끝납니다. 간단하네요.
*/
var (
	R, C, T, K, x    int
	S, E, a, b, c, d int
	V                int64
	N, lgN           int
	depth            []int
	G, sparse        [][]int
	ret              []int64
)

func dfs(x, d int) {
	depth[x] = d
	for _, i := range G[x] {
		if depth[i] != 0 {
			continue
		}
		sparse[i][0] = x
		dfs(i, d+1)
	}
}

func lca(u, v int) int {
	if depth[u] < depth[v] {
		u, v = v, u
	}
	for i := lgN - 1; i >= 0; i-- {
		if depth[v] <= depth[sparse[u][i]] {
			u = sparse[u][i]
		}
	}
	ret := u
	for i := lgN - 1; i >= 0; i-- {
		if u == v {
			break
		}
		if sparse[u][i] != sparse[v][i] {
			u = sparse[u][i]
			v = sparse[v][i]
		}
		ret = sparse[u][i]
	}
	return ret
}

func dist(u, v int) int {
	l := lca(u, v)
	return depth[u] - depth[l] + depth[v] - depth[l]
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	fmt.Fscan(br, &R, &C, &T)
	N = R * C
	lgN = int(math.Ceil(math.Log2(float64(N)))) + 1
	depth = make([]int, N+1)
	sparse = make([][]int, N+1)
	ret := make([]int64, T+2)
	G = make([][]int, N+1)
	for i := 0; i <= N; i++ {
		sparse[i] = make([]int, lgN)
		G[i] = make([]int, 0)
		for j := 0; j < lgN; j++ {
			sparse[i][j] = N
		}
	}
	for r := 0; r < R-1; r++ {
		for c := 0; c < C; c++ {
			fmt.Fscan(br, &x)
			if x == 0 {
				cur := r*C + c
				nxt := cur + C
				G[cur] = append(G[cur], nxt)
				G[nxt] = append(G[nxt], cur)
			}
		}
	}
	for r := 0; r < R; r++ {
		for c := 0; c < C-1; c++ {
			fmt.Fscan(br, &x)
			if x == 0 {
				cur := r*C + c
				nxt := cur + 1
				G[cur] = append(G[cur], nxt)
				G[nxt] = append(G[nxt], cur)
			}
		}
	}
	depth[N] = -1
	dfs(0, 1)
	for i := 1; i < lgN; i++ {
		for j := 1; j <= N; j++ {
			sparse[j][i] = sparse[sparse[j][i-1]][i-1]
		}
	}
	fmt.Fscan(br, &K)
	for i := 0; i < K; i++ {
		fmt.Fscan(br, &S, &E, &a, &b, &c, &d, &V)
		A := a*C + b
		B := c*C + d
		ln := int64(dist(A, B) + 1)
		ret[S] += V * ln
		ret[E+1] -= V * ln
	}
	for i := 1; i <= T; i++ {
		ret[i] += ret[i-1]
		fmt.Fprintln(bw, ret[i])
	}
}

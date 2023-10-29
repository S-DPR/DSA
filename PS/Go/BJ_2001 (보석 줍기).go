package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
2001번 보석 줍기

N개의 섬이 있고, 이중 K개의 섬에는 보석이 있다.
그런데 섬을 잇는 다리마다 버틸 수 있는 최대 보석 개수가 정해져있다.
1번에서 출발해서 1번으로 돌아올 때, 몇개의 보석을 가질 수 있을까?

진짜 보자마자 풀이법 바로 떠올랐는데 언어억까당했어 아
그냥 비트마스킹 들고 bfs 굴리면 됩니다. 진짜 그게 끝이에요
근데 언어억까로 3시간을 박았다고???
*/
type info struct {
	d, w int
}

type qinfo struct {
	x, b, c int
}

func max(i, j int) int {
	if i < j {
		return j
	}
	return i
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N, M, K int
	fmt.Fscan(br, &N, &M, &K)
	G := make([][]info, N+1)
	V := make([][]bool, N+1)
	H := make([]int, N+1)
	for i := 0; i <= N; i++ {
		G[i] = make([]info, 0)
		V[i] = make([]bool, 1<<K)
	}
	for i := 0; i < K; i++ {
		var x int
		fmt.Fscan(br, &x)
		H[x] = 1 << i
	}
	for i := 0; i < M; i++ {
		var u, v, w int
		fmt.Fscan(br, &u, &v, &w)
		G[u] = append(G[u], info{v, w})
		G[v] = append(G[v], info{u, w})
	}
	Q := make([]qinfo, 0)
	Q = append(Q, qinfo{1, 0, 0})
	if H[1] != 0 {
		Q = append(Q, qinfo{1, H[1], 1})
	}
	V[1][0] = true
	V[1][H[1]] = true
	ret := 0
	for len(Q) > 0 {
		cur := Q[0]
		Q = Q[1:]
		x, b, c := cur.x, cur.b, cur.c
		if x == 1 {
			ret = max(ret, c)
		}
		for _, i := range G[x] {
			nxtN, nxtW := i.d, i.w
			if c > nxtW {
				continue
			}
			if !V[nxtN][b] {
				Q = append(Q, qinfo{nxtN, b, c})
				V[nxtN][b] = true
			}
			nxtB := b
			if H[nxtN] != 0 && b&H[nxtN] == 0 {
				nxtB |= H[nxtN]
			}
			if !V[nxtN][nxtB] {
				Q = append(Q, qinfo{nxtN, nxtB, c + 1})
				V[nxtN][nxtB] = true
			}
		}
	}
	fmt.Println(ret)
}

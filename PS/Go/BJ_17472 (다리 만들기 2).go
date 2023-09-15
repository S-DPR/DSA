package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
)

/*
17472 다리 만들기 2

바다에 섬이 몇 개 있다.
섬을 다리로 이으려한다. 단, 가로로 혹은 세로로 맞닿아야만 이을 수 있다.
다리를 최대한 적은 칸에 지어 모든 섬을 이으려한다.
적어도 몇 칸을 다리칸으로 써야할까? 단, 다리의 길이는 적어도 2여야한다.
만약 조건을 지키며 모든 섬을 이을 수 없다면 -1을 출력한다.

그냥 뒤적뒤적 좀 하다가 만난 문제.
되게 귀찮은 구현 싹 다 해야합니다.
1. 각 섬을 서로 다른 숫자로 칠하기
2. 완전탐색으로 각 섬의 가능한 모든 거리 구하기
3. MST구하기

일단 각각만 보면 정말 쉬운 문제들이라 그냥 하면 되지만..
모든게 한 문제에 있다면 말은 달라지죠. 진짜 귀찮네요.
하지만.. 아이디어만 떠올린다면.. 그냥 슥슥 해서 풀 수 있는 문제들..
*/
var (
	R, C int
	V    [][]bool
	M    [][]int
	U    []int
	dx   = []int{1, -1, 0, 0}
	dy   = []int{0, 0, 1, -1}
)

func union(x, y int) {
	U[find(x)] = U[find(y)]
}

func find(x int) int {
	if U[x] != x {
		U[x] = find(U[x])
	}
	return U[x]
}

func dfs(x, y, k int) {
	V[y][x] = true
	M[y][x] = k
	for i := 0; i < 4; i++ {
		nx, ny := x+dx[i], y+dy[i]
		if !(0 <= nx && nx < C) {
			continue
		}
		if !(0 <= ny && ny < R) {
			continue
		}
		if V[ny][nx] || M[ny][nx] == 0 {
			continue
		}
		dfs(nx, ny, k)
	}
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	fmt.Fscan(br, &R, &C)
	M = make([][]int, R)
	V = make([][]bool, R)
	for i := 0; i < R; i++ {
		M[i] = make([]int, C)
		V[i] = make([]bool, C)
		for j := 0; j < C; j++ {
			fmt.Fscan(br, &M[i][j])
		}
	}
	islandNum := 1
	for i := 0; i < R; i++ {
		for j := 0; j < C; j++ {
			if V[i][j] || M[i][j] == 0 {
				continue
			}
			dfs(j, i, islandNum)
			islandNum++
		}
	}
	G := make([][]int, 0)
	curIsland := 0
	curDist := 0
	for i := 0; i < R; i++ {
		curIsland = 0
		curDist = 0
		for j := 0; j < C; j++ {
			if M[i][j] == 0 {
				curDist++
			} else {
				if curIsland != 0 && curDist > 1 {
					G = append(G, []int{curDist, curIsland, M[i][j]})
				}
				curIsland = M[i][j]
				curDist = 0
			}
		}
	}
	for i := 0; i < C; i++ {
		curIsland = 0
		curDist = 0
		for j := 0; j < R; j++ {
			if M[j][i] == 0 {
				curDist++
			} else {
				if curIsland != 0 && curDist > 1 {
					G = append(G, []int{curDist, curIsland, M[j][i]})
				}
				curIsland = M[j][i]
				curDist = 0
			}
		}
	}
	sort.Slice(G, func(i, j int) bool {
		return G[i][0] < G[j][0]
	})
	U = make([]int, islandNum)
	for i := 0; i < islandNum; i++ {
		U[i] = i
	}
	ret, unionCnt := 0, 0
	for i := 0; i < len(G); i++ {
		w, u, v := G[i][0], G[i][1], G[i][2]
		if find(u) == find(v) {
			continue
		}
		unionCnt++
		ret += w
		union(u, v)
	}
	if unionCnt != islandNum-2 {
		fmt.Fprintln(bw, -1)
	} else {
		fmt.Fprintln(bw, ret)
	}
}

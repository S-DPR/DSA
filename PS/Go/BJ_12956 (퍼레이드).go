package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
12956번 퍼레이드

정점이 N개, 간선이 M개 있다.
i번째 간선을 지운다면, 어떤 (i, j)쌍이 최단경로로 이동하지 못할 수 있다.
각 간선을 지울 때 (i, j)쌍은 몇 개 나올까?

다익인가? 했다가 그냥 뭐 N이 100이기도하고 간선 적기도하고.. 생각하면서 플로이드로 선회.
100^3*2000, 대충 20억번 연산인데 3초만에 되네요.

다익풀이는 한 번 봤는데, 전형적인 플레에 나오는 그거더라고요.
플로이드 막혔으면 한 번쯤 시도해봤을것같아요.
N M 제한 컸으면 시간 더 먹을뻔..
*/
var (
	u, v, w int
	N, M    int
	G       [][]int
	E       [][3]int
)

func getDist() [][]int {
	dist := make([][]int, N)
	for i := 0; i < N; i++ {
		dist[i] = make([]int, N)
		for j := 0; j < N; j++ {
			dist[i][j] = G[i][j]
		}
	}
	for k := 0; k < N; k++ {
		for i := 0; i < N; i++ {
			for j := 0; j < N; j++ {
				dist[i][j] = min(dist[i][j], dist[i][k]+dist[k][j])
			}
		}
	}
	return dist
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	fmt.Fscan(br, &N, &M)
	G = make([][]int, N)
	E = make([][3]int, M)
	for i := 0; i < N; i++ {
		G[i] = make([]int, N)
		for j := 0; j < N; j++ {
			G[i][j] = 1 << 28
		}
		G[i][i] = 0
	}
	for i := 0; i < M; i++ {
		fmt.Fscan(br, &u, &v, &w)
		E[i] = [3]int{u, v, w}
		G[u][v] = w
		G[v][u] = w
	}
	origin := getDist()
	for i := 0; i < M; i++ {
		u, v, w := E[i][0], E[i][1], E[i][2]
		G[u][v] = 1 << 28
		G[v][u] = 1 << 28
		dist := getDist()
		sum := 0
		for x := 0; x < N; x++ {
			for y := 0; y < N; y++ {
				if origin[x][y] != dist[x][y] {
					sum++
				}
			}
		}
		G[u][v] = w
		G[v][u] = w
		fmt.Fprintf(bw, "%d ", sum/2)
	}
}

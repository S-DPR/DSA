package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
27453번 귀엽기만 한 게 아닌 한별 양

맵이 주어진다. S에서 E로 가려는데, 아래 조건에 맞추어 가려고 한다.
최단거리를 구해보자.
 - 전전번에 밟은 칸의 수와 전칸에 밟은 수, 현재 칸에 밟은 수를 합쳐 K를 넘으면 안된다.
 - 직전에 온 방향을 되돌아 갈 수 없다.

이번주는 유난히 BFS 많이 푸네..
이왕 많이 푼거 그래프이론 플5나 찍어봅시다.

전체적으로 그냥 귀찮은 BFS입니다.
어렵진 않지만 귀찮아요. 많이.
그냥.. 진짜 위에꺼 그대로 구현해주면 됩니다. 처음엔 막막했는데 하다보니 또 쭉 풀리네..
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	min := func(i, j int) int {
		if i > j {
			return j
		}
		return i
	}
	INF := 1 << 30
	defer bw.Flush()

	var N, M, K int
	fmt.Fscan(br, &N, &M, &K)
	Map := make([]string, N)
	S, E := make([]int, 0), make([]int, 0)
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &Map[i])
		for j := 0; j < M; j++ {
			switch Map[i][j] {
			case 'S':
				S = []int{j, i}
				Map[i] = Map[i][:j] + "0" + Map[i][j+1:]
				break
			case 'H':
				E = []int{j, i}
				Map[i] = Map[i][:j] + "0" + Map[i][j+1:]
				break
			}
		}
	}
	V := make([][][]int, N)
	unF := make([][][]int, N)
	for i := 0; i < N; i++ {
		V[i] = make([][]int, M)
		unF[i] = make([][]int, M)
		for j := 0; j < M; j++ {
			V[i][j] = []int{INF, INF, INF, INF}
			unF[i][j] = []int{INF, INF, INF, INF}
		}
	}
	V[S[1]][S[0]] = []int{0, 0, 0, 0}
	unF[S[1]][S[0]] = []int{0, 0, 0, 0}
	dx := []int{1, -1, 0, 0}
	dy := []int{0, 0, 1, -1}
	Q := make([][]int, 0)
	Q = append(Q, []int{S[0], S[1], 0, 0, 0, 0, -1})
	for len(Q) > 0 {
		first := Q[0]
		Q = Q[1:]
		x, y, f, s := first[0], first[1], first[2], first[3]
		t, curD, move := first[4], first[5], first[6]^1
		curUn := f + s + t
		if x == E[0] && y == E[1] {
			continue
		}
		for i := 0; i < 4; i++ {
			if move == i {
				continue
			}
			nx, ny := x+dx[i], y+dy[i]
			if !(0 <= nx && nx < M) {
				continue
			}
			if !(0 <= ny && ny < N) {
				continue
			}
			if Map[ny][nx] == 'X' {
				continue
			}
			next := int(Map[ny][nx]) & 0xF
			if V[ny][nx][i^1] <= curD+1 {
				continue
			}
			if unF[ny][nx][i^1] <= curUn+next-f {
				continue
			}
			if K < curUn+next-f {
				continue
			}
			V[ny][nx][i^1] = curD + 1
			unF[ny][nx][i^1] = curUn + next - f
			Q = append(Q, []int{nx, ny, s, t, next, curD + 1, i})
		}
	}
	res := INF
	for _, i := range V[E[1]][E[0]] {
		res = min(res, i)
	}
	if res == INF {
		fmt.Fprintln(bw, -1)
	} else {
		fmt.Fprintln(bw, res)
	}
}

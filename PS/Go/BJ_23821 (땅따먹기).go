package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
23821번 땅따먹기

O, X, A로 주어진 맵이 세로 N, 가로 M 크기로 주어진다.
A는 당신이 시작하는 위치이다.
당신은 매 턴마다 아래 행위를 반복할 수 있다.
1. A를 가로 혹은 세로로 한칸 늘린다.
2. A를 가로 혹은 세로로 한 칸 움직인다.
'늘린다'라는 뜻은 1x1짜리 크기가 1x2 혹은 2x1이 된다는 뜻이며, 3x4의경우 4x4 혹은 3x5의 크기가 된다.
1번 행위 이후 2번 행위 할 수 없다면 1번행위도 같이 취소된다.
위 행위를 하는동안 맵 밖으로 나가거나 내부에 X가 들어가면 안된다.
이 때, 닿을 수 있는 부분은 Y로, 닿을 수 없는 부분은 X로 맵을 다시 표시해보자.
(1 <= N, M <= 50)

스위핑+누적합+BFS라니 세상에
어떻게 저 셋이 같이있냐
스위핑이랑 누적합 같이있는건 진짜 충격이네..

BFS : 1, 2번 행위를 시뮬레이션할 때 필요합니다.
누적합 : 내부에 X가 포함되는지 O(1)로 처리하기 위해 필요합니다.
스위핑 : BFS시 과정을 O(1)로, BFS의 결과를 O(N^2)으로 처리하기 위해 필요합니다.
스위핑은 좀 현명하게 하면 필요없을거같기도하고..

어쨌든 저거 그대로 하면 되긴합니다. 아슬아슬하게요..
저같은경우 Go라 추가 2초 받고 2.5초인데 1.7초만에 통과했네요.
그나저나 2차원 스위핑은 솔직히 듣기만하고 처음해본거라 좀 신기합니다.
*/
func calc(P [][]int, x1, y1, x2, y2 int) int {
	x1, y1, x2, y2 = x1+1, y1+1, x2+1, y2+1
	return P[y2][x2] - P[y1-1][x2] - P[y2][x1-1] + P[y1-1][x1-1]
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	max := func(i, j int) int {
		if i > j {
			return i
		}
		return j
	}
	min := func(i, j int) int {
		if i < j {
			return i
		}
		return j
	}

	var N, K int
	fmt.Fscan(br, &N, &K)
	M := make([][]string, N)
	A := make([][]int, 0)
	V := make([][]map[int]bool, N)
	P := make([][]int, N+1)
	P[0] = make([]int, K+1)
	sweep := make([][]int, N+1)
	for i := 0; i <= N; i++ {
		sweep[i] = make([]int, K+1)
	}
	for i := 0; i < N; i++ {
		M[i] = make([]string, K)
		V[i] = make([]map[int]bool, K)
		P[i+1] = make([]int, K+1)
		var tmp string
		fmt.Fscan(br, &tmp)
		for j := 0; j < K; j++ {
			V[i][j] = make(map[int]bool)
			if rune(tmp[j]) == 'X' {
				P[i+1][j+1] = 1
			}
			if rune(tmp[j]) == 'A' {
				A = append(A, []int{j, i, j, i})
				sweep[i][j]++
				sweep[i][j+1]--
				sweep[i+1][j]--
				sweep[i+1][j+1]++
			}
		}
	}

	for i := 1; i <= N; i++ {
		for j := 1; j <= K; j++ {
			P[i][j] = P[i][j] + P[i-1][j] + P[i][j-1] - P[i-1][j-1]
		}
	}

	dx := []int{1, 0, -1, 0}
	dy := []int{0, 1, 0, -1}
	for len(A) > 0 {
		x1, y1, x2, y2 := A[0][0], A[0][1], A[0][2], A[0][3]
		A = A[1:]
		for k := 0; k < 4; k++ {
			nx1, ny1 := min(x1, x1+dx[k]), min(y1, y1+dy[k])
			nx2, ny2 := max(x2, x2+dx[k]), max(y2, y2+dy[k])
			if nx1 < 0 || ny1 < 0 || K <= nx2 || N <= ny2 {
				continue
			}
			if calc(P, nx1, ny1, nx2, ny2) > 0 {
				continue
			}
			for i := 0; i < 4; i++ {
				nnx1, nny1 := nx1+dx[i], ny1+dy[i]
				nnx2, nny2 := nx2+dx[i], ny2+dy[i]
				if nnx1 < 0 || nny1 < 0 || K <= nnx2 || N <= nny2 {
					continue
				}
				if V[nny1][nnx1][nnx2*100+nny2] {
					continue
				}
				if calc(P, nnx1, nny1, nnx2, nny2) > 0 {
					continue
				}
				A = append(A, []int{nnx1, nny1, nnx2, nny2})
				sweep[ny1][nx1]++
				sweep[ny2+1][nx1]--
				sweep[ny1][nx2+1]--
				sweep[ny2+1][nx2+1]++
				sweep[nny1][nnx1]++
				sweep[nny2+1][nnx1]--
				sweep[nny1][nnx2+1]--
				sweep[nny2+1][nnx2+1]++
				V[ny1][nx1][nx2*100+ny2] = true
				V[nny1][nnx1][nnx2*100+nny2] = true
			}
		}
	}
	for i := 0; i <= N; i++ {
		for j := 1; j <= K; j++ {
			sweep[i][j] += sweep[i][j-1]
		}
	}
	for i := 1; i <= N; i++ {
		for j := 0; j <= K; j++ {
			sweep[i][j] += sweep[i-1][j]
		}
	}
	for i := 0; i < N; i++ {
		for j := 0; j < K; j++ {
			if sweep[i][j] > 0 {
				fmt.Fprint(bw, "Y")
			} else {
				fmt.Fprint(bw, "N")
			}
		}
		fmt.Fprintln(bw)
	}

	bw.Flush()
}

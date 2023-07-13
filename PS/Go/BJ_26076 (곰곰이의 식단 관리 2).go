package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
26076번 곰곰이의 식단 관리 2

맵이 주어진다. 여기에 장애물을 적어도 몇 개를 추가해야 (1, 1)에서 (M, N)으로 가는걸 막을 수 있을까?

아이디어는 짜냈는데 구현을 못해서 왕창 틀린문제
후.. 아.. 슬프다..

(1, 1)에서 (N, M)으로 못가려면 방법이 네 개가 있습니다.
X축으로 벽을 쌓거나, Y축으로 벽을 쌓거나.
아니면 왼쪽과 위쪽 벽을 이용해 사각형을 만들거나, 아래쪽과 오른쪽 벽을 이용해 사각혀을 만들거나.
이걸 그림으로 그려보면, 아래와 같은 사실을 알 수 있습니다.
[왼쪽, 아래쪽 벽]과 [우쪽, 오른쪽 벽]이 한 집합이라는 것.
그리고 이 두 집합을 어디서든 하나라도 이어주면 두 점이 서로 다른 공간으로 나눠진다는 점.

네, 그냥 이걸 구현하면 됩니다.
전 구현 못해서 엄청틀렸어요. 너무슬프다.
*/
type info struct {
	l, r bool
}

var (
	ret   int
	X, Y  int
	M     [][]int
	dx    = []int{1, -1, 0, 0, 1, 1, -1, -1}
	dy    = []int{0, 0, 1, -1, 1, -1, 1, -1}
	union = make(map[int]info)
)

func min(i, j int) int {
	if i < j {
		return i
	}
	return j
}

func max(i, j int) int {
	if i < j {
		return j
	}
	return i
}

func bfs(x, y int) {
	curS := len(union) + 2
	curI := info{x == 0 || y == Y-1, x == X-1 || y == 0}
	Q := [][]int{{x, y}}
	for len(Q) > 0 {
		cur := Q[0]
		Q = Q[1:]
		curX, curY := cur[0], cur[1]
		M[curY][curX] = curS
		for i := 0; i < 8; i++ {
			nx, ny := curX+dx[i], curY+dy[i]
			if !(0 <= nx && nx < X) {
				continue
			}
			if !(0 <= ny && ny < Y) {
				continue
			}
			if M[ny][nx] != 1 {
				continue
			}
			curI.l = curI.l || nx == 0 || ny == Y-1
			curI.r = curI.r || nx == X-1 || ny == 0
			nxt := []int{nx, ny}
			Q = append(Q, nxt)
		}
	}
	l := curI.l
	r := curI.r
	if l && r {
		ret = 0
	}
	union[curS] = curI
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	fmt.Fscan(br, &Y, &X)
	M = make([][]int, Y)
	for i := 0; i < Y; i++ {
		M[i] = make([]int, X)
		for j := 0; j < X; j++ {
			fmt.Fscan(br, &M[i][j])
		}
	}
	ret = min(min(Y, 2), X)
	for i := 0; i < Y; i++ {
		for j := 0; j < X; j++ {
			if M[i][j] == 1 {
				bfs(j, i)
			}
		}
	}
	for i := 0; i < Y; i++ {
		for j := 0; j < X; j++ {
			l, r := j == 0 || i == Y-1, j == X-1 || i == 0
			if (i == 0 && j == 0) || (i == Y-1 && j == X-1) || M[i][j] != 0 {
				continue
			}
			for k := 0; k < 8; k++ {
				nx, ny := j+dx[k], i+dy[k]
				if !(0 <= nx && nx < X) {
					continue
				}
				if !(0 <= ny && ny < Y) {
					continue
				}
				if M[ny][nx] == 0 {
					continue
				}
				K := union[M[ny][nx]]
				l = l || K.l
				r = r || K.r
			}
			if l && r {
				ret = min(ret, 1)
			}
		}
	}
	fmt.Fprintln(bw, ret)
}

package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
1525번 퍼즐

3*3짜리 보드가 있다. 여기에는 0부터 8까지 수가 적혀져있다.
0은 빈 공간으로, 이곳으로 상하좌우에 있는 숫자를 옮길 수 있다.
이 때, 123456780순서대로 나열되기 위해 최소 몇 번 옮겨야할까?

백트래킹인줄알았는데 생각 좀 해보니 bfs아니면 안되겠더라 ㅋㅋ

원래 문자열로 바꾸고 귀찮게해야 풀리는문제같은데,
우리 갓-GO는 그딴거 없습니다. 그냥 상남자답게 해시맵 돌파.
GO 그는 신이야!
*/

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	M := [3][3]int{}
	V := make(map[[3][3]int]int)
	for i := 0; i < 3; i++ {
		for j := 0; j < 3; j++ {
			fmt.Fscan(br, &M[i][j])
		}
	}
	Q := make([][3][3]int, 0)
	Q = append(Q, M)
	V[M] = 0
	dr := []int{1, -1, 0, 0}
	dc := []int{0, 0, 1, -1}
	for len(Q) > 0 {
		cur := Q[0]
		Q = Q[1:]
		r, c, t := 0, 0, V[cur]
		end := true
		for i := 0; i < 3; i++ {
			for j := 0; j < 3; j++ {
				if cur[i][j] == 0 {
					r, c = i, j
				} else {
					end = end && cur[i][j] == i*3+j+1
				}
			}
		}
		if end {
			fmt.Println(V[cur])
			return
		}
		for i := 0; i < 4; i++ {
			nr, nc := r+dr[i], c+dc[i]
			if !(0 <= nr && nr < 3) {
				continue
			}
			if !(0 <= nc && nc < 3) {
				continue
			}
			cpy := [3][3]int{}
			for i := range cur {
				copy(cpy[i][:], cur[i][:])
			}
			cpy[r][c], cpy[nr][nc] = cpy[nr][nc], cpy[r][c]
			if V[cpy] != 0 {
				continue
			}
			V[cpy] = t + 1
			Q = append(Q, cpy)
		}
	}
	fmt.Println(-1)
}

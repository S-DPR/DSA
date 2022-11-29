package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
4991번 로봇 청소기

출발점과 목적지가 최소 하나 있는 맵이 주어진다.
출발점에서 시작해 모든 목적지를 최단거리로 방문할 때, 움직여야하는 칸 수를 구하시오.
단, x가 쳐진 곳은 지나갈 수 없다.

bfs문제구나 히히 하고 했다가,
동일거리에 대해 우선순위를 잘못매겨 화려하게 틀리고.
이 부분에 대해 매기는게 불가능하다 판단하여 이 방법은 폐기했습니다.

두번째로는 출발점과 목적지의 위치를 모두 적어둔 뒤,
각각의 거리를 abs(x1-x2)+abs(y1-y2)로 구하고,
그걸 백트래킹으로 처리하기. 당연히 틀렸습니다. x를 고려 안해서..

마지막으로 한게, 진짜 이건 아니길 빌었는데,
각 점에서 bfs 다 돌리고, 거리를 다 얻어낸 뒤
그 거리에 백트래킹돌려서 답 얻기..
.. 음..

아, 백트래킹에 중단조건 한번 잘 설정해주니 256ms에서 68ms로 확줄었습니다.
중단조건은 소중한거같네요..

그래프 골드문제는 생각보다 거기서 거기같습니다. 골드 상위나 골드 하위나..
*/
func bfs(x, y, n, m int, canVisit [][]int, M []string) [][]int {
	dx, dy := [4]int{1, -1, 0, 0}, [4]int{0, 0, 1, -1}
	deque := make([][]int, 0)
	deque = append(deque, []int{x, y})
	for len(deque) > 0 {
		x, y = deque[0][0], deque[0][1]
		deque = deque[1:]
		for i := 0; i < 4; i++ {
			nx, ny := x+dx[i], y+dy[i]
			if !(0 <= nx && nx < n && 0 <= ny && ny < m) {
				continue
			}
			if canVisit[ny][nx] >= 0 || M[ny][nx] == 'x' {
				continue
			}
			canVisit[ny][nx] = canVisit[y][x] + 1
			deque = append(deque, []int{nx, ny})
		}
	}
	return canVisit
}

func backT(x, acc, depth, dirtycnt, curans int, visit []bool, dist [][]int) int {
	if curans <= acc {
		return 1000
	}
	res := 1000
	min := func(i, j int) int {
		if i > j {
			return j
		}
		return i
	}
	if depth == dirtycnt-1 {
		return acc
	}
	for i := 1; i < dirtycnt; i++ {
		if i != x && !visit[i] {
			visit[i] = true
			res = min(res, backT(i, acc+dist[x][i], depth+1, dirtycnt, curans, visit, dist))
			visit[i] = false
		}
	}
	return res
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	min := func(i, j int) int {
		if i > j {
			return j
		}
		return i
	}

	M := make([]string, 20)
	for {
		var n, m int
		fmt.Fscan(br, &n, &m)
		if n == 0 && m == 0 {
			break
		}
		for i := 0; i < m; i++ {
			fmt.Fscan(br, &M[i])
		}

		dirtyXY := make([][]int, 1)
		dirtyXY[0] = []int{-1, -1}
		for y := 0; y < m; y++ {
			for x := 0; x < n; x++ {
				if M[y][x] == 'o' {
					M[y] = M[y][:x] + "." + M[y][x+1:]
					dirtyXY[0] = []int{x, y}
				}
				if M[y][x] == '*' {
					dirtyXY = append(dirtyXY, []int{x, y})
				}
			}
		}

		flg := false
		dirtycnt := len(dirtyXY)
		dist := make([][]int, dirtycnt)
		for i := 0; i < dirtycnt; i++ {
			dist[i] = make([]int, dirtycnt)
			canVisit := make([][]int, m)
			for j := 0; j < m; j++ {
				canVisit[j] = make([]int, n)
				for k := 0; k < n; k++ {
					canVisit[j][k] = -1
				}
			}
			canVisit[dirtyXY[i][1]][dirtyXY[i][0]] = 0
			canVisit = bfs(dirtyXY[i][0], dirtyXY[i][1], n, m, canVisit, M)
			for j := 0; j < dirtycnt; j++ {
				curX, curY := dirtyXY[j][0], dirtyXY[j][1]
				if i == j {
					dist[i][j] = 1000
				} else {
					dist[i][j] = canVisit[curY][curX]
				}
				if canVisit[curY][curX] == -1 {
					flg = true
				}
			}
		}
		if flg {
			fmt.Fprintln(bw, -1)
			continue
		}

		ans := 1000
		visit := make([]bool, dirtycnt+1)
		for i := 1; i < dirtycnt; i++ {
			visit[i] = true
			ans = min(ans, backT(i, dist[0][i], 1, dirtycnt, ans, visit, dist))
			visit[i] = false
		}
		if ans == 1000 {
			fmt.Fprintln(bw, -1)
		} else {
			fmt.Fprintln(bw, ans)
		}
	}
	bw.Flush()
}

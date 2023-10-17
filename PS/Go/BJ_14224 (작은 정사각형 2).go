package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
14224번 작은 정사각형 2

점의 좌표가 N개 주어진다. 적어도 K개의 점을 포함하도록 하는 정사각형의 최소 넓이는 몇일까?

14658번, 1월 19일에 풀었던 문제가 약간 변형된 문제.
그래서 그때 기억을 되살렸습니다.

1. N의크기가 100입니다. 정말작죠.
2. 별에서 상하좌우 직선으로 레이저가 나온다고 합시다.
3. 그중 가장 좌측 상단의 점을 고릅니다.
4. 그 점이 사각형 내부의 최상단이 됩니다.
5. 이제 점을 거기로 잡고 정사각형을 그립니다.
6. 모든 별을 테스트합니다.

처음에는 직사각형인지 알았는데, 정사각형이어서 에이 뭐야 하고 풀었습니다.
매개변수탐색도 이제 좀 익숙해졌나?
*/
type info struct {
	x, y int64
}

var (
	dot []info
)

func min(x, y int64) int64 {
	if x < y {
		return x
	}
	return y
}

func max(i, j int) int {
	if i < j {
		return j
	}
	return i
}

func check(k int64) int {
	ret := 0
	for _, i1 := range dot {
		for _, i2 := range dot {
			curX := min(i1.x, i2.x) - 1
			curY := min(i1.y, i2.y) - 1
			curR := 0
			for _, i := range dot {
				if !(curX < i.x && i.x < curX+k) {
					continue
				}
				if !(curY < i.y && i.y < curY+k) {
					continue
				}
				curR++
			}
			ret = max(ret, curR)
		}
	}
	return ret
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N, K int
	fmt.Fscan(br, &N, &K)
	dot = make([]info, N)
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &dot[i].x, &dot[i].y)
	}
	MAX := int64(1_000_000_002)
	left, right := int64(0), MAX*MAX
	for left < right {
		mid := (left + right) >> 1
		if check(mid) >= K {
			right = mid
		} else {
			left = mid + 1
		}
	}
	fmt.Fprintln(bw, right*right)
}

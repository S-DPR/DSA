package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
5475번 Miners

두 개의 광산이 있다.
각 광산에 음식을 주려한다.
각 광산은 음식을 받을때마다 다음과 같은 규칙으로 석탄을 생산한다.
  - 이전 2번에 이 음식을 두 번 먹었다면 1개 생산한다.
  - 이전 2번에 이 음식을 한 번 먹었다면 2개 생산한다.
  - 이전 2번에 이 음식을 먹지 않았다면 3개 생산한다.

음식을 나눠주는 순서가 주어진다. 음식은 3종류 있다고 할 때, 최대 석탄 생산량은 몇 개일까?

그냥 보고나서 아 대충 그리디는 아니니 dp겠거니 했고.
JS로 두 번에 걸쳐 짜긴했는데 MLE..
Go로 하니까 바로 되네요. 대 황 고
그나저나 5차원dp는 좀 끔찍하네
*/
var (
	N   int
	S   string
	A   []int
	dp  [][4][4][4][4]int
	vis [][4][4][4][4]bool
)

func loop(idx, lprvv, lprv, rprvv, rprv int) int {
	if idx == N {
		return 0
	}
	if vis[idx][lprvv][lprv][rprvv][rprv] {
		return dp[idx][lprvv][lprv][rprvv][rprv]
	}
	vis[idx][lprvv][lprv][rprvv][rprv] = true
	leftScore, rightScore := 1, 1
	if lprvv != A[idx] && lprvv != 0 {
		leftScore++
	}
	if lprv != A[idx] && lprv != 0 && lprvv != lprv {
		leftScore++
	}
	if rprvv != A[idx] && rprvv != 0 {
		rightScore++
	}
	if rprv != A[idx] && rprv != 0 && rprvv != rprv {
		rightScore++
	}
	left := leftScore + loop(idx+1, lprv, A[idx], rprvv, rprv)
	right := rightScore + loop(idx+1, lprvv, lprv, rprv, A[idx])
	dp[idx][lprvv][lprv][rprvv][rprv] = max(left, right)
	return dp[idx][lprvv][lprv][rprvv][rprv]
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	fmt.Fscan(br, &N, &S)
	A = make([]int, N)
	dp = make([][4][4][4][4]int, N)
	vis = make([][4][4][4][4]bool, N)
	for idx, i := range S {
		if i == 'M' {
			A[idx] = 1
		}
		if i == 'B' {
			A[idx] = 2
		}
		if i == 'F' {
			A[idx] = 3
		}
	}
	fmt.Println(loop(0, 0, 0, 0, 0))
}

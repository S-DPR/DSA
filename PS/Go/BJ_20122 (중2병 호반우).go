package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
20122번 중2병 호반우

R*C크기의 맵이 주어진다.
맵은 0, 1, 2, 3으로 이루어져있는데,
1은 현재 점수에 P*a만큼의 값을 더한다.
2는 현재 점수에 P*b만큼의 값을 더한다.
3은 P에 c만큼의 값을 곱한다.
좌측 혹은 위쪽 측면에서 각각 우측/아래쪽으로 빔을 쏘려 한다.
그러면 왼쪽/위쪽에서 오른쪽/아래쪽방향으로 빔이 나가고 결국 위쪽 방법으로 점수를 얻게 된다.
초기 P는 1이고, 초기 점수는 0이다. 최댓값과 최솟값을 구해보자.
단, 한번 기능을 사용한 좌표의 경우 더이상 기능하지 못한다.

처음엔 백트래킹인가? 해서 한번 백트래킹처럼 짰는데,
짜다보니 문제 어떻게 풀지 설계할 때 안보였던 중복경우가 보였습니다.
별건 아니고, R과 C를 적절히 골랐다면 P는 무조건 고정입니다.
이제 그때부터, score을 0으로 두고 [현재 이렇게 R, C를 골랐을 때 얻을 수 있는 최대점수]를 저장합니다.

태그 전혀 안보고 비트dp 설계한거는 처음이었네요.
여러모로 제가 문제를 풀 때 뭘 써야할까에 대해 고민할 때,
어떻게해야할까를 다시 생각하게 해보는 문제였습니다.
*/
var (
	R, C    int
	a, b, c int64
	M       [][]int64
	dp      [][][2]int64
	V       [][]bool
)

func max(i, j int64) int64 {
	if i > j {
		return i
	}
	return j
}

func min(i, j int64) int64 {
	if i > j {
		return j
	}
	return i
}

func loop(rb, cb int, prod int64) [2]int64 {
	if V[rb][cb] {
		return dp[rb][cb]
	}
	V[rb][cb] = true
	for i := 0; i < R; i++ {
		if rb&(1<<i) == 0 {
			newScore := int64(0)
			newProd := prod
			for j := 0; j < C; j++ {
				if cb&(1<<j) != 0 {
					continue
				}
				switch M[i][j] {
				case 1:
					newScore += newProd * a
				case 2:
					newScore += newProd * b
				case 3:
					newProd *= c
				}
			}
			local := loop(rb|(1<<i), cb, newProd)
			dp[rb][cb][0] = max(dp[rb][cb][0], local[0]+newScore)
			dp[rb][cb][1] = min(dp[rb][cb][1], local[1]+newScore)
		}
	}
	for i := 0; i < C; i++ {
		if cb&(1<<i) == 0 {
			newScore := int64(0)
			newProd := prod
			for j := 0; j < R; j++ {
				if rb&(1<<j) != 0 {
					continue
				}
				switch M[j][i] {
				case 1:
					newScore += newProd * a
				case 2:
					newScore += newProd * b
				case 3:
					newProd *= c
				}
			}
			local := loop(rb, cb|(1<<i), newProd)
			dp[rb][cb][0] = max(dp[rb][cb][0], local[0]+newScore)
			dp[rb][cb][1] = min(dp[rb][cb][1], local[1]+newScore)
		}
	}
	return dp[rb][cb]
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	fmt.Fscan(br, &R, &C, &a, &b, &c)
	M = make([][]int64, R)
	for i := 0; i < R; i++ {
		M[i] = make([]int64, C)
		for j := 0; j < C; j++ {
			fmt.Fscan(br, &M[i][j])
		}
	}
	dp = make([][][2]int64, 1<<R)
	V = make([][]bool, 1<<R)
	for i := 0; i < 1<<R; i++ {
		dp[i] = make([][2]int64, 1<<C)
		V[i] = make([]bool, 1<<C)
	}
	ret := loop(0, 0, 1)
	fmt.Fprintf(bw, "%d %d", ret[1], ret[0])
}

package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
15924번 욱제는 사과팬이야!!

B, E, S, X로 주어진 맵이 주어진다. X는 오직 제일 우측 하단에만 주어진다.
(i, j)를 기준으로 B일경우 (i+1, j), (i, j+1)중 하나로 갈 수 있고,
E는 (i+1, j)로, S는 (i, j+1)로 갈 수 있다.
당신은 좌표의 어디서든 출발을 선언할 수 있다.
X로 가는 경우의수를 1_000_000_009로 나누어 출력해보자.

우와 DP!
또 너야 DP?
죽어 DP!

이상하게 문제를 풀었다하면 골드5나 4네요.
골드 3은 아직 제 숙련도가 부족한가봅니다..

이건 뭐.. 그냥 0, 0부터 문제 조건대로 쭉 해주면 풀립니다.
DP랑 그래프중 헷갈렸는데 한쪽으로만 이동할 수 있는거 보고 그냥 DP겠거니 했습니다.
솔직히 매 칸마다 1 더해줘야할줄은 몰랐지만..
*/

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	var n, m int
	fmt.Fscan(br, &n, &m)

	M := make([]string, n)
	for i := 0; i < n; i++ {
		var tmp string
		fmt.Fscan(br, &tmp)
		M[i] = tmp
	}

	dp := make([][]int, n)
	for i := 0; i < n; i++ {
		dp[i] = make([]int, m)
	}

	for y := 0; y < n; y++ {
		for x := 0; x < m; x++ {
			dp[y][x] %= 1_000_000_009
			dp[y][x]++
			switch M[y][x] {
			case 'B':
				if x+1 >= 0 {
					dp[y][x+1] += dp[y][x]
				}
				if y+1 >= 0 {
					dp[y+1][x] += dp[y][x]
				}
			case 'S':
				if y+1 >= 0 {
					dp[y+1][x] += dp[y][x]
				}
			case 'E':
				if x+1 >= 0 {
					dp[y][x+1] += dp[y][x]
				}
			}
		}
	}
	fmt.Fprintln(bw, dp[n-1][m-1])

	bw.Flush()
}

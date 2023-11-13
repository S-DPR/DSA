package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
20667번 크롬

CPU를 c만큼, 메모리를 m만큼 먹는 중요도가 p인 크롬 페이지가 N개 있다.
메모리를 K만큼, CPU를 M만큼 확보하려한다. 중요도를 최소로 해서 확보해보자.

생각보다 깔끔하게 풀린 dp 배낭문제
500*100000은 너무 큰거같아서 그냥 map으로 했는데,
의외로 저 배열로 해도 되나봅니다, 아마도..?
어쨌든 간단한 배낭이었습니다.
*/
var (
	dp  [][]int
	vis [][]bool
)

func max(i, j int) int {
	if i < j {
		return j
	}
	return i
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N, M, K int
	fmt.Fscan(br, &N, &M, &K)
	dp := make([]map[int]int, 5*N+1)
	for i := 0; i <= 5*N; i++ {
		dp[i] = make(map[int]int)
	}
	dp[0][0] = 0 // dp[priority][cpu] = memory
	for i := 0; i < N; i++ {
		var c, m, p int
		fmt.Fscan(br, &c, &m, &p)
		for j := 5 * N; j >= 0; j-- {
			for k, v := range dp[j] {
				dp[j+p][k+c] = max(dp[j+p][k+c], v+m)
			}
		}
	}
	ret := 1 << 30
	for i := 5 * N; i >= 0; i-- {
		for k, v := range dp[i] {
			if k >= M && v >= K {
				ret = i
			}
		}
	}
	if ret == 1<<30 {
		fmt.Println(-1)
	} else {
		fmt.Println(ret)
	}
}

package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
17161번 등수 찾기

어떤 수 N개의 대소관계가 M회 주어진다.
수 X에 대해 이게 가장 크다면 최대 몇 번째로 큰 수인지, 가장 작다면 최대 몇 번째로 작은 수인지 구하시오.
모든 수는 어떤 수가 치환된 값이라 1이 2보다 클 수 있고, 3이 1보다 작을 수 있다.

아이디어를 잘 내자! 하는 문제입니다.
확실히 DP같은거보단 나은거같기도하고..

방법은 간단합니다. 그래프를 정방향과 역방향, 두개로 만듭시다.
이후 dfs로 자기 아래에 몇개가 있나 (lower -> higher방향), 자기 위에 몇 개가 있나 (higher -> lower 방향)를 세봅시다.
자기 아래에 몇 개가 있나를 센 뒤 1에다가 더하면 자신의 최고등수가 되고,
자기 위에 몇 개가 있나를 센 뒤 n에다가 빼면 자기의 최저 등수가 됩니다.
*/
func dfs(G [][]int, visit []bool, x int) int {
	if visit[x] {
		return 0
	}
	visit[x] = true
	res := 1
	for _, i := range G[x] {
		res += dfs(G, visit, i)
	}
	return res
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	var n, m, x int
	fmt.Fscan(br, &n, &m, &x)
	Glower := make([][]int, n+1)
	Ghigher := make([][]int, n+1)
	for i := 1; i <= n; i++ {
		Glower[i] = make([]int, 0)
		Ghigher[i] = make([]int, 0)
	}
	for i := 0; i < m; i++ {
		var higher, lower int
		fmt.Fscan(br, &higher, &lower)
		Glower[lower] = append(Glower[lower], higher)
		Ghigher[higher] = append(Ghigher[higher], lower)
	}

	highest, lowest := 1, n
	visit := make([]bool, n+1)
	for _, i := range Ghigher[x] {
		lowest -= dfs(Ghigher, visit, i)
	}
	visit[x] = false
	for _, i := range Glower[x] {
		highest += dfs(Glower, visit, i)
	}
	fmt.Fprintf(bw, "%d %d", highest, lowest)

	bw.Flush()
}

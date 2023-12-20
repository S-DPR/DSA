package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
13024번 서브 트리의 크기

트리가 주어진다. 트리에 있는 모든 서브트리 크기의 합을 구해보자.
너무 커질 수 있으니, 1_000_000_007로 나눈 나머지를 구해보자.

아..
아이디어는 거의 확실한데.. 하면서 눈물을 흘리며 3일을 태워 겨우 맞췄네..
더 슬픈건 그냥 dp[x]를 반환 안한게 문제였어..

그냥 각 정점이 몇 개의 서브트리에 포함될까?를 고민해야하는데,
dp[i] = i를 루트로 할 때 i를 포함하는 서브트리의 개수.
그리고 트리는 자신과 연결된 모든 dp[i]를 곱하면 됩니다.
문제는 단 한 개, 부모의 경우가 까다로워 진다는 점.
저는 이거는 모듈러역원 박아넣어서 풀었습니다. 마침 제수가 소수라..

후.. 겨우겨우 풀었네..
*/
var (
	G   [][]int
	V   []bool
	dp  []int64
	ret = int64(0)
	MOD = int64(1_000_000_007)
)

func pow(x, p int64) int64 {
	ret := int64(1)
	for p > 0 {
		if p&1 == 1 {
			ret = (ret * x) % MOD
		}
		x = (x * x) % MOD
		p >>= 1
	}
	return ret
}

func dfs(x int) int64 {
	V[x] = true
	selectC := int64(1)
	child := int64(0)
	for _, i := range G[x] {
		if V[i] {
			continue
		}
		selectC = (selectC * (dfs(i) + 1)) % MOD
		child++
	}
	dp[x] = selectC
	return dp[x]
}

func dfs2(x int, parent int64) {
	V[x] = true
	newParent := parent + 1
	for _, i := range G[x] {
		if V[i] {
			continue
		}
		newParent = (newParent * (dp[i] + 1)) % MOD
	}
	for _, i := range G[x] {
		if V[i] {
			continue
		}
		dfs2(i, (newParent*pow(dp[i]+1, MOD-2))%MOD)
	}
	ret += (parent + 1) * dp[x]
	ret %= MOD
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N int
	fmt.Fscan(br, &N)
	V = make([]bool, N)
	dp = make([]int64, N)
	G = make([][]int, N)
	for i := 0; i < N; i++ {
		G[i] = make([]int, 0)
	}
	for i := 1; i < N; i++ {
		var u, v int
		fmt.Fscan(br, &u, &v)
		G[u] = append(G[u], v)
		G[v] = append(G[v], u)
	}
	dfs(0)
	V = make([]bool, N)
	dfs2(0, 0)
	fmt.Fprintln(bw, ret)
}

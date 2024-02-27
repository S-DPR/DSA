package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
14570번 나무 위의 구슬

트리에 구슬을 떨어뜨렸을 때, (왼쪽 총 구슬 개수) <= (오른쪽 총 구슬 개수)면 왼쪽으로 흐른다.
이진트리에 K개의 구슬을 떨어뜨리려 한다. K번째 구슬은 어디에 떨어질까?

아..
조금만 더 생각했더라면..
답을 안봐도 되지 않았을까..

처음에 트리dp로 생각했다가 망한 문제.
태그 까고 트리dp 왜아니야 하면서 허둥대다가 답보고 아..
관찰 진짜 다 했는데 트리dp라고 생각해버려서 망했구나..
앞으로 트리문제 보면 트리dp 바로 의심하진 말아야겠습니다.

K가 짝수면 오른쪽으로, 홀수면 왼쪽으로.
이 때 K값은, 짝수면 K/2개가 다 왼쪽으로 버려져서 K/2로.
홀수면 K/2-1개가 오른쪽으로 버려져서 (K+1)/2로.
왜 K+1을 하는가? 조건이 <=니까. <였으면 오른쪽에 +1해줬겠죠.
참.. 아.. 아쉽다..
근데 짰던코드 다시 보면 안아까운거같기도하고..
*/
var (
	dp []int64
	G  [][]int
)

func dfs(x int, k int64) int {
	l, r := G[x][0], G[x][1]
	if l == 0 && r == 0 {
		return x
	}
	if l != 0 && r != 0 {
		if k&1 == 0 {
			return dfs(r, k/2)
		}
		return dfs(l, (k+1)/2)
	}
	if l == 0 {
		return dfs(r, k)
	}
	return dfs(l, k)
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	var N int
	fmt.Fscan(br, &N)
	dp = make([]int64, N+1)
	G = make([][]int, N+1)
	G[0] = []int{0, 0}
	for i := 1; i <= N; i++ {
		var l, r int
		fmt.Fscan(br, &l, &r)
		G[i] = []int{max(0, l), max(0, r)}
	}
	var k int64
	fmt.Fscan(br, &k)
	fmt.Println(dfs(1, k))

	bw.Flush()
}

package main

import (
	"bufio"
	"fmt"
	"os"
)

type Edge struct {
	dst    int
	weight int32
}

/*
3405번 파산하는 왕국

그래프가 주어진다. 서로 다른 i와 j에 대해 G[i][j] = -G[j][i]를 만족한다.
각 그래프는 자신과 이어진 모든 노드의 가중치의 합을 점수로 한다.
점수가 양수인 노드를 하나씩 제거할 수 있다.
i번 노드를 제거했다면 j번 노드에게 G[i][j]의 합을 주고 자신은 사라지게 된다.
노드를 적절히 제거하여 노드를 하나만 남기려고 한다.
마지막에 남을 수 있는 노드를 오름차순으로 모두 출력해보자.
그런게 없다면 0을 출력하자.

입력데이터가 이상한 문제
아니 어떻게 int64로하면 터지고 int32로해야 맞냐?
진짜 이해가 안되네..

나름 문제 자체는 재밌다고 생각해서 오래전부터 생각하고있던 문제였습니다.
비트dp를 어제 안 것 뿐이지.. 알고나니까 금방 풀리네요.
이거로 최적화나 공부해야겠어요.

그나저나, Go는 오버플로 나면 바로 프로그램을 죽여버립니다.
그래서 당연히 S를 int64로했죠 입력데이터 꼬라지가 10의 9승까지 받아먹고있는데..
그런데 int32로 안하면 틀리는 개 억까문제인거에요 아

에혀.. 어쨌든 비트dp 열심히 굴리면 되는 문제였습니다.
*/
func loop(G [][]Edge, S []int32, dp [][]int32, V int32, cur int32) int32 {
	N := len(G)
	if dp[cur][V] != -1 {
		return dp[cur][V]
	}
	stable, ret := 0, int32(0)
	for idx := range S {
		if V&(1<<idx) != 0 {
			stable += 1
			ret |= 1 << idx
		}
	}
	if stable == 1 {
		dp[cur][V] = ret
		return ret
	}
	dp[cur][V] = 0
	for i := 0; i < N; i++ {
		if V&(1<<i) != 0 && S[i] > 0 {
			live := int32(^(1 << i) & 0x7FFFFFFF)
			for _, j := range G[i] {
				S[j.dst] += j.weight
			}
			dp[cur][V] |= loop(G, S, dp, V&live, int32(i))
			for _, j := range G[i] {
				S[j.dst] -= j.weight
			}
		}
	}
	return dp[cur][V]
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var T int
	fmt.Fscan(br, &T)
	for tc := 0; tc < T; tc++ {
		var N int
		fmt.Fscan(br, &N)
		G := make([][]Edge, N)
		S := make([]int32, N)
		for i := 0; i < N; i++ {
			G[i] = make([]Edge, 0)
			for j := 0; j < N; j++ {
				var w int32
				fmt.Fscan(br, &w)
				if w != 0 {
					G[i] = append(G[i], Edge{j, w})
				}
				S[i] += w
			}
		}
		dp := make([][]int32, N)
		for i := 0; i < N; i++ {
			dp[i] = make([]int32, 1<<N)
			for j := 0; j < 1<<N; j++ {
				dp[i][j] = -1
			}
		}
		ret := loop(G, S, dp, (1<<N)-1, 0)
		flg := true
		for i := 0; i < N; i++ {
			if ret&(1<<i) != 0 {
				fmt.Fprintf(bw, "%d ", i+1)
				flg = false
			}
		}
		if flg {
			fmt.Fprintf(bw, "%d", 0)
		}
		fmt.Fprintln(bw)
	}
}

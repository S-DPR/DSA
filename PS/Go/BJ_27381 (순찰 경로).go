package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
27381번 순찰 경로

노드가 N개인 완전그래프에서 하나의 트리를 구성하는 N-1개의 간선을 골라 제거했다.
이 때, 모든 노드를 하나씩 거치는 경로를 출력해보자.
그런게 없으면 -1을 출력하자.

여기서 참 많은걸 배웠는데,
먼저 성게그래프. 정말 간단히 말해서 한 정점에서 다른 모든 정점으로 한 번에 갈 수 있는 트리입니다.
트리인이유도 간단히, 그냥 간선이 N-1개있어서..
높이가 2인 트리 생각하면 편할거같네요.
이 경우에만 노드를 하나씩 거치는 경로가 없다고 합니다.

두번째로 트리는 이분그래프이다..인데.
아니, 이분그래프인건 알았는데 이게 왜 이분그래프랑 관련이있지?
하고 생각을 진짜 한시간 했습니다.

그래서 나온 결론이, 저 이분그래프때문에 깊이가 홀수인 정점끼리는 항상 이동 가능하더라고요.
똑같이 깊이가 짝수인 경우도.
우와, 진짜 이게 플레인가.. 하면서 감탄했습니다.
난이도기여 보니까 거의 골드1직전이던데, 이런거까지 골드로 떨구면 대체 플레엔 뭘남기려고..
*/
func dfs(x, curd int, depth, G *[][]int, vis *[]bool) {
	(*vis)[x] = true
	(*depth)[curd] = append((*depth)[curd], x)
	for _, i := range (*G)[x] {
		if (*vis)[i] {
			continue
		}
		dfs(i, curd^1, depth, G, vis)
	}
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var T, N, u, v int
	for fmt.Fscan(br, &T); T > 0; T-- {
		fmt.Fscan(br, &N)
		G := make([][]int, N+1)
		depth := make([][]int, 2)
		for i := 0; i < 2; i++ {
			depth[i] = make([]int, 0)
		}
		for i := 0; i <= N; i++ {
			G[i] = make([]int, 0)
		}
		fail := false
		for i := 1; i < N; i++ {
			fmt.Fscan(br, &u, &v)
			G[u] = append(G[u], v)
			G[v] = append(G[v], u)
			fail = fail || len(G[u]) == N-1 || len(G[v]) == N-1
		}
		if fail {
			fmt.Fprintln(bw, "-1")
			continue
		}
		vis := make([]bool, N+1)
		dfs(1, 1, &depth, &G, &vis)
		vis = make([]bool, N+1)
		canGoEven := -1
		for _, x := range depth[1] {
			vis[x] = true
			if canGoEven == -1 && len(G[x]) != len(depth[0]) {
				canGoEven = x
				continue
			}
			fmt.Fprintf(bw, "%d ", x)
		}
		fmt.Fprintf(bw, "%d ", canGoEven)
		for _, x := range G[canGoEven] {
			vis[x] = true
		}
		for i := 1; i <= N; i++ {
			if vis[i] {
				continue
			}
			vis[i] = true
			fmt.Fprintf(bw, "%d ", i)
			break
		}
		for _, x := range G[canGoEven] {
			vis[x] = false
		}
		for _, x := range depth[0] {
			if vis[x] {
				continue
			}
			fmt.Fprintf(bw, "%d ", x)
		}
		fmt.Fprintln(bw)
	}
}

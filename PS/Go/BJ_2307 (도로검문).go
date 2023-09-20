package main

import (
	"bufio"
	"container/heap"
	"fmt"
	"os"
)

/*
2307번 도로검문

그래프가 주어진다.
1에서 N으로 가는 최단경로와,
간선 하나를 없애고 1에서 N으로 가는 최단경로의 최대 차이를 구해보자.
만약 갈 수 없게 된다면 -1을 출력하자.

이야
나는 또 이게 엄청난 테크닉이 있는지알았는데..
그냥 반쯤 브루트포스네;

그냥 최단경로 한번 슥 구하고 최단경로에 있는 모든 간선을 잘라보고 다시 다익스트라 돌리면 됩니다.
찾아봐도 딱히 이거 외에는 안나오는거같고..
다만 저같은경우 최단경로가 여러개일경우 그 부분도 싹다 검사해서 더 느리게 나온 것으로 보입니다.
다른사람들은 제가 옛날에 최단경로 역추적할떄 쓰던 parent배열 만들어서 하는 방법 쓰더라고요.
*/
type dijNode struct {
	curN, curW int
}

type node struct {
	cur, dst, weight int
}

type dijHeap []dijNode

var (
	N, M int
	G    [][]node
	kth  []node
)

func (h dijHeap) Len() int {
	return len(h)
}

func (h dijHeap) Less(i, j int) bool {
	return h[i].curW < h[j].curW
}

func (h dijHeap) Swap(i, j int) {
	h[i], h[j] = h[j], h[i]
}

func (h *dijHeap) Push(x any) {
	*h = append(*h, x.(dijNode))
}

func (h *dijHeap) Pop() any {
	old := *h
	n := len(old)
	x := old[n-1]
	*h = old[:n-1]
	return x
}

func abs(i int) int {
	if i < 0 {
		return -i
	}
	return i
}

func max(i, j int) int {
	if i < j {
		return j
	}
	return i
}

func dij(s, a int) []int {
	dist := make([]int, N+1)
	h := &dijHeap{}
	heap.Push(h, dijNode{1, 0})
	for i := 0; i <= N; i++ {
		dist[i] = 1 << 30
	}
	dist[1] = 0
	for h.Len() > 0 {
		cur := heap.Pop(h).(dijNode)
		curN, curW := cur.curN, cur.curW
		if dist[curN] < curW {
			continue
		}
		for _, nxt := range G[curN] {
			nxtN, nxtW := nxt.dst, nxt.weight
			if nxtN+curN == s && abs(nxtN-curN) == a {
				continue
			}
			if curW+nxtW < dist[nxtN] {
				dist[nxtN] = curW + nxtW
				heap.Push(h, dijNode{nxtN, dist[nxtN]})
			}
		}
	}
	return dist
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	fmt.Fscan(br, &N, &M)
	G = make([][]node, N+1)
	kth = make([]node, M+1)
	for i := 0; i <= N; i++ {
		G[i] = make([]node, N+1)
	}
	for i := 1; i <= M; i++ {
		var u, v, w int
		fmt.Fscan(br, &u, &v, &w)
		G[u] = append(G[u], node{u, v, w})
		G[v] = append(G[v], node{v, u, w})
		kth[i] = node{u, v, w}
	}
	flg := false
	ret := -1
	dist := dij(-1, -1)
	for i := 1; i <= M; i++ {
		u, v, w := kth[i].cur, kth[i].dst, kth[i].weight
		if abs(dist[v]-dist[u]) != w {
			continue
		}
		rdist := dij(u+v, abs(u-v))[N]
		ret = max(ret, rdist-dist[N])
		flg = flg || rdist == 1<<30
	}
	if flg {
		fmt.Fprintln(bw, -1)
	} else {
		fmt.Fprintln(bw, ret)
	}
}

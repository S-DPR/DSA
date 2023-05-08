package main

import (
	"bufio"
	"fmt"
	"math"
	"os"
)
/*
16681번 등산

노드 1에서 N으로 가려고 하는데, 중간에 아무 노드를 거치고 가려고 한다.
각 노드는 높이가 있다. 1과 N은 높이가 1임이 보장된다.
목표로 한 노드의 높이를 K라고 하면 KE만큼의 성취도를 얻고,
1에서 그 노드를 거쳐 N으로 가는데 KD만큼의 체력을 잃는다.
단, 1에서 해당 노드로 가는데는 반드시 높이가 높아지는 방향으로,
해당 노드에서 N으로 가는데는 반드시 높이가 낮아지는 방향으로만 가야한다.
성취도-체력의 최댓값을 구해보자.

힙 구현 잘못해서 7번 틀린문제
진짜 실화냐
아니 이게 님들아 질문글에 있는 테스트케이스 다 맞고
막 2%에서 틀리면 내가 말을 안해 26%까지 오르는데 힙구현 실수했다고 누가생각하냐고요 진짜

후.. 어쩄든 그냥 1과 E에서 다익스트라 돌리면 됩니다.
1에서 어떤 노드까지는 높아져야하니 height[i]<height[j] 넣어주고,
어떤 노드에서 N까지는 낮아져야하니 또 반대로 보면 E에서 해당 노드까지는 높아져야한다는 말이됩니다.
그러므로 1에서 출발할 떄랑 똑같이 하면 됩니다.
아이디어는 바로 생각해냈는데 힙구현 실수 아 진짜..
*/
var INF = int64(math.MaxInt64)

type node struct {
	cur, weight int64
}

type heap struct {
	arr     []node
	arrSize int
}

func makeHeap() heap {
	var item heap
	item.arrSize = 0
	item.arr = make([]node, 1)
	return item
}

func (h *heap) push(item node) {
	h.arr = append(h.arr, node{})
	h.arrSize += 1
	idx := h.arrSize
	for idx > 1 && item.weight < h.arr[idx>>1].weight {
		h.arr[idx] = h.arr[idx>>1]
		idx >>= 1
	}
	h.arr[idx] = item
}

func (h *heap) pop() node {
	ret := h.arr[1]
	item := h.arr[h.arrSize]
	h.arr = h.arr[:h.arrSize+1]
	h.arrSize -= 1
	idx := 1
	for idx<<1 <= h.arrSize {
		child := idx << 1
		if child+1 <= h.arrSize && h.arr[child+1].weight < h.arr[child].weight {
			child += 1
		}
		if item.weight <= h.arr[child].weight {
			break
		}
		h.arr[idx] = h.arr[child]
		idx = child
	}
	h.arr[idx] = item
	return ret
}

func (h *heap) isEmpty() bool {
	return h.arrSize == 0
}

func (h *heap) print() {
	println(h.arrSize)
	for i := 0; i <= h.arrSize; i++ {
		fmt.Printf("[%d %d] ", h.arr[i].cur, h.arr[i].weight)
	}
	println()
}

func dij(G []map[int64]int64, height []int64, s int64) []int64 {
	N := len(G) - 1
	dist := make([]int64, N+1)
	for i := 1; i <= N; i++ {
		dist[i] = INF
	}
	dist[s] = 0
	h := makeHeap()
	h.push(node{s, 0})
	for !h.isEmpty() {
		cur := h.pop()
		curN, curW := cur.cur, cur.weight
		if dist[curN] < curW {
			continue
		}
		for nxtN, nxtW := range G[curN] {
			if height[curN] < height[nxtN] && curW+nxtW < dist[nxtN] {
				dist[nxtN] = curW + nxtW
				h.push(node{nxtN, dist[nxtN]})
			}
		}
	}
	return dist
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()
	min := func(i, j int64) int64 {
		if i < j {
			return i
		}
		return j
	}
	max := func(i, j int64) int64 {
		if i < j {
			return j
		}
		return i
	}

	var N, M int
	var D, E int64
	fmt.Fscan(br, &N, &M, &D, &E)
	height := make([]int64, N+1)
	G := make([]map[int64]int64, N+1)
	for i := 1; i <= N; i++ {
		fmt.Fscan(br, &height[i])
		G[i] = make(map[int64]int64)
	}
	for i := 0; i < M; i++ {
		var u, v, w int64
		fmt.Fscan(br, &u, &v, &w)
		{
			_, e := G[u][v]
			if !e {
				G[u][v] = INF
			}
		}
		{
			_, e := G[v][u]
			if !e {
				G[v][u] = INF
			}
		}
		G[u][v] = min(G[u][v], w)
		G[v][u] = min(G[v][u], w)
	}
	homeToM := dij(G, height, 1)
	schToM := dij(G, height, int64(N))
	ret := -INF
	for i := 2; i < N; i++ {
		if homeToM[i] == INF || schToM[i] == INF {
			continue
		}
		ret = max(ret, height[i]*E-homeToM[i]*D-schToM[i]*D)
	}
	if ret == -INF {
		fmt.Fprintln(bw, "Impossible")
	} else {
		fmt.Fprintln(bw, ret)
	}
}

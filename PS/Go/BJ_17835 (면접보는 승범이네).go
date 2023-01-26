package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
17835번 면접보는 승범이네

단방향 그래프가 주어진다.
이후 K개의 시작점이 주어진다.
모든 시작점으로부터 가장 먼 노드의 번호와 그 길이를 출력해보자.

실수로 디버깅용 print를 안지우고 내서 한 번 틀린 문제..
이 문제도 상당히 재밌는 문제였습니다.

아이디어는 이렇습니다. 그래프가 주어지는데, 이를 역방향으로 넣자!
디버깅 하면서 어 이거 좀 이상한데..? 하면서 역방향으로 넣었더니 성공했고,
다음으로는 다익스트라에 시작점을 모두 한번에 넣을 수 있다는 점.
솔직히 기대도 안하고 한번 해봤는데 통과해서 놀랐습니다. 오늘도 하나 알아갑니다.

Go로 한 이유는 저번에 우선순위큐 구현한게 안지워져있길래 어 개꿀 ㅋㅋ 하면서 써먹었..
*/
type info struct {
	weight int64
	node   int64
}

type PriorityQueue struct {
	arr     []info
	arrSize int
}

func newPQ() PriorityQueue {
	return PriorityQueue{arr: make([]info, 100_000), arrSize: 0}
}

func (PQ *PriorityQueue) push(x info) {
	PQ.arrSize++
	idx := PQ.arrSize
	for idx != 1 && PQ.arr[idx>>1].weight > x.weight {
		PQ.arr[idx] = PQ.arr[idx>>1]
		idx >>= 1
	}
	PQ.arr[idx] = x
}

func (PQ *PriorityQueue) pop() info {
	idx, item := 1, PQ.arr[PQ.arrSize]
	result := PQ.arr[1]
	PQ.arrSize--
	for idx<<1 <= PQ.arrSize {
		child := idx << 1
		if child < PQ.arrSize && PQ.arr[child].weight > PQ.arr[child+1].weight {
			child++
		}
		if PQ.arr[child].weight >= item.weight {
			break
		}
		PQ.arr[idx] = PQ.arr[child]
		idx = child
	}
	PQ.arr[idx] = item
	return result
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	min := func(i, j int64) int64 {
		if i > j {
			return j
		}
		return i
	}

	var N, M, K int
	fmt.Fscan(br, &N, &M, &K)
	G := make(map[int64]map[int64]int64)
	for i := 0; i <= N; i++ {
		G[int64(i)] = make(map[int64]int64)
	}
	for i := 0; i < M; i++ {
		var U, V, W int64
		fmt.Fscan(br, &U, &V, &W)
		if G[V][U] == 0 {
			G[V][U] = W
		} else {
			G[V][U] = min(G[V][U], W)
		}
	}
	start := make([]int64, K)
	for i := 0; i < K; i++ {
		fmt.Fscan(br, &start[i])
	}

	//dij
	INF := int64(100_000) * int64(100_000)
	dist := make([]int64, N+1)
	for i := 1; i <= N; i++ {
		dist[i] = INF
	}
	PQ := newPQ()
	for _, item := range start {
		dist[item] = 0
		PQ.push(info{0, item})
	}

	for PQ.arrSize > 0 {
		cur := PQ.pop()
		curW, curN := cur.weight, cur.node
		if dist[curN] < curW {
			continue
		}
		for newN, newW := range G[curN] {
			if dist[newN] > newW+curW {
				dist[newN] = newW + curW
				PQ.push(info{dist[newN], newN})
			}
		}
	}

	resN, resW := 0, int64(0)
	for i := 1; i <= N; i++ {
		if resW < dist[i] {
			resN, resW = i, dist[i]
		}
	}
	fmt.Fprintln(bw, resN)
	fmt.Fprintln(bw, resW)

	bw.Flush()
}

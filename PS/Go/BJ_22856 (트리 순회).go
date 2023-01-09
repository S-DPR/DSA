package main

import (
	"bufio"
	"fmt"
	"os"
)
/*
22856 트리 순회

트리를 다음 순서로 순회하려 한다.
1. 현재 위치한 노드의 왼쪽 자식 노드가 존재하고 아직 방문하지 않았다면, 왼쪽 자식 노드로 이동한다.
2. 그렇지 않고 현재 위치한 노드의 오른쪽 자식 노드가 존재하고 아직 방문하지 않았다면, 오른쪽 자식 노드로 이동한다.
3. 그렇지 않고 현재 노드가 유사 중위 순회의 끝이라면, 유사 중위 순회를 종료한다.
4. 그렇지 않고 부모 노드가 존재한다면, 부모 노드로 이동한다.
5. 순회를 종료할 때까지 1 ~ 4를 반복한다.
이 때, 노드를 몇 번 이동하게 되는지 구해보자.

에타에서 한명이 도와주세요~ 해서 가봤더니 이 문제
10분만에 풀어주고 40분동안 반례찾다가 반례 알려주고 글 쓰는 중입니다.
사실 로직은 간단합니다. 그냥 왼쪽으로 간 흔적 있으면 길을 2번씩 더하고, 없으면 1번 더하면 돼요.
이상태로 DFS돌리면 끝납니다.
*/
type Node struct {
	l, r int
}

func dfs(G []Node, x int, isLeft bool) int64 {
	result := int64(0)
	if G[x].l != -1 {
		result += 2
		result += dfs(G, G[x].l, true)
	}
	if G[x].r != -1 {
		if isLeft {
			result += 2
		} else {
			result += 1
		}
		result += dfs(G, G[x].r, isLeft)
	}
	return result
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	var N int
	fmt.Fscan(br, &N)
	G := make([]Node, N+1)
	for i := 0; i < N; i++ {
		var n, l, r int
		fmt.Fscan(br, &n, &l, &r)
		G[n] = Node{l, r}
	}

	fmt.Fprintln(bw, dfs(G, 1, false))

	bw.Flush()
}

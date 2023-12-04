package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
20506번 Kaisar - 생존

정점 쌍 (x, y)에 대해 LCA 노드 번호를 K라고 하자.
모든 정점 쌍을 찾은 뒤, 정렬을 해보자.
그리고, 거기서 홀수번째와 짝수번째 인덱스에 있는 원소를 모두 더한값을 각각 출력하자.

언제나 그랬듯이 대충 적당히 자식트리들의 노드 수로 장난치는 문제.
아싸 이번주 dp 하나 이거로 처리했다

자기 자신도 노드에 포함되고, (x, y)와 (y, x)를 모두 세기에 그거만 처리해주면 됩니다.
난이도 올리겠답시고 정렬 후 홀수/짝수번째 인덱스를 더한걸 각각 출력하라고했지만..
적당히 규칙성 찾아서 처리.
쉬운 플레5였네요.
*/
var (
	V []bool
	G [][]int64
	M map[int64]int64
)

func dfs(x int64) int64 {
	V[x] = true
	pf := int64(0)
	each := make([]int64, 0)
	for _, i := range G[x] {
		if V[i] {
			continue
		}
		nxt := dfs(i)
		each = append(each, nxt)
		pf += nxt
	}
	ret := pf
	M[x] = pf*2 + 1
	for _, i := range each {
		pf -= i
		M[x] += pf * i * 2
	}
	return ret + 1
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N int
	fmt.Fscan(br, &N)
	G = make([][]int64, N+1)
	V = make([]bool, N+1)
	M = make(map[int64]int64)
	root := 1
	for i := 0; i <= N; i++ {
		G[i] = make([]int64, 0)
	}
	for i := 1; i <= N; i++ {
		var x int64
		fmt.Fscan(br, &x)
		G[x] = append(G[x], int64(i))
		if x == 0 {
			root = i
		}
	}
	dfs(int64(root))
	odd, even := int64(0), int64(0)
	currentIsEven := int64(0)
	for i := 1; i <= N; i++ {
		k := int64(i)
		v := M[k]
		if currentIsEven == 0 {
			odd += k * ((v / 2) + (v % 2))
			even += k * (v / 2)
		} else {
			odd += k * (v / 2)
			even += k * ((v / 2) + (v % 2))
		}
		currentIsEven = (currentIsEven + v) % 2
	}
	fmt.Fprintln(bw, even, odd)
}

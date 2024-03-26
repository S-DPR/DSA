package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
13504번 XOR 합

수열 A의 부분연속XOR합 중 최댓값을 구해보자.

개념만 알고있던 트라이 구현
문제 보고 '아 이거 트라이 설명할때 국룰문제던데' 하고..
다른 방법 없을까.. 하다가 gg
트라이는 포인터구현이 더 메이저한거로 알아서 포인터 있는 Go로 왔습니다.
시간복잡도나 알아봐야지.
*/
type Node struct {
	left, right *Node
}

func insert(node *Node, x, kth int) {
	var nxt *Node
	if x&(1<<kth) == 0 {
		if node.left == nil {
			node.left = new(Node)
		}
		nxt = node.left
	} else {
		if node.right == nil {
			node.right = new(Node)
		}
		nxt = node.right
	}
	if kth > 0 {
		insert(nxt, x, kth-1)
	}
}

func find(node *Node, x, kth int) int {
	if kth == -1 {
		return 0
	}
	ret := 0
	if node.left != nil && node.right != nil {
		if x&(1<<kth) == 0 {
			ret |= find(node.right, x, kth-1) ^ (1 << kth)
		} else {
			ret |= find(node.left, x, kth-1)
		}
	} else if node.left != nil {
		ret |= find(node.left, x, kth-1)
	} else {
		ret |= find(node.right, x, kth-1) ^ (1 << kth)
	}
	return ret
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var T, N int
	fmt.Fscan(br, &T)
	for tc := 0; tc < T; tc++ {
		fmt.Fscan(br, &N)
		A := make([]int, N)
		for i := 0; i < N; i++ {
			fmt.Fscan(br, &A[i])
		}
		trie := new(Node)
		pf, mx := 0, 0
		insert(trie, 0, 31)
		for _, i := range A {
			pf ^= i
			mx = max(mx, pf^find(trie, pf, 31))
			insert(trie, pf, 31)
		}
		fmt.Println(mx)
	}
}

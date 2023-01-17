package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
2623번 음악프로그램

1부터 N까지의 수가 있는 수열을 A라 하자.
당신에게는 M개의 수열이 또 주어진다.
주어지는 M개의 수열을 부분수열로 하는 수열 A를 만들어보자.
단, 수열의 순서가 바뀌면 안된다.

이거 딱봐도 그래프네 ㅋㅋ 하면서 푼 문제.
순간 DP였나 헷갈렸지만, 위상정렬이더라고요.
위상정렬 복습을 오랜만에 해봤습니다.
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	var n, m int
	fmt.Fscan(br, &n, &m)
	G := make([][]int, n+1)
	withstand := make([]int, n+1)
	for i := 0; i <= n; i++ {
		G[i] = make([]int, 0)
	}
	for i := 0; i < m; i++ {
		var l int
		fmt.Fscan(br, &l)
		if l == 0 {
			continue
		}
		var prev int
		fmt.Fscan(br, &prev)
		for j := 1; j < l; j++ {
			var cur int
			fmt.Fscan(br, &cur)
			G[cur] = append(G[cur], prev)
			withstand[prev]++
			prev = cur
		}
	}

	queue := make([]int, 0)
	answer := make([]int, n+1)
	idx := n
	for i := 1; i <= n; i++ {
		if withstand[i] == 0 {
			queue = append(queue, i)
		}
	}
	for len(queue) > 0 {
		cur := queue[0]
		queue = queue[1:]
		answer[idx] = cur
		idx--
		for _, item := range G[cur] {
			withstand[item]--
			if withstand[item] == 0 {
				queue = append(queue, item)
			}
		}
	}
	if idx != 0 {
		fmt.Fprintln(bw, 0)
	} else {
		for i := 1; i <= n; i++ {
			fmt.Fprintln(bw, answer[i])
		}
	}

	bw.Flush()
}

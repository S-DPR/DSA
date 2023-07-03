package main

import (
	"bufio"
	"fmt"
	"os"
)
/*
27915번 금광

트리에 색을 칠하려 한다.
이 때, 같은 색을 칠하려면 다른 같은 색과 홀수만큼의 거리가 떨어져있어야한다.
적어도 몇 개의 색을 준비해야할까?

와 진짜
와...
진짜 생각도 못했다
결국 GPT가 풀어줬네

트리가 이분그래프란걸 처음알았습니다.
그니까, 이분그래프의 한 예.
사실 그걸 몰라도 아래 순서를 느긋하게 따라가 추적을 할 수 있어야 했네요.

1. 하나의 색은 많아봐야 2개밖에 칠하지 못합니다.
2. 그런데 그거는 각각 노드의 깊이가 홀/짝수일 때 하나씩을 칠할 수 있음을 파악해야합니다.
3. 짜잔 그러면 이제 깊이가 홀/짝수인거 세서 더 많은거 출력해주면 되겠네요?

.. 아.
그렇구나...
내가 멍청한놈이구나 그냥..
에휴..
*/
var (
	G [][]int
	d = []int{0, 0}
)

func dfs(cur, depth int) {
	d[depth%2]++
	for _, i := range G[cur] {
		dfs(i, depth+1)
	}
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N int
	fmt.Fscan(br, &N)
	G = make([][]int, N+1)
	for i := 0; i <= N; i++ {
		G[i] = make([]int, 0)
	}
	for i := 2; i <= N; i++ {
		var p int
		fmt.Fscan(br, &p)
		G[p] = append(G[p], i)
	}
	dfs(1, 1)
	if d[0] < d[1] {
		fmt.Fprintln(bw, d[1])
	} else {
		fmt.Fprintln(bw, d[0])
	}
}

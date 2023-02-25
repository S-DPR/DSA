package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
17071번 숨바꼭질 5

A는 현재 좌표 T에있고, B는 현재 좌표 K에 있다.
현재 좌표를 x라고 할 때,
A는 x+1, x-1, x*2로 이동할 수 있으며, (0 <= result)
B는 1초가 지날때마다 1씩 가속하여, 0초에는 x, 1초에는 x+1, 2초에는 x+1+2, ... 이런식으로 이동한다.
각 이동에는 1초가 걸린다.
A가 B를 잡는 최단시간을 구해보자.

크
이런문제 몇 번 접했는데 왜 이번엔 핵심을 못봤지..
홀/짝 나누어서 방문하는 경우를 체크해야합니다. T초에 방문하면 T+2초에도 방문 가능하니까.
결국, 영우의 기숙사 청소였나.. 그 문제랑 다를게 없다는 소리죠.

사실상 답을 봐버리고 푼 문제입니다. 에잉,,
그나저나 map으로하다가 list로하니까 시간이나 메모리나 엄청 줄어드네요.
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	sum := func(i int) int {
		return i * (i + 1) / 2
	}
	min := func(i, j int) int {
		if i > j {
			return j
		}
		return i
	}

	var T, K int
	fmt.Fscan(br, &T, &K)
	Q := make([][]int, 0)
	Q = append(Q, []int{T, 0})
	visit := make([][]int, 2)
	for i := 0; i < 2; i++ {
		visit[i] = make([]int, 500001)
	}
	result := 1_000_000
	for len(Q) > 0 {
		cur := Q[0]
		Q = Q[1:]
		curN, curT := cur[0], cur[1]
		isOdd := (curT + 1) & 1
		if curN+1 <= 500000 && visit[isOdd][curN+1] == 0 {
			Q = append(Q, []int{curN + 1, curT + 1})
			visit[isOdd][curN+1] = curT + 1
		}
		if curN-1 >= 0 && visit[isOdd][curN-1] == 0 {
			Q = append(Q, []int{curN - 1, curT + 1})
			visit[isOdd][curN-1] = curT + 1
		}
		if curN*2 <= 500000 && visit[isOdd][curN*2] == 0 {
			Q = append(Q, []int{curN * 2, curT + 1})
			visit[isOdd][curN*2] = curT + 1
		}
	}
	visit[0][T] = 0

	for i := 0; K+sum(i) <= 500000; i++ {
		if visit[i&1][K+sum(i)] <= i && (K+sum(i) == T || visit[i&1][K+sum(i)] != 0) {
			result = min(result, i)
		}
	}

	if result != 1000000 {
		fmt.Fprintln(bw, result)
	} else {
		fmt.Fprintln(bw, -1)
	}

	bw.Flush()
}

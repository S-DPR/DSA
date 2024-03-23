package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
2593번 엘리베이터

N층 빌딩에 엘리베이터가 M개 있다.
엘리베이터는 S층에서 시작해 S, S+T, S+T*2, S+T*3... 층으로 이동한다.
이 때, A층에서 B층으로 가는데 몇 개의 엘리베이터를 타야할까?
갈 수 있다면, 어떻게 타야할까?

음..
이전에 환승..이랑비슷한데.
뭔가.. 다릅니다.
이거 자바로 했다가 Integer가 16Byte라는 엄청난 사실을 깨닫고 절망을 하고..
Go로 도망오니까 되긴하네요.

제 풀이는 O(NM). 그닥 효율적이진 않죠.
실제로 메모리를 너무 많이 먹어서 MLE가 너무 많이 났습니다.
그래도 Go는 int가 4byte라서 통과는됐네요.

M^2풀이가 있다고 합니다. 그거나 알아보러 가야겠어요.
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N, M int
	fmt.Fscan(br, &N, &M)
	G := make([][]int, N+M+1)
	for i := 0; i < N+M+1; i++ {
		G[i] = make([]int, 0)
	}
	for i := N + 1; i < N+M+1; i++ {
		var S, T int
		fmt.Fscan(br, &S, &T)
		for f := S; f <= N; f += T {
			G[i] = append(G[i], f)
			G[f] = append(G[f], i)
		}
	}
	var S, E int
	fmt.Fscan(br, &S, &E)
	trace := make([]int, N+M+1)
	trace[S] = -1
	Q := make([]int, 0)
	Q = append(Q, S)
	for len(Q) > 0 {
		cur := Q[0]
		Q = Q[1:]
		for _, i := range G[cur] {
			if trace[i] == 0 {
				Q = append(Q, i)
				trace[i] = cur
			}
		}
	}
	cur := E
	T := make([]int, 0)
	for trace[cur] > 0 && cur != S {
		if trace[cur] > N {
			T = append(T, trace[cur])
		}
		cur = trace[cur]
	}
	if cur == S {
		fmt.Fprintln(bw, len(T))
		for i, j := 0, len(T)-1; i < j; i, j = i+1, j-1 {
			T[i], T[j] = T[j], T[i]
		}
		for _, i := range T {
			fmt.Fprintln(bw, i-N)
		}
	} else {
		fmt.Fprintln(bw, -1)
	}
}

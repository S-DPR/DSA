package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

/*
1039번 교환

100만 이하의 자연수가 주어진다. 그리고 10 이하의 K가 주어진다.
유효한 자연수가 되도록 두 자리를 계속 바꿀 때, 정확히 K번 바꾼 후 나올 수 있는 최댓값을 구해보자.

이야 이게 그리디가 아니네??
신기하다..

그냥 dfs 대충 방문처리로 하면서 처리해주면 되는 문제.
솔직히 진짜 이거 그리디라고 생각했는데 태그 보는거 없었으면 평생 못볼뻔..
너무어렵다..
*/
var (
	N string
	K int
	R = -1
	V []map[string]bool
)

func max(i, j int) int {
	if i > j {
		return i
	}
	return j
}

func dfs(A []rune, k int) {
	str := string(A)
	if k == K {
		ret, _ := strconv.Atoi(str)
		R = max(R, ret)
		return
	}
	isVis, _ := V[k][str]
	if isVis {
		return
	}
	V[k][str] = true
	for i := 0; i < len(A); i++ {
		for j := i + 1; j < len(A); j++ {
			if i == 0 && A[j] == '0' {
				continue
			}
			A[i], A[j] = A[j], A[i]
			dfs(A, k+1)
			A[i], A[j] = A[j], A[i]
		}
	}
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	fmt.Fscan(br, &N, &K)
	A := []rune(N)
	V = make([]map[string]bool, K)
	for i := 0; i < K; i++ {
		V[i] = make(map[string]bool)
	}
	dfs(A, 0)
	fmt.Fprintln(bw, R)
}

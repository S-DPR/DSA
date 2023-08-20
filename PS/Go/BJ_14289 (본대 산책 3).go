package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
14289번 본대 산책 3

그래프가 주어진다.
0번 노드에서 출발해 정확히 K개의 경로를 지나, 다시 0번 노드로 오는 경우의 수를 구해보자.

전에 무슨 대회 예선에서 비슷한 문제를 봤는데, 결국 못풀었는데 비슷한 문제여서 가져온 문제.
머 사이클 찾아서 수학적으로 계산하는줄 알았는데 의외로 그냥 상태공간 만들고 행렬거듭제곱?
와.. 오늘도 배워간다 진짜..
그나저나 그때는 2차원 그래프 상에서 하는거였는데, 이거를 1차원으로 변환하고 거듭제곱을 했어야 한다네요.
놀랍다놀라워..

먼저 그래프를 인접행렬로 나타냅니다.
이 행렬을 D번 제곱합니다.
상태행렬 초깃값과 제곱해서 나온 행렬을 곱합니다.
그러면 답이 나온다고 하네요.

K-Path라고 몇달동안 또 박혀있는 문제가 있는데 그거도 이거처럼 푸나봅니다..
진짜 선형대수학 기초 공부 해놔야겠는데..
*/
var (
	MOD int64 = 1_000_000_007
)

func matMul(A, B [][]int64) [][]int64 {
	n := len(A)
	m := len(A[0])
	k := len(B[0])
	ret := make([][]int64, n)
	for i := 0; i < n; i++ {
		ret[i] = make([]int64, k)
	}
	for i := 0; i < n; i++ {
		for j := 0; j < k; j++ {
			for x := 0; x < m; x++ {
				tmp := (A[i][x] * B[x][j]) % MOD
				ret[i][j] = (ret[i][j] + tmp) % MOD
			}
		}
	}
	return ret
}

func matPow(A [][]int64, N int) [][]int64 {
	if N == 1 {
		return A
	}
	half := matPow(A, N/2)
	if N&1 == 0 {
		return matMul(half, half)
	}
	return matMul(A, matMul(half, half))
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N, K int
	fmt.Fscan(br, &N, &K)
	G := make([][]int64, N)
	mat := make([][]int64, N)
	for i := 0; i < N; i++ {
		G[i] = make([]int64, N)
		mat[i] = []int64{0}
	}
	for i := 0; i < K; i++ {
		var u, v int
		fmt.Fscan(br, &u, &v)
		u--
		v--
		G[u][v] = 1
		G[v][u] = 1
	}
	mat[0][0] = 1
	var T int
	fmt.Fscan(br, &T)
	R := matPow(G, T)
	fmt.Fprintln(bw, matMul(R, mat)[0][0]%MOD)
}

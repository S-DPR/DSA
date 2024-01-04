package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
)

/*
18859번 부모님께 큰절 하고

길이가 N인 수열이 주어진다.
이 수열을 적절히 재배치하여 어느 순간까지 일정한 간격으로 감소하는 수열이었다가,
어느 순간부터 일정한 간격으로 증가하는 수열이 될 수 있을까?

엄..
플레4긴한데.. 왜 플레4인진 모르겠는 문제
두개만 제대로 잡고 가면 됩니다.

1. 가장 작은 수를 기준으로 보면 양쪽으로는 일정하게 증가한다.
2. 가장 작은 수가 이미 기준이 되었기 때문에, 공차가 같은 경우 결과는 같다.
그러므로 어떤 ld, rd에 대해 결과가 실패면 이전 수열이 어떻게 오든 항상 실패.

이거 기반으로 재귀 돌리면 됩니다.
이딴게..플레4..? 지금까지 내가 본 플레4들은..
*/
var (
	N int
	A []int
	V map[int]map[int]bool
)

func loop(idx, l, r, ld, rd int) bool {
	if idx == N {
		return ld > 0 && rd > 0
	}
	_, e := V[ld]
	if !e {
		V[ld] = make(map[int]bool)
	}
	if V[ld][rd] {
		return false
	}
	ret := false
	if ld == -1 || l+ld == A[idx] {
		nxtL := A[idx]
		nxtLd := ld
		if ld == -1 {
			nxtLd = nxtL - l
		}
		ret = ret || loop(idx+1, nxtL, r, nxtLd, rd)
	}
	if rd == -1 || r+rd == A[idx] {
		nxtR := A[idx]
		nxtRd := rd
		if rd == -1 {
			nxtRd = nxtR - r
		}
		ret = ret || loop(idx+1, l, nxtR, ld, nxtRd)
	}
	V[ld][rd] = true
	return ret
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	fmt.Fscan(br, &N)
	A = make([]int, N)
	V = make(map[int]map[int]bool)
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &A[i])
	}
	sort.Ints(A)
	ret := loop(1, A[0], A[0], -1, -1)
	if ret {
		fmt.Println("Yes")
	} else {
		fmt.Println("No")
	}
}

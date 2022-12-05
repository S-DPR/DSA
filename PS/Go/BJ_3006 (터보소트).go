package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
3006번 터보소트

정렬이 되지 않았고, 1부터 N까지의 자연수가 중복없이 있는 수열 A가 주어진다.
이를 다음과 같은 순서로 정렬하려 한다.
1. 수열 A에서 가장 작은 수를 찾는다.
2. 그 수를 앞 수와 스왑하면서 맨 앞에 배치한다.
3. 수열 A에서 가장 큰 수를 찾는다.
4. 그 수를 뒷 수와 스왑하면서 맨 뒤에 배치한다.
5. 1~4번 과정을 모든 수를 선택해볼때까지 반복한다. 단, 이미 선택했던 수는 더이상 선택하지 않는다.
2, 4번 과정 시에 스왑해야하는 횟수를 각 줄마다 출력하시오.

쉬운 세그트리 문제입니다.
보자마자 세그트리 삘이 딱 꽂히더라고요.
Fenwick Tree 하나로 구현해봤습니다.
그리고, 파이썬은 질리니 Go로 한번 해봤고요.
*/

type Fenwick struct {
	F     []int
	FSize int
}

func newFenwick(arr []int) *Fenwick {
	var tmp Fenwick
	tmp.F = make([]int, len(arr)+1)
	tmp.FSize = len(arr) + 1
	for _, i := range arr {
		tmp.update(i, 1)
	}
	return &tmp
}

func (Fen Fenwick) update(idx, val int) {
	for idx < Fen.FSize {
		Fen.F[idx] += val
		idx += idx & -idx
	}
}

func (Fen Fenwick) query(l, r int) int {
	return Fen.internalQuery(r) - Fen.internalQuery(l-1)
}

func (Fen Fenwick) internalQuery(idx int) int {
	res := 0
	for idx > 0 {
		res += Fen.F[idx]
		idx -= idx & -idx
	}
	return res
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	var n int
	fmt.Fscan(br, &n)
	arr := make([]int, n)
	for i := 0; i < n; i++ {
		var tmp int
		fmt.Fscan(br, &tmp)
		arr[tmp-1] = i + 1
	}
	F := newFenwick(arr)
	curS, curE := 0, n-1
	for i := 0; i < n; i++ {
		if i&1 == 0 {
			fmt.Fprintln(bw, F.query(1, arr[curS]-1))
			F.update(arr[curS], -1)
			curS++
		} else {
			fmt.Fprintln(bw, F.query(arr[curE]+1, n))
			F.update(arr[curE], -1)
			curE--
		}
	}

	bw.Flush()
}

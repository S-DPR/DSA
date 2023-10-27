package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
13555번 증가하는 부분 수열

한 수열의 증가하는 부분 수열 중 길이가 K인 것의 개수를 구해보자.

그냥 펜윅트리 K개 정의하고 dp 슥 굴리면 되는문제
할일이 있어서 빨리 가야지..
*/
var (
	N, K int
	T    []Fenwick
	MOD  = 5_000_000
)

type Fenwick struct {
	arr  []int
	size int
}

func newFenwick(size int) Fenwick {
	return Fenwick{make([]int, size+1), size}
}

func (f *Fenwick) update(idx, val int) {
	for idx < f.size {
		f.arr[idx] += val
		f.arr[idx] %= MOD
		idx += idx & -idx
	}
}

func (f *Fenwick) internal_query(idx int) int {
	ret := 0
	for idx > 0 {
		ret += f.arr[idx]
		ret %= MOD
		idx -= idx & -idx
	}
	return ret
}

func (f *Fenwick) query(s, e int) int {
	return f.internal_query(e) - f.internal_query(s-1)
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	fmt.Fscan(br, &N, &K)
	T = make([]Fenwick, K+1)
	for i := 0; i <= K; i++ {
		T[i] = newFenwick(100001)
	}
	ret := 0
	var x int
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &x)
		ret += T[K-1].query(1, x-1)
		ret %= MOD
		for j := K - 1; j > 0; j-- {
			item := T[j-1].query(1, x-1)
			T[j].update(x, item)
		}
		T[1].update(x, 1)
	}
	if K == 1 {
		ret = N
	}
	fmt.Fprintln(bw, ret)
}

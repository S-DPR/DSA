package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
)

/*
15816번 퀘스트 중인 모험가

배열이 길이 N으로 주어진다.
이후 다음과 같은 쿼리가 M개 주어진다.
1 x : 배열에 x를 추가한다.
2 l r : 배열에 `l`부터 `r`까지의 수 중 없는 수의 개수를 출력한다.
2번 쿼리마다 그 답을 한 줄씩 출력하시오.
(-1,000,000,000 <= x, l, r <= 1,000,000,000)
(l <= r)

20212번 나무는 쿼리를 싫어해~ 의 변종입니다.
난이도는 얘가 더 낮지만..
수의 범위가 무려 두 배(!)여서 20212번은 `Dynamic SegmentTree`로 이악물고 풀 수 있는반면,
얘는 그런거 써도 바로 메모리초과 터뜨립니다.
머 애초에 수 범위가 -를 뚫고 가는걸 보니 좌표압축하세요~ 하고앉아계시죠.
좌표압축하고 `lowerBound`로 범위 잡아서 처리해주면 끝납니다.
*/

type Fenwick struct {
	F     []int
	FSize int
}

func newFenwick(size int) Fenwick {
	var Fen Fenwick
	Fen.F = make([]int, size+1)
	Fen.FSize = size + 1
	return Fen
}

func (Fen Fenwick) update(idx, val int) {
	for idx < Fen.FSize {
		Fen.F[idx] += val
		idx += idx & -idx
	}
}

func (Fen Fenwick) internalQuery(idx int) int {
	res := 0
	for idx > 0 {
		res += Fen.F[idx]
		idx -= idx & -idx
	}
	return res
}

func (Fen Fenwick) query(left, right int) int {
	return Fen.internalQuery(right) - Fen.internalQuery(left-1)
}

func lowerBound(arr []int, x int) int {
	left, right := 0, len(arr)-1
	for left < right {
		mid := (left + right) >> 1
		if arr[mid] >= x {
			right = mid
		} else {
			left = mid + 1
		}
	}
	return right
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	// Input Array
	var n int
	fmt.Fscan(br, &n)
	arr := make([]int, n)
	set := make(map[int]int)
	for i := 0; i < n; i++ {
		fmt.Fscan(br, &arr[i])
		set[arr[i]]++
	}

	// Input Query
	var m int
	fmt.Fscan(br, &m)
	Q := make([][]int, m)
	var queryKind, x, l, r int
	for i := 0; i < m; i++ {
		Q[i] = make([]int, 0)
		fmt.Fscan(br, &queryKind)
		switch queryKind {
		case 1:
			fmt.Fscan(br, &x)
			Q[i] = append(Q[i], x)
			set[x]++
		case 2:
			fmt.Fscan(br, &l, &r)
			Q[i] = append(Q[i], []int{l, r}...)
		}
	}

	set[-1_000_000_001]++
	set[1_000_000_001]++
	cleared := make([]int, 0)
	for key := range set {
		cleared = append(cleared, key)
	}

	// Make Segment Tree
	sort.Ints(cleared)
	Fen := newFenwick(len(cleared))
	for i := 0; i < n; i++ {
		Fen.update(lowerBound(cleared, arr[i])+1, 1)
	}

	// Kill Query
	for i := 0; i < m; i++ {
		switch len(Q[i]) {
		case 1:
			Fen.update(lowerBound(cleared, Q[i][0])+1, 1)
		case 2:
			fmt.Fprintln(bw, (Q[i][1]-Q[i][0]+1)-Fen.query(lowerBound(cleared, Q[i][0])+1, lowerBound(cleared, Q[i][1]+1)))
		}
	}

	bw.Flush()
}

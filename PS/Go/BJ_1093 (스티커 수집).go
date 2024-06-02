package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
)

/*
1093번 스티커 수집

각 스티커의 가격과 가치가 주어진다.
현재 갖고있는 스티커의 번호가 주어질 때,
스티커 가치의 합이 K 이상이 되도록 하기 위해 필요한 최소 자본금을 구해보자.

MITM이구나~는 그냥 대충 보면 알 수 있고.
이제 그러면 그 큰 배열에서 어떻게 값을 가져올까..를 고민해야하는 문제.
원래는 이분탐색 하려고했는데, 생각해보니 가치가 최소라고 가격이 최소라는 보장이 없더라고요.
그래서 세그트리인가.. 하고있다가, 업데이트없이 한쪽끝이 정해진 쿼리만주어진다는 점에 착안.
누적합으로 처리했습니다.
(한쪽 끝이 정해진 이유 = 현재 가치 v가 주어진다면 K-v이상의 모든 경우는 가능하므로)
*/
var (
	have    [32]bool
	price   [32]int64
	value   [32]int64
	A, B    [][2]int64
	K       int64
	N, u, v int
)

func loop(x, end int, p, v int64, target *[][2]int64) {
	if x == end {
		*target = append(*target, [2]int64{p, v})
		return
	}
	loop(x+1, end, p, v, target)
	if have[x] {
		loop(x+1, end, p-price[x], v-value[x], target)
	} else {
		loop(x+1, end, p+price[x], v+value[x], target)
	}
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	fmt.Fscan(br, &N)
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &price[i])
	}
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &value[i])
	}
	fmt.Fscan(br, &K)
	fmt.Fscan(br, &u)
	for i := 0; i < u; i++ {
		fmt.Fscan(br, &v)
		have[v] = true
	}
	A = make([][2]int64, 0)
	B = make([][2]int64, 0)
	f_v, s_v := int64(0), int64(0)
	for i := 0; i < N/2; i++ {
		if have[i] {
			f_v += value[i]
		}
	}
	for i := N / 2; i < N; i++ {
		if have[i] {
			s_v += value[i]
		}
	}
	loop(0, N/2, 0, f_v, &A)
	loop(N/2, N, 0, s_v, &B)
	sort.Slice(A, func(i, j int) bool {
		return A[i][1] < A[j][1]
	})
	sort.Slice(B, func(i, j int) bool {
		return B[i][1] < B[j][1]
	})
	ret := int64(1) << 60
	idx := len(B) - 1
	for i := len(B) - 2; i >= 0; i-- {
		B[i][0] = min(B[i][0], B[i+1][0])
	}
	for _, i := range A {
		p, v := i[0], i[1]
		for idx-1 >= 0 && B[idx-1][1]+v >= K {
			idx--
		}
		if B[idx][1]+v >= K {
			ret = min(ret, B[idx][0]+p)
		}
	}
	if ret == int64(1)<<60 {
		fmt.Println(-1)
	} else {
		fmt.Println(max(0, ret))
	}
}

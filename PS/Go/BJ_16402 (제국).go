package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
	"strings"
)

/*
16402번 제국

N개의 나라가 있고, 전쟁이 Q번 일어났다.
A B 1 : A가 B를 이겼다.
A B 2 : B가 A를 이겼다.
패배한 나라는 이긴 나라의 속국이 되고, 패배한 나라의 속국또한 이긴 나라의 속국이 된다.
속국은 자신의 종주국을 공격할 수 있고, 성공할 경우 종주국 지위를 뺏는다.
이 때, 마지막에 남은 종주국 나라의 개수와 그 나라를 구해보자.

N = 500, Q = 2000밖에 안돼서 무지성 분리집합.
가볍게 풀립니다. 쉽네요.
w가 2인 경우는 A, B 위치를 바꿔서 처리.
속국 모두 뻇는건 그냥 반복문으로..
*/
var (
	U []int
)

func union(u, v int) bool {
	up, vp := find(u), find(v)
	if up == vp {
		return false
	}
	U[vp] = U[up]
	return true
}

func find(u int) int {
	if U[u] != u {
		U[u] = find(U[u])
	}
	return U[u]
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N, Q int
	fmt.Fscan(br, &N, &Q)
	U = make([]int, N)
	mapping := make(map[string]int)
	rev := make(map[int]string)
	br.ReadString('\n')
	for i := 0; i < N; i++ {
		s, _ := br.ReadString('\n')
		s = strings.TrimSpace(s)
		x := len(mapping)
		mapping[s] = x
		rev[x] = s
		U[i] = i
	}
	for i := 0; i < Q; i++ {
		s, _ := br.ReadString('\n')
		s = strings.TrimSpace(s)
		tokens := strings.Split(s, ",")
		A, B, w := tokens[0], tokens[1], tokens[2]
		if w == "2" {
			A, B = B, A
		}
		mA, mB := mapping[A], mapping[B]
		if !union(mA, mB) {
			newU := make([]int, N)
			for i := 0; i < N; i++ {
				newU[i] = U[i]
				if find(i) == find(mA) {
					newU[i] = mA
				}
			}
			U = newU
		}
	}
	ret := make(map[string]bool)
	for i := 0; i < N; i++ {
		ret[rev[find(i)]] = true
	}
	retA := make([]string, 0)
	for k, _ := range ret {
		retA = append(retA, k)
	}
	sort.Slice(retA, func(i, j int) bool {
		return retA[i] < retA[j]
	})
	fmt.Println(len(retA))
	for _, i := range retA {
		fmt.Println(i)
	}
}

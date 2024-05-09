package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
19565번 수열 만들기

다음 규칙을 만족하는 수열의 최대길이와 그 수열 하나를 출력해보자.
1. A의 첫 수와 마지막 수는 모두 1이다.
2. 1 <= i < j < len(A)에 대해, A[i] != A[j] 또는 A[i+1] != A[j+1] 둘중 하나는 만족한다.

우와
우와아
난 조금 사파로 푼거고 이거 정해가 되게 재밌네

우선 제 전략은..
일단 1 1 2 2 3 3 ... N N 이렇게 적어두고,
이후에 N으로 시작해서 두번째 조건을 만족하도록, 그 중 최대한 큰거만 가져오자.
근데 이거 매번 찾는데 O(N)걸리면 TLE 확정이므로,
분리집합으로 사용되지 않은 가장 큰 수를 가져오자.
이러면 이유는 몰라도 된다.

그리고 정해는..
A[i] != A[j] 또는 A[i+1] != A[j+1]의 의미는,
같은 정점에서 i -> j로 가는 간선을 여러번 타지 말아라, 라는 의미이다.
즉, 이걸 만족하면서 최대한 많이 돌고 오면 1로 돌아오면 된다.
어? 근데 이건 오일러경로네?

와 정해 좀 재밌더라
내 풀이는 결국 정당성 증명은 못했는데 정해 보고 바로 이해함
이걸 그래프로본다니..
*/
var (
	N int
	U [1002][1002]int
)

func union(r, i, j int) bool {
	ip := find(r, i)
	jp := find(r, j)
	if ip == jp {
		return false
	}
	mn, mx := min(ip, jp), max(ip, jp)
	U[r][mx] = U[r][mn]
	return true
}

func find(r, i int) int {
	if U[r][i] != i {
		U[r][i] = find(r, U[r][i])
	}
	return U[r][i]
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	fmt.Fscan(br, &N)
	for i := 0; i <= N+1; i++ {
		for j := 0; j <= N+1; j++ {
			U[i][j] = j
		}
	}
	ret := make([]int, 0)
	for i := 1; i <= N; i++ {
		ret = append(ret, i)
		ret = append(ret, i)
		union(i, i-1, i)
		union(i, i, i+1)
	}
	cur := N
	for {
		nxt := find(cur, N)
		if nxt == 0 {
			break
		}
		union(cur, nxt-1, N)
		ret = append(ret, nxt)
		cur = nxt
	}
	fmt.Fprintln(bw, len(ret))
	for _, i := range ret {
		fmt.Fprint(bw, i, " ")
	}
}

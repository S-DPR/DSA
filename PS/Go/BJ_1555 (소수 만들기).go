package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
1555번 소수 만들기

N개의 수가 주어진다. +, -, +, /, (, )를 원하는만큼 사용해 소수를 만들려 한다.
가능한 가장 큰 소수와 가장 작은 소수를 구해보자. /는 두 수가 양수일 때만 쓸 수 있고, 내림 몫연산을 수행한다.

오늘도 이어지는 완탐
다른 끌리는 문제 찾긴 했는데, 누가봐도 나 '그'트리요 하고 앉아있어서 갖다버리고 딴거 구경하다 주워왔습니다.
이거는.. 그동안 봐오긴했는데 좀 귀찮아서 안하려고 했던문제.
그냥 N이 6이하라서 대충 루프 돌리면 되겠지.. 했는데.

짜잔 (2*2*2*2)+(2/2) 같은 경우가 있네요~
아하, 이거 조금 더 귀찮구나..
하면서 미리 가능한 모든 경우의 수 짜놓고 처리.
그래서 결론적으로 재귀 두변 굴려주면 됩니다. 간단하죠?

사실 더 놀란부분은, 이게 난이도 편차가 되게 크다는거. 플레3 넣은사람도 있고 골드2 넣은사람도 있고..
*/
var (
	N    int
	A    []int64
	E    []int
	V    map[int64]map[int]bool
	M, m int64
)

func loop(x int64, v int) {
	_, e := V[x]
	if !e {
		V[x] = make(map[int]bool)
	}
	if V[x][v] {
		return
	}
	V[x][v] = true
	if v+1 == 1<<N {
		isPrime := x >= 2
		for i := int64(2); i*i <= x; i++ {
			isPrime = isPrime && x%i != 0
		}
		if isPrime {
			M = max(M, x)
			m = min(m, x)
		}
		return
	}
	for i := 0; i < len(A); i++ {
		if v&E[i] != 0 {
			continue
		}
		nxtV := v | E[i]
		loop(x+A[i], nxtV)
		loop(x-A[i], nxtV)
		loop(x*A[i], nxtV)
		if x > 0 && A[i] > 0 {
			loop(x/A[i], nxtV)
		}
	}
}

func comb(x int64, vis int, cnt int) {
	_, e := V[x]
	if !e {
		V[x] = make(map[int]bool)
	}
	if V[x][vis] {
		return
	}
	V[x][vis] = true
	if cnt == 0 {
		A = append(A, x)
		E = append(E, vis)
		return
	}
	for i := 0; i < N; i++ {
		if vis&(1<<i) != 0 {
			continue
		}
		nxtV := vis | (1 << i)
		comb(x+A[i], nxtV, cnt-1)
		comb(x-A[i], nxtV, cnt-1)
		comb(x*A[i], nxtV, cnt-1)
		if x > 0 && A[i] > 0 {
			comb(x/A[i], nxtV, cnt-1)
		}
	}
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	fmt.Fscan(br, &N)
	A = make([]int64, N)
	E = make([]int, N)
	V = make(map[int64]map[int]bool)
	M = -1
	m = int64(1) << 62
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &A[i])
		E[i] = 1 << i
	}
	for cnt := 2; cnt < N; cnt++ {
		for i := 0; i < N; i++ {
			comb(A[i], 1<<i, cnt-1)
		}
	}
	V = make(map[int64]map[int]bool)
	for i := 0; i < len(A); i++ {
		loop(A[i], E[i])
	}
	if M != -1 {
		fmt.Fprintln(bw, m)
		fmt.Fprintln(bw, M)
	} else {
		fmt.Fprintln(bw, -1)
	}
}

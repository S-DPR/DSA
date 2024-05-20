package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
31857번 사탕 공장

문자열 A, B, 그리고 1이상 N이하의 정수 R이 주어진다. 아래 쿼리를 처리해보자.
S : A와 B의 1번부터 R번까지 문자열을 교환한다.
L k : k == 1이면 A를 왼쪽으로 한 번, k == 2면 B를 왼쪽으로 한 번 회전시킨다.
R k : k == 1이면 A를 오른쪽으로 한 번, k == 2면 B를 오른쪽으로 한 번 회전시킨다.
I : R을 1 늘린다.
D : R을 1 줄인다.
모든 쿼리에 대해 1 <= R <= N이 됨을 보장한다.

어휴
그냥 이중연결리스트 구현하면 AC.
끄아아악.. 이딴문제에 3시간 넘게 쳐박다니..
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N, R, Q, k int
	var F, S, q string
	fmt.Fscan(br, &N, &R, &Q)

	fmt.Fscan(br, &F, &S)
	all := []rune(F + S)
	lld := make([][2]int, 2*N)
	for i := 0; i < 2*N; i++ {
		lld[i] = [2]int{i - 1, i + 1}
	}

	lld[0][0] = N - 1
	lld[N-1][1] = 0
	lld[N][0] = 2*N - 1
	lld[2*N-1][1] = N

	first, second := 0, N
	szf, szs := first+R-1, second+R-1
	sz := R

	for i := 0; i < Q; i++ {
		fmt.Fscan(br, &q)

		switch q {
		case "S":
			n_szf := lld[szf][1]
			n_szs := lld[szs][1]
			fend := lld[first][0]
			send := lld[second][0]

			if sz < N {
				lld[szf][1] = n_szs
				lld[szs][1] = n_szf
				lld[n_szf][0] = szs
				lld[n_szs][0] = szf
				lld[first][0] = send
				lld[second][0] = fend
				lld[fend][1] = second
				lld[send][1] = first
			}
			first, second = second, first
			szf, szs = szs, szf

		case "I":
			sz++
			szf = lld[szf][1]
			szs = lld[szs][1]

		case "D":
			sz--
			szf = lld[szf][0]
			szs = lld[szs][0]

		case "L":
			fmt.Fscan(br, &k)
			if k == 1 {
				first = lld[first][1]
				szf = lld[szf][1]
			} else {
				second = lld[second][1]
				szs = lld[szs][1]
			}

		case "R":
			fmt.Fscan(br, &k)
			if k == 1 {
				first = lld[first][0]
				szf = lld[szf][0]
			} else {
				second = lld[second][0]
				szs = lld[szs][0]
			}
		}
	}

	for i := 0; i < N; i++ {
		fmt.Fprint(bw, string(all[first]))
		first = lld[first][1]
	}
	fmt.Fprintln(bw)
	for i := 0; i < N; i++ {
		fmt.Fprint(bw, string(all[second]))
		second = lld[second][1]
	}
}

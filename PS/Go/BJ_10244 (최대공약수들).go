package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
10244번 최대공약수들

길이가 N인 수열 A가 주어진다. 각 원소는 1 이상 100 이하이다.
f(l, r)이 A[l], A[l+1], ..., A[r-1], A[r]의 최대공약수를 반환한다.
{f(l, r) | 1 <= l <= r <= N}의 크기를 구해보자.

은근히 생각 많이했던 문제.
저같은경우 dp 비슷하게 풀었습니다.
특히, 전에 1억개의 원소를 가진 bool배열은 메모리가 은근 괜찮다는걸 근거로..

E[i][j] = i번째 인덱스를 볼 때, 그때까지 gcd가 j가 될 수 있는가?
문젠 이게 시간제한도 상당히 오묘했다는 점.
5초고, 그리고 반복문 쭉 보면..
100*100000*100 = 10^9..
정말 오묘하긴하죠, 시간복잡도 계산이 10의 9승이라니.

하지만 전에 시간복잡도만이 답이 아니라는것도 알았었기에,
그냥 시간초과 각오하고 갖다박았더니 AC.
4400ms라서 별로 기분은 안좋은데, 한번 정해는 어떤지 봐야할거같네요.
*/
var (
	A []int
	E [][]bool
)

func gcd(i, j int) int {
	for j != 0 {
		i, j = j, i%j
	}
	return i
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N int
	for {
		fmt.Fscan(br, &N)
		if N == 0 {
			break
		}
		A = make([]int, N)
		E = make([][]bool, N)
		for i := 0; i < N; i++ {
			E[i] = make([]bool, 101)
		}
		for i := 0; i < N; i++ {
			fmt.Fscan(br, &A[i])
			E[i][A[i]] = true
		}
		for i := 100; i >= 1; i-- {
			for j := 0; j < N; j++ {
				if !E[j][i] {
					continue
				}
				for k := 1; k <= 100; k++ {
					if j > 0 && E[j-1][k] {
						x := gcd(i, k)
						E[j-1][x] = true
					}
					if j+1 < N && E[j+1][k] {
						x := gcd(i, k)
						E[j+1][x] = true
					}
				}
			}
		}
		ret := 0
		for i := 1; i <= 100; i++ {
			flg := false
			for j := 0; j < N; j++ {
				flg = flg || E[j][i]
			}
			if flg {
				ret++
			}
		}
		fmt.Fprintln(bw, ret)
	}
}

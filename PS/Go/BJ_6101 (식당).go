package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
6101번 식당

수열에서 [L, R] 범위를 잡자.
이 부분배열 집합의 크기의 제곱이 이 범위를 커버하는데 필요한 비용이다.
모든 원소를 커버하려고 한다. 최소 비용은 몇일까?

수열 길이가 최대 40000?
이야 이거 냄새가 난다 냄새가 나, 하면서 관찰은 성공.
그 관찰은, 200개 이상의 수를 한번에 잡을 필요가 없다는 것.
k개를 다르게 할거면 반드시 그 집합은 k^2개를 넘기는 크기를 가져야 이득이 됩니다.

.. 하지만, 저 관찰 이후로 진척이 없어서 결국 답을 봤습니다.
어.. 이거 dp쓰는건 맞구나. 그런데 다음은..? 이랬는데,
다음이아니라 그 전이 문제더라고요. L[i][j]를 정의합니다.
L[i][j] = i번째가 마지막일 때, j개의 수가 들어있을 수 있는 가장 왼쪽 인덱스.

위 전처리는 NsqrtN이라는 시간복잡도로 처리가 가능하며, (j <= sqrt N)
이제 dp식에 이를 적용하여 NsqrtN의 시간복잡도로 처리할 수 있게 됩니다.

dp 이전에 관찰 및 전처리 후 처리.. 이제 dp가 점점 이상해지네..
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N, M int
	fmt.Fscan(br, &N, &M)
	A := make([]int, N+1)
	L := make([][201]int, N+1)
	dp := make([]int, N+1)
	for i := 1; i <= N; i++ {
		fmt.Fscan(br, &A[i])
		dp[i] = 1 << 30
		for j := 0; j <= 200; j++ {
			L[i][j] = -1
		}
	}
	for c := 1; c*c <= N; c++ {
		cnts := make([]int, M+1)
		for lo, hi, t := 1, 1, 0; lo <= N; {
			if hi <= N && (t < c || (t == c && cnts[A[hi]] != 0)) {
				if cnts[A[hi]] == 0 {
					t++
				}
				cnts[A[hi]]++
				hi++
			} else {
				if cnts[A[lo]] == 1 {
					t--
				}
				cnts[A[lo]]--
				lo++
			}
			if t == c && L[hi-1][t] == -1 {
				L[hi-1][c] = lo
			}
		}
	}
	for i := 1; i <= N; i++ {
		for j := 1; j*j <= N; j++ {
			if L[i][j] == -1 {
				continue
			}
			dp[i] = min(dp[i], dp[L[i][j]-1]+j*j)
		}
	}
	fmt.Println(dp[N])
}

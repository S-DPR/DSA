package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
31265번 훈련

최대 용량 K와 넣을 물건의 종류 N이 주어진다.
각 물건의 종류들은 최소 하나씩은 넣어야 한다.
각 물건의 무게가 주어진다. 최대한 채울 때 그 무게를 구하시오.

ㅋㅋ
냅색인건 바로 알았는데 '최소'를 '최대'로 봐서 틀리고,
이게 bool배열로 풀고있었는데 답이 안나와서 답지보니까 응용이 흥미롭네
dp[i] = 무게를 i로 칠 때 넣은 물건 종류의 개수.
현재 n번째 종류를 넣는 중이면, 무게가 x일 때 dp[i-x]가 n-1 이상이여야 넣을 수 있겠죠.
그런 조건으로 채워나갑니다.

와 이 냅색 진짜 흥미롭네..
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N, K, x int
	fmt.Fscan(br, &N, &K)
	C := make([]int, N)
	dp := make([]int, K+1)
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &C[i])
	}
	for i := 0; i <= K; i++ {
		dp[i] = -2
	}
	dp[0] = -1
	for i := 0; i < N; i++ {
		for j := 0; j < C[i]; j++ {
			fmt.Fscan(br, &x)
			for k := K; k-x >= 0; k-- {
				if dp[k-x] >= i-1 {
					dp[k] = i
				}
			}
		}
	}
	for i := K; i >= 0; i-- {
		if dp[i] == N-1 {
			fmt.Println(i)
			return
		}
	}
	fmt.Println(-1)
}

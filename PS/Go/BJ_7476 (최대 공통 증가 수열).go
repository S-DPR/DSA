package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
7476번 최대 공통 증가 수열

두 배열의 LCIS를 찾아보자. 역추적도 해보자.

Ruby야 제발!!!!!!!!!!!!!! 정신을차려!!!!!!!!!!!!!!!!!

오늘도 Ruby들고 깝치다가 Go로 도망쳤습니다.
시간복잡도 O(N*M^2) 겨우 만들었더니 뭐? TLE? TLE??
에휴..
아니 근데 Go로 144ms만에 되는게 루비로 3초를 갈아넣어도 안된다고?

그거랑 별개로 알고리즘 고안에 6시간은 때려넣은것같습니다.
dp를 2차원으로도 해보고 했는데 이러면 O((NM)^2)이 너무 자명해져서 못했고..
그나마 생각한게 dp를 1차원으로 두고 역추적배열 trace를 2차원으로 두는 방법.
trace 역추적에 O(N^2), 메인로직에 O(N*M^2)이 듭니다. 사실상 N=M이니까 O(N^3).

로직은 꽤 간단합니다. 그냥 O(N^2)알고리즘에서 배열 순회를 1개로 하던걸 2개로 하는거에요.
배열 2개를 순회해야하니 O(N^2), 거기 내부에서 arr[i] == brr[j]일때만 O(N^2)의 그걸 해서 O(N^3).
그런데 여기서 끝나면 이미 7시간 전에 문제 끝! 이러고 밥먹으러 갔을거고, 역추적이 문젠데..

trace[i][j]에 이전에 밟은거를 넣습니다. 그니까 이거도 O(N^2)의 역추적 그거죠. 차원이 하나 늘어났을 뿐.
이제 이 다음이 문젠데.
1. 최장수열의 길이는 dp에서의 최댓값입니다. 이를 ret이라 합시다.
2. trace[i][j] = arr의 i번째 원소 = brr의 j번째 원소이며, 이전에 몇번째 brr을 밟아왔는지 적혀있습니다.
3. arr에서 가장 나중에 나오는 brr[ret]이 최장수열의 마지막 원소가 됩니다. dp[i]는 brr의 i번째 원소에서 가능한 최대길이니까요.
4. 항상 arr에서 가장 나중에 나오는 brr[ret]을 선택합니다. 둘이 같을때만 업데이트를 진행해왔으므로 문제 없습니다.
5. 그렇게하다가 초기화상태인 부분이 나오면 break합니다.

이게 그.. 아 뭐라하지..
그냥.. 그냥 너무 어렵다..
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N, M int
	fmt.Fscan(br, &N)
	arr := make([]int, N)
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &arr[i])
	}
	fmt.Fscan(br, &M)
	brr := make([]int, M)
	for i := 0; i < M; i++ {
		fmt.Fscan(br, &brr[i])
	}
	dp := make([]int, M)
	trace := make([][]int, N)
	for i := 0; i < N; i++ {
		trace[i] = make([]int, M)
		for j := 0; j < M; j++ {
			trace[i][j] = -2
		}
	}
	for i := 0; i < N; i++ {
		for j := 0; j < M; j++ {
			if arr[i] == brr[j] {
				prv, maxLen := -1, 0
				for k := 0; k < j; k++ {
					if brr[k] < brr[j] && maxLen < dp[k] {
						maxLen, prv = dp[k], k
					}
				}
				dp[j] = maxLen + 1
				trace[i][j] = prv
			}
		}
	}
	ret, tracking := -1, -1
	for i := 0; i < M; i++ {
		if ret < dp[i] {
			ret = dp[i]
			tracking = i
		}
	}
	row, col := -1, tracking
	for i := N - 1; i >= 0; i-- {
		if arr[i] == brr[col] {
			row = i
			break
		}
	}
	stk := make([]int, 0)
	for ret != 0 {
		stk = append(stk, brr[col])
		col = trace[row][col]
		check := false
		for i := row - 1; i >= 0 && col >= 0; i-- {
			if trace[i][col] != -2 {
				row = i
				check = true
				break
			}
		}
		if !check {
			break
		}
	}
	fmt.Fprintln(bw, ret)
	for i := len(stk) - 1; i >= 0; i-- {
		fmt.Fprintf(bw, "%d ", stk[i])
	}
}

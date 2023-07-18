package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
7976번 수열

1 <= i <= n-k+1을 만족하는 모든 i와 수열 A에 대해, sum(A[i..i+k-1])을 짝수로 만들려 한다.
수열에 있는 수를 마음대로 바꿀 수 있을 때, 위 조건을 만족하는 수열을 몇 번 만에 바꿀 수 있을까?

dp.. 모르겠다..
그리디.. 모르겟다..
그냥.. 난.. 바보다...

그니까.. 대충 길이 K 수열 하나 만들고..
길이 N짜리 수열 돌면서 i%K에 해당 수가 홀수인지 짝수인지 구별해서 넣고..
나온거중에 최솟값 쏙쏙 골라보고..
고른거 다 봤더니 홀수개면 그중에 하나 뺄 수 있는거 다른거로 바꾸고..
그거 출력하면 끝..
위 원리는 결국 수열의 홀짝이 K개 단위로 주기성만 띠면 된다는 점.
K=1과 K=2는 인생 편하게 따로 처리해주면 됩니다.

아..
이상하게 이런류 문제 풀떄마다 정신이 싹싹 긁히는느낌이야
*/
func max(i, j int) int {
	if i < j {
		return j
	}
	return i
}

func min(i, j int) int {
	if i < j {
		return i
	}
	return j
}

func abs(i int) int {
	if i < 0 {
		return -i
	}
	return i
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N, K int
	fmt.Fscan(br, &N, &K)
	arr := make([]int, N)
	for i := 0; i < N; i++ {
		var j int
		fmt.Fscan(br, &j)
		arr[i] = j % 2
	}
	cntZ := make([]int, K)
	cntI := make([]int, K)
	for i := 0; i < N; i++ {
		if arr[i] == 0 {
			cntZ[i%K]++
		} else {
			cntI[i%K]++
		}
	}
	ret := 0
	plusCnt := 0
	eq := false
	for i := 0; i < K; i++ {
		ret += min(cntI[i], cntZ[i])
		if cntZ[i] < cntI[i] {
			plusCnt++
		}
		eq = eq || cntZ[i] == cntI[i]
	}
	if plusCnt&1 == 1 && !eq {
		plus := 1 << 30
		newIdx := -1
		for i := 0; i < K; i++ {
			if abs(cntI[i]-cntZ[i]) < plus {
				plus = abs(cntI[i] - cntZ[i])
				newIdx = i
			}
		}
		ret += max(cntZ[newIdx], cntI[newIdx])
		ret -= min(cntZ[newIdx], cntI[newIdx])
	}
	if K == 1 {
		ret = cntI[0]
	} else if K == 2 {
		ret = min(cntI[0]+cntI[1], cntZ[0]+cntZ[1])
	}
	fmt.Fprintln(bw, ret)
}

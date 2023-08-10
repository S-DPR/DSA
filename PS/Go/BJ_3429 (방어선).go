package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
)

/*
3429번 방어선

수열이 주어진다. 수열의 연속한 일부를 한 번 제거할 수 있다고 할 때, LIS의 길이를 구해보자.

하아..
대충 입력 조금 입력받고 dp[i][0]는 제거하지 않은 LIS길이를, dp[i][1]에는 제거한걸 넣었는데..
넣다보니.. 갑자기 엄습해오는 불안감..
이거 세그트리 각이네 세그트리 각~

그래프가 세그트리 점수를 넘은지 단 하루! 만에 다시 세그트리가 36점을 얻고 또 올랐습니다. 와~
그래프로 36점 얻으려면 플레5 9개인데 아..
절망적이다..

저는 좌표압축+세그트리+dp를 써서 풀었습니다.
수 arr[i]에 대해, 1~arr[i]중 제거하지 않은 LIS길이를 얻어야해서요..
그거만 처리해주면 AC. 세그트리 이제 진짜 자연스럽게 스며드네..
한 번 틀린거는 세그트리 구현 미스. 너무슬프다..
*/

type segT struct {
	arrSize int
	arr     []int
}

func max(i, j int) int {
	if i < j {
		return j
	}
	return i
}

func segTMaker(size int) *segT {
	ret := segT{size, make([]int, (size<<1)+1)}
	return &ret
}

func (segT *segT) update(idx, val int) {
	N := segT.arrSize
	for idx += N; idx > 0; idx >>= 1 {
		segT.arr[idx] = max(segT.arr[idx], val)
	}
}

func (segT *segT) query(l, r int) int {
	N := segT.arrSize
	ret := 0
	for l, r = l+N, r+N; l <= r; l, r = l>>1, r>>1 {
		if l&1 == 1 {
			ret = max(ret, segT.arr[l])
			l++
		}
		if r&1 == 0 {
			ret = max(ret, segT.arr[r])
			r--
		}
	}
	return ret
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var T int
	fmt.Fscan(br, &T)
	for i := 0; i < T; i++ {
		var L int
		fmt.Fscan(br, &L)
		arr := make([]int, L)
		items := make([]int, 0)
		dp := make([][2]int, L)
		unique := make(map[int]int)
		comp := make(map[int]int)
		for j := 0; j < L; j++ {
			fmt.Fscan(br, &arr[j])
			_, e := unique[arr[j]]
			if !e {
				unique[arr[j]] = len(unique)
			}
			dp[j] = [2]int{1, 1}
		}
		for k, _ := range unique {
			items = append(items, k)
		}
		sort.Ints(items)
		for _, j := range items {
			_, e := comp[j]
			if !e {
				comp[j] = len(comp) + 1
			}
		}
		seg := segTMaker(len(comp))
		seg.update(comp[arr[0]], 1)
		for j := 1; j < L; j++ {
			if arr[j-1] < arr[j] {
				dp[j][0] = dp[j-1][0] + 1
				dp[j][1] = dp[j-1][1] + 1
			}
			seg.update(comp[arr[j]], dp[j][0])
			idx := seg.query(1, comp[arr[j]]-1)
			dp[j][1] = max(dp[j][1], idx+1)
		}
		ret := 0
		for j := 0; j < L; j++ {
			ret = max(ret, dp[j][0])
			ret = max(ret, dp[j][1])
		}
		fmt.Fprintln(bw, ret)
	}
}

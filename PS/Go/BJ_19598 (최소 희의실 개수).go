package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
)

/*
19598번 최소 회의실 개수

회의의 개수 N이 주어지고, 이후 N개의 줄에 대해 각 회의 시작/끝시간이 공백으로 구분되어 주어진다.
회의실이 최소 몇개가 있어야 모든 회의를 진행할 수 있나 계산해보자.
회의실 하나당 하나의 회의만 진행할 수 있다.

스위스위스위핑
이상하게 푸는문제는 전부 G5~G4네요
G3부터는 뭔가 강력한 결계가 쳐져있는 느낌이에요.

그냥 이건 스위핑을 하고, 그중 최댓값을 출력하면 끝나는 문제였습니다.
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	max := func(i, j int) int {
		if i > j {
			return i
		}
		return j
	}

	var n int
	fmt.Fscan(br, &n)
	arr := make(map[int]int)
	for i := 0; i < n; i++ {
		var x, y int
		fmt.Fscan(br, &x, &y)
		arr[x]++
		arr[y]--
	}
	sortArr := make([]int, 0)
	for key := range arr {
		sortArr = append(sortArr, key)
	}
	sort.Ints(sortArr)

	cur, M := 0, 0
	for _, i := range sortArr {
		cur += arr[i]
		M = max(M, cur)
	}
	fmt.Fprintln(bw, M)

	bw.Flush()
}

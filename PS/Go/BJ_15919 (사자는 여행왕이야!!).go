package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
)

type data struct {
	start, end int
}

/*
15919번 사자는 여행왕이야!!

숫자 N과 M이 주어진다.
이후 M개의 줄에 구간 이 시작과 끝점으로, S E의 형태로 공백으로 구분되어 두 정수로 주어진다. (1 <= S <= E <= N)
이 때, M개의 선택지중 구간이 겹치지 않게 원하는만큼 고르고 '선택되지 않은 구간 중 가장 긴 길이'를 구해보자.
위 방식으로, 모든 조합으로 골라보았을 때 그 길이의 최솟값을 구해보자.

DPDPDPDPDPDP한 DPDPDPDPPP문제입니다.
전엔 이게 머여 하고 넘겼다가, 답지 보고, 이게 뭔소리여하고 끄고.
기억에서 잊고 지내다가 갑자기 생각나서 다시 풀어봤습니다.

풀이 방법은 이렇습니다.
먼저, 입력받은 M개의 구간을 정렬합시다.
이후 하나씩 for문을 돌며, 아래 과정을 반복합니다.
  - 자신 이전 인덱스를 돌면서 다음 조건을 만족하는 인덱스를 찾아봅시다.
  - 자신의 start값이 대상의 end값보다 큽니다.
  - 현재 구한 최대길이와, max(대상의 현재까지의 최대 길이, 자신의 end값-대상의 start값-1)이 더 작은 값을 고릅니다.
  - 결론적으로 가장 긴 빈 구간 길이가 가장 짧은 인덱스를 찾아낼 수 있습니다.

만약에 그런 인덱스를 찾지 못했다면 현재 최대길이를 [자신의 start값-1]로 갱신합니다.
찾았다면 현재 최대길이를 [max(대상의 최대 길이, 자신의 start값 - 대상의 end값 - 1)]로 갱신합니다.
찾은경우는, 갱신하면서 이전 값이 사용이 되었다는 체크를 남겨줍니다.

마지막으로 체크한 배열을 돌려보면서,
True (사용되었음) 상태면 continue하고,
False면 [max(N - 자신의 end값, 자신의 현재 최대 길이)]으로 새로 갱신한 뒤 현재 나온 답과 비교하여 작은 값을 집어넣습니다.
마지막으로, 그 값을 출력합니다.
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	min := func(i, j int) int {
		if i > j {
			return j
		}
		return i
	}
	max := func(i, j int) int {
		if i > j {
			return i
		}
		return j
	}

	var n, m int
	fmt.Fscan(br, &n, &m)
	arr := make([]data, m)
	for i := 0; i < m; i++ {
		var start, end int
		fmt.Fscan(br, &start, &end)
		arr[i] = data{start, end}
	}
	sort.Slice(arr, func(i, j int) bool {
		if arr[i].start < arr[j].start {
			return true
		}
		if arr[i].start > arr[j].start {
			return false
		}
		if arr[i].end < arr[j].end {
			return true
		}
		return false
	})

	brr := make([]int, m)
	selected := make([]bool, m)
	for i := 0; i < m; i++ {
		brr[i] = 1_000_000_000
	}

	for idx := 0; idx < m; idx++ {
		getMinIdx := idx
		calc := brr[idx]
		for i := 0; i < idx; i++ {
			next := max(brr[i], arr[idx].start-arr[i].end-1)
			if arr[i].end < arr[idx].start && next < calc {
				getMinIdx = i
				calc = next
			}
		}
		if getMinIdx == idx {
			brr[idx] = arr[idx].start - 1
		} else {
			selected[getMinIdx] = true
			brr[idx] = max(arr[idx].start-arr[getMinIdx].end-1, brr[getMinIdx])
		}
	}

	ans := 1_000_000_000
	for idx, item := range selected {
		if item {
			continue
		}
		brr[idx] = max(brr[idx], n-arr[idx].end)
		ans = min(brr[idx], ans)
	}

	fmt.Fprintln(bw, ans)

	bw.Flush()
}

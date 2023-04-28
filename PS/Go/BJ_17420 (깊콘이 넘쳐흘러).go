package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
)

/*
17420번 깊콘이 넘쳐흘러

기프티콘이 N개 있고, 각각 정확히 K일에 쓸 것이다.
하지만 기프티콘 유효기간이 K일보다 적을 수도 있다.
그렇기에 기프티콘 연장을 해야하는데, 이 횟수를 최소로 하려한다.
단, 반드시 처음 사용하는 기프티콘은 사용하지 않은 모든 기프티콘중 남은 유효기간이 가장 짧아야한다.
기프티콘 연장 시 30일씩 연장이 될 때, 연장 횟수의 최소를 구해보자.

태그를 안 순간 끝나버린 문제
..라고 난이도 기여에 적혀있던데, 맞는것같습니다.
그리디 난이도 원천은 그리디인걸 모를때 나오는거니까요..
그리고 질문게시판에서 문제 이해를 99% 해버려서.. 이부분도 컸던것같습니다.

사용일이 같은 날짜를 한 리스트로 묶고, 그걸 정렬한 뒤 하나씩 꺼내와,
이전에 사용한 것중 가장 긴 남은기간을 토대로 연장횟수를 모두 계산한 뒤,
마지막에 가장 긴 남은 기간을 갱신하면 됩니다.
앞에서 뭐.. 연장하고 나온 날짜를 모두 계산해봤더니 52 48 59 이렇게 나왔으면 59를 '가장 긴 남은 기간'으로 갱신하면 돼요.
사용 날짜가 같을 떄 처리를 못하면 어려워지는 문제였습니다.
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()
	max := func(i, j int64) int64 {
		if i > j {
			return i
		}
		return j
	}

	var N int
	fmt.Fscan(br, &N)
	arr := make([]int64, N)
	brr := make(map[int64][]int64)
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &arr[i])
	}
	for i := 0; i < N; i++ {
		var T int64
		fmt.Fscan(br, &T)
		_, e := brr[T]
		if !e {
			brr[T] = make([]int64, 0)
		}
		brr[T] = append(brr[T], arr[i])
	}

	times := make([]int64, 0)
	for i := range brr {
		times = append(times, i)
	}
	sort.Slice(times, func(i, j int) bool {
		return times[i] < times[j]
	})
	res := int64(0)
	maxR := int64(0)
	for _, i := range times {
		item := int64(0)
		for _, j := range brr[i] {
			remainT, useT := j, i
			res += (max(0, maxR-remainT) + 29) / 30
			remainT += (max(0, maxR-remainT) + 29) / 30 * 30
			res += (max(0, useT-remainT) + 29) / 30
			remainT += (max(0, useT-remainT) + 29) / 30 * 30
			item = max(item, remainT)
		}
		maxR = max(maxR, item)
	}
	fmt.Fprintln(bw, res)
}

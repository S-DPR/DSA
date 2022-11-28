package main

import (
	"bufio"
	"fmt"
	"os"
)
/*
2872번 우리집엔 도서관이 있어

1부터 N까지의 수가 중복 없이 있는 수열 A가 주어진다.
수열 A에서 하나의 인덱스를 뽑아 맨 앞(인덱스 0)으로 옮기는 행위를 최소한으로 하여 수열을 정렬할 때, 몇 번 이 행위를 해야 하는지 구하시오.

그리디는 그리디인걸 모를 때 제일 무서운 법입니다.
이게 뭔문젠가.. 하고 있었는데,
그냥 수열의 맨 뒤부터 제대로 정렬되어있는 개수를 세면 됩니다..
그니까 n보다 n-1이 앞, n-2가 그 앞, n-3이 그 앞..
그렇게해서 인덱스 0까지 갔을 때 몇 개가 저렇게 정렬이 되어있나 보면,
쉽게 풀 수 있습니다.
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	var n int
	fmt.Fscan(br, &n)
	arr := make([]int, n)
	for i := 0; i < n; i++ {
		fmt.Fscan(br, &arr[i])
	}
	for i := n - 1; i >= 0; i-- {
		if arr[i] == n {
			n--
		}
	}
	fmt.Fprintln(bw, n)

	bw.Flush()
}

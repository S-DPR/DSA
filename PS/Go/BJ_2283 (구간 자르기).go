package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
2283번 구간 자르기

선분의 시작점과 끝점이 N개 주어진다.
두 구간을 정하여, 그 구간에 내의 길이가 K가 되는 값을 찾아보자.
만약 그런 구간이 여러개가 있다면, 오름차순으로 가장 앞에 오는 구간을 출력하자.

스위핑 + 투포인터 문제입니다.
스위핑을 제대로 써본건 처음인데, 잘 돼서 다행이네요.
투포인터는 누적합 구하는데 사용됩니다.
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	//bw := bufio.NewWriter(os.Stdout)

	var n, k int
	fmt.Fscan(br, &n, &k)
	arr := make([]int, 1000001)

	for i := 0; i < n; i++ {
		var s, e int
		fmt.Fscan(br, &s, &e)
		arr[s] += 1
		arr[e] -= 1
	}
	for i := 1; i <= 1000000; i++ {
		arr[i] += arr[i-1]
	}
	s, e, acc := 0, 0, arr[0]
	for s <= 1000000 {
		if acc < k && e < 1000000 {
			e++
			acc += arr[e]
		} else {
			acc -= arr[s]
			s++
		}
		if acc == k {
			fmt.Printf("%d %d", s, e+1)
			return
		}
	}
	fmt.Print("0 0")
}

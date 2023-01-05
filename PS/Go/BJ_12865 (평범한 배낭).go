package main

import (
	"bufio"
	"fmt"
	"os"
)
/*
12865번 평범한 배낭

물품의 개수 N과 최대 용량 M이 주어진다.
이후 N줄에 각 물품의 차지용량 W와 얻을 수 있는 가치 V가 주어진다.
최대 용량을 넘지않는 선에서, 최대의 가치를 출력해보자.

DPDP한 냅색문제입니다.
웰-노운 그 자체, 그저 배낭문제의 기본중의 기본.
저는 1차원 배열로 풀었습니다. 이제 인터넷으로 2차원 공부할예정..
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

	var N, M int
	fmt.Fscan(br, &N, &M)
	arr := make([]int, M+1)
	for i := 0; i < N; i++ {
		var W, V int
		fmt.Fscan(br, &W, &V)
		for j := M - W; j >= 0; j-- {
			arr[j+W] = max(arr[j+W], arr[j]+V)
		}
	}

	answer := 0
	for i := 0; i <= M; i++ {
		answer = max(answer, arr[i])
	}
	fmt.Fprintln(bw, answer)

	bw.Flush()
}

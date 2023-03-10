package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
25908번 수열의 합

두 수 S, T가 주어진다.
S와 T 사이의 어떤 수 i에 대해, -1을 i의 약수로 제곱하자.
그리고 그 합을 a(i)라고 할 때, S와 T 사이 모든 수의 a(i)의 합을 구해보자.

그냥 간단한 문제입니다.
친구만나러 가야해서 쉬운거 푼거도 있고..
그냥 S-1, T 각각에 나눈 몫에 대해 홀/짝을 다르게 구분해 값을 얻고,
t-s를 하면 끝납니다.
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	var S, T int
	fmt.Fscan(br, &S, &T)
	S -= 1
	s := 0
	for i := 1; i <= S; i++ {
		if i&1 == 0 {
			s += S / i
		} else {
			s -= S / i
		}
	}

	t := 0
	for i := 1; i <= T; i++ {
		if i&1 == 0 {
			t += T / i
		} else {
			t -= T / i
		}
	}
	fmt.Fprintln(bw, t-s)

	bw.Flush()
}

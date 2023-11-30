package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
14860번 GCD 곱

N, M이 주어질 때, gcd(1, 1)*gcd(2, 1)*...*gcd(N, 1)*gcd(N, 2)*...*gcd(N, M)을 구해보자.

적당히 파이썬으로 N = 10, M = 10 만들고 격자무늬 규칙이 생기는걸 파악한 뒤 푼 문제.
N//i*M//i를 계산하는데, //가 먼저 되는줄알고 삽질 좀 했습니다..
여하튼 대충 분할정복 거듭제곱과 함께 격자무늬규칙을 따라가면 됩니다.
이정도면 쉬운플레4 수학!
*/
func min(i, j int64) int64 {
	if i < j {
		return i
	}
	return j
}

func pow(x, p, mod int64) int64 {
	ret := int64(1)
	for p > 0 {
		if p&1 == 1 {
			ret *= x
			ret %= mod
		}
		p >>= 1
		x *= x
		x %= mod
	}
	return ret
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N, M int64
	fmt.Fscan(br, &N, &M)
	K := min(N, M)
	P := make([]int64, 0)
	A := make([]int64, K+1)
	for i := int64(0); i <= K; i++ {
		A[i] = i
	}
	for i := int64(2); i <= K; i++ {
		if A[i] == i {
			P = append(P, i)
		}
		for _, j := range P {
			if i*j > K {
				break
			}
			A[i*j] = j
			if i%j == 0 {
				break
			}
		}
	}
	mod := int64(1_000_000_007)
	ret := int64(1)
	for _, i := range P {
		p := i
		for (N/i)*(M/i) > 0 {
			ret *= pow(p, (N/i)*(M/i), mod)
			ret %= mod
			i *= p
		}
	}
	fmt.Fprintln(bw, ret)
}

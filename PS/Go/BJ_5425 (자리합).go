package main

/*
5425번 자리합

A부터 B까지 자리합을 구해보자.
A = 28, B = 31이라면,
2+8+2+9+3+0+3+1을 구하면 된다.

1. 이런 문제는 항상 1~B - 1~A-1이더라
 -> 경험 정답
2. 이런 문제는 항상 자릿수단위로 계산하더라
 -> 경험 정답
결국 빅데이터로 푼 문제.
그런데도 솔직히 음..
로직은 바로 떠올랐는데 구현하는데 시간이 쪼금 걸렸습니다.
쉽지않네..
*/
import (
	"bufio"
	"fmt"
	"os"
)

func calc(x int64) int64 {
	if x <= 0 {
		return 0
	}
	ret := int64(0)
	for i := int64(1); i <= 9; i++ {
		for prod, k := int64(1), x; prod <= x; prod, k = prod*10, k/10 {
			ret += x / prod * (prod / 10) * i
			if k%10 > i {
				ret += prod / 10 * i
			} else if k%10 == i {
				ret += min(prod/10, x%(prod/10)+1) * i
			}
		}
	}
	return ret
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var T int
	var u, v int64
	fmt.Fscan(br, &T)
	for i := 0; i < T; i++ {
		fmt.Fscan(br, &u, &v)
		fmt.Println(calc(v) - calc(u-1))
	}
}

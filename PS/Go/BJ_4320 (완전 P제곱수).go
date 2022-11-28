package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
4320번 완전 P제곱수

모든 수 x는 x = a^p (1 <= p)로 나타낼 수 있다.
x가 주어질 때 최대의 p를 구하시오.
(-INT_MAX <= x <= INT_MAX)

어렵게 생각하면 절대 못푸는 문제입니다.
굳이 수학지식을 사용하는거라면, 소인수분해의 유일성이겠죠..?

그냥 x에 대해 sqrt(x)까지의 수로 나누어떨어지면 계속 나눠봅니다.
나누다가 1이라는 결과가 나오면 그건 완전p제곱수입니다. 이때 p가 답입니다.
단, 음수의 경우 p가 홀수여야합니다. 짝수일경우 1이 최대의 p입니다.
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	for {
		var n int
		fmt.Fscan(br, &n)
		if n == 0 {
			break
		}
		if n == 1 {
			fmt.Fprintln(bw, 1)
			continue
		}
		cnt := 1
		minus := false
		if n < 0 {
			minus = true
			n *= -1
		}
		for i := 2; i*i <= n; i++ {
			p, tmp := 0, n
			for ; tmp%i == 0 && tmp > 1; p++ {
				tmp /= i
			}
			// go는 유난히 이렇게 하는게 훨씬 보기 좋더라고요. C보다 심한것같습니다.
			if tmp != 1 {
				continue
			}
			if minus && p&1 == 0 {
				continue
			}
			cnt = p
			break
		}
		fmt.Fprintln(bw, cnt)
	}
	bw.Flush()
}

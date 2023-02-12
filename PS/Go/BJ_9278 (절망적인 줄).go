package main

import (
	"bufio"
	"fmt"
	"io"
	"os"
)
/*
9278번 절망적인 줄

50원짜리를 들고있는 사람 N/2명, 100원짜리를 들고있는 사람 N/2명이 있다.
이중 몇 명은 절대 자리를 바꾸려 하지않고, 이를 각각 (, )로 표기한다.
관리인은 거스름돈을 주려는데, 초기에 갖고있는 50원짜리가 없어 50원을 낸 사람들의 동전으로 거스름돈을 주려한다.
줄의 상태가 주어질 때, 거스름돈을 부족함 없이 줄 수 있는 경우의 개수를 구해보자.
단, 뒤 6자리만 출력하자.

DP 또 너야?
진짜 온 세상이 DP다..
보고나서 경우의수? 조합만으론 까다롭겠는데? dp네? << 이 흐름대로 타고내려갔습니다.
이거도 빅데이터로 푼거긴한데..

dp[i][j]의 뜻은 'i번째 사람일 때 50원짜리가 j개 남는 경우의 수' 입니다.
그리고 j는 1개가 증가하거나, 감소하는 방향으로만 이루어지기때문에 i-1번째 사람의 경우에서 j-1, j+1의 경우만 끌고오면 됩니다.
사실상 50원을 (, 100원을 )로 쳐서 괄호문제로 바꾸면 같은 문제가 되는데, 그런 문제가 또 있긴하네요.

덤으로 Ruby와 Swift는 똑같이 풀었지만 이상하게 시간초과가 나더라고요.. 왤까요..?
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	for {
		var str string
		_, e := fmt.Fscan(br, &str)
		if e == io.EOF {
			break
		}
		half := len(str) / 2
		dp := make([][]int, len(str))
		for i := 0; i < len(str); i++ {
			dp[i] = make([]int, half+1)
		}
		if str[0] != ')' {
			dp[0][1] = 1
		}
		for i := 1; i < len(str); i++ {
			for j := 0; j <= half; j++ {
				if j != 0 && str[i] != ')' {
					dp[i][j] += dp[i-1][j-1]
				}
				if j != half && str[i] != '(' {
					dp[i][j] += dp[i-1][j+1]
				}
				dp[i][j] %= 1_000_000
			}
		}
		fmt.Fprintln(bw, dp[len(str)-1][0]%1_000_000)
	}

	bw.Flush()
}

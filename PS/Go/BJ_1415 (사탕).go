package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
1415번 사탕

10000 이하의 음이 아닌 정수 N개가 있다.
수를 원하는만큼 집합에 넣어, 집합에 있는 수를 모두 더한 값을 소수로 만들려 한다.
가능한 집합의 총 개수를 구하시오. 집어온 인덱스가 달라도 수가 같으면 같은 수이다.

수학배낭 또 왔다

수가 10000이라 생각 못했는데, N도 그만큼 작아서 배낭이 될거라는 생각을 못했고.
그래도 조금만 더 생각해봤으면 배낭 생각할 수도 있었을거같은데, 태그본거 아쉽네..

또, 배낭의 중복처리는 처음해봤습니다. 흥미롭네.. 최대 K개만 수용하도록 중복처리라..
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N int
	fmt.Fscan(br, &N)

	MAX := 10000 * N
	sieve := make([]bool, MAX+1)
	for i := 2; i <= MAX; i++ {
		sieve[i] = true
	}
	for i := 2; i*i <= MAX; i++ {
		for j := i + i; sieve[i] && j <= MAX; j += i {
			sieve[j] = false
		}
	}

	inp := make(map[int]int)
	inp[0]++
	dp := make([]int64, MAX+1)
	for i := 0; i < N; i++ {
		var item int
		fmt.Fscan(br, &item)
		inp[item]++
	}
	dp[0] = 1
	for key, val := range inp {
		if key == 0 {
			continue
		}
		vis := make(map[int]int64)
		for r := 0; r < val; r++ {
			for i := MAX; i >= key; i-- {
				if dp[i-key] == 0 {
					continue
				}
				dp[i] += dp[i-key] - vis[i]
				vis[i] = dp[i-key]
			}
		}
	}
	ret := int64(0)
	for i := 2; i <= MAX; i++ {
		if sieve[i] {
			ret += dp[i] * int64(inp[0])
		}
	}
	fmt.Fprintln(bw, ret)
}

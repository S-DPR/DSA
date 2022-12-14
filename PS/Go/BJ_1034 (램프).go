package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
1034번 램프

N과 M이 주어진다.
이후 N개의 줄에, M개의 비트로 이루어진 수가 주어진다.
마지막으로 k가 주어진다.
주어진 N개의 줄에서, t번 비트를 모두 반전시킬 수 있다.
어떤 열을 정확히 k번 반전시켜, 모든 비트가 1인 것이 최대의 개수가 되게 하고, 그 수를 출력하자.

아이디어는 좀 쉽게 떠올렸다 생각하는데..
println과 fmt.Fprintln에 농락당해 틀렸..

맵에 N개의 줄을 키로 하여 T에 그 개수를 적어줍시다.
이후 각각의 키에 있는 0의 개수를 세고, k와 0의 개수가 둘 다 홀/짝수인지 검사합니다.
둘 다 홀/짝수라면 ans에 ans와 T[key]의 값을 비교하여 큰 값을 쓰고,
마지막에 출력합니다.
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
	ans := 0

	var row, col, k int
	fmt.Fscan(br, &row, &col)
	M := make(map[string]int)
	for i := 0; i < row; i++ {
		var tmp string
		fmt.Fscan(br, &tmp)
		M[tmp]++
	}

	fmt.Fscan(br, &k)
	for key := range M {
		count := 0
		for i := 0; i < col; i++ {
			if key[i] == '0' {
				count++
			}
		}
		if (count <= k) && ((k & 1) == (count & 1)) {
			ans = max(ans, M[key])
		}
	}
	fmt.Fprintln(bw, ans)

	bw.Flush()
}

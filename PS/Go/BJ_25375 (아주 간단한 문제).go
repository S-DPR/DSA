package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
25375번 아주 간단한 문제

쿼리의 개수 Q가 주어진다.
이후 각 쿼리마다 a, b가 주어진다.
gcd(x, y) = a, x+y = b를 만족하는 x, y가 있을경우 1, 없을경우 0을 각 쿼리마다 출력하시오.

gcd(x, y) = a이므로 어떤 자연수 n, m에 대해
x = an, y = am이 성립합니다.
이를 x+y = an+am으로 고칠 수 있고,
x+y = a(n+m) == b = a(n+m) == b/a = n+m이 됩니다.
n과 m은 모두 자연수이므로 둘을 더한 결과값도 자연수가 됩니다.
즉, b/a는 실수가 아닌 자연수로 표현할 수 있어야 합니다. (조건 1)

n, m이 자연수이므로 둘의 최솟값은 1입니다.
n+m은 최소 2의 값을 가지므로 b/a는 2 이상의 값을 가져야합니다. (조건 2)

두 조건을 합쳐 b/a가 2 이상의 자연수일경우 1, 아닐경우 0을 출력하면 됩니다.
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	var Q int
	fmt.Fscan(br, &Q)
	for i := 0; i < Q; i++ {
		var x, y int
		fmt.Fscan(br, &x, &y)
		if y%x == 0 && y/x != 1 {
			fmt.Fprintln(bw, 1)
		} else {
			fmt.Fprintln(bw, 0)
		}
	}

	bw.Flush()
}

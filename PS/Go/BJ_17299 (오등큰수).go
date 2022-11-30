package main

import (
	"bufio"
	"fmt"
	"os"
)
/*
17299번 오등큰수

수열 A가 주어진다.
수열의 모든 원소에 대해, 자신의 오른쪽에 있는 수 중 자기보다 더 많이 나오는 수를 출력하시오.
어떤 원소에 대해 그런 수가 없는 경우 -1을 대신 출력한다.

럭키 오큰수.
솔직히 아이디어 거의 오큰수꺼랑 비슷합니다.
오큰수를 풀어봐서그런가, 난이도는 쉽습니다.
counting-sort와 오큰수를 융합하면 금방 풀려요.
 */
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	var n int
	fmt.Fscan(br, &n)
	arr := make([]int, n)
	cnt := make([]int, 1000001)
	for i := 0; i < n; i++ {
		fmt.Fscan(br, &arr[i])
		cnt[arr[i]]++
	}

	stack := make([]int, 0)
	ans := make([]int, n)
	for i := 0; i < n; i++ {
		ans[i] = -1
		for len(stack) >= 1 {
			pop := stack[len(stack)-1]
			if cnt[arr[pop]] < cnt[arr[i]] {
				ans[pop] = arr[i]
				stack = stack[:len(stack)-1]
			} else {
				break
			}
		}
		stack = append(stack, i)
	}
	for i := 0; i < n; i++ {
		fmt.Fprintf(bw, "%d ", ans[i])
	}

	bw.Flush()
}

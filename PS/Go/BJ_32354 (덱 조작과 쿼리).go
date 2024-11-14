package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
32354번 덱 조작과 쿼리

다음 쿼리가 Q개 주어진다. 처리해보자.
  - push x    : x를 스택에 넣는다.
  - pop       : 가장 위에 있는 값을 스택에서 뺀다.
  - restore x : x번째 쿼리 이후 상태로 돌아간다.
  - print     : 현재 스택에 있는 값을 합한 값을 출력한다.

옛날에 푼 문제랑 같아서 그냥 슥슥 보면서 풀었습니다.
연결리스트? 스택? 뭐 상관 없나?
gpt는 스택이라고 하네요. 뭐 그럼 스택이겠죠

prv 관리를 어떻게 하냐가 제일 관건인 문제였습니다.
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N, i int
	var x int64
	var s string
	fmt.Fscan(br, &N)
	kth := make([]int64, 0)
	sum := make([]int64, 0)
	prv := make([]int, 0)
	kth = append(kth, 0)
	sum = append(sum, 0)
	prv = append(prv, 0)
	for n := 1; n <= N; n++ {
		fmt.Fscan(br, &s)
		switch s {
		case "push":
			fmt.Fscan(br, &x)
			sum = append(sum, sum[len(sum)-1]+x)
			kth = append(kth, x)
			prv = append(prv, n-1)
		case "pop":
			last := prv[len(prv)-1]
			sum = append(sum, sum[len(sum)-1]-kth[len(kth)-1])
			kth = append(kth, kth[last])
			prv = append(prv, prv[last])
		case "restore":
			fmt.Fscan(br, &i)
			sum = append(sum, sum[i])
			kth = append(kth, kth[i])
			prv = append(prv, prv[i])
		case "print":
			last := len(sum) - 1
			fmt.Fprintln(bw, sum[last])
			sum = append(sum, sum[last])
			kth = append(kth, kth[last])
			prv = append(prv, prv[last])
		}
	}
}

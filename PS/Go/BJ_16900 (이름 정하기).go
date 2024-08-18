package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
16900번 이름 정하기

주어진 문자열 S가 N번 나오는 가장 짧은 문자열의 길이를 구해보자.

어제 KMP 배운김에 문제 뒤적이다가 잡은 문제.
fail배열의 의미를 생각해보면..
가장 뒤에있는 애가 앞에 접두사랑 얼마나 같은지가 되고..
그정도는 이제 아껴서 쓸 수 있으니 그 길이만 N-1번 빼주면 됩니다.
실패함수 의미 아니까 되게 쉬워지네..
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var s string
	var S []rune
	var N int64
	fmt.Fscan(br, &s, &N)
	S = []rune(s)
	ln := len(S)
	fail := make([]int64, ln)
	for i, j := 1, int64(0); i < ln; i++ {
		for j > 0 && S[i] != S[j] {
			j = fail[j-1]
		}
		if S[i] == S[j] {
			j++
			fail[i] = j
		}
	}

	fmt.Println(int64(ln)*N - fail[ln-1]*(N-1))
}

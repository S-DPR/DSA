package main

/*
6143번 문자열 생성 2

문자열 S가 주어진다.
S의 앞 뒤 하나를 떼서 초기엔 빈 문자열인 T 뒤쪽에 붙일 수 있다.
이 때, 가능한 문자열 T중 가장 앞에 있는 문자열을 구해보자.

아니 N이 30000인데 O(N^2)이 정해라고?
이거 좀 잘못됨
접미사배열로 O(NlogN)이 된다고하는데 O(N^2)이 뚫린다니..
*/
import (
	"bufio"
	"fmt"
	"os"
)

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N int
	fmt.Fscan(br, &N)
	S := make([]string, N)
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &S[i])
	}
	for lo, hi, kth := 0, N-1, 0; lo <= hi; kth++ {
		left := true
		for j := 0; lo+j <= hi-j; j++ {
			if S[lo+j] == S[hi-j] {
				continue
			}
			left = S[lo+j] < S[hi-j]
			break
		}
		if left {
			fmt.Fprintf(bw, "%s", S[lo])
			lo++
		} else {
			fmt.Fprintf(bw, "%s", S[hi])
			hi--
		}
		if kth%80 == 79 {
			fmt.Fprintln(bw)
		}
	}
}

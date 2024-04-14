package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
13558번 등차수열의 개수

1 <= i < j < k <= N인 (i, j, k)에 대해,
A[i]+A[k] = 2A[j]를 만족하는 (i, j, k)의 개수를 구해보자.

슬쩍 답지 본 문제.
참..
이게 그니까 어..
시간복잡도 계산해보면 대충 30억이 나오는데..
이게 돌아갈거라고 믿는게 골드2 난이도..

배열로 바꾼건 걍 답지 보고 바꾸긴했는데,
아이디어 자체는 그래도 맞추긴했습니다.
근데 저는 map으로하면 더 빠를줄알았죠..
현실은 해시때매 더 느려졌나봐요..

근데 코드 진짜 깔끔해서 좋다. C++이 저래서 진짜 좋은데.
테크닉도 나름 몇개 얻어간거같아요.
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N int
	fmt.Fscan(br, &N)
	A := make([]int, N)
	cnt := [2][30001]int64{}
	L, R := cnt[0], cnt[1]
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &A[i])
		R[A[i]]++
	}
	ret := int64(0)
	R[A[0]]--
	for i := 1; i < N; i++ {
		L[A[i-1]]++
		R[A[i]]--
		ret += L[A[i]] * R[A[i]]
		for j := 1; 1 <= A[i]-j && A[i]+j <= 30000; j++ {
			ret += L[A[i]+j] * R[A[i]-j]
			ret += L[A[i]-j] * R[A[i]+j]
		}
	}
	fmt.Println(ret)
}

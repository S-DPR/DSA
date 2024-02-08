package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
1441번 나누어 질까

L 이상 R 이하의 수 중 A에 있는 원소로 나누어지는 수의 개수를 구해보자.

간단한 포함배제문제
이왜골1

사실 포함배제 까먹어서 옛날에 푼거 가져오긴했는데..
포함배제 이제 보니까 머 5문제정도 풀었더만.. 까먹을만했네
어쨌든 간단한 문제였습니다.
그냥 진짜 포함배제 쓰면 끝나요.
전부 소수 아니어도 되나? 했는데 진짜 아니어도 되네요..
*/
func gcd(x, y int64) int64 {
	if y == 0 {
		return x
	}
	return gcd(y, x%y)
}

func lcm(x, y int64) int64 {
	return x / gcd(x, y) * y
}

func combination(x int64, idx int, use int, limit int, source *[]int64, ret *[]int64) {
	if x > 1_000_000_000 {
		return
	}
	if use == limit {
		*ret = append(*ret, x)
		return
	}
	for i := idx; i < len(*source); i++ {
		combination(lcm(x, (*source)[i]), i+1, use+1, limit, source, ret)
	}
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N int
	var L, R int64
	fmt.Fscan(br, &N, &L, &R)
	A := make([]int64, N)
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &A[i])
	}
	ret := int64(0)
	for i, j := int64(1), 1; j <= len(A); i, j = -i, j+1 {
		B := make([]int64, 0)
		combination(1, 0, 0, j, &A, &B)
		for _, v := range B {
			ret += i * (R / v)
			ret -= i * ((L - 1) / v)
		}
	}
	fmt.Println(ret)
}

package main

import (
	"bufio"
	"fmt"
	"os"
)
/*
21920번 서로소 평균

배열 A와 수 x가 주어진다. 배열 A중 x와 서로소인것의 평균을 구하시오.

그냥 유클리드 호제법 활용문제입니다.
딱 실버 평균 문제.
*/
func gcd(x, y int) int {
	if x < y {
		x, y = y, x
	}
	if y > 0 {
		return gcd(y, x%y)
	}
	return x
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	var n, x int
	fmt.Fscan(br, &n)
	arr := make([]int, n)
	for i := 0; i < n; i++ {
		fmt.Fscan(br, &arr[i])
	}
	fmt.Fscan(br, &x)

	cnt := float64(0)
	pls := float64(0)
	for _, i := range arr {
		if gcd(i, x) == 1 {
			cnt++
			pls += float64(i)
		}
	}
	fmt.Fprintln(bw, pls/cnt)

	bw.Flush()
}

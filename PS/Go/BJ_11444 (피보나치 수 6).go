package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
11444번 피보나치 수 6

N번째 피보나치 수를 1_000_000_007로 나눈 나머지를 구해보자.

비상식량문제
오늘 너무 피곤했어
*/
var MOD = int64(1_000_000_007)

func pow(x [][]int64, j int64) [][]int64 {
	row := len(x)
	ret := make([][]int64, row)
	for i := 0; i < row; i++ {
		ret[i] = make([]int64, row)
		ret[i][i] = 1
	}
	for k := j; k > 0; k >>= 1 {
		if k&1 == 1 {
			ret = prod(ret, x)
		}
		x = prod(x, x)
	}
	return ret
}

func prod(x, y [][]int64) [][]int64 {
	row := len(x)
	col := len(y[0])
	ret := make([][]int64, row)
	for i := 0; i < row; i++ {
		ret[i] = make([]int64, col)
	}
	for i := 0; i < row; i++ {
		for j := 0; j < col; j++ {
			for k := 0; k < len(y); k++ {
				ret[i][j] = (ret[i][j] + x[i][k]*y[k][j]) % MOD
			}
		}
	}
	return ret
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var x int64
	fmt.Fscan(br, &x)
	fib := [][]int64{
		{1, 1},
		{1, 0},
	}
	fmt.Println(pow(fib, x-1)[0][0])
}

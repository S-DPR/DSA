package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
27895번 특별한 학생증

맵이 주어진다. 0은 뚫린곳, 1은 막힌곳이다.
이후 각 포탈의 위치가 주어진다. 각 이동에서 포탈은 최대 한 번 사용할 수 있다.
포탈의 어느쪽이든 사용가능하며, 반대쪽에서 나오게 된다.
이 때, (1, 1)에서 (R, C)에 가는 경우의 수를 구해보자.
포탈을 타는 것을 제외한 모든 이동은 오른쪽, 아래쪽으로만 가능하다.

음
그냥 대충 dp겠구나 한거고..
어렵진않네요.
어제보단 상태가 좋은것같아 다행입니다
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()
	abs := func(i int) int {
		if i < 0 {
			return -i
		}
		return i
	}

	MOD := 1_000_000_007
	var R, C, K int
	var r1, r2, c1, c2 int
	var s string
	fmt.Fscan(br, &C, &R, &K)
	M := make([][]rune, R+1)
	D1 := make([][]int, R+2)
	D2 := make([][]int, R+2)
	for i := 1; i <= R; i++ {
		fmt.Fscan(br, &s)
		M[i] = append([]rune{0}, []rune(s)...)
	}
	for i := 0; i <= R+1; i++ {
		D1[i] = make([]int, C+2)
		D2[i] = make([]int, C+2)
	}
	D1[R][C] = 1
	D2[1][1] = 1
	for r := R; r >= 1; r-- {
		for c := C; c >= 1; c-- {
			if M[r][c] == '1' {
				continue
			}
			D1[r][c] = (D1[r+1][c] + D1[r][c+1] + D1[r][c]) % MOD
		}
	}
	for r := 1; r <= R; r++ {
		for c := 1; c <= C; c++ {
			if M[r][c] == '1' {
				continue
			}
			D2[r][c] = (D2[r-1][c] + D2[r][c-1] + D2[r][c]) % MOD
		}
	}
	ret := D2[R][C]
	for i := 0; i < K; i++ {
		fmt.Fscan(br, &c1, &r1, &c2, &r2)
		r1, c1, r2, c2 = r1+1, c1+1, r2+1, c2+1
		dist := abs(r1-r2) + abs(c1-c2)
		if dist != 1 || (r1 < r2 || c1 < c2) {
			ret = (ret + (D1[r1][c1]*D2[r2][c2])%MOD) % MOD
		}
		if dist != 1 || (r1 > r2 || c1 > c2) {
			ret = (ret + (D2[r1][c1]*D1[r2][c2])%MOD) % MOD
		}
	}
	fmt.Println(ret)
}

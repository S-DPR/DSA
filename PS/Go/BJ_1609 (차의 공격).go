package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
1609번 차의 공격

N*N 보드판에 숫자가 있다.
그리고, '차'가 있다. '차'는 가로세로로 공격할 수 있다.
'차'가 있는 칸은 공격받는 것으로 취급되지 않는다.
공격받는 칸에 있는 수를 모두 합 했을 때 최댓값이 되길 원한다.
이 때, 차 두개를 두어 얻을 수 있는 최대 점수는 몇일까?

이야
브루트포스라는거 알고도 어려웠다
O(N^4)이 터져서 어떻게 O(N^3)으로 바꿀지가 관건.
열 하나 고르고 행 2개, 행 하나 고르고 열 2개고르는건 O(N^3)으로 되는데..
열 둘 행 둘 고를때가 문제입니다.

일단 열 둘 골라줍니다.
이제 행 고를건데,
1. 행을 고르면 열 두개 점수를 일단 빼고 (이미 공격당하고있으니)
2. 배치할 위치에 있는 점수를 또 빼줍니다. (차를 둘 위치니까)
그중 최댓값을 구합니다.
그리고 해당 열의 누적합을 잠시 0으로 한 뒤, 두번째 행도 똑같이 해줍니다.
그렇게 해서 다 더한 값이 현재 열 2개를 고른 상태일 때 최댓값입니다.
이를 다 처리해주면 AC.
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N int
	fmt.Fscan(br, &N)
	A := [300][300]int{}
	rpf, cpf := [300]int{}, [300]int{}
	for i := 0; i < N; i++ {
		for j := 0; j < N; j++ {
			fmt.Fscan(br, &A[i][j])
			rpf[i] += A[i][j]
			cpf[j] += A[i][j]
		}
	}
	ret := 0
	for r := 0; r < N; r++ {
		for c1 := 0; c1 < N; c1++ {
			for c2 := c1 + 1; c2 < N; c2++ {
				ret = max(ret, rpf[r]+cpf[c1]+cpf[c2]-A[r][c1]*2-A[r][c2]*2)
			}
		}
	}
	for c := 0; c < N; c++ {
		for r1 := 0; r1 < N; r1++ {
			for r2 := r1 + 1; r2 < N; r2++ {
				ret = max(ret, cpf[c]+rpf[r1]+rpf[r2]-A[r1][c]*2-A[r2][c]*2)
			}
		}
	}
	for r1 := 0; r1 < N; r1++ {
		for r2 := 0; r2 < N; r2++ {
			if r1 == r2 {
				continue
			}
			r := []int{r1, r2}
			for k := 0; k <= 1; k++ {
				rr1, rr2 := r[k], r[k^1]
				c1mx, c1 := -1<<30, -1
				c2mx := -1 << 30
				for c := 0; c < N; c++ {
					if c1mx < cpf[c]-A[rr1][c]*2-A[rr2][c] {
						c1mx = cpf[c] - A[rr1][c]*2 - A[rr2][c]
						c1 = c
					}
				}
				safe := cpf[c1]
				cpf[c1] = 0
				for c := 0; c < N; c++ {
					if c2mx < cpf[c]-A[rr2][c]*2-A[rr1][c] {
						c2mx = cpf[c] - A[rr2][c]*2 - A[rr1][c]
					}
				}
				cpf[c1] = safe
				ret = max(ret, c1mx+c2mx+rpf[rr1]+rpf[rr2])
			}
		}
	}
	fmt.Println(ret)
}

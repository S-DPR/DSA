package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
1852번 수 묶기

3*N크기의 배열이 있다.
각 원소의 상하좌우중 하나랑 묶을 수 있다.
모든 원소가 정확히 하나의 원소와 묶여있을 때, 모든 묶인 원소들의 차이의 합의 최대와 최소를 구해보자.

그냥 뭐..
비트dp 슥 굴리면됩니다.
그냥 dp인가 했는데 비트마스크 넣어서 해야겠더라고요.
한 줄에 대해 가로로 묶는경우 두개는 따로 처리해주고,
세로로는 빈 곳 전부 채워넣는다는 방식으로 하면 됩니다.
*/
var (
	N  int
	A  [][3]int64
	dp [][8]int64
)

func abs(x int64) int64 {
	if x < 0 {
		return -x
	}
	return x
}

func loop(x, v int, init int64, fn func(int64, int64) int64) int64 {
	if x == N || (x == N-1 && v == 7) {
		return 0
	}
	if dp[x][v] != -1 {
		return dp[x][v]
	}
	dp[x][v] = init
	if (v & 0b110) == 0 {
		w := abs(A[x][0] - A[x][1])
		if (v&0b001) == 0 && x+1 < N {
			w += loop(x+1, 0b001, init, fn) + abs(A[x][2]-A[x+1][2])
		} else {
			w += loop(x+1, 0b000, init, fn)
		}
		dp[x][v] = fn(dp[x][v], w)
	}
	if (v & (0b011)) == 0 {
		w := abs(A[x][1] - A[x][2])
		if (v&0b100) == 0 && x+1 < N {
			w += loop(x+1, 0b100, init, fn) + abs(A[x][0]-A[x+1][0])
		} else {
			w += loop(x+1, 0b000, init, fn)
		}
		dp[x][v] = fn(dp[x][v], w)
	}
	if x+1 < N {
		w := int64(0)
		for j := 0; j < 3; j++ {
			if ((v ^ 7) & (1 << j)) != 0 {
				w += abs(A[x][2-j] - A[x+1][2-j])
			}
		}
		dp[x][v] = fn(dp[x][v], loop(x+1, v^7, init, fn)+w)
	}
	return dp[x][v]
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	fmt.Fscan(br, &N)
	A = make([][3]int64, N)
	dp = make([][8]int64, N)
	for i := 0; i < N; i++ {
		for j := 0; j < 8; j++ {
			dp[i][j] = -1
		}
		for j := 0; j < 3; j++ {
			fmt.Fscan(br, &A[i][j])
		}
	}
	mx := loop(0, 0, -int64(1)<<60, func(i, j int64) int64 {
		if i > j {
			return i
		}
		return j
	})
	for i := 0; i < N; i++ {
		for j := 0; j < 8; j++ {
			dp[i][j] = -1
		}
	}
	mn := loop(0, 0, int64(1)<<60, func(i, j int64) int64 {
		if i < j {
			return i
		}
		return j
	})
	fmt.Println(mx)
	fmt.Println(mn)
}

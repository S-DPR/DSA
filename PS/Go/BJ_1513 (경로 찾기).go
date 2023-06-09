package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
1513번 경로 찾기

(1, 1)에서 (N, K)로 가려 한다. 가는 길에는 번호가 붙여진 오락실이 C개 있다.
오락실을 방문하는 순서가 반드시 오름차순이어야 한다고 할 때,
오락실을 0번 방문하는 경우의 수, 1번 방문, 2번 방문, ..., C번 방문하는 경우의 수를 구해보자.
단, 이동은 x가 증가하는 방향, y가 증가하는 방향으로만 가능하다.

뭐야 쉬운데? 이게 골드2? 라고 생각하고 접근했다가,
아, 이래서 골드 2. 라고 생각했습니다.

일단 DAG니까 DP구나~ 하고있었고.
3차원 DP도 '나 퇴사임?'문제에서 풀어봤는데, 4차원 DP는 처음봅니다..
정말 전형적인, 고등학교때 엄청 했던 경로 개수 찾기라 엄청 어렵다..는 아닌데,
어, 이게 코드가 생각보다 깔끔하게 안나오네? 라는 생각은 했습니다.
Go 특성상 초기화에 라인이 꽤 길게 할당된거도 있고.. 코드가 마음에 들진 않아요.
그래도 아이디어는 꽤 재밌었다고 봅니다.

dp[i][j][k][t] = (j, i)에서 k번 오락실을 t번째로 방문한 경우의 수.
현재 위치에 오락실이 없다면 위쪽과 왼쪽을 합친거를 그냥 다 이번 칸에 집어넣습니다.
있다면 위쪽과 왼쪽에서 방문을 r (0 <= r < C)번 한 것 중 현재 오락실 번호 미만인 곳의 경우의 수를 다 합치고,
그 값을 현재 dp[i][j][cur][r+1]에 다 더합니다.
이거를 다 하면 됩니다. 생각보다 기초로 돌아가야 하는 문제.
솔직히 태그에 구현 없는게 더 신기하다.
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N, K, C int
	fmt.Fscan(br, &N, &K, &C)
	M := make([][]int, K+1)
	for i := 0; i <= K; i++ {
		M[i] = make([]int, N+1)
	}
	for i := 1; i <= C; i++ {
		var u, v int
		fmt.Fscan(br, &u, &v)
		M[v][u] = i
	}

	V := make([][][][]int, K+1)
	for i := 0; i <= K; i++ {
		V[i] = make([][][]int, N+1)
		for j := 0; j <= N; j++ {
			V[i][j] = make([][]int, C+1)
			for k := 0; k <= C; k++ {
				V[i][j][k] = make([]int, C+1)
			}
		}
	}

	if M[1][1] == 0 {
		V[1][1][0][0] = 1
	} else {
		V[1][1][M[1][1]][1] = 1
	}
	for i := 1; i <= K; i++ {
		for j := 1; j <= N; j++ {
			cur := M[i][j]
			if cur == 0 {
				for k := 0; k <= C; k++ {
					for t := 0; t <= C; t++ {
						V[i][j][k][t] += V[i-1][j][k][t] + V[i][j-1][k][t]
						V[i][j][k][t] %= 1_000_007
					}
				}
			} else {
				for k := C - 1; k >= 0; k-- {
					sum := 0
					for t := 0; t < cur; t++ {
						sum += V[i-1][j][t][k] + V[i][j-1][t][k]
					}
					sum %= 1_000_007
					V[i][j][cur][k+1] += sum
					V[i][j][cur][k+1] %= 1_000_007
				}
			}
		}
	}

	for i := 0; i <= C; i++ {
		cnt := 0
		for j := 0; j <= C; j++ {
			cnt += V[K][N][j][i]
			cnt %= 1_000_007
		}
		fmt.Fprintf(bw, "%d ", cnt)
	}
}

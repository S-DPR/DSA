package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
15808번 주말 여행 계획

노드가 N개인 그래프가 주어진다.
그리고 시작점 X개, 끝점 Y개가 주어진다. 각 시작점과 끝점은 가중치를 가지고있다.
(시작점의 가중치) + (끝점의 가중치) - (시작점과 끝점의 거리) 의 최댓값을 구해보자.

N = 1000이라서 플로이드에 완탐 박으면 되는 쉬운 문제
골드3 기여 박고 왔습니다.
굳이 다익이어도 그냥 다중시작점 다익스트라 박으면 될텐데?
이왜플?
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N, X, Y int
	fmt.Fscan(br, &N)
	A := make([][]int64, N)
	for i := 0; i < N; i++ {
		A[i] = make([]int64, N)
		for j := 0; j < N; j++ {
			fmt.Fscan(br, &A[i][j])
			if A[i][j] == 0 {
				A[i][j] = int64(1) << 60
			}
		}
		A[i][i] = 0
	}
	for k := 0; k < N; k++ {
		for i := 0; i < N; i++ {
			for j := 0; j < N; j++ {
				A[i][j] = min(A[i][j], A[i][k]+A[k][j])
			}
		}
	}
	fmt.Fscan(br, &X, &Y)
	S := make([][2]int, X)
	T := make([][2]int, Y)
	for i := 0; i < X; i++ {
		fmt.Fscan(br, &S[i][0], &S[i][1])
	}
	for i := 0; i < Y; i++ {
		fmt.Fscan(br, &T[i][0], &T[i][1])
	}
	ret := int64(-1) << 60
	for i := 0; i < X; i++ {
		for j := 0; j < Y; j++ {
			ret = max(ret, int64(S[i][1]+T[j][1])-A[S[i][0]-1][T[j][0]-1])
		}
	}
	fmt.Println(ret)
}

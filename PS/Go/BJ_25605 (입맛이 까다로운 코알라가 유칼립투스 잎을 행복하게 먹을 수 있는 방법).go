package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
25605번 입맛이 까다로운 코알라가 유칼립투스 잎을 행복하게 먹을 수 있는 방법

A만큼의 독을 버틸 수 있는 코알라가 있다.
이 코알라는 매일 B만큼의 독을 해독하고, 1일차 시점에서 이미 C만큼의 독을 갖고있다.
당신은 이 코알라에게 먹이를 줄건데, 이 먹이는 코알라에게 x만큼의 독과 y만큼의 행복도를 준다.
코알라는 이 먹이를 먹는데는 하루가 걸리고, 원한다면 언제든 원하는 횟수만큼 갖다버릴 수 있다.
코알라는 갖다버리면 즉시 다음 먹이를 제공받기에, 하루에 '원하는만큼 버리고 원하는 잎을 먹기'가 가능하다.
단, 이미 버린 먹이는 다시 줄 수 없다. 또한, N개의 먹이 이후에는 먹이가 더이상 제공되지 않는다.
M일이 되는 시점에서 코알라가 얻을 수 있는 최대의 행복도를 구해보자.

이건 뭐 문제 어떻게 요약하기도 애매하고,
애초에 난이도자체가 높은데 이게 왜 골드1이지..

진짜 몇일동안 생각했다가 오늘 드디어 푼 문제
냅색인데 변형을 미친듯이 때려버렸습니다.

일단 dp판을 만듭니다. 이 문제가 1차원으로 될 일은 없으니 무조건 2차원으로 만듭니다.
dp판의 가로는 '몇 일째인지'를, 세로는 '쌓인 독'을 뜻할겁니다. 이걸 그대로 냅색을 진행해주는데, 사항을 주의해줍시다.
쌓인 독을 기입할때에는 B를 미리 빼서 기입합니다. 안그러면 복잡해집니다.
그리고, B가 몇이든 관계없이 for문 하나 더 써서 모든 날짜에 대해 쌓인 독이 0이 될 때까지 기입합니다. (60~66라인)

냅색시에 이 최초값을 업데이트해야할 때가 오는데,
이 경우도 마찬가지입니다. 쌓인 독이 0이 될 때까지 모든 날짜에 기입해줍니다.
이후 최댓값을 얻어 출력해줍니다.
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	max := func(i, j int) int {
		if i > j {
			return i
		}
		return j
	}

	var N, M int
	fmt.Fscan(br, &N, &M)
	var A, B, C int
	fmt.Fscan(br, &A, &B, &C)

	dp := make([][]int, M+1)
	for i := 0; i <= M; i++ {
		dp[i] = make([]int, A+1)
	}

	for i := 0; i < N; i++ {
		var poison, score int
		fmt.Fscan(br, &poison, &score)
		for day := M; day >= 1; day-- {
			for curP := A; curP-poison >= 0; curP-- {
				if dp[day-1][curP-poison] > 0 {
					curS := dp[day-1][curP-poison] + score
					for nextD := day; nextD <= M; nextD++ {
						newP := max(0, curP-B*(nextD-day+1))
						dp[nextD][newP] = max(dp[nextD][newP], curS)
						if newP == 0 {
							break
						}
					}
				}
			}
		}
		for day := 1; day <= M; day++ {
			newP := max(0, C-B*(day-1)+poison)
			if newP > A {
				continue
			}
			dp[day][max(0, newP-B)] = max(dp[day][max(0, newP-B)], score)
			if newP == 0 {
				break
			}
		}
	}
	result := 0
	for i := 1; i <= M; i++ {
		for j := 0; j <= A; j++ {
			result = max(result, dp[i][j])
		}
	}
	fmt.Fprintln(bw, result)

	bw.Flush()
}

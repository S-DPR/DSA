package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
16157번 블로그

색칠이 안된 막대에 색칠하려한다. 그래서, 어떻게 색칠하려는지 주어진다.
한 번 색칠을 할 때 연속하게 해야한다. 이 때 비용은 1이다.
원하는대로 색칠 할 때 최소비용을 구해보자.

1. dp인거 몰랐음 -> 분할정복으로 착각했었는데 분류봄
2. dp식은 맞춤
3. 그런데 dp구현을 못함..
4. 그래서 답지보고 품
아..
그런데 이거랑 풀이가 완전히 같은 문제가 P3에있다네..

플레dp는 이런맛이구나.. 하게 된 문제입니다.
dp식은 맞췄는데 되게 풀이가 신기해서 놀랐습니다.
저는 l, r 범위 내에서 가능한 모든 left, right 조합을 했었는데,
같은 색으로 칠할 때 (l, i) + (i+1, r)의 비용은 0.
다른색으로 칠한다면 그냥 (l, r)로 하고 비용 1 추가.
근데 솔직히 왜 모든 left, right 쌍 구해서 한게 답이 안된진 모르겠어요.

어쨌든 진짜 놀랍다..
답지보면서 푸니까 되게 알아가는건 많은느낌..
*/
var (
	N   int
	S   []rune
	D   [100][100][3]int
	C   map[rune]int
	INF int = 1 << 30
)

func min(i, j int) int {
	if i < j {
		return i
	}
	return j
}

func loop(l, r, c int) int {
	if l == r {
		if C[S[l]] == c {
			return 0
		}
		return 1
	}
	if D[l][r][c] != -1 {
		return D[l][r][c]
	}
	D[l][r][c] = INF
	for i := l; i < r; i++ {
		D[l][r][c] = min(D[l][r][c], loop(l, i, c)+loop(i+1, r, c))
	}
	for i := 0; i < 3; i++ {
		if i != c {
			D[l][r][c] = min(D[l][r][c], loop(l, r, i)+1)
		}
	}
	return D[l][r][c]
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	fmt.Fscan(br, &N)
	var tmp string
	fmt.Fscan(br, &tmp)
	S = []rune(tmp)
	D = [100][100][3]int{}
	C = make(map[rune]int)
	C['R'] = 0
	C['G'] = 1
	C['B'] = 2
	for i := 0; i < N; i++ {
		for j := 0; j < N; j++ {
			for k := 0; k < 3; k++ {
				D[i][j][k] = -1
			}
		}
	}
	ret := 1 << 30
	for i := 0; i < 3; i++ {
		ret = min(ret, loop(0, N-1, i)+1)
	}
	fmt.Fprintln(bw, ret)
}

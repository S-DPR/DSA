package main

/*
14957번 Rectilinear Regions

x축으로 정렬된 두 선 U, L이 주어진다.
x = 0일 때 y값과 어떤 x에서 y값이 몇으로 바뀌는지에 대한 정보가 주어진다.
U가 위쪽과 왼쪽 변을 구성하고, L이 오른쪽과 아래를 구성하는 다각형의 개수와,
그 다각형의 너비를 구해보자.

아니 어캐푸냐 하다가 스위핑 생각났네요.
처음엔 뭐 변수만으로 하다가 생각해보니 그냥 배열에 박으면 된다는 깨달음을 얻고..
쭉 풀었습니다.
*/
import (
	"bufio"
	"fmt"
	"os"
)

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N, M, x, y int
	fmt.Fscan(br, &N, &M)
	U := make([]int64, 51000)
	L := make([]int64, 51000)
	UU := make([]bool, 51000)
	LU := make([]bool, 51000)
	fmt.Fscan(br, &L[0])
	curX := 0
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &x, &y)
		x++
		for j := 1; curX+j <= x; j++ {
			L[curX+j] = L[curX]
		}
		curX = x
		L[curX] = int64(y)
		UU[x] = true
	}
	for curX+1 <= 50001 {
		curX++
		L[curX] = L[curX-1]
	}
	fmt.Fscan(br, &U[0])
	curX = 0
	for i := 0; i < M; i++ {
		fmt.Fscan(br, &x, &y)
		x++
		for j := 1; curX+j <= x; j++ {
			U[curX+j] = U[curX]
		}
		curX = x
		U[curX] = int64(y)
		LU[x] = true
	}
	for curX+1 <= 50001 {
		curX++
		U[curX] = U[curX-1]
	}
	cnt, ret := 0, int64(0)
	up, area := false, int64(0)
	for i := 1; i <= 50001; i++ {
		if up {
			if L[i] < U[i] {
				area += U[i] - L[i]
			} else {
				up = false
				ret += area
				cnt++
			}
			continue
		}
		if U[i-1] < L[i-1] && L[i] < U[i] {
			area = U[i] - L[i]
			up = true
		}
	}
	fmt.Println(cnt, ret)
}

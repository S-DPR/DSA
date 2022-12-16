package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
10714번 케이크 자르기 2

A와 B가 있고, 서로 다른 수로 이루어진 길이 n의 배열 arr이 주어집니다.
이제, A가 여기서 아무런 하나의 조각을 가져갑니다.
B는 A가 가져간 조각과 가장 가까운 왼쪽, 오른쪽 조각중 가장 커보이는 조각을 가져갑니다.
이후 A는 빠진 조각과 가장 가까운 왼쪽, 오른쪽 조각중 하나 선택하여 가져가고, B는 위 행동을 반복합니다.
이 작업을 계속 반복하여 arr의 모든 배열을 A, B가 나누어가졌을 때, A가 갖는 가장 큰 값을 가질 때의 값을 구해주세요.
단, 인덱스 0의 왼쪽에는 n-1이 있고, 인덱스 n-1의 오른쪽에는 0이 있습니다.

DPDPDPP한 DPDP문제입니다.
처음엔 BFS로 했다가 15점맞고,
두번쨰론 제 능지 최대출력시켜서 60점맞고,
결국 답지 보고 끝냈습니다.

일단 환형구조입니다. 대충 원형이란뜻인데,
이부분은 `getLeft`, 'getRight'함수를 참고해주세요.

제가 이해한 부분까지는, 전에 풀었던 "수확"문제에서 영감을 받아,
dp를 2차원으로 선언한 뒤 left와 right를 인덱스로 삼아 저장할 것이라는 점 입니다.
예를들어, 첫번째 조각을 먹었다면 dp[1][1]에 arr[1]의 값을 저장하겠죠.
그상태에서 왼쪽 두개를 더 먹었다면 dp[3][1]에 해당 값을 저장할겁니다.
일단 이 부분은 넘기고요..

main()함수에서는 입력을 모두 받고, 각 케이크를 첫번째로 집었을 때 기대되는 최댓값을 res함수에 저장합니다.
이 부분에서 loop()함수가 호출됩니다. 이 함수가 프로그램의 핵심입니다.

loop()함수에서는 JOI의 턴이라면 loop 두개를 호출합니다. 첫 번째는 왼쪽조각을 집어 먹었을 때, 두 번째는 오른쪽 조각을 집어먹었을 때 입니다.
dp[left][right]가 0을 넘는다면, 즉, 업데이트가 된 값이라면 dp[left][right]를 반환합니다.
잘 보시면, 이 값은 int64를 반환하는데, 이 값은 이번 left right일 때 내가 먹을 수 있는 최대치입니다.
그래서 이 값에 dp[left][right]를 반환하는거구요.
케이크를 다 먹은 순간 (getLeft() == right) 에는 0을 그래서 반환하는겁니다.
참, 이게 답지보고해서그런가 뭔뜻인진 모르겠네요.. 외우는게 답인가..

여하튼 위 작업을 모든 인덱스에 대해 실행해보면 됩니다. 모든 케이크를 처음으로 먹어볼 수 있을테니까요.
DP의 세계는 깊고 어둡구나..
*/
func max(i, j int64) int64 {
	if i > j {
		return i
	}
	return j
}

func getLeft(n, idx int) int {
	/*
		:param:
			:n: 배열의 크기
			:idx: 왼쪽으로 이동시킬 인덱스
	*/
	return (idx - 1 + n) % n
}

func getRight(n, idx int) int {
	/*
		:param:
			:n: 배열의 크기
			:idx: 오른쪽으로 이동시킬 인덱스
	*/
	return (idx + 1) % n
}

func loop(dp [][]int64, arr []int64, left, right, n int, turn bool) int64 {
	/*
		:param:
			:dp: dp배열
			:arr: 입력받은 환형 배열
			:left: 먹은 왼쪽 끝
			:right: 먹은 오른쪽 끝
			:n: arr의 배열 크기
			:turn: true이면 JOI의 턴, false이면 IOI의 턴
	*/
	if dp[left][right] > 0 {
		return dp[left][right]
	}
	nextLeft, nextRight := getLeft(n, left), getRight(n, right)
	if nextLeft == right {
		return 0
	}
	if turn {
		LeftValue := arr[nextLeft] + loop(dp, arr, nextLeft, right, n, false)
		RightValue := arr[nextRight] + loop(dp, arr, left, nextRight, n, false)
		dp[left][right] = max(LeftValue, RightValue)
	} else {
		if arr[nextLeft] > arr[nextRight] {
			dp[left][right] = loop(dp, arr, nextLeft, right, n, true)
		} else {
			dp[left][right] = loop(dp, arr, left, nextRight, n, true)
		}
	}
	return dp[left][right]
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	var n int
	fmt.Fscan(br, &n)
	arr := make([]int64, n)
	dp := make([][]int64, n)
	for i := 0; i < n; i++ {
		dp[i] = make([]int64, n)
		fmt.Fscan(br, &arr[i])
	}

	res := int64(0)
	for i := 0; i < n; i++ {
		res = max(loop(dp, arr, i, i, n, false)+arr[i], res)
	}
	fmt.Fprintln(bw, res)

	bw.Flush()
}

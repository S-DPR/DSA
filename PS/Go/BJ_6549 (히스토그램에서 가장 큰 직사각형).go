package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
6549번 히스토그램에서 가장 큰 직사각형

배열 `arr`가 주어진다.
min(arr[l:r])*(r-l+1) (l <= r) 의 최대값을 구하시오.

오랫동안 잡고있던 문제입니다. 웰노운이긴 한데..
스택도 있고 세그트리도 있고 검색해보니 우선순위큐도 있는데..
전 분할정복으로 했습니다. 아무리 생각해도 분할정복이 제일 맛깔나보여요.
옛날엔 못했는데 지금은 나름 뭐.. 잘 한거같네요.

mid에 대해, 왼쪽 오른쪽 모두 갈 수 있다면 더 큰쪽으로 먼저 이동한 뒤 높이/넓이를 재계산하고,
한쪽은 못 가는 상태라면 갈 수 있는곳만 쭉 가면서 높이/넓이를 재계산합니다.
그리고, 재귀호출을 합니다. 딱 이정도네요.
*/
func max(i, j int64) int64 {
	if i > j {
		return i
	}
	return j
}

func min(i, j int64) int64 {
	if i > j {
		return j
	}
	return i
}

func loop(arr []int64, left, right int) int64 {
	getArea := func(height int64, left, right int) int64 {
		return height * int64(right-left+1)
	}
	if left == right {
		return arr[left]
	}
	mid := (left + right) >> 1

	result := arr[mid]
	curHeight := arr[mid]
	curLeft, curRight := mid, mid
	for left < curLeft && curRight < right {
		if arr[curLeft-1] < arr[curRight+1] {
			curRight++
			curHeight = min(arr[curRight], curHeight)
		} else {
			curLeft--
			curHeight = min(arr[curLeft], curHeight)
		}
		result = max(getArea(curHeight, curLeft, curRight), result)
	}

	for left < curLeft {
		curLeft--
		curHeight = min(arr[curLeft], curHeight)
		result = max(getArea(curHeight, curLeft, curRight), result)
	}

	for curRight < right {
		curRight++
		curHeight = min(arr[curRight], curHeight)
		result = max(getArea(curHeight, curLeft, curRight), result)
	}

	leftResult := loop(arr, left, mid)
	rightResult := loop(arr, mid+1, right)
	return max(max(leftResult, rightResult), result)
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	for {
		var length int
		fmt.Fscan(br, &length)
		if length == 0 {
			break
		}
		arr := make([]int64, length)
		for i := 0; i < length; i++ {
			fmt.Fscan(br, &arr[i])
		}
		fmt.Fprintln(bw, loop(arr, 0, length-1))
	}

	bw.Flush()
}

package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

/*
3017번 가까운 수 찾기

수 S, T가 주어진다.
T를 재배열하여 S와 가장 가까운 S 이상의 수와 S 미만의 수를 하나씩 출력해보자.

이딴게.. 플레..?
그럼 내가 지금까지 풀어온 플레는..

그냥 어..
그리디하게 백트래킹 떄리면 됩니다. 네.
어.. 수를 넣을때는 처음에는 S와 같은 수부터 시작해서 크거나 작은값만 넣고요,
S와 같은 인덱스인데 다른값 넣은 순간부터 가장 작은값 / 가장 큰값부터 넣으면 됩니다.
그러면 가까워지니까요..
*/
func maxLoop(S_, arr, res []int, idx int, isBig bool) (bool, []int) {
	if idx == len(S_) {
		return true, res
	}
	if isBig {
		for i := 0; i < 10; i++ {
			if arr[i] > 0 {
				arr[i]--
				res[idx] = i
				result, val := maxLoop(S_, arr, res, idx+1, true)
				if result {
					return true, val
				}
				arr[i]++
			}
		}
	} else {
		for i := S_[idx]; i < 10; i++ {
			if arr[i] > 0 {
				arr[i]--
				res[idx] = i
				result, val := maxLoop(S_, arr, res, idx+1, S_[idx] < i)
				if result {
					return true, val
				}
				arr[i]++
			}
		}
	}
	return false, make([]int, 0)
}

func minLoop(S_, arr, res []int, idx int, isSmall bool) (bool, []int) {
	if idx == len(S_) {
		return isSmall, res
	}
	if isSmall {
		for i := 9; i >= 0; i-- {
			if arr[i] > 0 {
				arr[i]--
				res[idx] = i
				result, val := minLoop(S_, arr, res, idx+1, true)
				if result {
					return true, val
				}
				arr[i]++
			}
		}
	} else {
		for i := S_[idx]; i >= 0; i-- {
			if idx == 0 && i == 0 {
				break
			}
			if arr[i] > 0 {
				arr[i]--
				res[idx] = i
				result, val := minLoop(S_, arr, res, idx+1, S_[idx] > i)
				if result {
					return true, val
				}
				arr[i]++
			}
		}
	}
	return false, make([]int, 0)
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	var S, T string
	fmt.Fscan(br, &S, &T)
	arr := make([]int, 10)
	brr := make([]int, 10)
	n := len(S)
	for i := 0; i < n; i++ {
		item, _ := strconv.Atoi(string(T[i]))
		arr[item]++
		brr[item]++
	}
	S_ := make([]int, n)
	for i := 0; i < n; i++ {
		S_[i], _ = strconv.Atoi(string(S[i]))
	}

	isBig, big := maxLoop(S_, arr, make([]int, n), 0, false)
	isSmall, small := minLoop(S_, brr, make([]int, n), 0, false)

	if isBig {
		for i := 0; i < n; i++ {
			fmt.Fprintf(bw, "%d", big[i])
		}
		fmt.Fprintln(bw)
	} else {
		fmt.Fprintln(bw, 0)
	}
	if isSmall {
		for i := 0; i < n; i++ {
			fmt.Fprintf(bw, "%d", small[i])
		}
		fmt.Fprintln(bw)
	} else {
		fmt.Fprintln(bw, 0)
	}

	bw.Flush()
}

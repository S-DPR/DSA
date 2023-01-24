package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
)

/*
2696번 중앙값 구하기

테스트케이스 T가 주어진다.
이후 수열의 길이 N이 주어진다. (N은 홀수)
이제 여기서 홀수번째 입력이 들어올 때마다, 중앙값을 출력해보자.

가운데를 말해요와 같은 문제랍니다.
동아리 선배 화면 보니까 이거풀고있길래 슬쩍 풀었습니다.

아이디어는 편의점 알바하면서 빙빙 돌다가 생각났는데요,
현재 가운데값을 기준으로 작은값과 큰 값을 보관하는 우선순위큐를 넣자 입니다.
작은값을 보관하는곳에는 MaxHeap, 큰 값 보관에는 MinHeap으로요.
그리고 수 두개를 입력받고 현재 Standard와 비교하여 왼쪽오른쪽에 넣고,
두개 넣은 다음 각각에 넣은만큼 다시 빼고 가운데값 얻고 나머진 다시 왼쪽 오른쪽에 넣자.
이 아이디어입니다.

골드 2치고 진짜 빨리 푼 문제네요.
Go로 푼건 의도적이었습니다. 입력이 좀 기분 나쁘게 와서, C류 아니면 받기 귀찮겠더라고요.
*/
type PriorityQueue struct {
	arr     []int
	arrSize int
}

func newPQ() PriorityQueue {
	return PriorityQueue{arr: make([]int, 10_000), arrSize: 0}
}

func (PQ *PriorityQueue) push(x int) {
	PQ.arrSize++
	idx := PQ.arrSize
	for idx != 1 && PQ.arr[idx>>1] > x {
		PQ.arr[idx] = PQ.arr[idx>>1]
		idx >>= 1
	}
	PQ.arr[idx] = x
}

func (PQ *PriorityQueue) pop() int {
	idx, item := 1, PQ.arr[PQ.arrSize]
	result := PQ.arr[1]
	PQ.arrSize--
	for idx<<1 <= PQ.arrSize {
		child := idx << 1
		if child < PQ.arrSize && PQ.arr[child] > PQ.arr[child+1] {
			child++
		}
		if PQ.arr[child] >= item {
			break
		}
		PQ.arr[idx] = PQ.arr[child]
		idx = child
	}
	PQ.arr[idx] = item
	return result
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	var T int
	fmt.Fscan(br, &T)
	for i := 0; i < T; i++ {
		var L int
		fmt.Fscan(br, &L)
		l, r := newPQ(), newPQ()
		var standard int
		fmt.Fscan(br, &standard)
		lcnt, rcnt := 0, 0
		answerCnt := 1

		fmt.Fprintln(bw, (L>>1)+1)
		fmt.Fprintf(bw, "%d ", standard)
		for j := 2; j <= L; j++ {
			var x int
			fmt.Fscan(br, &x)
			if x < standard {
				l.push(-x)
				lcnt++
			} else {
				r.push(x)
				rcnt++
			}
			if j&1 == 1 {
				answerCnt++
				tmp := make([]int, 0)
				tmp = append(tmp, standard)
				for lcnt > 0 {
					lcnt--
					tmp = append(tmp, -l.pop())
				}
				for rcnt > 0 {
					rcnt--
					tmp = append(tmp, r.pop())
				}
				sort.Ints(tmp)
				fmt.Fprintf(bw, "%d ", tmp[1])
				standard = tmp[1]
				l.push(-tmp[0])
				r.push(tmp[2])
			}
			if answerCnt == 10 {
				fmt.Fprintln(bw)
				answerCnt = 0
			}
		}
		fmt.Fprintln(bw)
	}

	bw.Flush()
}

package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
)

/*
2065번 나룻배

강의 좌측과 우측에 언제 사람이 오는지에 대한 정보가 주어지고,
나룻배가 아래 조건으로 움직인다.
1. 현재 방향에 사람이 없다면 가만히 있는다.
1-1. 하지만 반대쪽에 사람이 있다면 그곳으로 간다.
2. 나룻배에 사람을 최대 M명 태울 수 있다.
3. 사람을 태웠다면 반드시 반대쪽으로 간다.
이 때, 각 사람이 몇 분만에 각각 반대로 갈 수 있는지 구해보자.

많조분 많조분 신나는노래
사실 그렇게 많조분은 아닌데 세미만조분이었습니다.
구현의 양을 줄이기 위해 인덱스를 잘 써야했고..
구현에 생각보다 미스가 많이 났습니다. 헷갈리긴 하네요.
그나저나 이건 진짜 큐네 ㅋㅋ
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var M, T, N int
	fmt.Fscan(br, &M, &T, &N)
	customer := make([][][2]int, 2)
	for i := 0; i < 2; i++ {
		customer[i] = make([][2]int, 0)
	}
	for i := 0; i < N; i++ {
		var t int
		var s string
		fmt.Fscan(br, &t, &s)
		idx := 0
		if s == "right" {
			idx = 1
		}
		customer[idx] = append(customer[idx], [2]int{t, i})
	}
	for i := 0; i < 2; i++ {
		sort.Slice(customer[i], func(u, v int) bool {
			return customer[i][u][0] < customer[i][v][0]
		})
	}
	ret := make([]int, N)
	idx := []int{0, 0}
	curT, curD := 0, 0
	for idx[0] < len(customer[0]) || idx[1] < len(customer[1]) {
		has := false
		for i := 0; i < M; i++ {
			if idx[curD] < len(customer[curD]) && customer[curD][idx[curD]][0] <= curT {
				has = true
				ret[customer[curD][idx[curD]][1]] = curT + T
				idx[curD]++
				continue
			}
			break
		}
		if has {
			curT += T
			curD ^= 1
		} else {
			lMin, rMin := 1<<30, 1<<30
			if idx[0] < len(customer[0]) {
				lMin = customer[0][idx[0]][0]
			}
			if idx[1] < len(customer[1]) {
				rMin = customer[1][idx[1]][0]
			}
			if lMin < rMin || (lMin == rMin && curD == 0) {
				if curD == 0 {
					curT = max(curT, lMin)
				} else {
					curT = max(lMin, curT) + T
					curD ^= 1
				}
			} else {
				if curD == 0 {
					curT = max(rMin, curT) + T
					curD ^= 1
				} else {
					curT = max(rMin, curT)
				}
			}
		}
	}
	for _, i := range ret {
		fmt.Fprintln(bw, i)
	}
}

package main

import (
	"bufio"
	"fmt"
	"math/big"
	"os"
	"strconv"
)

/*
24436번 K번째 최단 경로

노드 X에서 노드 Y로 가려면 정확히 한 자릿수에 대해 정확히 1만큼 차이가 나야 한다.
숫자의 최대 길이 L, 몇 번째 최단겅로인지에 대한 K, 출발노드 X, 도착노드 Y가 주어진다.
X에서 Y로 가는 최단경로 중, 사전순으로 나열했을 때 K번째 인 경로를 출력해보자.
만약 최단경로의 개수보다 K가 더 크다면 NO를 출력하자.

dp와 bfs는 모르겠고, 조합론+깡구현으로 풀었습니다.
diff는 각 자리수에 대해 몇이나 차이나는지 여부이고, diffSign은 각 자리수에 대해 어느 방향으로 차이나는지 여부.
diffSum은 diff 원소들의 합입니다.

한 자릿수만 1만큼 차이난다는 뜻은 다시말해 10의 제곱수중 하나 만큼 차이가 난다는 뜻이므로 10의 제곱수를 리턴하는 함수를 만듭시다.
자릿수를 건드는 우선순위를 정할겁니다. 자릿수가 -만큼 차이나면 앞쪽, +만큼 차이나면 뒤로 몰아두고,
-차이나는 부분은 내림차순, +차이나는 부분은 오름차순으로 정렬한 뒤 둘을 붙입니다.

자릿수를 건들일 때, 이걸 건들면 몇 개의 경우의수가 생기는지 체크합니다.
우선순위대로 해보다가, 경우의 수가 K를 넘어서면 K에서 더하기 전 값을 빼고,
위 행위를 반복합니다.
K가 넘지 않았다면 다시 원상복귀시키고 다음 우선순위로 넘어갑니다.

.. 이거를 반복하면 됩니다.
정답률 100%는 역시 함정이었어..
*/

func abs(x int64) int64 {
	if x < 0 {
		return -x
	}
	return x
}

func getDiff(x, y, L int64) []int64 {
	result := make([]int64, 0)
	div := int64(10)
	for i := int64(0); i < L; i++ {
		KthX := (x % div) / (div / 10)
		KthY := (y % div) / (div / 10)
		result = append([]int64{abs(KthY - KthX)}, result...)
		div *= 10
	}
	return result
}

func getSign(x, y, L int64) []int {
	result := make([]int, 0)
	div := int64(10)
	for i := int64(0); i < L; i++ {
		KthX := (x % div) / (div / 10)
		KthY := (y % div) / (div / 10)
		if KthX-KthY < 0 {
			result = append([]int{1}, result...)
		} else {
			result = append([]int{-1}, result...)
		}
		div *= 10
	}
	return result
}

func isContainNonzero(diff []int64) bool {
	for _, item := range diff {
		if item != 0 {
			return true
		}
	}
	return false
}

func get10square(x int64) int64 {
	result := int64(1)
	for i := int64(0); i < abs(x); i++ {
		result *= 10
	}
	return result
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	var T int
	fmt.Fscan(br, &T)

	for i := 0; i < T; i++ {
		var L, K_ int64
		var x__, y__ string
		fmt.Fscan(br, &L, &K_, &x__, &y__)
		x_, _ := strconv.Atoi(x__)
		y_, _ := strconv.Atoi(y__)
		x, y := int64(x_), int64(y_)
		K := big.NewInt(K_)
		diff := getDiff(x, y, L)
		diffSign := getSign(x, y, L)
		result := []int64{x}
		diffSum := int64(0)
		for _, j := range diff {
			diffSum += j
		}

		cur := big.NewInt(1).MulRange(1, diffSum)
		for _, k := range diff {
			cur.Div(cur, big.NewInt(1).MulRange(1, abs(k)))
		}
		if cur.Cmp(K) == -1 {
			fmt.Fprintln(bw, "NO")
			continue
		}

		tmp1, tmp2 := make([]int64, 0), make([]int64, 0)
		for idx, j := range diffSign {
			if j == -1 {
				tmp1 = append(tmp1, int64(idx))
			} else {
				tmp2 = append([]int64{int64(idx)}, tmp2...)
			}
		}
		priority := append(tmp1, tmp2...)

		for isContainNonzero(diff) {
			prev, prefix := big.NewInt(0), big.NewInt(0)
			for _, j := range priority {
				if diff[j] != 0 {
					diff[j]--
					diffSum--
					cases := big.NewInt(1).MulRange(1, diffSum)
					for _, k := range diff {
						cases.Div(cases, big.NewInt(1).MulRange(1, k))
					}
					prefix.Add(prefix, cases)
					if prefix.Cmp(K) >= 0 {
						K.Add(K, prev.Neg(prev))
						x += int64(diffSign[j]) * get10square(L-j-1)
						result = append(result, x)
						break
					}
					prev.Set(prefix)
					diff[j]++
					diffSum++
				}
			}
		}

		for _, item := range result {
			fmt.Fprintf(bw, "%0*d ", L, item)
		}
		fmt.Fprintln(bw)
	}

	bw.Flush()
}

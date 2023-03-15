package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
)

/*
3366번 수열 줄이기

길이가 N인 수열을 길이가 1이 되도록 줄이려한다.
아래 연산을 N-1번 했을 때 그 비용의 최솟값을 구하시오.
reduce(i) : i번째 원소를 max(arr[i], arr[i+1])의 비용으로 없앤다.

후..
연결리스트에 유니온파인드에 이것저것 쓰면서 풀었으나..
아... 됐다..

맨 밑에 정해 써뒀습니다. 자괴감이 막 드네요..

제가 한 방법은 연결리스트가 삽입/삭제에 O(1)이라는 점을 이용해,
제일 작은 수부터 인접한 작은 수를 취하며 유니온파인드로 합치고,
연결리스트 방식으로 해당 인덱스를 지워버리는 방식입니다.

그리디 말고 모노톤 스택을 쓰는 방법도 있다고 하네요.
*/

func union(G []int, arr []int, x, y int) {
	x = find(G, x)
	y = find(G, y)
	if arr[x] < arr[y] {
		G[y] = G[x]
	} else {
		G[x] = G[y]
	}
}

func find(G []int, x int) int {
	if G[x] != x {
		G[x] = find(G, G[x])
	}
	return G[x]
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	var N int
	fmt.Fscan(br, &N)
	arr := make([]int, N)
	idxs := make(map[int][]int)
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &arr[i])
		idxs[arr[i]] = append(idxs[arr[i]], i)
	}
	keys := make([]int, 0)
	for key := range idxs {
		keys = append(keys, key)
	}
	sort.Ints(keys)
	adjoin := make([][]int, 0)
	for i := 0; i < N; i++ {
		if i == 0 {
			adjoin = append(adjoin, []int{-1, 1})
		} else if i == N-1 {
			adjoin = append(adjoin, []int{i - 1, -1})
		} else {
			adjoin = append(adjoin, []int{i - 1, i + 1})
		}
	}
	G := make([]int, N)
	for i := 0; i < N; i++ {
		G[i] = i
	}

	res := int64(0)
	cnt := 0
	for _, val := range keys {
		for _, idx := range idxs[val] {
			if cnt == N-1 {
				break
			}
			left := adjoin[find(G, idx)][0]
			right := adjoin[find(G, idx)][1]
			if left == -1 {
				arr[idx] = arr[find(G, right)]
				union(G, arr, idx, right)
				adjoin[right][0] = -1
			} else if right == -1 {
				arr[idx] = arr[find(G, left)]
				union(G, arr, idx, left)
				adjoin[left][1] = -1
			} else {
				if arr[left] < arr[right] && find(G, left) != find(G, idx) {
					arr[idx] = arr[find(G, left)]
					union(G, arr, idx, left)
					adjoin[left][1] = right
					adjoin[right][0] = left
				} else {
					arr[idx] = arr[find(G, right)]
					union(G, arr, idx, right)
					adjoin[right][0] = left
					adjoin[left][1] = right
				}
			}
			res += int64(arr[idx])
			cnt++
		}
	}
	fmt.Fprintln(bw, res)

	bw.Flush()
}

/*
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	max := func(i, j int64) int64 {
		if i < j {
			return j
		}
		return i
	}
	var N int
	var old int64
	fmt.Fscan(br, &N, &old)
	res := int64(0)
	for i := 1; i < N; i++ {
		var new int64
		fmt.Fscan(br, &new)
		res += max(new, old)
		old = new
	}
	fmt.Fprintln(bw, res)

	bw.Flush()
}
*/

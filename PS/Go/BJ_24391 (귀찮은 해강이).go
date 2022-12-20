package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
24391번 귀찮은 해강이

수 N과 M이 주어진다.
이후 M개의 줄에 같은 집합에 속한 정수 x, y가 주어진다. (1 <= x, y <= N)
M+2번째 줄에 수열 arr이 주어진다.
인접한 두 인덱스의 값에 대해, 같은 집합에 속하지 않은 쌍의 개수를 구하자.
(arr[i], arr[j])와 (arr[j], arr[i])는 같은 쌍이다.

전형적인 유니온파인드 문제입니다.
근데 구현 잘못해서 틀렸어요..
여기에 더 충격인건 AI한테 제가 멀 잘못했는지 물어보고 고쳐달라니까 고친게 정답으로 나왔어요..
현타 두배로..
*/
func union(arr []int, i, j int) {
	parentI, parentJ := find(arr, i), find(arr, j)
	arr[parentJ] = parentI
}

func find(arr []int, x int) int {
	if arr[x] != x {
		arr[x] = find(arr, arr[x])
	}
	return arr[x] // 이걸 그냥 x로해서 틀렸었고
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	var n, m int
	fmt.Fscan(br, &n, &m)
	arr := make([]int, n)
	for i := 0; i < n; i++ {
		arr[i] = i
	}
	for i := 0; i < m; i++ {
		var x, y int
		fmt.Fscan(br, &x, &y)
		union(arr, x-1, y-1)
	}

	out := 0
	brr := make([]int, n)
	for i := 0; i < n; i++ {
		fmt.Fscan(br, &brr[i])
	}
	for i := 0; i < n-1; i++ {
		// arr[brr[i]], arr[brr[i+1]]이 부모의 인덱스를 이미 가리키고있을줄알았는데 find로 찾더라고요..?
		parentI, parentJ := find(arr, brr[i]-1), find(arr, brr[i+1]-1)
		if parentI != parentJ {
			out++
		}
	}
	fmt.Fprintln(bw, out)

	bw.Flush()
}

package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
)
/*
13274번 수열

길이가 N인 수열이 있다. 각 원소는 절댓값이 10^18을 넘지 않는다.
쿼리가 K개 주어진다. L, R, X로 이루어져있으며, 수열을 정렬한 뒤 L부터 R까지 X를 더하고 다시 정렬하라는 뜻이다.
모든 쿼리를 처리한 후 수열의 상태를 출력하라.
(1 <= L <= R <= N, |X| <= 10**18)

병합정렬형이 왜 거기서나와..?
어디서 삽입정렬은 O(N)이라고 이야기해서 생각만 하다가 못했는데,
arr[0..L-1], arr[L..R], arr[R+1..N]까지는 정렬이 되어있다는 점에 착안,
left, mid, right로 나누어 3포인터를 이용해 O(N)으로 각 쿼리를 처리했습니다.

병합정렬이 진짜 갑자기 튀어나올줄은..
진짜 한동안 못풀었던 골드5인데 드디어 풀었습니다.
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	var N, K int
	fmt.Fscan(br, &N, &K)
	arr := make([]int64, N)
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &arr[i])
	}
	sort.Slice(arr, func(i, j int) bool {
		return arr[i] < arr[j]
	})
	arr = append([]int64{0}, arr...)
	for i := 0; i < K; i++ {
		var L, R int
		var X int64
		fmt.Fscan(br, &L, &R, &X)
		for j := L; j <= R; j++ {
			arr[j] += X
		}
		cp := make([]int64, N+1)
		left, mid, right := 1, L, R+1
		for idx := 1; idx <= N; idx++ {
			if left < L && (mid > R || arr[left] <= arr[mid]) && (right > N || arr[left] <= arr[right]) {
				cp[idx] = arr[left]
				left++
			} else if mid <= R && (left >= L || arr[mid] <= arr[left]) && (right > N || arr[mid] <= arr[right]) {
				cp[idx] = arr[mid]
				mid++
			} else if right <= N && (left >= L || arr[right] <= arr[left]) && (mid > R || arr[right] <= arr[mid]) {
				cp[idx] = arr[right]
				right++
			}
		}
		copy(arr, cp)
	}
	for i := 1; i <= N; i++ {
		fmt.Fprintf(bw, "%d ", arr[i])
	}

	bw.Flush()
}

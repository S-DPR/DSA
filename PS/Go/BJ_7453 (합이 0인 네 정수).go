package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
7453번 합이 0인 네 정수

수열 A, B, C, D가 주어진다. A[a]+B[b]+C[c]+D[d] = 0인 (a, b, c, d)의 쌍을 구해보자.
각 수열의 길이는 모두 같으며, 4000 이하의 자연수이다.

절륜한 성능의 map을 가진 파이썬과 Go는 map 날먹이 되고, 나머지는 투포인터나 이분탐색 박아야하는 문제.
진짜 두렵다 두려워..

MITM, 중간에서 만나기를 기반으로 하여 해시를 쓰든 투포인터를 쓰든 이분탐색을 쓰는 문제입니다.
리해싱 엄청나게 박히는 C++같은거론 아마 해시는 안될것같아요.
성능은 이분탐색 < 투포인터 < 해시(파이썬/Go) 이고,
먹는 메모리는 투포인터 < 이분탐색 < 해시(파이썬/Go) 인것같습니다.

어.. 근데 먹는 메모리가 어느정도냐면, 기본 메모리 1기가 + Go 추가메모리 512MB인데,
해시로 풀면 메모리를 1489380KB, 다시말해 1452MB를 먹습니다.
해시 카운터 문제인데 해시로 풀리는게 어이가없네..
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	var N int
	fmt.Fscan(br, &N)
	arr := make([]int, N)
	brr := make([]int, N)
	crr := make([]int, N)
	drr := make([]int, N)
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &arr[i], &brr[i], &crr[i], &drr[i])
	}
	cdrr := make(map[int]int)
	for i := 0; i < N; i++ {
		for j := 0; j < N; j++ {
			cdrr[crr[i]+drr[j]]++
		}
	}
	res := 0
	for _, i := range arr {
		for _, j := range brr {
			res += cdrr[-i-j]
		}
	}
	fmt.Fprintln(bw, res)

	bw.Flush()
}

/*
이분탐색
package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
)

func lowerBound(arr []int, x int) int {
	left, right := 0, len(arr)
	for left < right {
		mid := (left + right) >> 1
		if arr[mid] >= x {
			right = mid
		} else {
			left = mid + 1
		}
	}
	return right
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	var N int
	fmt.Fscan(br, &N)
	arr := make([]int, N)
	brr := make([]int, N)
	crr := make([]int, N)
	drr := make([]int, N)
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &arr[i], &brr[i], &crr[i], &drr[i])
	}
	abrr := make([]int, 0)
	for i := 0; i < N; i++ {
		for j := 0; j < N; j++ {
			abrr = append(abrr, arr[i]+brr[j])
		}
	}
	cdrr := make([]int, 0)
	for i := 0; i < N; i++ {
		for j := 0; j < N; j++ {
			cdrr = append(cdrr, crr[i]+drr[j])
		}
	}
	sort.Ints(abrr)
	sort.Ints(cdrr)
	res := int64(0)
	for _, i := range abrr {
		res += int64(lowerBound(cdrr, -i+1)) - int64(lowerBound(cdrr, -i))
	}
	fmt.Fprintln(bw, res)

	bw.Flush()
}
*/

/*
투포인터
package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
)

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	var N int
	fmt.Fscan(br, &N)
	arr := make([]int, N)
	brr := make([]int, N)
	crr := make([]int, N)
	drr := make([]int, N)
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &arr[i], &brr[i], &crr[i], &drr[i])
	}
	abrr := make([]int, 0)
	for i := 0; i < N; i++ {
		for j := 0; j < N; j++ {
			abrr = append(abrr, arr[i]+brr[j])
		}
	}
	cdrr := make([]int, 0)
	for i := 0; i < N; i++ {
		for j := 0; j < N; j++ {
			cdrr = append(cdrr, crr[i]+drr[j])
		}
	}
	sort.Ints(abrr)
	sort.Ints(cdrr)
	res := int64(0)
	curIdx := len(cdrr) - 1
	acc, std := int64(0), 1<<30
	for _, i := range abrr {
		if -i == std {
			res += acc
			continue
		} else {
			acc = 0
		}
		for 0 <= curIdx && cdrr[curIdx] > -i {
			curIdx--
		}
		for 0 <= curIdx && cdrr[curIdx] == -i {
			res++
			acc++
			std = cdrr[curIdx]
			curIdx--
		}
	}
	fmt.Fprintln(bw, res)

	bw.Flush()
}

*/

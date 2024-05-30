package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
)

/*
29157번 폭탄 피하기

그리드의 크기 R, C가 주어지고, 폭탄의 개수 K가 주어진다.
이후 폭탄의 좌표가 주어진다.
폭탄을 밟지 않고 (0, 0)에서 (C, R)로 가는 경우의 수를 구하시오.
단, 우측과 아래로만 이동할 수 있다.

어..
일단 같은것이있는 순열..이고.
정렬 하나 합시다. 폭탄은 x가 작은순, y가 작은순으로.
그리고 이제 계산을하는데..

이게 팩토리얼 모듈러역원 매번 새로 구하다가 tle나서 한번에 전처리했는데,
빠른사람들꺼 보니까 이런거 전처리 한번에하는 방법이 있네요??
와.. 알아둬야겠는데 이건..
*/
func h(i, j [2]int) int64 {
	x := j[0] - i[0]
	y := j[1] - i[1]
	if x < 0 || y < 0 {
		return 0
	}
	return perm(x, y)
}

func perm(i, j int) int64 {
	return ((facto[i+j] * moduler[i]) % mod * moduler[j]) % mod
}

var (
	facto, inv [2_000_001]int64
	moduler    [2_000_001]int64
	mod        int64 = 1_000_000_007
)

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	facto[0] = 1
	facto[1] = 1
	moduler[0] = 1
	moduler[1] = 1
	inv[1] = 1
	for i := int64(2); i <= 2_000_000; i++ {
		facto[i] = (facto[i-1] * i) % mod
		inv[i] = mod - (mod/i)*inv[mod%i]%mod
		moduler[i] = (moduler[i-1] * inv[i]) % mod
	}

	var R, C, K int
	fmt.Fscan(br, &R, &C, &K)
	bomb := make([][2]int, K)
	for i := 0; i < K; i++ {
		fmt.Fscan(br, &bomb[i][0], &bomb[i][1])
	}
	sort.Slice(bomb, func(u, v int) bool {
		if bomb[u][0] != bomb[v][0] {
			return bomb[u][0] < bomb[v][0]
		}
		return bomb[u][1] < bomb[v][1]
	})
	ret := perm(R, C)
	for i := 1; i < 1<<K; i++ {
		cnt, target := 0, make([][2]int, 0)
		target = append(target, [2]int{0, 0})
		for j := 0; 1<<j <= i; j++ {
			if i&(1<<j) != 0 {
				cnt++
				target = append(target, bomb[j])
			}
		}
		target = append(target, [2]int{R, C})
		sign := int64(1)
		if cnt&1 == 1 {
			sign = -1
		}
		cur := int64(1)
		for j := 0; j < len(target)-1; j++ {
			cur = (cur * h(target[j], target[j+1])) % mod
		}
		ret = (ret + sign*cur + mod) % mod
	}
	fmt.Println(ret)
}

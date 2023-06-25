package main

import (
	"bufio"
	"fmt"
	"os"
)
/*
1131번 숫자

F(n)을 n의 각자리수를 k제곱 하여 더한 값으로 정의하자.
n을 u 이상 v 이하의 모든 자연수라고 할 때,
F(F(F(...F(n)...)))의 최솟값의 합을 구해보자.

'사이클이 있을거라는 믿음'
이거 하나로 밀어붙이는 문제.
맘에 안들지 몰라도 두 번의 상태를 체크해야합니다.

저같은경우 [수가 현재 방문되지 않음], [수가 이전에 한 번 방문됨], [수가 사이클을 생성함] 이 세개로 구분했고,
첫번째는 map에 존재 여부, 두번째는 현재 값이 -1, 세번째는 -2. 이렇게 정의했습니다.
순회하다 보통은 첫번째 상태를 마주하는데, 그러면 걔들은 -1로 초기화됩니다.
그러다보면 사이클 생겨서 -1을 재방문하는데, 이때는 자기를 -2로 초기화합니다.
이제 -2를 다시 만날때까지 최솟값 저장해주고, 만나면 사이클의 최솟값으로 모두 초기화해줍니다.

dp랑 그래프 섞인 악질문제였습니다. 으..
*/
func min(i, j int) int {
	if i > j {
		return j
	}
	return i
}

func prod(i, k int) int {
	ret := 0
	for i > 0 {
		cur := i % 10
		pls := 1
		for j := 0; j < k; j++ {
			pls *= cur
		}
		ret += pls
		i /= 10
	}
	return ret
}

func loop(items map[int]int, i, k, curMin int) int {
	val, err := items[i]
	if !err {
		items[i] = -1
		newI := prod(i, k)
		items[i] = min(i, loop(items, newI, k, newI))
	} else if val == -1 {
		items[i] = -2
		newI := prod(i, k)
		items[i] = min(i, loop(items, newI, k, newI))
	} else if val == -2 {
		items[i] = curMin
	}
	return items[i]
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var u, v, k int
	fmt.Fscan(br, &u, &v, &k)
	items := make(map[int]int)
	ret := 0
	for i := u; i <= v; i++ {
		ret += loop(items, i, k, i)
	}
	fmt.Fprintln(bw, ret)
}

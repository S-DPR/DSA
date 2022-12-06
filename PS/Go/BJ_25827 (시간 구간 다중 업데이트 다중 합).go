package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

/*
25827번 시간 구간 다중 업데이트 다중 합

아래 내용의 시간 쿼리가 N개 주어집니다. 2번 쿼리에 대한 답을 출력해주세요.
1. [h1:m1:s1 : h2:m2:s2] 범위에 1을 더합니다.
2. [h1:m1:s1 : h2:m2:s2] 범위의 합을 구합니다.
단, 1번 쿼리가 끝난 뒤에 2번 쿼리가 나옵니다.

이게 1시간 20분이나 걸릴 문제였나..
마지막 조건이 없었다면 평범한 플레4 레이지세그트리가 될뻔했으나..
있어버려서 난이도가 실버1까지 쭉 떨어졌습니다. 전 개인적으로 골드5 줬지만.
물론 레이지세그트리로 풀 수 있고, 그건 거의 공식수준이라 간단하긴 한데,
이런 실버문제에 그런 고급기술을 쓸 필요는 없죠. 다르게 봅시다.

생각을 이런식으로 해봅시다.
범위가 저렇게 주어져서 그렇지, 제가 그동안 처리한 쿼리문제와 비슷한 범위를 가집니다.
평소 쿼리문제는 100,000개의 배열을 주었지만 이 문제는 86400개를 준 정도.
그래도 1번쿼리를 매번 O(n)으로 처리하기엔 너무 느리죠.
2번쿼리도 매번 O(n)으로 처리하기엔 지나치게 느립니다.
86400에 10만에 10만을 곱하면 8,640,000,000,000,000가 되어 시간 내에 답을 낼 수 없게됩니다.

2번쿼리를 O(1)만에 처리하는 방법이 있습니다.
1번쿼리가 끝난 뒤 2번쿼리가 나오므로 누적합을 구해두어서 S[e]-S[s]를 해버리자는거죠.
하지만 한다해도 86,400*99,999 정도이니 아직 부족합니다.

이제 1번쿼리를 O(1)만에 처리해야합니다.
방법은, 끝점과 시작점만 체크를 해두고,
누적합을 구할떄 그걸 이용해서 촤악 더해버리자는거죠.
Sweeping이라는 방식을 이용하는건데, 이러면 이제 전체쿼리 처리에 O(n)이 되어 답을 낼 수 있게 됩니다.
*/

func getT(time string) int {
	arr := strings.Split(time, ":")
	h, _ := strconv.Atoi(arr[0])
	m, _ := strconv.Atoi(arr[1])
	s, _ := strconv.Atoi(arr[2])
	return h*60*60 + m*60 + s + 1
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	var n int
	fmt.Fscan(br, &n)
	arr := make([]int, 86401)
	sweeping := true
	for i := 0; i < n; i++ {
		var Q, s, e string
		fmt.Fscan(br, &Q, &s, &e)
		getS := getT(s)
		getE := getT(e)
		if Q == "1" {
			arr[getS]++
			arr[getE]--
			continue
		}
		if sweeping {
			plus := arr[0]
			for j := 1; j <= 86400; j++ {
				tmp := arr[j]
				arr[j] += arr[j-1] + plus
				plus += tmp
			}
			sweeping = false
		}
		fmt.Fprintln(bw, arr[getE-1]-arr[getS-1])
	}

	bw.Flush()
}

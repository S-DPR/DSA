package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
5875번 오타

(, )로 이루어진 문자열이 주어진다.
괄호를 하나 골라 반대로 할 때, 올바른 괄호 문자열이 되는지 알아보자.
올바른 괄호 문자열은 아래 조건을 만족한다.
 - ()는 올바르다.
 - A가 올바른 괄호 문자열이라면 (A)는 올바르다.
 - A와 B가 올바르다면, AB도 올바르다.

누-적합문제.
이제 슬슬 골드따리에선 브루트포스/DP/그리디 빼고는 태그가 눈에 보입니다.

아이디어를 잘 내야하는 골드따리문제인데요..
제 아이디어는 이렇습니다.
일단 (, )를 각각 1과 -1로 치환합니다.
이후 누적합을 구해줍시다. 이를 prefix라고 합시다.

누적합의 맨 뒷값, 즉 모든 합을 보았을 때,
2 혹은 -2가 아니라면 하나를 거꾸로 써 제대로 될 수 없습니다.
그러므로 2 혹은 -2만 보면 되는데요.

2인경우, 먼저 prefix에서 음수가 있나 체크합니다. 없을경우 아래 문장을 실행합니다.
 - prefix의 제일 뒤에서부터, prefix[idx]가 2 이상이고 (라면 result에 1을 더합니다.
 - prefix[idx]가 1 이하인 인덱스를 만났다면, 그 앞부분에서 (를 )로 바꾸면 이 인덱스가 -가 되게 됩니다.
 - 그러므로, 앞부분에는 더이상 바꿀 수 있는 부분이 없습니다. prefix[idx] <= 1이면 break합니다.

-2인경우 아래 문장만 실행하면 됩니다.
 - 제일 앞부분부터 )라면 1을 추가합니다.
 - 그러다가 prefix가 음수인 부분을 만난다면 (인지 체크를 하고 1을 추가까지 한 뒤에 break합니다.
 - 음수인 부분은 지금 잘못된 부분인데, 이 뒤쪽을 (로 고친다고 이 부분이 고쳐지지 않기 때문입니다.
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	var S string
	fmt.Fscan(br, &S)
	length := len(S)
	arr := make([]int, length)
	for i := 0; i < length; i++ {
		if S[i] == '(' {
			arr[i] = 1
		} else {
			arr[i] = -1
		}
	}
	prefix := make([]int, length)
	prefix[0] = arr[0]
	for i := 1; i < length; i++ {
		prefix[i] = prefix[i-1] + arr[i]
	}
	result := 0
	if prefix[length-1] == 2 {
		flg := false
		for i := length - 1; i >= 0; i-- {
			if prefix[i] < 0 {
				flg = true
				break
			}
		}
		if !flg {
			for i := length - 1; i >= 0 && prefix[i] >= 2; i-- {
				if S[i] == '(' {
					result++
				}
			}
		}
	} else if prefix[length-1] == -2 {
		for i := 0; i < length; i++ {
			if S[i] == ')' {
				result++
			}
			if prefix[i] < 0 {
				break
			}
		}
	}

	fmt.Fprintln(bw, result)

	bw.Flush()
}

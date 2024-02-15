package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
19643번 좀비 떼가 전역 때보다 먼저 오다니

좀비가 집을 향해 오고있다! 아래 3 행동 중 하나를 할 수 있다.
1. 파워가 B1인 총을 쏜다. 횟수에 제한은 없다.
2. 파워가 B2인 총을 쏜다. 최대 B2A번 사용 가능하다.
3. 파워가 B3인 총을 쏜다. 이 총은 쏜 곳을 포함해 B3L칸 뒤까지 공격할 수 있다. B3A번 사용 가능하다.
과연 좀비가 나를 향해 오기 전까지 모든 좀비를 죽일 수 있을까?

문제 보고
ㅋㅋ 요리보고 조리보고 째려보고 야려봐도 백트래킹 간단하네 ㅋㅋ
어림도없지 즉시 구현 1억배

솔직히 백트래킹..느낌보단 재귀 얼마나 잘써? 물어본거같은데.
딱히 시간초과가 나진 않았습니다. 그런데 진짜 간절하게 기도했을 뿐.
시간초과는 1번, 나머지 10번은 다 틀렸습니다..
진짜 어떻게해야하나 고민 좀 많이했네요.
*/
var (
	L            int
	B1           int
	B2, B2A      int
	B3, B3L, B3A int
	A, b3        []int
)

func loop(l, target, pf, b3pf, b2a, b3a int) bool {
	nxtTarget, nxtPf, nxtB3pf := target, pf, b3pf
	kill := A[target] <= pf+b3pf+b3[target]
	if kill {
		for i := target + 1; i <= L+1; i++ {
			if A[i] != 0 {
				nxtTarget = i
				break
			}
		}
		if A[target] <= b3[target] {
			return loop(l, nxtTarget, pf, b3pf, b2a, b3a)
		}
		for i := target; i < target+B3L; i++ {
			b3[i] += b3pf
		}
		ret := loop(l, nxtTarget, 0, 0, b2a, b3a)
		for i := target; i < target+B3L; i++ {
			b3[i] -= b3pf
		}
		return ret
	}
	if l == L {
		return nxtTarget == L+1
	}
	if l == nxtTarget {
		return false
	}
	ret := false
	ret = ret || loop(l+1, nxtTarget, nxtPf+B1, nxtB3pf, b2a, b3a)
	if b2a > 0 {
		ret = ret || loop(l+1, nxtTarget, nxtPf+B2, nxtB3pf, b2a-1, b3a)
	}
	if b3a > 0 {
		ret = ret || loop(l+1, nxtTarget, nxtPf, nxtB3pf+B3, b2a, b3a-1)
	}
	return ret
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	fmt.Fscan(br, &L)
	fmt.Fscan(br, &B1)
	fmt.Fscan(br, &B2, &B2A)
	fmt.Fscan(br, &B3, &B3L, &B3A)
	A = make([]int, L+2)
	b3 = make([]int, L+B3L+2)
	A[L+1] = 1_000_000_000
	for i := 1; i <= L; i++ {
		fmt.Fscan(br, &A[i])
	}
	if loop(0, 1, 0, 0, B2A, B3A) {
		fmt.Println("YES")
	} else {
		fmt.Println("NO")
	}
}

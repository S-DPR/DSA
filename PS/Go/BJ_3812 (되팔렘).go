package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
3812번 되팔렘

물건이 N개 있다. 각 물건은 현재 가격과 나중 가격으로 구성되어있다.
P명의 사람이 물건을 팔고 있는데, 각 줄의 첫번째 수로 몇 개를 팔고있는지 자연수 K로 주어지고,
이후 2K개의 수가 주어진다. 각각 둘로 묶어 i번째 물건을 k개 갖고있다는 의미이다.
현재 C원을 갖고 있을 때, 물건을 사들여 최대한의 이득을 보려한다. 얼마의 이득을 볼 수 있을까?

수상할정도로 테스트케이스가 약한 문제.
사실 딴건 잘 모르겠고 푼사람이 3명이라길래 힙스터 점수로 풀었습니다.
답은 냅색인데, 냅색인건 금방 알았는데 문제는 C가 너무 크다는 점..
그래서 고민하다가 생각한게 거를거 다 거르고 map 우겨넣어서 처리하자! 인데 이게 진짜 됐네요.
이상해서 딴사람 코드 보니 gcd를 쓰더라고요? 정해도 그렇고?
그래서 좀 이상한데.. 하면서 따라 쳐보니 1100ms정도 줄었습니다.
신기하네..
*/
type info struct {
	price, adv int64
}

func gcd(x, y int64) int64 {
	if y > 0 {
		return gcd(y, x%y)
	}
	return x
}

func max(i, j int64) int64 {
	if i < j {
		return j
	}
	return i
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var C, N, P int64
	fmt.Fscan(br, &C, &N, &P)
	items := make([]info, N+1)
	sale := make([]info, P)
	for i := int64(1); i <= N; i++ {
		var u, v int64
		fmt.Fscan(br, &u, &v)
		items[i] = info{u, v - u}
	}
	packGcd := int64(0)
	for i := int64(0); i < P; i++ {
		var c int
		fmt.Fscan(br, &c)
		price, adv := int64(0), int64(0)
		for j := 0; j < c; j++ {
			var u, v int64
			fmt.Fscan(br, &u, &v)
			price += items[u].price * v
			adv += items[u].adv * v
		}
		sale[i] = info{price, adv}
		if price >= 0 && price <= C {
			packGcd = gcd(packGcd, price)
		}
	}
	dp := make([]int64, C/packGcd+1)
	newC := C / packGcd
	for _, item := range sale {
		if item.adv <= 0 || item.price > C {
			continue
		}
		newPrice := item.price / packGcd
		for i := newC - newPrice; i >= 0; i-- {
			if dp[i] == 0 {
				continue
			}
			dp[i+newPrice] = max(dp[i+newPrice], dp[i]+item.adv)
		}
		dp[newPrice] = max(dp[newPrice], item.adv)
	}
	ret := int64(0)
	for _, val := range dp {
		ret = max(ret, val)
	}
	fmt.Fprintln(bw, ret)
}

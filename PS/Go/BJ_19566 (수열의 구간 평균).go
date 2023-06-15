package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
19566번 수열의 구간 평균

길이가 N인 수열에서 평균이 K인 연속 부분 수열의 개수를 찾아보자.

아니
아니 이걸
이걸 아니 진짜
이걸 진짜로 3시간을 박았다고?????
머야 내 1년 돌려줘요

플레5 하나 분할정복인줄 알고 풀다가 실컷 틀리고 스택문제인거 확인하고 정신 나가서 고른 문제
진짜 아니 이게 근데 님들아 아니..
어떻게 이걸 divmod를 할 생각을 하고 난리를쳤지?
다시생각해봐도 이해가 안되는데?

i번째 수에 대하여, K*i의 값을 가지면 평균은 K가 됩니다. 이는 자명하죠.
그러면 K*i-arr[i]를 하면 arr[i]와 K*i의 차이를 구할 수 있고,
이 수가 이전 수열에 있는지 체크하면 끝나는 문제입니다.
아니 진짜 아....
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N, K int64
	fmt.Fscan(br, &N, &K)
	arr := make([]int64, N+1)
	items := make(map[int64]int64)
	items[0]++
	ret := int64(0)
	for i := int64(1); i <= N; i++ {
		fmt.Fscan(br, &arr[i])
		arr[i] += arr[i-1]
		ret += items[K*i-arr[i]]
		items[K*i-arr[i]]++
	}
	fmt.Println(ret)
}

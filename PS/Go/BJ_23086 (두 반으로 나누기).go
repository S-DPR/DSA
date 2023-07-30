package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
23086번 두 반으로 나누기

그래프를 이분그래프로 만들기 위해 정해진 간선을 최소한으로 제거하려 한다.
적어도 몇 개를 제거해야할까?
단, 초기 그래프는 연결그래프이며 정해진 간선을 모두 제거해도 연결그래프임이 보장된다.

매개변수탐색한테 4달만에 쳐맞으니까 좀 어지럽네

문제 또 잘못읽어서 삽질 5번 한 문제
그와중에 처음 제출한게 또 정답에 제일 가까웠고, dfs 한번 더 안돌려서 틀린거야
앆!!!!!!!!!!!!!!!!!!!!!!!!

아니 근데 님들아 제말좀 들어봐요 나 억울해
간선 제거한 이후에도 연결그래프여야한다!가 아니라 간선을 제거해도 연결그래프이다! 래요 님들아
이거 나 억울해서 어떻게해 아
아..
하..

그래서.. 어쨌든 매개변수탐색 다 굴리면됩니다.. 네..
아니 근데 검색해보니 출제진들이 정해는 이런거에요~ 올려놨던데
내가 처음에 태그 까기 전에 생각한게 진짜 정해고 매개변수탐색은 출제후에 알았다더라..
아.. 무섭다고 태그 까지 말고 우직하게 갈걸..
*/
type info struct {
	dst, n int
}

var (
	G   [][]info
	Kth []int
	V   []int
)

func dfs(x, c, u int) bool {
	if V[x] != 0 {
		return V[x] == c
	}
	V[x] = c
	ret := true
	for _, j := range G[x] {
		if Kth[j.n] == 0 || Kth[j.n] > u {
			ret = ret && dfs(j.dst, c^1, u)
		}
	}
	return ret
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N, M, K int
	fmt.Fscan(br, &N, &M, &K)
	G = make([][]info, N+1)
	Kth = make([]int, M+1)
	for i := 0; i <= N; i++ {
		G[i] = make([]info, 0)
	}
	for i := 1; i <= M; i++ {
		var u, v int
		fmt.Fscan(br, &u, &v)
		G[u] = append(G[u], info{v, i})
		G[v] = append(G[v], info{u, i})
	}
	for i := 1; i <= K; i++ {
		var k int
		fmt.Fscan(br, &k)
		Kth[k] = i
	}
	left, right := 0, K+1
	for left < right {
		mid := (left + right) >> 1
		V = make([]int, N+1)
		if dfs(1, 2, mid) {
			right = mid
		} else {
			left = mid + 1
		}
	}
	V = make([]int, N+1)
	dfs(1, 2, right)
	cnt := make([]int, 2)
	for i := 1; i <= N; i++ {
		cnt[V[i]&1]++
	}
	if cnt[1] < cnt[0] {
		cnt[0], cnt[1] = cnt[1], cnt[0]
	}
	if right != K+1 {
		fmt.Fprintln(bw, right)
		fmt.Fprintf(bw, "%d %d", cnt[0], cnt[1])
	} else {
		fmt.Fprintln(bw, -1)
	}
}

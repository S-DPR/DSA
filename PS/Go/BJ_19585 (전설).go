package main

import (
	"bufio"
	"fmt"
	"os"
)

/*
19585번 전설

문자열 S가 N개, 문자열 T가 M개 주어진다.
이후 문자열 K가 Q개 주어진다. 각 문자열 K에 대해, S+T로 만들 수 있는지 구해보자.

보자마자 트라이? 오케이
그런데.. T를 보는 방식이 좀 신기했습니다.
무지성으로 2중For문 박아서 트라이 히히 하면서 구현했는데,
메모리초과를 한 3번 받고.. 질문게시판 뒤져보니,

T는 해시테이블에 넣고, S가 있다면 그 해시테이블에 나머지가 있나 체크해보자!
라는 아이디어가 있길래 한번 해보니 됐네요.
시간복잡도 생각해보면, 최대 O(len(S))만큼의 시간이 투자되니..
이건 좀 신박했다..
*/
var name map[string]bool

type Trie struct {
	child map[byte]*Trie
	exist bool
}

func getTrie() *Trie {
	return &Trie{
		child: make(map[byte]*Trie),
		exist: false,
	}
}

func (trie *Trie) insert(s string, kth int) {
	if len(s) == kth {
		trie.exist = true
		return
	}
	ch := s[kth] - 'a'
	if trie.child[ch] == nil {
		trie.child[ch] = getTrie()
	}
	trie.child[ch].insert(s, kth+1)
}

func (trie *Trie) find(s string, kth int) bool {
	if len(s) == kth {
		return name[s[kth:len(s)]]
	}
	if trie.exist && name[s[kth:len(s)]] {
		return true
	}
	ch := s[kth] - 'a'
	if trie.child[ch] == nil {
		return false
	}
	return trie.child[ch].find(s, kth+1)
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var N, M, Q int
	var s string
	fmt.Fscan(br, &N, &M)
	color := make([]string, N)
	name = make(map[string]bool)
	for i := 0; i < N; i++ {
		fmt.Fscan(br, &color[i])
	}
	for i := 0; i < M; i++ {
		fmt.Fscan(br, &s)
		name[s] = true
	}
	trie := getTrie()
	for i := 0; i < N; i++ {
		trie.insert(color[i], 0)
	}
	fmt.Fscan(br, &Q)
	for i := 0; i < Q; i++ {
		fmt.Fscan(br, &s)
		if trie.find(s, 0) {
			fmt.Fprintln(bw, "Yes")
		} else {
			fmt.Fprintln(bw, "No")
		}
	}
}

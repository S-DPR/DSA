package main

import (
	"bufio"
	"fmt"
	"math"
	"os"
)

/*
3526번 이상적인 경로

1번 방부터 N번 방으로 이동해야하는 미로가 있다.
각 통로를 지날 때마다 숫자를 하나 적어야하는데,
숫자가 적을수록, 숫자 수가 같다면 작은 수가 더 앞에 올수록 이상적인 경로가 된다.
가장 이상적인 경로를 구해보자.

루비님 루비님!!!!!!!! 정신을 좀 차려보세요!!!!!!!

언제나 그랬듯이 같은 로직 다른 결과를 보여주는 루비.
그나저나, 진짜 반년 가까이 북마크에 있던거 드디어 하나 끝냈습니다.
거 뭐냐, 거의 최단경로 문제가 생각나더라고요.
도착점에서 정점 i까지 거리를 다 구한 뒤, 출발점에서부터 인접한 노드 중,
(자신이 있는 도착점과의 거리 - 1)를 도착점과의 거리로 하는 노드로 간다면 항상 최단거리가 됨을 보장할 수 있습니다.

그 외에는 visit을 안써서 한페이지정도 더 시간초과로 틀렸단점..?
BFS의 시간초과는 거의 무조건 visit문제다.. 라는걸 다시 생각하게 되었습니다..
*/
var (
	N, M int
	INF  = math.MaxInt
	G    []map[int]int
)

func bfs() []int {
	dist := make([]int, N+1)
	for i := 0; i <= N; i++ {
		dist[i] = INF
	}
	dist[N] = 0
	Q := [][]int{{N, 0}}
	for len(Q) > 0 {
		cur := Q[0]
		curN, curT := cur[0], cur[1]
		Q = Q[1:]
		for nxtN := range G[curN] {
			if dist[nxtN] == INF {
				dist[nxtN] = curT + 1
				Q = append(Q, []int{nxtN, curT + 1})
			}
		}
	}
	return dist
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()
	min := func(i, j int) int {
		if i < j {
			return i
		}
		return j
	}

	fmt.Fscan(br, &N, &M)
	G = make([]map[int]int, N+1)
	for i := 0; i <= N; i++ {
		G[i] = make(map[int]int)
	}
	for i := 0; i < M; i++ {
		var u, v, c int
		fmt.Fscan(br, &u, &v, &c)
		if u != v {
			_, e := G[u][v]
			if !e {
				G[u][v] = INF
				G[v][u] = INF
			}
			G[u][v] = min(G[u][v], c)
			G[v][u] = min(G[v][u], c)
		}
	}
	dist := bfs()
	Q := []int{1}
	visit := make([]bool, N+1)
	color := []int{}
	for len(Q) > 0 {
		minC := INF
		for _, cur := range Q {
			for nxtN, nxtC := range G[cur] {
				if dist[nxtN] == dist[cur]-1 {
					minC = min(minC, nxtC)
				}
			}
		}
		length := len(Q)
		for i := 0; i < length; i++ {
			cur := Q[0]
			Q = Q[1:]
			for nxtN, nxtC := range G[cur] {
				if visit[nxtN] || minC < nxtC || dist[nxtN] != dist[cur]-1 {
					continue
				}
				visit[nxtN] = true
				Q = append(Q, nxtN)
			}
		}
		if minC != INF {
			color = append(color, minC)
		} else {
			break
		}
	}
	fmt.Fprintln(bw, len(color))
	for i := 0; i < len(color); i++ {
		fmt.Fprintf(bw, "%d ", color[i])
	}
}

/*
루비는 아래코드. (시간초과)
def bfs(start)
  dist = [INF]*(N+1)
  dist[start] = 0
  queue = [[start, 0]]
  until queue.empty?
    curN, curT = queue.shift
    G[curN].each do |nxtN, _|
      next if dist[nxtN] != INF
      dist[nxtN] = curT+1
      queue.push([nxtN, curT+1])
    end
  end
  dist
end

INF = 1_000_000_001
N, M = gets.split.map &:to_i
G = (N+1).times.map do Hash.new(INF) end
M.times do
  u, v, c = gets.split.map &:to_i
  next if u == v
  G[u][v] = [G[u][v], c].min
  G[v][u] = [G[v][u], c].min
end
dist = bfs(N)
Q = [1]
color = ""
visit = [false]*(N+1)
visit[1] = true
color_cnt = 0
until Q.empty?
  minC = Q.map do |cur|
    G[cur].map do |nxtN, nxtC|
      dist[nxtN] != dist[cur]-1 ? INF : nxtC
    end.min
  end.min
  Q.length.times do
    cur = Q.shift
    G[cur].each do |nxtN, nxtC|
      if !visit[nxtN] && nxtC == minC && dist[nxtN] == dist[cur]-1
        Q << nxtN
        visit[nxtN] = true
      end
    end
  end
  break if minC == INF
  color += "#{minC} "
  color_cnt += 1
end
print color_cnt, "\n", color
*/
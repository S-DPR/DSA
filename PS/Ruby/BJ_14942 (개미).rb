=begin
14942번 개미

N이 주어진다.
트리의 모든 노드에 개미가 있고, N개의 줄에 개미의 체력이 주어진다.
간선과 그 가중치가 주어질 때, 1부터 N까지 모든 노드에 있는 개미에 대하여,
K번째 개미가 최대 몇 번째 층까지 올라갈 수 있는지 체크해보자.

희소배열 아십니까? 문제 개수 자체가 LCA 빼면(사실 포함해도) 정말 적은 문제입니다!
그런데 걸렸네.
문제 헌팅하고 있는 도중, 그냥 슥 읽어보니 "어 이거 희소배열인데?" 그래서 열심히 풀었습니다.
이제 희소배열로 부모노드로 올라가는건 좋은데 어떻게 그 거리를 얻느냐..가 문제였는데,
조금 건드려보니 그냥 1번노드와 거리 굴리면 되더라고요. 어차피 트리라서 그냥 BFS 굴리면 되고.
결국 트리+BFS+희소배열+누적합 조합으로 풀었습니다.

가장 높게 올라가는걸 어떻게 찾나가 복병이었는데, 그냥 뭐 while 굴렸습니다..
한 시간만에 푼 문제. 와우!
=end 
INF = 1<<31
N = gets.to_i
ants = [0] + N.times.map do gets.to_i end
G = (N+1).times.map do [] end
(N-1).times do
  u, v, w = gets.split.map &:to_i
  G[u] << [v, w]
  G[v] << [u, w]
end
Q = [1]
dist = [0]*(N+1)
log = Math.log2(N).ceil
sparse = (N+1).times.map do [0]*log end
until Q.empty?
  cur = Q.shift
  G[cur].each do |dst, w|
    next if dist[dst] != 0
    sparse[dst][0] = cur
    dist[dst] = dist[cur]+w
    Q << dst
  end
end
sparse[1][0] = 0
dist[1] = 0
(1...log).each do |i|
  (1..N).each do |j|
    sparse[j][i] = sparse[sparse[j][i-1]][i-1]
  end
end
ret = []
(1..N).each do |cur|
  hp = ants[cur]
  log.times.reverse_each do |j|
    while cur != 1
      nxt = sparse[cur][j]
      need = dist[cur]-dist[nxt]
      break if nxt.zero? || need > hp
      hp -= need
      cur = nxt
    end
  end
  ret << cur
end
puts ret

ini = ->() { gets.to_i }
ins = ->() { gets.split.map &:to_i }
INF = 1<<64
=begin
2021번 최소 환승 경로

지하철 역 N개와 지하철 L개가 있다.
S에서 E로 가려 할 때 최소 몇 번 환승해야할까?

간단한 BFS문제.
보고 10분만에 뚝딱 끝냈네요.

N+L개의 노드가 있다고 생각하면 됩니다.
대신 N에서 L로 가는데는 비용이 들지만 N에서 N으로 가는데는 비용이 들지 않게.
이는 N개의 노드는 그대로 두고 L개의 노드롤 N+1, N+2, ..., N+L번 노드로 만들어서 해결.
그러면 다음 이동이 N보다 크면 환승이라고 쳐도 되겠죠.
이걸 그대로 bfs구현하면 됩니다.
해봤던 유형이라 쉽네요.
=end
N, L = ins.()
G = (N+L+1).times.map do [] end
(1..L).each do |i|
  *l, _ = ins.()
  l.each do |j|
    G[j] << i+N
    G[i+N] << j
  end
end
S, E = ins.()
V = [-1]*(N+L+1)
Q = [S]
V[S] = 0
G[S].each do |i|
  Q << i
  V[i] = 0
end
until Q.empty?
  curN = Q.shift
  G[curN].each do |i|
    next if V[i] != -1
    V[i] = i > N ? V[curN]+1 : V[curN]
    Q << i
  end
end
p V[E]

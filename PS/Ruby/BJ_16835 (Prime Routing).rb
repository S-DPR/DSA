ini = ->() { gets.to_i }
ins = ->() { gets.split.map &:to_i }
INF = 1<<64
=begin
16835번 Prime Routing

가중치가 없는 무향 그래프가 주어진다.
S에서 E로 갈 때 이동한 거리가 소수가 될 수 있을까?
한 간선을 여러번 쓸 수 있다.

그냥 홀짝 나누는 아이디어.
홀짝 나누고 짝수쪽은 2인지만 확인하고 홀수쪽은 항상 짝수번 더할 수 있다는거 활용, 홀수쪽 소수 아무거나 찾으면 됩니다.
보자마자 풀었네 이건..
=end
require 'prime'
N, K, S, E = ins.()
G = (N+1).times.map do [] end
V = (N+1).times.map do [-1, -1] end
K.times do
  u, v = ins.()
  G[u] << v
  G[v] << u
end
Q = [[S, 0]]
until Q.empty?
  n, w = Q.shift
  G[n].each do |i|
    next if V[i][~w&1] != -1
    V[i][~w&1] = w+1
    Q << [i, w+1]
  end
end
ret = V[E][0] == 2 ? 2 : INF
if V[E][1] != -1
  Prime.each do |i|
    next if i == 2
    next if i < V[E][1]
    ret = [ret, i].min
    break
  end
end
p (ret == INF ? -1 : ret)

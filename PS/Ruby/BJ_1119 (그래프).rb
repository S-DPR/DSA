ini = ->() { gets.to_i }
ins = ->() { gets.split.map &:to_i }
INF = 1<<64
=begin
1119번 그래프

그래프가 주어진다.
여기서 네 노드 A, B, C, D를 잡자. 단, A와 B는 이어져있고 C와 D는 이어져있어야한다.
이 때, A-B와 C-D간선을 끊고 아래 두 상태중 하나로 바꿀 수 있다.
1. A-C B-D
2. A-D B-C
그래프를 하나의 연결그래프로 만들 수 있을까? 있다면 최소 몇 번 해야할까?

이런 옛날문제는 볼때마다 숨이 턱 하고 막힙니다.
생각하기 전까진 어떻게 해야할지 아예 감이 안와요.

그래서 나온 답이..
사이클이면 이게 그 사이클 이루는 간선이 직선이 될때까지 빼내도 되거든요?
그니까 그게 '남는 간선'이 됩니다.
그거를 이제 나랑 연결이 안되어있는 다른 이어진 노드 두 개 잡아서 연결하면 되는거죠.
그 남는 간선의 개수는 (이번 연결요소에 있는 간선 개수)/2 - (연결요소에 있는 노드 개수 - 1) 개 입니다.
/2 하는건 무향그래프라서.
=end
dfs = ->(x) do
  @V[x] = 1
  @G[x].map.with_index do |i, idx|
    next if i == 0 || @V[idx] == 1
    dfs.(idx)
  end.compact.sum + @G[x].sum
end

N = ini.()
@G = N.times.map do
  s = gets.strip
  s.chars.map do |i| i == 'N' ? 0 : 1 end
end
@V = [0]*N
ret, leftE = false, 0
conn = 0
N.times do |i|
  ret = true if @G[i].sum == 0
  next if @V[i] == 1 || ret
  prvV = @V.sum
  d = dfs.(i)
  afV = @V.sum
  leftE += d/2 - (afV-prvV-1)
  conn += 1
end
if N == 1
  p 0
else
  p ((ret || leftE < conn-1) ? -1 : conn-1)
end

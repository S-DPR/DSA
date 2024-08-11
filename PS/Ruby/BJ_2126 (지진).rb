ini = ->() { gets.to_i }
ins = ->() { gets.split.map &:to_i }
INF = 1<<64
=begin
2126번 지진

그래프가 주어진다. 각 간선은 건설할 때 비용 c와 시간 t가 필요하다.
그래프에서 트리로 만들 때, (F-비용의 합) / 시간의 합이 최대가 되게 하려고 한다.
소모할 수 있는 최대 비용은 F이다. 이 때, 가능한 최댓값을 구해보자.

이야
이게 플레3의 맛?
앞으로 식 나오면 무조건 전개 해봐야겠어요..

(F-(비용의 합)) / (시간의 합) >= K
F-(비용의 합) >= K*(시간의 합)
F >= K*(시간의 합)+(비용의 합)
그러므로.. 간선을 K*(시간의 합)+(비용의 합)으로 나타내고.
이를 최소화하면 되므로 매개변수탐색..
이야..
=end
def union(u, x, y)
  xp, yp = find(u, x), find(u, y)
  return false if xp == yp
  u[xp] = u[yp]
  return true
end

def find(u, x)
  u[x] != x ? u[x] = find(u, u[x]) : u[x]
end

N, K, F = ins.()
G = K.times.map do ins.() end
lo, hi = 0.0, (F+1).to_f
while hi-lo > 1e-9
  mid = (lo + hi) / 2
  G.sort_by! do |i| i[2]+mid*i[3] end
  u = [*(0..N)]
  s = G.sum do |i, j, c, t|
    union(u, i, j) ? c+mid*t : 0
  end
  s > F ? hi = mid : lo = mid
end
printf "%.4f", hi

spl = ->() { gets.split.map &:to_i }
=begin
10423번 전기가 부족해

정점이 N개인 그래프에 특별한 M개의 정점이 있다.
모든 정점에서 특별한 정점으로 이동할 수 있게 하려한다.
그 최소 가중치를 구해보자.

보자마자 '머야 이거 MST아닌가?' 하고 문제 다시 확인하고,
MST 확신하자마자 풀어서 거의 5분만에 푼 문제.
문제는 이렇게풀어야하는데..

저같은경우 특별한 정점을 관리하는 P 배열을 다시 만들었는데,
"특별한 정점을 미리 합쳐둔다" 라는 놀라운 방법을 알게되었습니다.
와.. 진짜 훨씬 더 깔끔한방법이다.. 란 생각이 들었네요..
=end
find = ->(x) {
  U[x] = find.(U[x]) if U[x] != x
  U[x]
}

union = ->(x, y) {
  xp, yp = find.(x), find.(y)
  P[xp] = P[yp] = P[xp]|P[yp]
  U[xp] = U[yp]
}

N, M, K = spl.()
P = [0]*(N+1)
U = Array(0..N)
spl.().each { |i| P[i] = 1 }
G = M.times.map { spl.() }.sort_by { |i| i[2] }
ret = G.map do |u, v, w|
  next if find.(u) == find.(v)
  next if (P[find.(u)]&P[find.(v)]).positive?
  union.(u, v)
  w
end.compact.sum
p ret

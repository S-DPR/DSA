ini = ->() { gets.to_i }
ins = ->() { gets.split.map &:to_i }
INF = 1<<64
=begin
17947번 상남자 곽철용

1부터 4*N까지 숫자가 적힌 카드가 각각 1장씩 있다.
여기서 2*M개의 카드를 버리고, 두 개의 카드를 취했다.
이 때 점수는 두 수를 K로 나눈 나머지의 차이일 때,
자신보다 점수가 높은 사람의 최대 수를 구해보자.

이야 투포인터를 이렇게쓰네
요즘 그냥 답지보면서 빠르게 넘기는데, 솔직히 조금 놀랍습니다.
이걸 어떻게 생각하지??가 좀 커요

나온 배열을 두 개의 그룹으로 나눕니다.
간단하게, 중간 기준 왼쪽과 오른쪽으로..
그상태에서 이분탐색을 해주는데,
대신 답이 M-1을 넘지 않는다는 점을 고려해야한다네요.
캬.. 신기하다..
=end
N, M, K = ins.()
A = [1]*(N*4+1)
M.times do
  ins.().each do |i| A[i] = 0 end
end
u, v = ins.()
A[u] = A[v] = 0
mn = ((u%K) - (v%K)).abs
remainder = (1..4*N).map do |i|
  i%K if A[i] == 1
end.compact.sort
lo, hi = 0, remainder.length/2
res = 0
while hi < remainder.length && res < M-1
  if remainder[hi]-remainder[lo] > mn
    res += 1
    lo += 1
  end
  hi += 1
end
p res

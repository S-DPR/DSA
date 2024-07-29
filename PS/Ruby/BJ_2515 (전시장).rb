ini = ->() { gets.to_i }
ins = ->() { gets.split.map &:to_i }
INF = 1<<64
=begin
2515번 전시장

u, v로 주어진 수열이 주어진다.
다음 규칙에 따라 원소를 고를 때, 고른 요소의 v 합을 최대화해보자.
 - 고른 원소를 u를 기준으로 정렬했을 때 간격이 S 이상이다.

보자마자 어 dp다
근데 음.. u 어떻게구하지?
아몰라 이분탐색 슛~ 해서 맞은 문제.

푸는데 한 10분..걸렸나
이런거도 걸리니까 좋당
=end
N, S = ins.()
A = [[-INF, 0]] + N.times.map do ins.() end.sort
dp = [0]*(N+1)
(1..N).each do |i|
  u, v = A[i]
  midx = A.bsearch_index do |j| j[0] >= u-S+1 end
  dp[i] = [dp[i-1], dp[midx-1]+v].max
end
p dp.max

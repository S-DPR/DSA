ini = ->() { gets.to_i }
ins = ->() { gets.split.map &:to_i }
INF = 1<<64
=begin
3032번 승리

A와 B가 게임을 한다. A는 처음에 수 하나를 아무거나 고른다.
이후 각 플레이어는 자기 턴마다 인접한 수가 하나인 수만 고를 수 있다.
홀수를 더 많이 고른 사람이 이길 때, 최선의 플레이를 한다면 첫번째로 고를 수 있는 수는 몇 개일까?

그냥 평범한 구간dp.
히히 하면서 슥슥 풀었습니다.
이정도도 플레5구나..
=end
loopfn = ->(l, r, t) do
  return @dp[l][r][t] if @dp[l][r][t] != -1
  nxtL, nxtR = (l+N-1)%N, (r+1)%N
  return 0 if nxtL == r
  getL = loopfn.(nxtL, r, t^1)
  getR = loopfn.(l, nxtR, t^1)
  getL += A[nxtL].odd? ? 1 : 0 if t == 1
  getR += A[nxtR].odd? ? 1 : 0 if t == 1
  @dp[l][r][t] = [getL, getR].send(t == 1 ? :max : :min)
end

N = ini.()
A = ins.()
@dp = N.times.map do N.times.map do [-1]*2 end end
odd = A.filter do |i| i.odd? end.length
p(N.times.sum do |i|
  n = loopfn.(i, i, 0)
  n += A[i].odd? ? 1 : 0
  n > odd/2 ? 1 : 0
end)

ini = ->() { gets.to_i }
ins = ->() { gets.split.map &:to_i }
INF = 1<<64

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

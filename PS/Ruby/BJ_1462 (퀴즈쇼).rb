spl = ->() { gets.split.map &:to_i }
inf = 1<<64

N, K = spl.()
A = [0] + spl.()
B = [0] + spl.()
dp = [[0, 0, 0, -inf]]
# dp[i][0] = i번째를 사용 안함
# dp[i][1] = i번째를 연속적으로 사용함 (보너스 X)
# dp[i][2] = i번째를 연속적으로 사용함 (보너스 O)
# dp[i][3] = i번째를 보너스점수를 얻으며 사용
(1..N).each do |i|
  dp << [-inf]*4
  dp[i][0] = dp[i-1].max - A[i]
  dp[i][1] = dp[i-1][1] + A[i]
  dp[i][2] = [dp[i][1], dp[i-1][2]+A[i], dp[i-1][3]+A[i]].max
  dp[i][3] = dp[i][1]-dp[i-K][1] + [dp[i-K][0], dp[i-K][3]].max + B[i] if i-K >= 0
end
# dp.each do |i| print i, "\n" end
p dp.flatten.max

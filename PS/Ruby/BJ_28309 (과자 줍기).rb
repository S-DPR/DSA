ini = ->() { gets.to_i }
ins = ->() { gets.split.map &:to_i }
INF = 1<<64
=begin
28309번 과자 줍기

R*C 보드에 과자가 N개 떨어져있다.
(1, 1)에서 시작해 (R, C)까지 과자 개수를 최대한 주우면서 가는 경우의 수는 몇개일까?

조금 더 최적화하면 O(N^2) 될거는같은데 그냥 N이 100인김에 O(N^3)으로 풀어버렸습니다.
전에 푼 폭탄 피하기랑 비슷하게 풀었어요.
=end
MOD = 1_000_003
MAX = 2_000_000
facto = [1]*(MAX+1)
inv = [1]*(MAX+1)
moduler = [1]*(MAX+1)
(2..MAX).each do |i|
  facto[i] = (facto[i-1]*i)%MOD
  inv[i] = MOD - (MOD/i)*inv[MOD%i]%MOD
  moduler[i] = (moduler[i-1]*inv[i]) % MOD
end
ini.().times do
  r, c, n = ins.()
  dp = (0..n+1).map do [0]*(n+2) end
  snack = n.times.map do ins.() end
  snack << [1, 1] << [r, c]
  snack.sort!
  dp[0][0] = 1
  snack.each_with_index do |i, iidx|
    r, c = i
    (0..n).each do |k|
      snack.each_with_index do |j, jidx|
        nr, nc = j
        dr, dc = nr-r, nc-c
        next if dr < 0 || dc < 0 || iidx == jidx
        dp[jidx][k+1] = (dp[jidx][k+1]+dp[iidx][k]*facto[dr+dc]*moduler[dr]*moduler[dc])%MOD
      end
    end
  end
  (0..n+1).reverse_each do |i|
    next if dp[-1][i] == 0
    p dp[-1][i]
    break
  end
  p 0 if dp[-1].sum == 0
end

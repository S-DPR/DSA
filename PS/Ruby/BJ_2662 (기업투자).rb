ini = ->() { gets.to_i }
ins = ->() { gets.split.map &:to_i }
INF = 1<<64
=begin
2662번 기업투자

N의 자본이 있고, M개의 기업이 있다.
자본을 모두 사용해 기업에 투자할것인데,
기업에 i원을 투자하면 j번째 기업은 M[i][j]원의 이득을 준다고 한다.
i가 1부터 N까지 모든 수일 때 얻을 수 있는 최대 이익을 구하시오.
어떻게 투자해야하는지까지 구해보자.

냅색역추적
처음엔 M이 수상하게 작아서 의외의 완탐? 허점의 그리디? 생각했는데,
아무리생각해도 이건 배낭이 정배더라고요..

trace배열도 만들어서 갱신해주면 됩니다.
배낭 역추적은 거의 안해봐서 시간 조금 썼네요.
=end
N, M = ins.()
A = M.times.map do [[0, 0]] end
N.times do
  x, *a = ins.()
  M.times do |i|
    A[i] << [x, a[i]]
  end
end
dp = (M+1).times.map do [-1]*(N+1) end
trace = (M+1).times.map do [-1]*(N+1) end
M.times do |i| dp[i][0] = trace[i][0] = 0 end
A.each_with_index do |i, idx|
  idx += 1
  i.each do |w, v|
    (0..N).reverse_each do |j|
      break if j-w < 0
      next if dp[idx-1][j-w] == -1
      if dp[idx][j] <= dp[idx-1][j-w]+v
        dp[idx][j] = dp[idx-1][j-w]+v
        trace[idx][j] = j-w
      end
    end
  end
end
mx_idx = N
ret = []
M.times.map do |i|
  nxt = trace[-1-i][mx_idx]
  ret.unshift(mx_idx-nxt)
  mx_idx = nxt
end
print dp[-1][-1], "\n"
print ret.join(' ')

ini = ->() { gets.to_i }
ins = ->() { gets.split.map &:to_i }
INF = 1<<64
=begin
8311번 Variable Subsequences

수열이 주어진다.
이 수열의 부분 수열 중, 중복된 원소가 연속해 나타나는 경우가 없는 수열의 개수는 몇 개일까?

와 어캐풀지 하면서
스택? 수학? 조합론? 이렇게 망상하고있었는데
와 진짜 모르겠다 하면서 태그 까니까 dp
하..
dp는 진짜 용의범에 넣지도 않았는데 어째서 이런일이..

dp[i] = i로 끝난 부분 수열의 개수.
이러면.. dp처리가 완벽하게 끝납니다.
와.. 진짜 깔끔해서 놀랐다
=end
MOD = 1_000_000_007
MX = 500_000
N = ini.()
cnt = [0]*(MX+1)
total = 0
ret = N
ins.().map do |i|
  get = total-cnt[i]
  total = (total+get+1)%MOD
  cnt[i] = (cnt[i]+get+1)%MOD
  ret = (ret+get)%MOD
end
p ret

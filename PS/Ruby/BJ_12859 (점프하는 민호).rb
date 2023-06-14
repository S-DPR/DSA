=begin
12859번 점프하는 민호

당신은 수직선상의 한 좌표에 서있다.
수 N개와 그 수를 사용하는데 필요한 비용 N개가 주어진다.
수를 사용하면 현재 있는 좌표에서 +i, -i만큼 이동 가능 할 때,
수 몇 개를 사야 모든 좌표를 이동 가능한지 구해보자.
단, 수는 한 번 사면 무한번 사용 할 수 있다.

그럴듯한 문제로 주어진 배주항등식
대충 최소비용으로 수를 골라서 gcd를 1로 만들어보자, 하는 이야기입니다.
N이 유난히 작은 300이라서 이것저것 떠올려봤는데..

1. 브루트포스 / 백트래킹 : 2^300 뻔해서 패스
2. 그래프 : 이건 그냥 모르겠다
3. dp : 2차원dp는 아닌거같은데..
 -> 어 근데 이거 해시로 배낭때릴만하지않나?

해서 풀었습니다.
옛날에 이 문제 처음 보고 배주항등식 공부하고 잊어버린 문제였는데..
이걸 다시보네..
=end
INF = 1<<62
N = gets.to_i
arr = gets.split.map &:to_i
cost = gets.split.map &:to_i
dp = Hash.new(INF)
arr.zip(cost).each do |cur, cost|
  dp.clone.each do |k, _|
    new = k.gcd(cur)
    dp[new] = [dp[new], dp[k]+cost].min
  end
  dp[cur] = [dp[cur], cost].min
end
p dp[1] == INF ? -1 : dp[1]
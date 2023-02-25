=begin
2240번 자두나무

T와 W가 주어지고, 총 T번 1 혹은 2가 주어진다.
처음에는 1만 잡을 수 있고, 방향을 바꾸어 2를 잡을 수 있다.
방향은 1 -> 2, 2 -> 1로 바꿀 수 있으며 최대 W번 바꿀 수 있다.
당신은 최대 몇 개의 수를 잡을 수 있을까?

보면 바로 나오는 '너, DP'.
10분만에 푼거면 꽤 빨리푼거같기도 하고??

dp[j][i]는 j번 방향을 바꾸었고, 이제 T번중 i번째 수일 때 최댓값을 뜻합니다.
그러므로 아래 코드처럼 짜면 쉽게 AC가 나옵니다.
=end
T, W = gets.split.map &:to_i
dp = (W+2).times.map do [0]*(T+1) end
(1..T).each do |i|
  cur = gets.to_i
  (1..W+1).each do |j|
    dp[j][i] = [dp[j][i-1], dp[j-1][i-1]].max
    dp[j][i] += 1 if (cur+j).even?
  end
end
p (dp.map &:max).max
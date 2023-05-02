=begin
2008번 사다리 게임

시작/도착지점이 n개인 사다리 게임의 다리를 조정 하여서 A에서 B로 가도록 하려고 한다.
사다리는 기본적으로 M개 있고, 기본적으로 있는 사다리를 지우는데는 X만큼의 비용이,
사다리를 새로 짓는데는 Y만큼의 비용이 필요하다.
A에서 B로 갈 수 있도록 최소비용으로 만들려고 한다.
이 최소 비용을 구해보자.

이게.. 플레..?
지금까지 내가 본 골드1은...

태그를 바로 꿰뚫으면 쉬워지는 문제.
저는 이전으로 못돌아가는 점을 고려하여 dp로 바로 생각했는데, 정답이었습니다.
dp[i][j]는 i번째 라인을 타기 직전에 j번째 노드에 있는 경우 최소비용입니다.
이번 사다리와 이어진 경우 사다리 직전에 인접한 두 값중 작은 값을 넣어주고,
나머지는 그대로 내려오게 한 뒤 한번 갱신하면 됩니다.

이제보니 굳이 1..N으로 하지 않고 사다리부분만 했어도 될거같네요.
=end
INF = 1 << 63
N, M = gets.split.map &:to_i
a, b, X, Y = gets.split.map &:to_i
dp = (M+1).times.map do [INF]*(N+1) end
(1..N).each do |i| dp[0][i] = Y*(i-a).abs end
(1..M).each do |i|
  cur = gets.to_i
  dp[i][cur] = [dp[i-1][cur+1], dp[i-1][cur]+X].min
  dp[i][cur+1] = [dp[i-1][cur], dp[i-1][cur+1]+X].min
  (1..N).each do |j|
    (1..N).each do |k|
      dp[i][k] = [dp[i][k], dp[i][j]+Y*(j-k).abs].min
      dp[i][k] = [dp[i][k], dp[i-1][k]].min if k != cur && k != cur+1
    end
  end
end
p dp[-1][b]
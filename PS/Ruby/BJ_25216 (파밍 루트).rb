=begin
25216번 파밍 루트

당신은 현재 1번 노드에 있고, T초동안 움직일 것이다.
적의 기초공격력 a, 시간 1초당 공격력 x, 내 방어력 1당 줄어드는 공격력 y, 가면 얻을 수 있는 점수 c의 정보가 N줄에 걸쳐 주어진다.
항상 적이 먼저 선공하며, 당신은 체력을 1이라도 잃으면 그자리에서 게임 패배가 된다.
단, 당신은 한 번 버티면 상대를 바로 죽일 수 있기 때문에 첫 한 턴만 버티면 된다.
시간 1초당 공격력의 의미는, 현재 시간 T의 의미이다. 전투중 흐른 시간이 아니다.
N+2번째 줄부터 단방향 그래프의 간선 M개가 주어진다.
u v로 주어지며 u에서 v로 갈 수 있다는 뜻이다. 이동에는 시간이 걸리지 않는다.
각 노드에는 얻을 수 있는 점수와 적의 공격력이 있다.
얻을 수 있는 최대 점수 중, 최소 방어력을 구해보자.
단, 동일한 곳으로 가는 서로 다른 포탈은 주어지지 않는다.

아..
59%에서 틀린건 미스라 이해가 가는데 91%는 좀..
INF값 너무 작게 설정해서 틀려버렸네요.
뭔 그런 테스트케이스가 91%에..

어쨌든 이거도 BFS탈을 쓴 DP입니다. dp[i][j]를 두 개 만드는데, 각각 V, D라고 합시다.
V[i][j]는 i초의 시간이 흐른 뒤 j노드에서의 최대점수입니다.
D[i][j]는 i초의 시간이 흐른 뒤 j노드에서의 최소 방어력입니다. 단, 이 점수는 최대점수와 연동됩니다.
그렇게 T*N을 돌리고, 업데이트는 다음 노드를 k라 할 때 V[i+1][k], D[i+1][k]를 업데이트해주면 됩니다.

다음으로 고려해야할건 T초를 다 못 채우고 던전을 탈출하는 경우입니다.
이 경우는 저는 N+1이라는 새로운 노드를 만들어서 거기서 처리했습니다.

후.. 이렇게 틀릴 문제가 아닌데 INF값 부족으로 틀린건 허탈하네..
=end
INF = 1_000_000_000
INF *= INF
N, M, T = gets.split.map &:to_i
monster = N.times.map do gets.split.map &:to_i end
portal = (N+1).times.map do [] end
M.times do
  x, y = gets.split.map &:to_i
  portal[x] << y
end

V = (T+1).times.map do [0]*(N+2) end
D = (T+1).times.map do [INF]*(N+2) end
portal[1] << N+1 if portal[1].empty?
portal[1].each do |i|
  V[1][i] = monster[0][3]
  D[1][i] = (monster[0][0] + monster[0][2] - 1) / monster[0][2]
end
(1...T).each do |i| # curT
  (1..N).each do |j| # curN
    next if V[i][j].zero?
    a, x, y, c = monster[j-1]
    item = [D[i][j], (a+x*i + y-1) / y].max
    portal[j] << N+1 if portal[j].empty?
    portal[j].each do |k|
      next if V[i][j]+c < V[i+1][k]
      D[i+1][k] = V[i+1][k] < V[i][j]+c ? item : [D[i+1][k], item].min
      V[i+1][k] = [V[i+1][k], V[i][j]+c].max
    end
  end
end

maxV = V.map(&:max).max
p((0..T).map do |i|
  (1..N+1).map do |j|
    maxV == V[i][j] ? D[i][j] : INF
  end.min
end.min)

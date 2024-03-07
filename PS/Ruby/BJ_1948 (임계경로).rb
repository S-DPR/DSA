ini = ->() { gets.to_i }
ins = ->() { gets.split.map &:to_i }
INF = 1<<64
=begin
1948번 임계경로

DAG가 주어진다.
S에서 E로 가는 모든 경로 중 가장 긴 경로의 길이를 구해보자.
그리고 모든 최장 경로들이 지나오는 간선 집합의 크기를 구해보자.

문제 이해를 못해서 헤맸던 문제
DAG -> DP 위상정렬 가능
이건 금방 알았는데,
이제 두번째로 출력하라는게 뭔소린지 모르겠어서 좀 해멨습니다.
다른분에 답변에 잘 써주셔서 다행..

난이도는 어렵지 않습니다, 문제 이해가 난이도의 90%.
dp 위상 bfs 순서대로 굴려주면 AC가 쉽게 나옵니다.
=end
N = ini.()
K = ini.()
G = (N+1).times.map do [] end
IG  = (N+1).times.map do [] end
indep = [0]*(N+1)
K.times do |i|
  u, v, w = ins.()
  G[u] << [v, w, i]
  IG[v] << [u, w, i]
  indep[v] += 1
end
S, E = ins.()
Q = [S]
dp = [0]*(N+1)
until Q.empty?
  curN = Q.shift
  curW = dp[curN]
  G[curN].each do |nxtN, nxtW, _|
    indep[nxtN] -= 1
    dp[nxtN] = [dp[nxtN], curW+nxtW].max
    Q << nxtN if indep[nxtN] == 0
  end
end
useE = [0]*K
Q = [[E, 0]]
until Q.empty?
  curN, curW = Q.shift
  IG[curN].each do |nxtN, nxtW, kth|
    next if nxtW != dp[curN]-dp[nxtN]
    next if useE[kth] == 1
    useE[kth] = 1
    Q << [nxtN, dp[nxtN]]
  end
end
p dp[N]
p useE.sum

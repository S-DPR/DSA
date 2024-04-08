ini = ->() { gets.to_i }
ins = ->() { gets.split.map &:to_i }
INF = 1<<64
=begin
23887번 프린트 전달

R*C 상에 K개의 노드가 있다.
S번째 노드에서 시작해 모든 노드에 종이를 나눠주려 한다.
나눌 때는 자신 기준 8방향에 나눠줄 수 있다.
나누는 행위가 모든 방향에 대해 동시에 일어날 때,
각 노드는 최소 몇 개의 종이를 받아야하는지 구해보자.

ㅋㅋ
ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ
난바보야
아
쓰기도싫다
=end
R, C, K = ins.()
M = (R+1).times.map do [0]*(C+1) end
P = [1<<30]*(K+1)
pos = []
(1..K).each do |i|
  r, c = ins.()
  M[r][c] = i
  pos[i] = [r, c]
end
S = ini.()
dr = [1, 1, 1, 0, -1, -1, -1, 0]
dc = [1, 0, -1, -1, -1, 0, 1, 1]
Q, lQ = [pos[S]], []
V = (R+1).times.map do [-1]*(C+1) end
P[S] = 0
V[pos[S][0]][pos[S][1]] = 1
curT = 1
until Q.empty? && lQ.empty?
  if Q.empty?
    until lQ.empty?
      r, c = lQ.pop
      Q << [r, c]
      8.times do |i|
        nr, nc = r+dr[i], c+dc[i]
        next unless 0 < nr && nr <= R
        next unless 0 < nc && nc <= C
        next if V[nr][nc] != curT
        next if M[nr][nc] == 0
        P[M[r][c]] = [P[M[r][c]], M[nr][nc]].min
      end
    end
    curT += 1
  end
  r, c = Q.shift
  8.times do |i|
    nr, nc = r+dr[i], c+dc[i]
    next unless 0 < nr && nr <= R
    next unless 0 < nc && nc <= C
    next if V[nr][nc] != -1
    next if M[nr][nc] == 0
    V[nr][nc] = curT+1
    lQ << [nr, nc]
  end
end
if P[1..K].filter_map do |i| i >= 1<<30 end.length >= 1
  p -1
else
  indep = [0]*(K+1)
  (1..K).each do |i| indep[P[i]] += 1 end
  (1..K).each do |i| Q << i if indep[i] == 0 end
  dp = [0]*(K+1)
  until Q.empty?
    cur = Q.shift
    dp[cur] += 1
    next if cur == S
    indep[P[cur]] -= 1
    dp[P[cur]] += dp[cur]
    Q << P[cur] if indep[P[cur]] == 0
  end
  print dp[1..K].join(' ')
end

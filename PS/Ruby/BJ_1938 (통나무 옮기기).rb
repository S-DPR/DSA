ini = ->() { gets.to_i }
ins = ->() { gets.split.map &:to_i }
INF = 1<<63
=begin
1938번 통나무 옮기기

통나무를 상하좌우로 밀 수 있다. 단, 민곳은 모두 비어있어야한다.
또, 통나무의 중심 기준 3*3이 비어있다면 방향을 세로나 가로로 바꿀 수 있다.
최소 횟수로 BBB 통나무를 EEE로 옮기려 한다. 최소 몇 번의 행동이 요구될까?

구아아아악 구데기 구현
BFS만 있는 구현문제는 너무 시러요..
딱히 어려울거 없고 그대로 구현하면되는데..
별로네요.
=end
def isAble(r, c, i, arr, ch)
  dr = [1, 0, -1, 0]
  dc = [0, 1, 0, -1]
  range = (0...N)
  3.times.map do |j|
    nr, nc = r+dr[i]*j, c+dc[i]*j
    range.cover?(nr) && range.cover?(nc) && arr[nr][nc] == ch
  end.all?
end

N = ini.()
IM = N.times.map do gets.chars end
s, e = [], []
dr = [1, 0, -1, 0]
dc = [0, 1, 0, -1]
M = []
V = N.times.map do N.times.map do [INF, INF] end end
N.times do |r|
  M << N.times.map do |c|
    if 'BE'.include?(IM[r][c])
      2.times do |i|
        IM[r][c] == 'B' ? s = [r, c, i] : e = [r, c, i] if isAble(r, c, i, IM, IM[r][c])
      end
    end
    IM[r][c] == '1' ? 1 : 0
  end
end
V[s[0]][s[1]][s[2]] = 0
Q = [[*s, 0]]
until Q.empty?
  r, c, p, t = Q.shift
  4.times do |i|
    nr, nc = r+dr[i], c+dc[i]
    next unless isAble(nr, nc, p, M, 0)
    next unless (0...N).cover?(nr)
    next unless (0...N).cover?(nc)
    next if V[nr][nc][p] <= t+1
    V[nr][nc][p] = t+1
    Q << [nr, nc, p, t+1]
  end
  midr, midc = r+dr[p], c+dc[p]
  can_rotate = [-1, 0, 1].map do |i|
    [-1, 0, 1].map do |j|
      (0...N).cover?(midr+i) && (0...N).cover?(midc+j) && M[midr+i][midc+j] == 0
    end.all?
  end.all?
  next unless can_rotate
  nr = p == 0 ? r+1 : r-1
  nc = p == 0 ? c-1 : c+1
  next unless isAble(nr, nc, p^1, M, 0)
  next if V[nr][nc][p^1] <= t+1
  V[nr][nc][p^1] = t+1
  Q << [nr, nc, p^1, t+1]
end
p V[e[0]][e[1]][e[2]] == INF ? 0 : V[e[0]][e[1]][e[2]]

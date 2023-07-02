=begin
20158번 사장님 달려가고 있습니다

(0, 0)에서 (N-1, N-1)로 가려한다.
단, 한 방향으로만 이동시 1초에 1, 2, 3, ... 칸을 이동하게 된다.
방향을 바꾸면 1부터 다시 시작하게된다.
이동 할 때, 공사 예정 지역이 있을 수 있다. 현재 시간이 공사 시작 시간 이상이라면 이동할 수 없다.
1회 이동이 도달하기 전까지 시간은 증가하지 않는다.
예를들어, (0, 0)에서 (0, 3)로 간다고 해보자.
처음 (0, 0)에서 (0, 1)로 가는데는 1초가 걸린다.
하지만 (0, 1)에서 (0, 3)으로 갈 때, (0, 1)에서 (0, 2), (0, 3)을 순서대로 거치게 된다.
반면 (0, 2)에서는 (0, 1)과 같은 시간인 1초이고, (0, 3)에 도착했을 때 2초가 된다.
이 때, 목적지로 가는 최소시간을 구하시오.

그냥 오랜만에 BFS 굴려보고싶어서 고른 문제.
사실 그냥 심심해서 P5로 랜덤 돌렸다가 쉬워보이는 P5가 있길래 골랐습니다.
그냥 뭐.. 솔직히 벽부수기 3보다 쉬워요.
대충 V[Y][X][D]에서 비트마스킹으로 속도 관리해주면 됩니다.
가는도중 막히는거도 그냥 대충 while로 나이브하게 해도 돼고..
반 시간 만에 슥슥 푼 문제였습니다.
=end
N = gets.to_i
M = N.times.map do gets.split.map &:to_i end
dx = [1, -1, 0, 0]
dy = [0, 0, 1, -1]
Q = [[0, 0, -1, 0, 0]] # x, y, 방향, 가속도, 시간
V = N.times.map do N.times.map do [0]*4 end end
flg = false
until Q.empty?
  x, y, dir, spd, time = Q.shift
  if x == N-1 && y == N-1
    print time
    flg = true
    break
  end
  4.times.map do |i|
    nxt_spd = dir == i ? spd+1 : 1
    nx = x+dx[i]*nxt_spd
    ny = y+dy[i]*nxt_spd
    next unless 0 <= nx && nx < N
    next unless 0 <= ny && ny < N
    next if V[ny][nx][i]&(1<<nxt_spd) != 0
    blocked = false
    (1...nxt_spd).each do |j|
      tx = x+dx[i]*j
      ty = y+dy[i]*j
      blocked |= M[ty][tx].nonzero? && M[ty][tx] <= time
    end
    blocked |= M[ny][nx].nonzero? && M[ny][nx] <= time+1
    next if blocked
    V[ny][nx][i] |= 1<<nxt_spd
    Q << [nx, ny, i, nxt_spd, time+1]
  end
end
print "Fired" unless flg
=begin
25189번 시니컬한 개구리

맵의 크기 N, M이 주어진다.
이후 시작점 sy, sx와 도착점 ey, ex가 주어진다.
맵에서 이동할 때에는 맵의 해당 좌표에 있는 값만큼 상하좌우로 이동할 수 있으며,
딱 한번, 이 규칙을 무시하고 상하좌우로 원하는 만큼 움직일 수 있다.
걸리는 최소시간을 구하시오. 도착하지 못하면 -1을 출력하자.

않이 난 분명 비슷한 생각을 한거같은데 안되네
제 생각 : 한 좌표에서 상하좌우로 쭉 뻗을 때, '이 방향에서 왔어요'라고 해당 좌표에 적어두자.
 -> 30% 시간초과
인터넷 아이디어 : 한 좌표에서 상하좌우로 쭉 뻗을 때, x, y 전부 다 칠해버리자.
 -> 최적화 조금 더 넣으니까 정답
조금 억울하네 아..
그래도 생각의 방향이 조금 많이 바뀌었습니다..
중간에 이게 언어문젠가 해서 Go로도 짰었..
=end
INF = 1_000_000_000
N, M = gets.split.map &:to_i
sy, sx, ey, ex = gets.split.map &:to_i
MAP = N.times.map do gets.split.map &:to_i end
cnt = N.times.map do M.times.map do [INF, INF] end end
is_row_long_jump = N.times.map do false end
is_col_long_jump = M.times.map do false end
deque = [[sx - 1, sy - 1, 0, 0]]
cnt[sy - 1][sx - 1][0] = 0
vector = [[0, 1], [0, -1], [1, 0], [-1, 0]]

until deque.empty?
  cur_x, cur_y, cur_t, long_jump = deque.shift
  if cur_x == ex - 1 and cur_y == ey - 1 then next end
  if cnt[ey - 1][ex - 1].min <= cur_t then next end

  if long_jump == 0
    unless is_row_long_jump[cur_y]
      (0...M).each do |i|
        if cnt[cur_y][i][1] != INF or cnt[cur_y][i][0] <= cur_t then next end
        cnt[cur_y][i][1] = cur_t+1
        deque.push([i, cur_y, cur_t+1, 1])
      end
      is_row_long_jump[cur_y] = true
    end
    unless is_col_long_jump[cur_x]
      (0...N).each do |i|
        if cnt[i][cur_x][1] != INF or cnt[i][cur_x][0] <= cur_t then next end
        cnt[i][cur_x][1] = cur_t+1
        deque.push([cur_x, i, cur_t+1, 1])
      end
      is_col_long_jump[cur_x] = true
    end
  end

  vector.each do |dx, dy|
    nx = cur_x + dx * MAP[cur_y][cur_x]
    ny = cur_y + dy * MAP[cur_y][cur_x]
    unless 0 <= nx and nx < M and 0 <= ny and ny < N then next end
    unless cnt[ny][nx][long_jump] == INF then next end
    cnt[ny][nx][long_jump] = cur_t + 1
    deque.push([nx, ny, cur_t + 1, long_jump])
  end
end
puts cnt[ey - 1][ex - 1].min == INF ? -1 : cnt[ey - 1][ex - 1].min

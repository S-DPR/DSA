=begin
11967번 불켜기
 
N, M이 주어진다. N은 맵 크기의 한 변이고, M은 스위치의 개수이다.
이후 M줄에 x, y, a, b가 주어진다. (x, y)에 도달하면 (a, b)의 불을 조종할 수 있다는 의미이다.
당신은 상하좌우로 움직일 수 있고, 불이 켜진 곳으로만 움직일 수 있다. 당연히 맵 밖으론 못나간다.
(1, 1)에서 시작해 불을 최대한 킬 때 최대한 불이 켜고 그 수를 출력해보자.

처음 보고 스위치를 반드시 전부 다 눌러야하는줄 알고 이게 맞나??? 하던 문제.
그런데 다시보니까 스위치 누르는건 취사선택이 되는거였고..
플레5인가? 에서 골드2겠네..로 급락..

(x, y)에 도달 했을 때 두 번 bfs를 돌리면 됩니다.
첫번째로는 (x, y)에서 조종할 수 있는 스위치에서,
두번째로는 상하좌우 1칸.

첫번째만 보면 될텐데, 일단 갔으면 스위치는 켜줍니다.
그리고 그 불이 켜진 칸에 상하좌우를 보고, visit이 이미 된 것이 있으면 큐에 넣습니다.
아니면 불만 켜두고 패스합니다.

두번째는 그냥 (visit이 안되어있는데) (불만 켜진경우)만 체크하면 됩니다.
그런곳 있으면 그냥 큐에 넣고 끝.
문제 제대로 읽고나서도 한시간이나 박았네..
=end
N, M = gets.split.map &:to_i
switch = (N+1).times.map do (N+1).times.map do [] end end
turn_on = (N+1).times.map do [0]*(N+1) end
visit = (N+1).times.map do [0]*(N+1) end
turn_on[1][1] = 1
visit[1][1] = 1
M.times do
  x, y, u, v = gets.split.map &:to_i
  switch[y][x].push([u, v])
end
dx = [1, -1, 0, 0]
dy = [0, 0, 1, -1]
Q = [[1, 1]]
until Q.empty?
  cur_x, cur_y = Q.shift
  switch[cur_y][cur_x].each do |x, y|
    next if turn_on[y][x].nonzero?
    turn_on[y][x] = 1
    can_go = 4.times.map do |i|
      nx, ny = x+dx[i], y+dy[i]
      calc = true
      calc = false unless 1 <= nx && nx <= N
      calc = false unless 1 <= ny && ny <= N
      calc ? visit[ny][nx] : 0
    end.sum
    if can_go.nonzero?
      visit[y][x] = 1
      Q.push([x, y])
    end
  end
  4.times do |i|
    nx, ny = cur_x+dx[i], cur_y+dy[i]
    next unless 1 <= nx && nx <= N
    next unless 1 <= ny && ny <= N
    next if visit[ny][nx].nonzero? # 방문 표시는 안돼있는데
    next if turn_on[ny][nx].zero? # 불은 켜져있음
    visit[ny][nx] = 1
    Q.push([nx, ny])
  end
end
p turn_on.map(&:sum).sum

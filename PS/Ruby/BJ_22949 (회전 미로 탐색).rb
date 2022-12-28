=begin
22949번 회전 미로 탐색

첫 줄에 k가 주어진다.
이후 두 번째 줄부터 4k+1번째 줄까지 길이가 4k인 문자열이 주어진다.
문자열은 'S', 'E', '.', '#'으로 구성되어있으며, 각 문자의 의미는 다음과 같다.
S : 시작위치로, 모든 입력중 정확히 하나만 주어짐이 보장된다.
E : 목표위치로, 모든 입력중 정확히 하나만 주어짐이 보장된다.
. : 이동 가능한 위치이다.
# : 이동 불가능한 위치이다.
이 문자열의 전체 집합을 '맵'이라고 하자.
맵은 가로세로 4*4로 쪼개져, 플레이어가 이동한 후 플레이어가 존재하는 조각은 플레이어의 위치도 포함하여 시계방향으로 회전을 한다.
단, 플레이어가 존재하지 않는다면 초기상태로 돌아온다.
이 때, 시작위치에서 목표위치로 가는 최단거리를 구해보자.

오랜만의 BFS입니다.
항상 DFS나 유니온파인드만 풀었는데, 북마크 제거할 겸 골랐습니다.
루비는 어색하지만 골드3+ 첫 데뷔전으로 써봤습니다.
쓰고 느낀건, 배열만큼은 파이썬보다 강력하다는것..
뭐 파이썬에선 모듈써야하는 기능이 여기선 전부 지원을 합니다.
2D배열을 회전시키는게 내장되어있을줄은 몰랐어요..

어쨌든, BFS가 난이도가 높다면 일단 visit을 다차원으로 만들면 90%는 맞습니다.
저같은경우 [K번째 조각][y][x][배열회전 횟수]로 visit을 잡았습니다.
그리고 bfs 쭉 돌리면 됩니다. 배열 쪼개는거랑 플레이어의 좌표를 이동시키는게 제일 까다로울거같네요.
이걸 문제를 안읽어서 3시간 날릴줄은 몰랐지만..
=end
def get_territory(k, x, y)
  x, y = x>>2, y>>2
  y*k+x
end

def get_rotate(x, y)
  largerX, largerY = x/4, y/4
  x, y = x%4, y%4
  [3-y+largerX*4, x+largerY*4]
end

k = gets.to_i
len = k*4
M = []
len.times do
  M << (gets.rstrip.split(""))
end
arr = (k*k).times.map do
  4.times.map do
    []
  end
end

k.times do |i|
  k.times do |j|
    4.times do |t|
      arr[i*k+j][0] << M[i*4+t].slice(j*4, 4)
    end
  end
end

(k*k).times do |i|
  3.times do |j|
    arr[i][j+1] = arr[i][j].transpose.map(&:reverse)
  end
end


startX, startY = -1, -1
len.times do |i|
  len.times do |j|
    if M[i][j] == 'S' then startX = j; startY = i end
  end
end

visit = len.times.map do
  len.times.map do
   [false]*4
  end
end

vector = [[1, 0], [-1, 0], [0, -1], [0, 1], [0, 0]]
deque = [[startX, startY, 0, 0]]
until deque.empty? do
  curX, curY, rotate, time = deque.shift
  curTerritory = get_territory(k, curX, curY)
  if arr[curTerritory][rotate][curY%4][curX%4] == 'E' then puts time; return end
  vector.each do |dx, dy|
    nx, ny = curX+dx, curY+dy
    unless 0 <= nx and nx < len and 0 <= ny and ny < len then next end
    nextTerritory = get_territory(k, nx, ny)
    _rotate = rotate
    if curTerritory != nextTerritory then _rotate = 0 end
    if arr[nextTerritory][_rotate][ny%4][nx%4] == '#' then next end
    nx, ny = get_rotate(nx, ny)
    if visit[ny][nx][(_rotate+1)%4] then next end
    visit[ny][nx][(_rotate+1)%4] = true
    deque.push([nx, ny, (_rotate+1)%4, time+1])
  end
end

puts -1
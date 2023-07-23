=begin
1175번 배달

아래 조건을 지키며 순서 상관 없이 C 두 곳을 방문하려 한다.
1. 이동한 방향으로는 다시 이동할 수 없다.
최단시간을 구해보자.

진짜 이건 푸는데 10분도 안걸린거같은데..

빡센 최적화가 필요한가? (X)
조건이 많은가? (X)
조건이 헷갈리는가? (X)

여러모로 27453번 하위호환이라는 생각이 크게 든 문제였습니다.
그런데 같은난이도네..
=end
R, C = gets.split.map &:to_i
M = R.times.map do gets.chars end
Q = []
O = []
V = R.times.map { C.times.map { 4.times.map { [false]*4 }}}
R.times do |i|
  C.times do |j|
    Q << [j, i, 0, -1, 0] if M[i][j] == 'S'
    O << [j, i] if M[i][j] == 'C'
  end
end
dx = [1, -1, 0, 0]
dy = [0, 0, 1, -1]
ret = -1
until Q.empty?
  x, y, t, p, vis = Q.shift
  if vis == 3
    ret = t
    break
  end
  4.times do |i|
    next if p == i
    nx, ny, np = x+dx[i], y+dy[i], vis
    next unless 0 <= nx && nx < C
    next unless 0 <= ny && ny < R
    next if M[ny][nx] == '#'
    np |= 1 if nx == O[0][0] && ny == O[0][1]
    np |= 2 if nx == O[1][0] && ny == O[1][1]
    next if V[ny][nx][np][i]
    V[ny][nx][np][i] = true
    Q << [nx, ny, t+1, i, np]
  end
end
print ret
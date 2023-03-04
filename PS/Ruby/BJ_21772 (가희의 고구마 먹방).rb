require 'set'
=begin
21772번 가희의 고구마 먹방

G에서 출발하여 T초 내에 S를 최대한 많이 밟으려 한다.
그 최대 수를 구해보자.

친구만나는 약속 갑자기 잡혀서 골드5 암거나 잡아서 푼 문제
걍 어딜 봐도 개 쓰레기같이 코드를 짰습니다.
2번인가 틀리면서 질문글 보면서 20분만에 풀었네요..
으윽.. 쓰레기코드..
=end
def dfs(x, y, t, cnt, v, k)
  return if x < 0 || y < 0 || !@M[y] || !@M[y][x]
  cnt += 1 if @M[y][x] == 'S' && !v.include?(y*k+x)
  @res = [@res, cnt].max
  return if t == @T
  return if @M[y][x] == '#'
  dfs(x+1, y, t+1, cnt, v | Set[y*k+x], k)
  dfs(x-1, y, t+1, cnt, v | Set[y*k+x], k)
  dfs(x, y+1, t+1, cnt, v | Set[y*k+x], k)
  dfs(x, y-1, t+1, cnt, v | Set[y*k+x], k)
end
N, K, @T = gets.split.map &:to_i
@res = 0
@M = N.times.map do gets end
N.times do |i|
  dfs(@M[i].index('G'), i, 0, 0, Set.new, K) if @M[i].index('G')
end
p @res
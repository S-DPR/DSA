=begin
4803번 트리

그래프가 주어진다. 트리의 개수를 세어보자.

실수 하나 해서 한시간 걸린 문제..
문제 풀이 방법은 바로 떠올랐지만 간단한 예외 하나 찾지 못해서 시간이..
'양방향 간선'으로 저장된다는 점때문에, dfs에 'prev'라는 전에서 어디서 왔나를 붙이고,
다음으로 갈 곳이 이 prev와 같다면 패스하는 방식으로 했습니다.
아.. 너무 간단한 실수였다..
=end
def dfs(x, prev)
  return false if @visit[x]

  condition = true
  @visit[x] = true
  @g[x].each do |i|
    condition &= dfs(i, x) if i != prev
  end
  condition
end

testCase = 1
loop do
  n, m = gets.split.map &:to_i
  break if (n + m).zero?

  @g = (0..n).map do [] end
  m.times do
    x, y = gets.split.map &:to_i
    @g[x].push(y)
    @g[y].push(x)
  end
  @visit = (0..n).map do false end
  @tree = 0
  (1..n).each do |i|
    @tree += 1 if dfs(i, 0) unless @visit[i]
  end
  puts "Case #{testCase}: No trees." if @tree.zero?
  puts "Case #{testCase}: There is one tree." if @tree == 1
  puts "Case #{testCase}: A forest of #{@tree} trees." if @tree > 1
  testCase += 1
end
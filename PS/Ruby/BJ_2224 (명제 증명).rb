'
2224번 명제 증명

단방향 간선인 그래프가 주어진다.
노드 i에서 j로 갈 수 있는 쌍의 개수와 i와 j를 모두 출력해보자.
i와 j는 알파벳 문자이며 i와 j가 같은 경우는 세지도, 출력하지도 말자.

간선의 개수가 10000개 이하인걸 보고 플로이드는 안되겠거니 해서 dfs로 풀었는데,
풀고보니 플로이드워셜이네? 아, 노드가 52개구나!
..해서 두 번 풀었습니다.
그냥 뭐.. 기초 그래프문제네요.
'
inf = 1_000_000_000
N = gets.to_i
G = 60.times.map do [inf]*60 end
60.times do |i| G[i][i] = 0 end
N.times do
  u, v = gets.split(' => ').map do |i| i.ord - 'A'.ord end
  G[u][v] = 1 if u != v
end
60.times do |i|
  60.times do |j|
    60.times do |k|
      G[j][k] = [G[j][k], G[j][i] + G[i][k]].min
    end
  end
end
cnt, answer = 0, ""
60.times do |i|
  60.times do |j|
    next if G[i][j] == inf || i == j
    answer += "#{(i+'A'.ord).chr} => #{(j+'A'.ord).chr}\n"
    cnt += 1
  end
end
puts cnt
print answer

=begin
def dfs(graph, x, visit)
  return if visit[x]
  @result.push((x+'A'.ord).chr)
  visit[x] = true
  graph[x].each do |i|
    dfs(graph, i, visit)
  end
end

N = gets.to_i
G = 60.times.map do [] end
key = {}
N.times do
  u, v = gets.split(' => ').map do |i| i.ord - 'A'.ord end
  key[u] = 1 unless key[u]
  G[u].push(v)
end

cnt = 0
answer = ""
key.keys.sort.each do |i|
  @result = []
  dfs(G, i, [false]*60)
  @result.sort.each do |j|
    next if (i+'A'.ord).chr == j
    answer += "#{(i+'A'.ord).chr} => #{j}\n"
    cnt += 1
  end
end
puts cnt
print answer
=end
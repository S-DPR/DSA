=begin
25198번 곰곰이의 심부름

트리모양의 도시가 있고, 곰곰이는 현재 S도시에, 집은 H도시, 심부름장소는 C도시에 있다.
곰곰이는 S -> C -> H를 최단거리로 가려고 한다.
각 도시에서는 닭 다리를 하나 살 수 있다. 곰곰이는 두 개의 닭다리를 살 것이다.
그리고, 산 도시를 순서쌍으로 적어둘 것이다.
이 때, 순서쌍이 나올 수 있는 경우의 수를 구해보자.

트리트리한 트리트리문제
S -> C -> H로 가는 최단거리..는 무슨 그냥 S -> C는 한 경우밖에 없고 C -> H도 한 경우밖에 없습니다.
왜냐면 트리니까요. 그냥 왔다리갔다리 안한다 표현하고싶었던거같습니다.

순서쌍의 의미가 중요한데, 1 -> 2 -> 3 -> 5 -> 3 -> 4로 갔다고 하면,
1은 2, 3, 5, 4랑 순서쌍을 만들 수 있고,
2는 3, 5, 4와 순서쌍을 만들 수 있고,
3은 5, 4랑 순서쌍을 만들 수 있고,
5는 3, 4랑 순서쌍을 만들 수 있습니다.
두번째 3은 이미 위에서 해서 패스해야하고,
4는 이후로 가는곳이 없어서 순서쌍을 못만듭니다.
즉, 위 경우는 11개의 순서쌍을 가질 수 있습니다.

그러므로 우리는 S -> C를 먼저 구해야합니다.
그냥 visit에 역추적 가능하도록 이전에 어디 기록했는지 적어두고요,
C -> H도 똑같이 해줍니다.

다음으로 역추적을 모두 합니다. 딕셔너리와 스택을 활용합니다.
마지막으로 스택에서 하나씩 빼면서, 딕셔너리의 길이를 cnt에 더합니다.
스택을 모두 소비한 이후 cnt를 출력합니다.
=end
N = gets.to_i
S, C, H = gets.split.map &:to_i
G = (N+1).times.map do [] end
(N-1).times do
  u, v = gets.split.map &:to_i
  G[u] << v
  G[v] << u
end

Q = [S]
newQ = []
visit = [0]*(N+1)
until Q.empty?
  cur = Q.shift
  if cur == C
    newQ << cur
    Q.pop until Q.empty?
    break
  end
  G[cur].each do |i|
    next if visit[i].nonzero?
    Q << i
    visit[i] = cur
  end
end

newVisit = [0]*(N+1)
until newQ.empty?
  cur = newQ.shift
  break if cur == H
  G[cur].each do |i|
    next if newVisit[i].nonzero?
    newQ << i
    newVisit[i] = cur
  end
end

trace = {H => 1}
stk = [H]
cur = H
while cur != C
  cur = newVisit[cur]
  trace[cur] = trace[cur] ? trace[cur]+1 : 1
  stk << cur
end
while cur != S
  cur = visit[cur]
  trace[cur] = trace[cur] ? trace[cur]+1 : 1
  stk << cur
end

cnt = 0
visited = [false]*(N+1)
until stk.empty?
  cur = stk.pop
  trace[cur] -= 1
  trace.delete(cur) if trace[cur].zero?
  next if visited[cur]
  cnt += trace.include?(cur) ? trace.length-1 : trace.length
  visited[cur] = true
end
puts cnt
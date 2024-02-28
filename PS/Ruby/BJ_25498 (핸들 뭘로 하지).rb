ini = ->() { $stdin.gets.to_i }
ins = ->() { $stdin.gets.split.map &:to_i }
INF = 1<<64
=begin
25498번 핸들 뭘로 하지

트리가 주어진다. 각 노드에는 문자가 할당되어있고,
루트노드에서 출발해 사전순으로 가장 뒤인 문자열을 얻으려 한다.
최종적으로 어떤 문자열이 나올까?

개발하다가 하나 고치니까 싹다 고쳐야하는 기적이 일어났고,
현타와서 잠시 생각해본 문제.
dfs로 생각하다가 어.. 아닌거같은데.. 하고 멍떄리다가,
그냥 그리디하게 bfs굴리면 깔끔하게 O(N+M)으로 처리 되겠는데? 해서 했습니다.
다행히 접근법 맞아서 AC. 간단하게 풀었네요.
=end
N = ini.()
S = [' '] + gets.strip.chars
G = (N+1).times.map do [] end
(N-1).times do
  u, v = ins.()
  G[u] << v
  G[v] << u
end
Q = [1]
V = [false]*(N+1)
V[1] = true
mx, mxc = [], 'a'
ret = [S[1]]
until Q.empty? && mx.empty?
  if Q.empty?
    ret << mxc
    Q = mx.clone
    mx, mxc = [], 'a'
  end
  cur = Q.shift
  G[cur].each do |i|
    next if mxc > S[i] || V[i]
    mxc, mx = S[i], [] if mxc < S[i]
    mx << i
    V[i] = true
  end
end
print ret.join

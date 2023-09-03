=begin
5214번 환승

역 N개와 하이퍼튜브 M개가 있다.
역은 몇개의 하이퍼튜브와 연결되어있고, 하이퍼튜브는 몇개의 역과 연결되어있다.
이 때, 1번 역에서 N번 역으로 이동할 때 몇개의 역을 거쳐야하는지 구해보자.

확실히 사람들이 많이 푼 골드2는 쉽네요.
진짜 할만해.
아니면 그냥 BFS라서 그런거일지도 모르고?

아이디어는, 역과 하이퍼튜브를 각각의 그래프로 그리자.
어쩌면 이걸 더미노드라고 할 수도 있겠네요.
그리고, 각각의 그래프로 그렸으니 그 둘이 방문처리도 각각 해줘야합니다.
한 번 생각없이 역만 방문처리했다가 TLE났네요.
이거 빼면 너무 간단하게 풀리는 문제였습니다.
=end
N, K, M = gets.split.map &:to_i
G = (0..N).map do [] end
H = (0..M).map do [] end
V = [0]*(N+1)
VH = [false]*(M+1)
Q = [1]
V[1] = 1
(1..M).each do |i|
  items = gets.split.map &:to_i
  items.each do |j|
    G[j] << i
  end
  H[i] = items
end
until Q.empty?
  cur = Q.shift
  G[cur].each do |i|
    next if VH[i]
    VH[i] = true
    H[i].each do |j|
      next if V[j].nonzero?
      V[j] = V[cur]+1
      Q << j
    end
  end
end
p(V[N].nonzero? ? V[N] : -1)
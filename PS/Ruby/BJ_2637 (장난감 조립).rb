spl = ->() { gets.split.map &:to_i }
=begin
2637번 장난감 조립

X번 부품을 만드는데 Y번 부품이 W개 필요하다는 정보가 M개 주어진다.
완제품을 만드는데 필요한 기초 부품이 각각 몇번이고, 몇개 필요한가 구해보자.

그냥 보자마자 아 이거는 풀리겠다 싶어서 5분만에 푼 문제.
진짜 P4문제에 묶여버려서 G2중 쉬운거 사냥하고다녔습니다..
그동안 위상정렬+dp에 하도 쳐맞다보니 이정도 문제는 금방 해결되네요.
=end
N = gets.to_i
K = gets.to_i
G = (N+1).times.map do [] end
I = [0]*(N+1)
W = [0]*(N+1)
K.times do
  u, v, w = spl.()
  G[u] << [v, w]
  I[v] += 1
end
Q = []
R = []
(1..N).each do |i|
  next unless I[i].zero?
  Q << i
  W[i] = 1
end
until Q.empty?
  x = Q.shift
  G[x].each do |nxtN, nxtW|
    W[nxtN] += W[x]*nxtW
    I[nxtN] -= 1
    next unless I[nxtN].zero?
    Q << nxtN
  end
end
(1..N).each do |i|
  next if G[i].length.nonzero?
  print i, " ", W[i], "\n"
end

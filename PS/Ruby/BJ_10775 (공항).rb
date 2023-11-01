spl = ->() { gets.split.map &:to_i }
=begin
10775번 공항

0부터 N까지 있는 수열이 주어진다.
수를 P개 고르자. 각 수를 x라고 하자.
이 때, x는 왼쪽 수와 합쳐진다. 만약에 0까지 합쳐졌다면 더 합쳐질 수 없다.
최대 몇 개의 수를 시도할 수 있을까?

그냥 보고 좀 생각해보다가 '어 이거 왠지 분리집합같은데 ㅋㅋ' 해서 시도하고,
진짜 그냥 갑자기 생각난건데 이론적으로 맞는거같아서 구현했더니 맞은 문제.
쉽게 풀어서 기분좋당
=end
find = ->(x) {
  U[x] = find.(U[x]) if x != U[x]
  return U[x]
}
union = ->(x, y) {
  xp = find.(x)
  yp = find.(y)
  U[[xp, yp].max] = U[[xp, yp].min]
}

N = gets.to_i
P = gets.to_i
U = Array(0..N)
ret = 0
(1..P).each do |i|
  x = gets.to_i
  break if find.(x) == find.(0)
  ret = i
  union.(find.(x)-1, x)
end
puts ret

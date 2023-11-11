spl = ->() { gets.split.map &:to_i }
=begin
27727번 버튼 정렬

버튼을 한 번 누르면 배열에서 가장 작은 수가 1 증가한다.
그런 수가 여러개면 가장 앞에 있는 수가 증가한다.
버튼을 K번 누를 예정이다. 배열은 그 도중 몇 번 비내림차순이 될까?

오랜만의 관찰 그리디 문제
답지 보고 깨달았는데, 만약 i에 대해 A[i] > A[i+1]이 있으면,
배열의 모든 수는 A[i]로 맞춰져야합니다.
그래서 저거중 가장 큰 수를 가져와야하고요.

그래서 그거로 초기 수열을 비내림차순으로 만든 뒤,
주기성을 이용해 남은 K를 처리하면 됩니다.

아래는 쉬운데 저 위에꺼 관찰이 정말 어려웠던것같아요.
=end
N = gets.to_i
A = spl.()
k = gets.to_i
H = Hash.new(0)
max = (A.each_cons(2).max_by do |f, b|
  f > b ? f : 0
end || A).first
minus = A.map do |i|
  H[[max, i].max] += 1
  [max-i, 0].max
end.sum
key = H.keys.sort
k -= minus
ret = minus.positive? && k >= 0 ? 1 : 0
while k.positive? && !key.empty?
  left = key.shift
  nxt = key.first || 1<<62
  plus = [nxt-left, k/H[left]].min
  k -= plus*H[left]
  H[nxt] += H[left]
  ret += plus
end
p ret

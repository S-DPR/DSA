spl = ->() { gets.split.map &:to_i }
=begin
14476번 최대공약수 하나 빼기

자연수로 이루어지고 길이가 N인 수열이 주어진다.
여기서 하나를 뺀 뒤 나머지 값들의 gcd를 구하려 한다.
단, 나온 gcd가 뺀 값의 약수이면 안된다.
이 때, 가능한 가장 큰 gcd와 그 때 뺀 가장 값을 구해보자.

이야 이거를 보자마자 곧바로 풀었네
이거시 능지상승??

왼쪽 누적합, 오른쪽 누적합 만들어주고,
A를 순회하며 왼쪽오른쪽부분의 gcd를 구해주는게 핵심.
그러면 O(NlogN)으로 모든 문제가 처리됩니다!
신기해..
=end
N = gets.to_i
A = spl.()
lpf, rpf = [0], [0]
N.times do |i|
  lpf << lpf[-1].gcd(A[i])
  rpf.unshift(rpf[0].gcd(A[N-1-i]))
end
ret, retK = -1, nil
(1..N).each do |i|
  expectK = A[i-1]
  gcd = lpf[i-1].gcd(rpf[i])
  next if (expectK%gcd).zero?
  ret, retK = gcd, expectK if ret < gcd
end
print ret, " ", retK

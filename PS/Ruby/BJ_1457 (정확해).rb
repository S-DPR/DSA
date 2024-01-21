spl = ->() { gets.split.map &:to_i }
inf = 1<<64
=begin
1457번 정확해

F(x)는 x보다 작은 약수 k중 k^N이 x의 약수가 아닌 수의 개수이다.
A, B, N이 주어진다. F(A) + F(A+1) + ... + F(A+B-1) + F(A+B)의 값을 구해보자.

어려운 문제인지 알았는데 잘 생각해보니..
그냥 다 해보면 되는거 아닌가?? 해서 하니까 되네..
의외로 쉬웠던 문제입니다. 증명은 잘 모르겠고..
=end
calc = ->(x) do
  (2..x).sum do |i|
    ksq, k = 1, 0
    while k < N && ksq <= x
      ksq *= i
      k += 1
    end
    x/i - x/ksq
  end
end

A, B, N = spl.()
ret = calc.(A+B)-calc.(A-1)-B
ret -= 1 if A != 1
p ret

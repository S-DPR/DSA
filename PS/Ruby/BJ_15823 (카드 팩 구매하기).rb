ini = ->() { gets.to_i }
ins = ->() { gets.split.map &:to_i }
INF = 1<<64
=begin
15823번 카드 팩 구매하기

N개의 수열에서 길이가 K인 M개의 연속부분배열을 선택하려한다. 조건은 다음과 같다.
1. 연속부분배열의 원소는 중복되지 않아야한다.
2. 하나의 카드가 여러 배열에 포함될 수 없다.
최대 K를 구하시오.

대충 뭐 매개변수겠거니로 시작.
이제 다음은 뭐인가.. 하니, 투포인터가 적절해보이던데요.
그래서 구현해주고 AC.. 한 30분정도 걸린거같네요.
투포인터 구현이 좀 쉽지 않았습니다. 생각보다.
=end
N, K = ins.()
A = ins.()
lo, hi = 1, N+1
while lo < hi
  mid = (lo + hi) >> 1
  s = Hash.new(0)
  dp = [0]*(N+1)
  N.times do |i|
    dp[i] = [dp[i], dp[i-1]].max if i-1 >= 0
    s[A[i]] += 1
    left = i-mid+1
    next if left < 0
    dp[i+1] = [dp[i+1], dp[left]+1].max if s.size == mid
    s[A[left]] -= 1
    s.delete(A[left]) if s[A[left]] == 0
  end
  dp.max >= K ? lo = mid+1 : hi = mid
end
p hi-1

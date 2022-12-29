=begin
3142번 즐거운 삶을 위한 노력

수 N이 있다. 이 값의 초기값은 1이다.
여기에 T번동안 수를 곱하도록 하자.
곱할 때마다, N이 완전제곱수인지 판단하여 완전제곱수이면 DA, 아니면 NE를 출력하자.

정수정수한 정수론문제입니다. 아이디어는 금방 떠올랐는데,
set형으로 푸니까 시간초과로 틀리고 배열로 바꾸니까 맞았습니다..
에라토스테네스도 문제 없지만, 여기선 linear-sieve를 사용해봤습니다.
=end
MAX = 1_000_000
sieve = (MAX+1).times.map do |i| i end
prime = []
(2..MAX).map do |i|
  if sieve[i] == i then prime.push(i) end
  prime.each do |j|
    if MAX < i*j then break end
    sieve[i*j] = j
    if i%j == 0 then break end
  end
end

odd = [0]*(MAX+1)
is_odd = 0
T = gets.to_i
T.times do
  x = gets.to_i
  while x != 1
    odd[sieve[x]] ^= 1
    if odd[sieve[x]] == 0 then is_odd -= 1 else is_odd += 1 end
    x = x/sieve[x]
  end
  if is_odd == 0 then puts 'DA' else puts 'NE' end
end
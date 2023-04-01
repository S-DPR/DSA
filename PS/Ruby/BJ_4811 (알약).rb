=begin
4811번 알약

알약이 N개 있다. 이 알약은 반개씩 먹어야하기때문에 완제는 반으로 쪼개어 먹게된다.
완제를 쪼개어 먹을때는 W, 있던 반개를 먹을때는 H를 쓰기로 했다고 하자.
N개의 알약을 2N일동안 먹었을 때 나올 수 있는 문자열의 경우의 수를 구해보자.

P5와 G1에게 너덜너덜해질정도로 맞고 멘탈 깎여나가서 결국 골드5로 도망
그냥 카탈란수 문제입니다. 1 1 2 5 14 42..
(2nCn)/(n+1)이요.
=end
factorial = [1]
(1..60).each do |i|
  factorial << factorial[-1]*i
end
dp = [0]
(1..30).each do |i|
  dp << factorial[i*2]/factorial[i]/factorial[i]/(i+1)
end
res = ""
while true
  i = gets.to_i
  break if i.zero?
  res += "#{dp[i]}\n"
end
puts res
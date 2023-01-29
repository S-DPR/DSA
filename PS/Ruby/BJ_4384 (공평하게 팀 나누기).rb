require 'set'
=begin
4384번 공평하게 팀 나누기

수열이 하나 주어진다.
이를 길이차가 1 이하가 되도록 둘로 나누려고 한다.
(당연히, 나눈 두 수열을 합치면 원래 수열이 되어야 한다.)
두 수열의 합의 차가 최소가 되도록 했을 때,
나눈 수열의 합을 작은값과 큰 값 둘로 나누어 출력해보자.

태그보고 푼 문제.
여역시 태그를 보면 풀었다는 느낌은 안들지만,
저번 시로코 문제 다음으로 '배낭으로 이런거도 가능하구나'를 알게 되었습니다.

다만, 여기서는 2차원 배열 대신 set으로 dp를 관리했습니다.
dp 크기가 50*45000정도가 되니까 시간이 버티질 못하더라고요.
(최악의경우, 수열의 길이 100, 가져갈 수 있는 수열의 길이 50, 모두다 450인 값일경우 45000.
셋을 곱하면 1.5억정도가 나옵니다.)
그래서 그냥 set으로 관리해버렸습니다. 근본적으로 배낭문제와 다를거는 없죠.
=end
N = gets.to_i
team_cnt = N/2
arr = N.times.map do gets.to_i end
S = arr.sum
dp = (team_cnt+1).times.map do Set.new end

arr.each do |i|
  team_cnt.downto(1).each do |j|
    dp[j-1].each do |k|
      dp[j].add(k+i)
    end
  end
  dp[1].add(i)
end

result = S
dp[-1].each do |i|
  result = i if (S-i*2).abs < (S-result*2).abs
end
print [S-result, result].min, " ", [S-result, result].max

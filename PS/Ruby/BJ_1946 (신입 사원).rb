=begin
1946번 신입 사원

테스트케이스 T가 주어진다.
테스트케이스는 첫 줄에 N, 다음줄부터 N줄에 걸쳐 두 정수가 공백으로 구분되어 주어진다.
두 정수는 1부터 N까지의 수로 이루어져있으며, 세로줄끼리는 겹치는 수가 존재하지 않는다.
이 때, K번째로 나온 두 수가 J번째로 나온 수에 대해 두 수 모두 크지 않은 경우를 세어보자.
(1 <= T <= 20, 1 <= N <= 100000, K != J)

이렇게 쿼리형식으로 만든걸로 보나 실제 문제로 보나 문제 이해 자체가 쉽지 않은 문제입니다.
문제 이해 후 난이도는 쉬웠습니다. 마침 언어가 루비라 '어떻게 하면 루비처럼 짤 수 있을까'를 더 많이 고민했어요.
루비는 쉬운문제여도 루비처럼 코드 짜는걸 고민하게 만들다보니, 재밌다고 느껴지네요.

첫번째 원소에 대해 정렬을 합시다.
그 다음, for문을 돌며 앞에 있는 수중 두번째가 가장 작은것과 자신의 두번째 원소를 비교해보고, 
더 작으면 그걸 갱신하고, 더 크면 1을 더하지 않는 방향으로 갑시다.
=end

T = gets.to_i
T.times do
  n = gets.to_i
  arr = n.times.map do
    gets.split.map &:to_i
  end
  lower = n+1
  F = arr.sort_by {|i| i[0]}.map do |_, y|
    if lower > y then lower=y; 1 else 0 end
  end.sum
  puts F
end
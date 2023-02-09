=begin
12931번 두 배 더하기

모든 원소가 0이고 길이가 N인 배열이 있다.
다음 연산을 하여 주어진 배열로 만들려할 때, 필요한 최소횟수를 구해보자.
1. 원소 하나에 1을 더한다.
2. 모든 원소에 2를 곱한다.

아아.. 이것은 그리디의 기초라는 것이다..
역시, 역으로 가면 되는 문제입니다.
긴 설명은 필요없을것같네요.
=end
N = gets.to_i
arr = gets.split.map &:to_i
cnt = 0
while true
  break if (arr.map do |i| i end.sum).zero?
  is_odd = arr.map do |i| i.odd? ? 1 : 0 end.sum
  if is_odd.zero?
    N.times do |i| arr[i] /= 2 end
    cnt += 1
  else
    N.times do |i| arr[i] -= 1 if arr[i].odd? end
    cnt += is_odd
  end
end
puts cnt

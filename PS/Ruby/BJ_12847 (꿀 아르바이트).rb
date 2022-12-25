=begin
12847번 꿀 아르바이트

수열의 길이 n, 고를 수 있는 연속한 배열의 길이 k가 주어지고,
수열 arr이 주어진다.
길이 k만큼 연속한 길이의 배열을 골랐을 때 최댓값을 출력하시오.

전형적인 누적합 문제입니다.
별 어려울건 없고..
몇 번 틀린건 그냥 이 언어답게 짜보겠다고 했다가 틀렸..
=end
n, k = gets.split.map &:to_i
arr = gets.split.map &:to_i
cur = 0
ans = (0...n).map do |i|
  cur+=arr[i]
  if i >= k then cur-=arr[i-k] end
  cur
end.max
puts ans
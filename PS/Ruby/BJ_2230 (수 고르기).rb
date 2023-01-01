=begin
2230번 수 고르기

길이가 N인 수열 arr이 주어진다.
여기서 두 수를 골랐을 때 그 차가 M 이상이고 그중 가장 작은 값을 출력하라.
정답이 반드시 있는 입력임이 보장된다.

실수로 분류를 봐버려서 투포인터로 풀었는데,
만약 저라면 이분탐색으로 풀었을거같습니다.
=end
n, m = gets.split.map &:to_i
arr = n.times.map do gets.to_i end.sort!
left, right = 0, 0
minimum = 2_000_000_000
while left < n
  if arr[right]-arr[left] >= m then minimum = [minimum, arr[right]-arr[left]].min end
  if arr[right]-arr[left] >= m or right == n-1 then left += 1 else right += 1 end
end
puts minimum

=begin
이하는 이분탐색 숏코딩 코드입니다. (118B)
n,m=gets.split.map &:to_i
a=(1..n).map{gets.to_i}.sort!
puts a.map{|i|a.bsearch{|x|x>=i+m}.to_i-i}.filter{|i|i>=m}.min
=end
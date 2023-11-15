spl = ->() { gets.split.map &:to_i }
=begin
1365번 꼬인 전깃줄

LIS의 길이를 구하고, N에서 그 길이를 뺀 값을 구해보자.

사실 문제는 저게 아니지만 쭉 생각해보면 저거고..
골드2에서 제일 만만한 LIS문제.. 또 당신입니까 GOAT시여..
=end
def bisect(arr, x)
  left, right = 0, arr.length
  while left < right
    mid = (left + right) >> 1
    arr[mid] >= x ? right = mid : left = mid+1
  end
  right
end

N = gets.to_i
A = spl.()
lis = []
A.each do |i|
  idx = bisect(lis, i)
  lis[idx] = lis[idx] ? [lis[idx], i].min : i
end
p N-lis.length

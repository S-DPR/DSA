def ini() gets.to_i end
def ins() gets.split.map &:to_i end
INF = 1<<63
=begin
3745번 오름세

lis의 길이를 구해보자.

그냥 lis 구하는 문제.
회식때문에 시간이 없어서 이거나 풀었습니다. 쩝..
=end
def bisect(arr, x)
  left, right = 0, arr.length
  while left < right
    mid = (left + right) >> 1
    arr[mid] >= x ? right = mid : left = mid+1
  end
  right
end

until STDIN.eof?
  n = ini()
  lis = [-INF]
  ins().each do |i|
    idx = bisect(lis, i)
    lis[idx] = lis[idx] ? [lis[idx], i].min : i
  end
  p lis.length-1
end

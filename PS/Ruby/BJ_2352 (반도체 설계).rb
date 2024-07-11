ini = ->() { gets.to_i }
ins = ->() { gets.split.map &:to_i }
INF = 1<<64
=begin
2352번 반도체 설계

i번째 포트를 A[i]번째 포트에 연결했다.
선이 교차하지 않도록 포트를 최대한 연결할 때,
최대 몇 개의 포트를 연결 할 수 있을까?

대충 LIS문제인건 보자마자 보이는 문제.
LIS 구현하고 그 길이 출력하면 끝~
=end
def bisect(arr, x)
  lo, hi = 0, arr.length
  while lo < hi
    mid = (lo + hi) >> 1
    arr[mid] >= x ? hi = mid : lo = mid + 1
  end
  hi
end

N = ini.()
A = ins.()
lis = [-INF]
A.each do |i|
  idx = bisect(lis, i)
  lis[idx] = lis[idx] ? [lis[idx], i].min : i
end
print lis.length - 1

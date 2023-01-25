require 'set'
=begin
22963번 3초 정렬

오름차순 정렬이 안된 수열이 주어진다.
3개 이하의 원소를 바꿔 이 수열을 오름차순 정렬을 할 수 있을까?
할 수 있다면, 뭘 바꿔야할까? 굳이 최소의 개수를 고를 필요는 없다.

LIS문제.
오늘 따로 제대로 공부해봤습니다. LIS..
이 문제는 O(NlgN)의 시간복잡도를 가진 LIS를 만들어야하며,
만들었다면 이제 역추적도 해야합니다.

혼자 LIS를 공부했다 했지, LIS 역추적을 공부하진 않아서,
두시간정도 박치기를 해서 풀었습니다.

역추적을 했다면, 전체 길이에서 LIS의 길이를 뺍니다.
3개 초과면 그냥 NO 출력하고 끝내고,
이하면 이제 바꿀 자리가 제일 앞자리인경우를 예외로 치고,
그게 아닌 경우를 arr[i-1]의 값으로 바꿔주고, 예외의 경우에는 1로 바꿔주면 됩니다.

아, 중복된 수가 나올지도 모릅니다. bisect_left말고 bisect_right를 써줍시다.
=end
def bisect_right(arr, x)
  l, r = 0, arr.length
  while l < r
    m = (l + r) >> 1
    if x < arr[m][0] then r = m else l = m + 1 end
  end
  r
end

INF = 1_000_000_000
N = gets.to_i
arr = gets.split.map &:to_i
dp = [[-INF, -INF]]
trace = N.times.map do -INF end
N.times do |i|
  idx = bisect_right(dp, arr[i])
  dp[idx] = if dp[idx] then [[dp[idx][0], arr[i]].min, i] else [arr[i], i] end
  trace[i] = dp[idx-1][1] if idx >= 2
end
dp.shift

cnt = N-dp.length
if cnt > 3
  puts 'NO'
else
  puts 'YES'
  puts cnt
  tmp = Set.new
  N.times do |i| tmp.add(i) end
  cur = dp[-1][1]
  while cur != -INF
    tmp.delete(cur)
    cur = trace[cur]
  end
  tmp.each do |i|
    change = i == 0 ? 1 : arr[i-1]
    print i+1, " ", change, "\n"
    arr[i] = change
  end
end
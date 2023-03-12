=begin
1450번 냅색문제

N개의 원소를 가진 수열이 주어진다.
0개 이상의 원소를 골라 더해서 C 이하가 되는 모든 경우의 개수를 찾아보자.

냅색 (dp아님)
N이 30 이하라는 특이한 조건입니다.
그래도 1<<31-1은 어마어마하게 큰 수라서 이거 브루트포스는 아니네 ㅋㅋ 하고 dp로 하려했으나,
메모리초과로 개같이 멸망.
태그를 보니 새로운게 있었고, 검색해보니 이게 기본문제더라고요.
G1이 기본문제.. 세그트리는 문제라도 많던데..

정해는 중간에서 만나기입니다.
1<<31-1은 어마어마하게 크지만 1<<16-1은 할만합니다. 100000도 안되니까요.
그러니 배열을 둘로 잘라서 나올 수 있는 모든 경우의 수를 얻어줍니다.
1+1+1과 2+1은 다른경우이므로 dictionary로 관리해줍니다.

한 쪽에는 나온 수에 따라 정렬한 뒤 누적합 배열을 구해줍니다.
다음으로, 다른 한쪽에서 한 수를 고릅니다.
그리고 오른쪽에서 C-(고른수)로 이분탐색을 한 뒤,
두 값을 곱한뒤 cnt에 더합니다.
이를 모든 수에 대해 반복합니다.
=end
INF = 1_000_000_001
N, M = gets.split.map &:to_i
arr = gets.split.map &:to_i
l, r = {}, {}
(0..N/2+1).each do |i|
  arr.slice(0, N/2+1).combination(i) do |j|
    j = j.sum
    l[j] = l[j] ? l[j]+1 : 1
  end
  arr.slice(N/2+1, N/2).combination(i) do |j|
    j = j.sum
    r[j] = r[j] ? r[j]+1 : 1
  end
end
l, r = l.to_a.sort, r.to_a.sort + [[INF, INF]]
(1...r.length).each do |i| r[i][1] += r[i-1][1] end
cnt = 0
l.each do |key, val|
  next if key > M
  idx = r.bsearch_index do |i, _| i > M-key end
  cnt += val * r[idx-1][1]
end
puts cnt

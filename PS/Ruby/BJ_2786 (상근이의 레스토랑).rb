=begin
2786번 상근이의 레스토랑

레스토랑에 요리가 N개가 있다.
가격은 A, B인데, 처음으로 요리를 주문하면 A가격, 이후는 B가격이 된다.
가격 리스트가 주어질 때, 1개 주문시, 2개 주문시, ..., N개 주문시 최소가격을 구해보자.

그리디인건 대충 N제한 보고 알았는데..
대체 어떻게 그리디하게 해야할지 모르겠어서 답지 본 문제.
너무어렵다..

1. B 기준으로 정렬
2. 첫번째는 arr.min박아서 처리
3. 두번째부터는, 현재 i번째라 할 때
 - 1~i를 모두 B가격으로 사고, A로 바꿀때 가격 상승폭이 가장 적은 것을 A로 구매
 - 1~i-1을 모두 B가격으로 사고, i~N번째중 A가 가장 싼거를 구매

이야..
너무 정확해서 할말이 없었네요..
정렬 그리디는 이런 시야로 봐야하는구나..
=end
N = gets.to_i
arr = N.times.map do
  gets.split.map &:to_i
end.sort_by do |_, j| j end
min = [1<<60]
N.times do |i|
  min.unshift([min[0], arr[N-1-i][0]].min)
end
diff = arr[0][0]-arr[0][1]
sum = arr[0][1]
max = arr[0][1]
ret = [min[0]]
(1...N).map do |i|
  diff = [diff, arr[i][0]-arr[i][1]].min
  max = [max, arr[i][1]].max
  sum += arr[i][1]
  ret << [sum+diff, sum-max+min[i]].min
end
ret.each do |i| print i, "\n" end
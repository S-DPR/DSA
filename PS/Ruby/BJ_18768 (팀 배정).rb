=begin
18768번 팀 배정

A, B를 원소로 가지는 배열을 원소로 가지는 수열이 주어진다.
각 원소에서 A, B를 하나씩 선택하여 최댓값을 얻어보자.
단, 취한 A와 B의 개수의 차가 K를 넘어서는 안된다.

수열 크기때문에 DP는 안될거같고 그럼 그리디인건 대충봐도 알겠지만..
능지차이때문에 좀 많이 틀렸습니다.
제 방식은 이렇습니다.

1. 일단 A, B중 A가 더 큰애는 l, B가 더 큰애는 r, 똑같은애는 m에 넣습니다.
2. l과 r은 각 원소의 차의 절댓값으로 sort해줍니다.
3. m을 l과 r에 넣을건데, 더 적은쪽으로 넣어줍니다.
4. l과 r의 크기를 비교하여 k를 넘을경우 더 긴쪽에서 더 짧은쪽으로 옮겨줍니다.
 - 위에서 sort하였으니 옮겨도 손해가 더 적은놈만 옮기면 됩니다.

인터넷에서 본 방식은 이렇습니다.
1. 각 원소의 차로 정렬을 한 뒤 더 큰애를 res에 더해줍시다.
2. 1번을 하는 도중 한쪽이 "팀을 받을 수 있는 최대치가 될경우" 나머지를 모두 반대로보냅니다.
3. 답을 구합니다.

이야.. k 이하의 차이를 유지하는 방법이 저게 있다는걸 알고 대단하다고 느꼈습니다.
=end
T = gets.to_i
T.times do
  n, k = gets.split.map &:to_i
  arr = gets.split.map &:to_i
  brr = gets.split.map &:to_i
  l, m, r = [], [], []
  arr.zip(brr).each do |x, y|
    if x > y
      l.push([x, y])
    elsif x < y
      r.push([x, y])
    else
      m.push([x, y])
    end
  end
  l.sort_by! do |i, j| (i-j).abs end
  r.sort_by! do |i, j| (i-j).abs end
  until m.empty?
    l.length < r.length ? l.unshift(m.pop) : r.unshift(m.pop)
  end
  until (l.length - r.length).abs <= k
    if l.length > r.length
      r.push(l.shift)
    else
      l.push(r.shift)
    end
  end
  res = l.map do |i, _| i end.sum + r.map do |_, i| i end.sum
  puts res
end

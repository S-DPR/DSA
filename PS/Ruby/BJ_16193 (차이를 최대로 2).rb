=begin
16193번 차이를 최대로 2

수열의 길이 N이 주어진다. 이후 배열 A가 주어진다.
배열 원소의 수를 적절히 바꾸었을 때, 배열의 모든 인접한 수들의 차이를 더한 값의 최대를 구해보자.
(3 <= N <= 1000000)

N이 최대 8이어서 그냥 순열 때려박아도 되던 날은 어디가고,
이제 N이 100만이네..

이게 그리디로 되는구나? 라는 생각이 드는 문제였습니다.
먼저 A를 정렬합니다. 이후 새로운 배열을 만들건데요 이 배열을 T라고 합시다,
먼저 제일 큰 값을 T[1]에 넣고, 그 다음으로 큰 값을 T[3]에 넣고.. 이렇게 i번째로 큰 수를 T[2*i-1] (2*i-1 < N) 까지 넣습니다.
이제 제일 작은 값을 T[2]에 넣고, 그 다음으로 T[4]에 넣고.. i번째로 작은 수를 T[2*i] (2*i < N) 까지 넣습니다.
이러면 원래 값이 하나 남는데, 이 값은 T[0]에 넣습니다.
이를 작은값과 큰 값을 바꾸어서 한번 더 실행한 뒤, 가장 큰 값을 취합니다.

그냥 직감적으로 이렇게하면 제일 좋지않을까? 라는 마음가짐으로 풀었는데,
솔직히 이게 왜 되는지는 진짜 모르겠습니다. 증명을 어떻게하지?
=end
N = gets.to_i
arr = gets.split.map(&:to_i).sort
arr2 = arr.clone
new_arr = (1...N).map do |idx|
  idx.odd? ? arr.pop : arr.shift
end
new_arr.unshift(arr.pop)
res = (N-1).times.map do |i|
  (new_arr[i]-new_arr[i+1]).abs
end.sum

arr = arr2
new_arr = (1...N).map do |idx|
  idx.odd? ? arr.shift : arr.pop
end
new_arr.unshift(arr.pop)
res = [res, (N-1).times.map do |i|
  (new_arr[i]-new_arr[i+1]).abs
end.sum].max
p res
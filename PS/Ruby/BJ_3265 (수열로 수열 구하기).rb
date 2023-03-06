require 'set'
=begin
3265번 수열로 수열 구하기

0과 1로 이루어진 배열이 주어진다. 각 인덱스 k에 대해 0과 1은 다음 뜻을 갖고있다.
arr[k] = 1 : k번째 인덱스까지 배열의 원소가 1~K로만 이루어져있다.
arr[k] = 0 : 그렇지 않다.
(arr은 1부터 시작하는 배열이다.)
이후 K개의 원래 배열의 원소가 주어진다. res[u-1] = v라는 뜻이다.
위 arr을 만족하는 원래 수열을 구해보자. 그런 수열이 없다면 -1을, 있다면 아무거나 출력하자.

수열 문제는 많은경우 재미있습니다.
이 문제도 그런부류인데, 핵심은 1이 오는 것은 이전에 1이 나온것 바로 앞의 인덱스가 거기 들어가야한다는거.
예를들어 (0 1 0 0 1) 로 주어졌다면 (- 1 - - 3) 이렇게 되어야 0과 1을 만족합니다.
가운데는 1에 나온 숫자+1 ~ 1 인덱스까지 수가 아무거나 나와도 됩니다. 정해져있으면 정해진거로 넘어가고요.
어쨌든.. 쉬운 플레5였습니다.
=end
N, K = gets.split.map &:to_i
arr = gets.split.map &:to_i
res = [0]*N
K.times do
  u, v = gets.split.map &:to_i
  res[u-1] = v
end
idx = 0
flg = false
N.times do |i|
  next if arr[i].zero?
  flg = true if res[i].nonzero? && res[i] != idx+1
  res[i] = idx+1
  s = Set.new(idx+2..i+1)
  (idx...i).each do |j|
    flg = true if res[j].nonzero? && !s.include?(res[j])
    s.delete(res[j]) if s.include?(res[j])
  end
  s = s.to_a.sort
  (idx...i).each do |j| res[j] = s.pop if res[j].zero? end
  idx = i+1
end
print(flg ? '-1' : res.join(" "))

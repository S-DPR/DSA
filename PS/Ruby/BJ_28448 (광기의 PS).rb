=begin
28448번 광기의 PS

N개 문제의 난이도 K와 푸는데 걸리는 시간 T가 주어진다.
어떤 문제를 푸는데는 KT의 광기가 쌓이며, 이 광기는 절대 L을 넘기면 안된다.
모든 KT는 L 이하의 값이고 문제를 푼 뒤 min(KT, 5K)만큼 광기가 줄어들며,
시간 1을 소비해 광기를 1 줄일 수 있다.
모든 문제를 푸는데 걸리는 최소시간을 구해보자.

곧 플레에서 떨어질 문제같으니 빨리 풀어야지 히히
그런데 증명은..? 진짜 모르겠는데..?

그냥 광기를 해소할 수 있는 문제를 우선으로 풉니다.
나머지는 그냥 간단한 구현이고요.
지금 기여가 3개라 별로 믿을건 안되긴 한데(체감상 골드3..?), 왜 저게 저렇게되는진 모르겠습니다.
음.. 어떻게증명하냐 이건..
=end
N, K = gets.split.map &:to_i
items = N.times.map do gets.split.map &:to_i end
items.sort_by! do |i, j| -(i*[j, 5].min) end
cur, t = 0, 0
items.each do |i, j|
  if K-cur < i*j
    t += i*j-(K-cur)
    cur = K-i*j
  end
  cur += (i*j)-(i*[j, 5].min)
  t += j
end
print t

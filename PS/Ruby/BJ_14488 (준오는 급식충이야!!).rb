=begin
14488번 준오는 급식충이야!!

N명의 사람들의 현재 위치가 주어진다.
각 사람의 1초당 속력도 주어진다.
단위는 모두 미터이고, T초의 시간만 뛸 수 있다.
이 때, 이 N명의 사람이 모두 만날 수 있는지 구해보자.

플레 5 풀려다가 쫓겨나고 골드 5라도 풀려고 주워온 문제
상당히 재밌습니다.

일단 T가 소숫점 넷째자리까지 나올 수 있다는게 상당한 흠이므로,
t에 10000을 곱해버립니다. (사실 생각해보니 이거도 별로 안전하다는 생각은 안드네요)
그리고 거리에도 10000을 곱합니다. 거리 = 속력 * 시간이니, 각 변에 10000을 곱한것과 같습니다.
그리고 range를 -inf와 inf로 잡고,
left부분과 right부분을 계속 새로 업데이트를 해줍니다.
단, 어떤 사람의 최대거리가 left보다 작거나 최소거리가 right보다 큰경우 flg에 불가능 처리를 해둡니다.
=end
n, t = gets.split.map &:to_f
t = (t*10000).to_i
arr = gets.split.map do |i| i.to_i*10000 end
brr = gets.split.map do |i| i.to_i end
range = [-10**18, 10**18]
flg = 1
arr.zip(brr).each do |i, j|
  left = i-j*t
  right = i+j*t
  flg = 0 if right < range[0] || range[1] < left
  range[0] = [range[0], left].max
  range[1] = [range[1], right].min
end
print flg

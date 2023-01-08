=begin
14229번 모두싸인 출근길

판자의 l, r이 N개 주어진다.
당신은 점프를 할 수 있는데, 그 거리는 당신이 걸은 판자의 길이만큼이다.
예를들어, 8에서 10으로 이동했다면 2만큼 점프할 수 있다.
당신이 가장 멀리 갈 때, 얼마나 갈 수 있나 구해보자.

문제자체는 딱봐도 그리디구나 느낄 수 있고,
이게 스위핑이라길래 어리둥절하고있었다가 그냥 안쓰고 풀었습니다.
이거도 스위핑이라고 하나? 제가 아는 스위핑과는 다르네요.
애초에 범위가 10억인거보고 이거 스위핑은 글렀는데 생각먼저 들었습니다.

이 문제의 주요 아이디어는 연속된 판자를 합치자.
이거만 하면 이후가 그리디로 변합니다.

딴건 모르겠고 코드 더러운게 별로 맘에 안드네요..
어떻게 고쳐야할지 감도 못잡겠어요..
Ruby는 진짜 코드 더럽게쓰면 파이썬보다 더럽게 써지던데..
=end
N = gets.to_i
dic = {}
N.times.map do
  s, e = gets.split.map &:to_i
  dic[s] ? (dic[s] += 1) : (dic[s] = 1)
  dic[e] ? (dic[e] -= 1) : (dic[e] = -1)
end

s = 0
scheck = false
connect = 0
arr = []
dic.keys.sort.each do |i|
  if scheck
    s = i
    scheck = false
  end
  connect += dic[i]
  next if connect.positive?

  arr.append([s, i])
  scheck = true
end

jump = 0
current = 0
arr.each do |left, right|
  break if jump < left

  jump = [right*2 - left, jump].max
  current = right
end

print current
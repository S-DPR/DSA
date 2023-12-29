spl = ->() { gets.split.map &:to_i }
=begin
28068번 I Am Knowledge

읽어야 할 책이 N개 있다. 각 책은 지루함 A[i], 즐거움 B[i]를 갖고있다.
i번째 책을 읽기 직전에 A[i]만큼 멘탈수치가 감소하고, 다 읽고나면 멘탈수치가 B[i]만큼 증가한다.
이 때, N개의 책을 모두 읽을 수 있을지 체크해보자. 초기 멘탈 수치는 0이다.

우선순위 큐 쓰고 뻘짓좀해보다가,
"아 생각해보니 A[i] <= B[i] 먼저 읽는게 무조건 이득이네" 하고 바로 생각을 바꿨습니다.
A[i] > B[i]인 경우는 헤메다가, B[i]를 내림차순으로 하면 된다는 말을 보고 해봤는데 진짜되네..
왜인진 계속 생각해보고있는데, 잘 모르겠네요.. A[i]-B[i]가 제일 큰게 그나마 이득아닌가..
=end
N = gets.to_i
A = N.times.map do spl.() end
Pos, Neg = [], []
A.each do |i|
  i[1]-i[0] >= 0 ? Pos << i : Neg << i
end
Pos.sort! { |i, j| i[0] <=> j[0] }
Neg.sort! { |i, j| j[1] <=> i[1] }
cur, flg = 0, 1
Pos.each do |i|
  flg = 0 if cur < i[0]
  cur -= i[0]
  cur += i[1]
end
Neg.each do |i|
  flg = 0 if cur < i[0]
  cur -= i[0]
  cur += i[1]
end
p flg

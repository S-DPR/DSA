ini = ->() { gets.to_i }
ins = ->() { gets.split.map &:to_i }
INF = 1<<64
=begin
25637번 회전 목마

길이가 N인 수열 A가 있다.
A의 원소에서 한 번에 1씩 왼쪽/오른쪽으로 옮겨 담을 수 있다.
A의 모든 원소가 1이 되게 하려 한다. A의 합이 N일 때, 최소횟수를 구하시오.

1년 6개월 묵은 문제
알고보니 웰노운이라고?
또 나만 모르는 웰노운이야

그리디..인데 이왜그 소리만 나온 문제..
뭐? 원형을 직선으로 보고, 그걸 N개 보자고? ㅇㅋ, 이건 이해 됨.
그런데 이제 그러면 그리디를 쓸 수가 있다고??
0인애 기준으로 왼쪽중 2 이상인애꺼 우선으로 가져오고, 없으면 오른쪽에서 가져오면된다고???
하아..
쉽지않다 진짜..
증명..증명을달라..
=end
N = ini.()
A = ins.()
p(N.times.map do
  A << A.shift
  need, enough = [], []
  A.map.with_index do |i, idx|
    ret = 0
    (i-1).times do |k|
      need.empty? ? enough << idx : ret += idx-need.shift
    end
    enough.empty? ? need << idx : ret = idx-enough.shift if i == 0
    ret
  end.compact.sum
end.min)

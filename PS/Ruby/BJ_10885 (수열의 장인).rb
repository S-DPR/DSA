spl = ->() { gets.split.map &:to_i }
=begin
10885번 수열의 장인

길이가 N인 수열 A가 있고, 1 <= i <= j <= N인 두 (i, j)를 고르자.
그리고 A[i]*A[i+1]*...*A[j-1]*A[j]로 되는 최댓값을 구해보자.
단, A에는 -2 이상 2 이하의 정수만 존재한다.

그리-디
이리보고 저리봐도 그리-디
dp로 접근하면 시간의 멸망이 일어날 것..

일단 0이 들어가면 무조건 망합니다. 그래서 0을 기준 해서 좌우를 나눠야합니다.
각 부분수열에 대해, 거기에 있는 음수의 개수를 셉니다.
만약 홀수개면 왼쪽이나 오른쪽에서 음수 하나 뺍니다. 물론 그 좌/우측에 있는거도 다 빠집니다..
짝수개라면 그냥 그 2의 개수가 최대입니다.
그리고 2의 개수가 제일 많은 경우를 체크하면 됩니다.

후..
최댓값%제수의 나머지가 아니라 제수의 최댓값을 구해서 틀린건 너무 슬프다..
=end
def fn(seg, two_cnt)
  ret = two_cnt
  while !seg.empty? && !seg[-1].negative?
    x = seg.pop
    ret -= 1 if x.abs == 2
  end
  !seg.empty? && seg[-1].abs == 2 ? ret-1 : ret
end

MOD = 1_000_000_007
T = gets.to_i
R = []
T.times do
  len = gets.to_i
  arr = spl.() + [0]
  ret = -1
  segment = []
  arr.each do |i|
    if i.zero?
      unless [[], [0], [-1], [-2]].include?(segment)
        two_cnt = segment.count(-2) + segment.count(2)
        is_minus = segment.count(-2) + segment.count(-1)
        left = fn(segment.clone, two_cnt)
        right = fn(segment.clone.reverse, two_cnt)
        end_two_cnt = [left, right].max
        end_two_cnt = [end_two_cnt, two_cnt].max if (is_minus&1).zero?
        ret = [ret, end_two_cnt].max
      end
      segment = []
      next
    end
    segment << i
  end
  R << (ret.negative? ? 0 : 2.pow(ret, MOD))
end
R.each do |i|
  print i, "\n"
end

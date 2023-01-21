=begin
16938번 캠프 준비

첫줄에 N, L, R, X가 주어지고 길이가 N인 수열이 주어진다.
여기서 두 개 이상의 수를 골라 합이 L 이상 R 미만이고,
최댓값과 최솟값의 차가 X 이상이 되도록 골라내려고 한다.
경우의 수를 구해보자.
(1 <= N <= 15)

이건 무슨문제인고.. 투포인터 문제신가..
하다가 N을 보니 15.
2의 15승은 얼마 되지 않으니 대충 O(2^N)시간복잡도겠구나..
그러면 그냥 완전탐색 조합으로 풀어내면 되겠구나..
아..
라는 의식의 흐름대로 풀었습니다.
루비는 편한데에선 신이 맞는거같아요.
=end
def loop(l, r, s)
  (0..r-l-1).each do |k|
    @arr.slice(l+1, r-l-1).combination(k).each do |i|
      t = i.sum
      @result += 1 if @L <= s+t && s+t <= @R
    end
  end
end

@N, @L, @R, @X = gets.split.map &:to_i
@arr = (gets.split.map &:to_i).sort!
@result = 0
@N.times do |i|
  (i+1...@N).each do |j|
    next if @arr[j]-@arr[i] < @X
    loop(i, j, @arr[i]+@arr[j])
  end
end
puts @result
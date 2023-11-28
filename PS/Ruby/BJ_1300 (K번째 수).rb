spl = ->() { gets.split.map &:to_i }
=begin
1300번 K번째 수

크기가 N*N인 배열 A의 값은 A[i][j] = i*j이다.
이를 1차원배열 B에 모두 옮기고, 오름차순 했을 때 K번째 수를 구해보자.

숙제 밀려서 까먹은 비상식량문제.
그냥 대충 매개변수 돌려주면 됩니다.
근데 hi가아니라 lo출력해야 맞는거는 놀랍네..
=end
check = ->(x) {
  (1..N).map do |i|
    [x/i, N].min
  end.sum
}

N = gets.to_i
K = gets.to_i
lo, hi = 1, N*N
while lo < hi
  mid = (lo + hi) >> 1
  check.(mid) >= K ? hi = mid : lo = mid + 1
end
p lo

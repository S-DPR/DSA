=begin
27377번 읽씹 멈춰!

1이 있다. 이 수에 1을 추가하는데는 s의 시간이, 두배를 하는데는 t의 시간이 든다.
1을 N으로 만드는 데는 적어도 어느정도의 시간이 필요할까?

N이 10의 18승이라는 흉악한 크기를 자랑하는 문제.
하지만 O(logN)정도면 충분합니다. 놀랍게도요.

전형적인 그리디문제입니다. 1에서 N을 가는 대신 N에서 1로 가면 됩니다.
단, 현재 수가 k일 때 어느 순간부턴 t를 하는거보다 그냥 k/2번 s를 돌리는게 더 이득이 됩니다.
그러므로, 저 상황이 되면 s만 돌리지 말고 그냥 k*s를 답에 추가하고 루프를 나오면 됩니다.
간단하죠.
=end
T = gets.to_i
output = ''
T.times do
  n = gets.to_i
  s, t = gets.split.map &:to_i
  time = 0
  while n.positive?
    if n.odd?
      time += s
      n -= 1
    elsif t <= n/2*s
      time += t
      n /= 2
    else
      time += n*s
      n = 0
    end
  end
  output += "#{time}\n"
end
print output

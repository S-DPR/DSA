=begin
10827번 a^b

a^b를 정확히 계산하자. a에는 반드시 소수점이 있다.

그냥 귀찮아서 방치한 문제. 오늘 할일이 있어서 그냥 지금 처리했습니다.
C++같은거면 모르겠지만, 큰 수를 기본지원하는 Python이나 Ruby라면 그냥 날먹문제입니다.
a의 소숫점 위치를 구해두고, 계산 할 때 점을 insert해서 처리하면 됩니다.

근데 숏코딩 보니까 의외로 루비도 bigdecimal 지원하네요?
없는게 머야?
=end
a, b = gets.split
b = b.to_i
adot = a.index('.')
dot = (a.length - adot - 1)*b
a = a.delete('.').to_i
result = (a**b).to_s
result.insert(0, '0') while result.length <= dot
result.insert(-dot-1, '.')
puts result
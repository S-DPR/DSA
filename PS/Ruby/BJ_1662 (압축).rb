=begin
1662번 압축

문자열을 K(Q)의 형태로 압축하였다. Q는 문자열이며, K는 한 자리 정수이다. Q를 K번 반복하였다는 뜻이다.
예를들어 43(123)이라면 4 + 3(123) = 4 + 123123123 = 4123123123 이 된다.
위 방법을 이용하여 압축한 문자열 s가 주어진다. 원래 문자열의 길이를 구해보자.
주어지는 문자열은 항상 올바름이 보장된다.

재귀로풀려다가 스택으로풀려다가 재귀로풀려다가 스택으로 푼 문제
쓸데없이 시간 엄청 썼습니다..
그냥.. 좀.. 문제이해가 까다로운문제.. 그런느낌..
=end
s = gets.strip.chars
stk = []
inside = [0]
s.each do |i|
  stk << i
  inside << 0 if i == '('
  if i == ')'
    stk.pop
    inside[-1] += 1 while stk.pop != '('
    inside[-1] *= stk.pop.to_i
    item = inside.pop
    inside[-1] += item
  end
end
print inside[0]+stk.length
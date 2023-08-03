=begin
20442번 ㅋㅋ루ㅋㅋ

1. R은 ㅋㅋ루ㅋㅋ 문자열이다.
2. ㅋㅋ루ㅋㅋ 양쪽에 K가 붙으면 ㅋㅋ루ㅋㅋ문자열이다.
문자열이 주어진다. 가장 긴 ㅋㅋ루ㅋㅋ문자열의 길이를 구해보자.

크크루삥뽕

처음엔 감도 못잡아서 몇시간 걸렸는데,
갖다버리고 몇 달 있다가 생각해보니 방법 생각나서 푼 문제.
역시 태그 까는건 진짜 난이도 엄청 낮춘다..

투포인터인데, 이건 양 끝에서 시작하는 투포인터입니다.
일단 초기 길이는 주어진 문자열에서 R의 개수입니다.
left < right인동안 아래 행위를 반복합니다.

1. S[left], S[right]가 모두 K라면 length에 2를 더하고 둘을 좁힙니다.
2. 1이 아닐 때
2-1. S[left]가 R이면 length에 1을 줄이고, 결과값과 length를 비교해 더 큰값을 가져갑니다.
2-2. S[right]에 같은 행위를 반복합니다.

항상 난이도 보면 이게 왜 이 난이도 이러는데,
풀고보면 이러니까 이 난이도지.. 하게되네..
=end
S = gets.strip
left, right = 0, S.length-1
length = S.count('R')
ret = length
while left <= right
  if S[left] == 'K' && S[right] == 'K'
    left += 1
    right -= 1
    length += 2
  else
    if S[left] == 'R'
      left += 1
      ret = [ret, length].max
      length -= 1
    end
    if S[right] == 'R'
      right -= 1
      ret = [ret, length].max
      length -= 1
    end
  end
end
puts ret
=begin
LeetCode - String to Integer (atoi)

문자열을 정수로 바꾸어봅시다. 규칙은 다음과 같습니다.
문자열 s에 대해,
1. 공백을 받아들이고 무시합니다.
2. + 혹은 -를 입력받습니다. 아무것도 없다면 +로 가정합니다.
3. 문자열을 정수로 전환합니다.
4. 정수가 (1<<31)-1 초과 혹은 -(1<<31) 미만이라면 해당 수로 전환합니다.
만약 문자열을 읽어들이는 중, 정수가 아닌 문자를 만난다면 즉시 그 자리에서 종료합니다.
이후, 아무것도 없었다면 0을, 읽어들인게 있다면 그 값을 return합니다.

문제가 저게 맞나? 모르겠네요.
어쨌든 별로 여러 언어를 지원하는 문제로서는 좋은 문제라 생각되진 않습니다.
이유요? 저기 맨 끝에 보시면 압니다..

그냥 구현만 하는 문제인데, 솔직히 틀린 테스트케이스 안보여줬으면 진짜 두시간 이상 잡혔을거같습니다.
LeetCode가 백준에 비에 훨씬 친절하긴 하네요.
=end
def my_atoi(s)
  result = ''
  s.split("").each do |i|
    if %w[- +].include?(i)
      break if result != ''
      result += i
      next
    elsif i == ' '
      next if result == ''
      break
    elsif '0'.ord > i.ord || '9'.ord < i.ord
      break
    end
    result += i if '0'.ord <= i.ord && i.ord <= '9'.ord
  end
  result = result.to_i
  if result.negative?
    [result, -(1<<31)].max
  else
    [result, (1<<31)-1].min
  end
end

=begin
이게 되다니.
def my_atoi(s)
  [[s.to_i, (1<<31)-1].min, -1<<31].max
end
=end
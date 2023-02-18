=begin
16472번 고냥이

문자열이 주어진다.
N개 이하의 알파벳을 갖는 부분 연속 문자열중 가장 긴 것의 길이를 출력해보자.

이게 투포인터문제구나..
투포인터는 많이 접해보지 않아 분류보고 풀었습니다.
투포인터인거 아는게 조금 쉽지 않네요..
=end
def getOrd(idx) @string[idx].ord - 'a'.ord end
N = gets.to_i
@string = gets.rstrip
left, right = 0, 0
count = 1
arr = [0]*26
arr[getOrd(0)] = 1
result = 0
while left < @string.length
  if right+1 < @string.length && (count+1 <= N || arr[getOrd(right+1)] != 0)
    right += 1
    count += 1 if arr[getOrd(right)] == 0
    arr[getOrd(right)] += 1
  else
    arr[getOrd(left)] -= 1
    count -= 1 if arr[getOrd(left)] == 0
    left += 1
  end
  result = [result, right-left+1].max
end
p result

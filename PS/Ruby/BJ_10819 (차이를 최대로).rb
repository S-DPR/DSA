=begin
10819번 차이를 최대로

수열의 길이 n과 수열 arr이 주어진다. (1 <= n <= 8)
arr 원소의 순서를 적절히 바꾸어, 다음 수식의 최댓값을 구하시오.
sum(|arr[i]-arr[i+1]|) (0 <= i < n-1)

새로운 언어는 항상 즐겁죠. 아닌가?
어쨌든 Ruby로 처음 풀어본 실버문제입니다.
가볍게 입력과 출력, 조건문과 반복문, 함수정의까지만 배우고 풀어봤습니다.

N이 8까지기떄문에 그냥 브루트포스로 모든 결과를 확인하면 됩니다.
N 크기 보기 전까지 그리디인줄알았는데 아니어서 당황했었네요..
ruby는 permutation을 지원하기때문에 써서 바로 풀어낼 수 있습니다.

으윽, 자바가 더 나은거같아. 도망가고싶다.
=end

n = gets.to_i
arr = gets.split.map &:to_i
ans = arr.permutation.map do |i|
  (n-1).times.sum do |j|
    (i[j]-i[j+1]).abs
  end
end.max
puts ans

=begin
아래는 처음에 짠 코드.

n = gets.to_i
arr = gets.split.map &:to_i
ans = 0
abs = lambda { |x| x >= 0 ? x : -x }
arr.permutation.each do |i|
  result = 0
  (0...n-1).each do |j|
    result += abs.(i[j]-i[j+1])
  end
  ans = [ans, result].max
end
puts ans

=end
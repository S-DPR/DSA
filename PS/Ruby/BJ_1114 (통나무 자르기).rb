=begin
1114번 통나무 자르기

통나무를 정해진 위치에서 C번 이하로 자르려 한다.
가장 긴 통나무가 가장 짧게 되도록 자를 때, 그 길이를 출력하자.
그리고, 자르는 첫 지점을 출력하자. 방법이 여러개라면, 자르는 첫 지점이 가장 앞인 때를 출력하자.

와
와..
진짜 골드1문제는 매일매일이 새롭다

진짜 보자마자 이거 그냥 매개변수탐색이네 엌ㅋㅋㅋㅋㅋㅋㅋㅋ 하고 놀고있었는데..
아.. 역추적이 진 난이도였구나.. 골드1의 이유는 여기였구나..
사람들이 골드 상위권 던져주는데는 이유가 있구나...

역추적때문에 생각을 바꿔야하는 문제였습니다.
기왕이면 제일 앞에서 닦으세요~ = 마 자스가 뒤에서부터 처리해라
라는 의미일줄은 몰랐죠..
뒤에서부터 처리했는데도 자를 기회가 남았다면, 항상 제일 앞에서 자르면 됩니다.
와.. 그리디 진자 매섭다 진짜로...
=end
L, K, C = gets.split.map &:to_i
items = gets.split.map(&:to_i) + [0, L]
items = items.uniq.sort
arr = items.each_cons(2).map do |i, j| j-i end
left = arr.max
right = L
while left < right
  mid = (left + right) >> 1
  sum = 0
  cnt = 0
  arr.length.times.reverse_each do |i|
    if sum+arr[i] > mid
      sum = 0
      cnt += 1
    end
    sum += arr[i]
  end
  cnt <= C ? right = mid : left = mid + 1
end
sum = 0
cnt = 0
f = 0
arr.length.times.reverse_each do |i|
  if sum+arr[i] > right
    f = i
    sum = 0
    cnt += 1
  end
  sum += arr[i]
end
f = 0 if cnt < C
print right, " ", arr[0..f].sum

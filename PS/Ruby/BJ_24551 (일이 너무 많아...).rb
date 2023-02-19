=begin
24551번 일이 너무 많아...

정수 N 이하의 수중 11, 111, 1111, ... 를 약수로 갖는 수의 개수를 출력해주세요.

포함배제 또 너야? 라기엔 지금까지 dp가 너무 많이나왔네.
그런데 조금 다른게, 이문제는 원래 Go로 귀찮아도 풀려고했으나..
BigInteger가 필요합니다. 11111111111111111*1111111111111111같은 연산이 나오거든요.
네, 루비라서 쉽게 풀었습니다. 아니었으면 적어도 골드2~1에 박혀있을 문제.
=end
N = gets.to_i
cnt = 0
arr = [11]
(2..17).each do |i|
  arr.push(arr[-1]+10**i)
end
(1..17).each do |i|
  arr.combination(i) do |j|
    cur = 1
    j.each do |k| cur = cur.lcm(k) end
    cnt += i.odd? ? N/cur : -(N/cur)
  end
end
puts cnt

=begin
17436번 소수의 배수

N개의 소수와 13자리 이하의 양수 M이 주어진다.
M 이하의 자연수중 N의 배수인 수의 개수를 골라보자.

보자마자 딱 오는 이 서늘한 느낌..
포함배제원리.. 너구나..
9359번 (서로소, 파이썬으로 품)에서 고통을 준..

이건 딱 포함배제원리 기초 문제입니다. 배우기 좋은 문제.
Swift로 하려했는데, DFS로 조합 구하기 너무너무 귀찮아서 그냥 Ruby로 풀었습니다.
Ruby, Python 이 둘은 진짜 내장함수가 너무 풍부해요..
파이썬은 import라도 시키지 얘는 뭐..
=end
N, M = gets.split.map &:to_i
arr = gets.split.map &:to_i
cnt = 0
(1..N).each do |i|
  arr.combination(i).each do |j|
    cur = 1
    j.each do |k| cur = cur.lcm(k) end
    cnt += i.odd? ? M/cur : -(M/cur)
  end
end
puts cnt
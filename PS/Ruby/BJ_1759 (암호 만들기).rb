=begin
1759번 암호 만들기

소문자 char이 C개 주어진다.
당신은 여기서 L개를 골라, 모음 (aiueo)중 하나 이상, 이외에 자음이 두 개 이상이 있는 문자열이 있다면,
모두 사전순으로 나열하여 출력해보자.

이제 언어들 익숙해져서 골드는 풀어야 노션에 올릴 수 있고, 근데 오늘은 문제풀이 하기 너무 싫고
그러던 와중 신성처럼 나타난 문제..
파이썬은 요즘 안쓰기 챌린지 하는데 Ruby의 combination으로 날먹이 가능한 문제..
감사힙니다 Ruby GOAT..
=end
L, C = gets.split.map &:to_i
arr = gets.split.sort
arr.combination(L).each do |i|
  cnt = 0
  i.each do |j|
    cnt += 1 if %w[a i u e o].include?(j)
  end
  next if cnt == 0 || L-cnt < 2
  i.each do |j|
    print j
  end
  puts ""
end
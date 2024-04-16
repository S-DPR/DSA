ini = ->() { gets.to_i }
ins = ->() { gets.split.map &:to_i }
INF = 1<<64
=begin
6051번 시간 여행

쿼리 세개를 처리해보자. 각 쿼리를 처리한 뒤, 스택에서 제일 위에 있는 요소를 출력하면 된다.
a x : x를 스택에 넣는다.
s : 스택에서 제일 위 요소를 제거한다.
t x : x번째 쿼리 이전으로 돌아간다. x는 항상 현재까지 나온 쿼리 개수보다 적다.

스택문제 풀어달래서 풀던중 이거 스택보단..이라면서 딴생각하다 푼 문제.
아니 이거 그냥 보다보니 이전인덱스가 언젠지를 적어두는게 나을거같고..
이거 잘 생각해보면 연결리스트죠. 그렇게 풀었습니다.
머 사실상 트리 생성한거같기도하고..
=end
kth = [-1]
prv = [0]
ret = ""
N = ini.()
(1..N).each do |i|
  q, *args = gets.split
  if q == 'a'
    kth << args[0].to_i
    prv << i-1
  elsif q == 's'
    kth << kth[prv[-1]]
    prv << prv[prv[-1]]
  else
    kth << kth[args[0].to_i-1]
    prv << prv[args[0].to_i-1]
  end
end
puts kth[1..N]

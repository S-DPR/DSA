=begin
14395번 4연산

N에서 M으로 갈 때 +, -, *, / 연산을 사용할 수 있다.
+연산 = x+x
-연산 = x-x
*연산 = x*x
/연산 = x/x (x != 0)
최소 연산해 M으로 가는 경우를 출력하자.
그런게 여러개라면 사전순으로 제일 먼저 나오는걸 출력하자.

걍 다 구하고 sort 잘 구셩해서 해야지~ << 개같이 멸망
그냥 bfs로 하나하나 해야지.. << /를 먼저 쓰는게 최적해가 아님을 잊어서 멸망
아... 그냥 +랑 *는 두개밖에 없는데 하드코딩하자.. << AC

이제 메모해둔 꿀문제 다 썼네 어카지
=end
N, M = gets.split.map &:to_i
if N == M
  puts 0
else
  Q = [[N*N, "*"], [N+N, "+"], [1, "/"]]
  flg = false
  until Q.empty?
    cur, trace = Q.shift
    if cur == M
      flg = true
      puts trace
      break
    end
    next if cur >= M
    Q << [cur*cur, trace+"*"] if cur > 1
    Q << [cur+cur, trace+"+"] if cur != 0
  end
  puts -1 unless flg
end
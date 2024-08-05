ini = ->() { gets.to_i }
ins = ->() { gets.split.map &:to_i }
INF = 1<<64
=begin
9527번 1의 개수 세기

정수 A부터 B까지 이진수라 나타냈을 때 1의 개수를 구해보자.

너무 피곤한가
이런문제도 너무 오래걸렸네.. 2시간걸린거같은데
어렵진않은문제인데.. 아..
그냥 너무 당연한 계산식 하나 찾으면 되는..
=end
def cnt(x)
  return 0 if x == 0
  ret = 0
  i = 1
  while x > 0
    ret += x/(1<<i)*(1<<(i-1))
    ret += [x%(1<<i), 1<<(i-1)].min
    x -= 1<<(i-1)
    i += 1
  end
  ret
end

S, E = ins.()
p cnt(E)-cnt(S-1)

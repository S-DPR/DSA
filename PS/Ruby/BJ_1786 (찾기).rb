ini = ->() { gets.to_i }
ins = ->() { gets.split.map &:to_i }
INF = 1<<63
=begin
1786번 찾기

S에서 T가 몇 번, 어디서 나올까?

맛있는 도시락이었는데 마라톤에 나와버려서 그만..
그냥 KMP. 근데 생각해보니 진짜 KMP로 문자가 어디서 몇 번 나오는지 세는건 안했더라고요.
그래서 또 배웠습니다..
=end
S = gets.chomp
T = gets.chomp
N, M = S.length, T.length
f = [0]*M
j = 0
(1...M).each do |i|
  j = f[j-1] while j != 0 && T[i] != T[j]
  if T[i] == T[j]
    j += 1
    f[i] = j
  end
end

j, ret = 0, []
N.times do |i|
  j = f[j-1] while j > 0 && S[i] != T[j]
  next if S[i] != T[j]
  if j == M-1
    ret << i-M+2
    j = f[j]
  else
    j += 1
  end
end

p ret.length
print ret.join("\n")

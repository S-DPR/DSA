ini = ->() { gets.to_i }
ins = ->() { gets.split.map &:to_i }
INF = 1<<64
=begin
3113번 양

N, 수열 A, K가 주어진다.
아래 두 연산을 최소한으로 해서 원래 배열 A를 만들어보자.
ENTER p : 현재 수를 p로 바꾼다.
CLONE a1, a2, ..., ak : 1번째, 2번째, ... k번째 수에 p를 곱한다.

음..
이게..이게플레..?
이딴게..?

그냥 그리디하게 처리해주면됩니다.
솔직히 그냥 k개마다 처리해주는게 더 어려워요 ㅋㅋ
대충.. 대충풀면됩니다.
체스나하러가야지~
=end
require 'prime'
MAX = 1_000_000_000
N = ini.()
A = ins.()
K = ini.()

ret = []
Prime.each(MAX**0.5+1).each do |i|
  cnts = N.times.map do |j| [0, j] end
  N.times do |j|
    while A[j]%i == 0
      A[j] /= i
      cnts[j][0] += 1
    end
  end
  first = true
  until cnts.empty?
    cnts.sort!
    cnts.shift while !cnts.empty? && cnts[0][0] == 0
    break if cnts.empty?
    items = []
    K.times do |j|
      next unless cnts[-1-j]
      next if cnts[-1-j][0] == 0
      items << cnts[-1-j][1]
      cnts[-1-j][0] -= 1
    end
    if first
      ret << ["ENTER", i]
      first = false
    end
    ret << ["CLONE"]
    items.each do |j|
      ret[-1] << j+1
    end
  end
end
dic = Hash.new { |h, k| h[k] = [] }
N.times do |i|
  next unless Prime.prime?(A[i])
  dic[A[i]] << i+1
end
dic.each do |k, v|
  ret << ["ENTER", k]
  until v.empty?
    ret << ["CLONE"]
    K.times do
      ret[-1] << v.pop unless v.empty?
    end
  end
end
result = ""
ret.each do |i|
  result += "#{i.join(" ")}\n"
end
print result

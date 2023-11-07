spl = ->() { gets.split.map &:to_i }
=begin
4716번 풍선

N개의 팀이 있고, A위치에 풍선이 a개, B위치에 풍선이 b개 있다.
각 팀은 i개의 풍선이 필요하고 A위치와 거리가 j만큼, B위치와 거리가 k만큼 떨어져있다.
모든 팀이 풍선을 가져오기 위한 최소 거리 합은 몇일까?

그냥 abs(j-k)가 큰 순으로 정렬한 뒤 차례대로 그 거리를 더하는 문제.
처음에 i도 정렬조건에 있어야 할줄 알았는데 아니었네요.
확실히 그리디는 내 그날 컨디션이 중요한거같아요..
=end
loop do
  n, a, b = spl.()
  break if n.zero?
  arr = n.times.map do spl.() end
  arr.sort! do |i, j|
    -(i[1]-i[2]).abs <=> -(j[1]-j[2]).abs
  end
  puts (arr.map do |i, j, k|
    if j < k
      getA = [a, i].min
      getB = i-getA
    else
      getB = [b, i].min
      getA = i-getB
    end
    a -= getA
    b -= getB
    getA*j+getB*k
  end.sum)
end

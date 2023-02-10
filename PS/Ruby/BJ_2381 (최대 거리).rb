=begin
2381번 최대 거리

점이 N개 있을 때, L1-metric의 최댓값을 구해보자.
(a, b), (c, d)가 있을 때 |a-c|+|b-d|의 최댓값을 구하라는 뜻이다.

아니 어이가없네
풀이보고 풀어버리긴했는데 어이가..

|a-c|+|b-d|에서 더하는 값 a, b를 최대화하고, c, d를 최소화하면 됩니다.

좀 더 자세히 보자면, 이런 경우가 나오겠죠.
a < c && b < d : (c-a)+(d-b) = -(a+b)+(c+d)
a < c && b > d : (c-a)+(b-d) = (b-a)+(c-d)
a > c && b < d : (a-c)+(d-b) = (a-b)+(d-c)
a > c && b > d : (a-c)+(b-d) = (a+b)-(c+d)
결국 묶으면 +-((a+b)-(c+d)), +-((a-b)-(c-d))가 되고..
결국 최대합-최소합 혹은 최대차-최소차를 빼어 최댓값을 취한다.. 가 되겠죠.

아.. 이 간단한걸 놓치냐..
=end
N = gets.to_i
arr = N.times.map do gets.split.map &:to_i end
p = arr.sort_by do |x, y| x+y end
m = arr.sort_by do |x, y| x-y end
print [(p[-1][0]-p[0][0]).abs+(p[-1][1]-p[0][1]).abs,
       (m[-1][0]-m[0][0]).abs+(m[-1][1]-m[0][1]).abs].max

=begin
결국엔 이거랑 같다.
f = arr.minmax_by do |i| i[0]+i[1] end
s = arr.minmax_by do |i| i[0]-i[1] end
print [(f[0][0]-f[1][0]).abs+(f[0][1]-f[1][1]).abs,
       (s[0][0]-s[1][0]).abs+(s[0][1]-s[1][1]).abs].max
=end
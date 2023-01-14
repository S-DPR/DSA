=begin
23815번 똥게임

당신은 수 1을 갖고있다.
N개의 줄에 이 수를 변형할 수 있는 연산자 k와 수 x가 나온다.
k는 +-*/중 하나이며, x는 9 이하의 자연수이다.
매 줄마다 k와 x의 조합이 두개씩 나오는데, 당신은 둘중 하나를 골라 적용시킬 수 있으며,
둘다 원하지 않을경우 N번중 딱 1회만 선택지를 스킵할 수 있다.
이 때, 당신이 가진 수를 최대로 만들었을 때 그 수를 구해보자.

DP입니다. 네.
진짜 "이유는 몰라도 어쨌든 맞음"의 산물..
DP의 첫번째줄에는 '그냥 죽 더한값', 두번째줄에는 '한번 스킵한 값'을 넣었으며,
첫번째줄에는 '현재수에 연산자를 적용했을때 최댓값'을 집어넣었습니다.
두번째줄에는 '이전 값중 스킵한거에 대해 연산자를 적용했을때 최댓값.'
솔직히 뭔진 잘 모르겠는데 맞았네요..

이하는 숏코딩 코드입니다. (231B)
=end

N=gets.to_i
y,z=1,1
s=p=false
N.times{
n,m=gets.split
a=eval"#{y}#{n}"
b=eval"#{y}#{m}"
c=eval"#{z}#{n}"
d=eval"#{z}#{m}"
z=[y,c,d].max
y=[a,b].max
p=true if y<=0&&s
s=true if y<=0
y=z if y<=0}
puts p ? "ddong game":s ? y:[y,z].max

=begin
이하는 평범한 코드입니다. 이 코드를 극한으로 줄인게 저거.
N = gets.to_i
dp = (N+1).times.map do [0, 0] end
dp[0] = [1, 1]
skipped = die = false
N.times.map do |i|
  s1, s2 = gets.split
  curs1 = eval "#{dp[i][0]}#{s1}"
  curs2 = eval "#{dp[i][0]}#{s2}"
  ignores1 = eval "#{dp[i][1]}#{s1}"
  ignores2 = eval "#{dp[i][1]}#{s2}"
  dp[i+1][0] = [curs1, curs2].max
  dp[i+1][1] = [dp[i][0], ignores1, ignores2].max
  die = true if dp[i+1][0] <= 0 && skipped
  skipped = true if dp[i+1][0] <= 0
  dp[i+1][0] = dp[i+1][1] if dp[i+1][0] <= 0
end
puts die ? "ddong game" : skipped ? dp[-1][0] : dp[-1].max
=end
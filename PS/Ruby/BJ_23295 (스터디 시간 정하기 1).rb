=begin
23295번 스터디 시간 정하기 1

N명의 원하는 스터디 시간이 주어진다.
각 사람은 원하는 스터디 시간을 k개 제출했고,
자기가 원하는 시간이 1시간 들어갈 때마다 행복도가 1 늘어난다.
스터디는 T시간동안 할 것이다. 최대 행복도를 할 수 있는 가장 빠른 구간을 구해보자.

아.
e까지가 아니라 e에서 끝나는거였구나!!
그거때매 시간 30분날렸네..
아....

웬일로 그냥 스위핑이 올라와서 해본 문제.
누적합으로 했어도 됐을거같은데..
.. e+1한게 문제라고 생각도 못해서 누적합은 안썼습니다.
투포인터+스위핑문제였습니다.
=end
N, T = gets.split.map &:to_i
MAX = 100000
S = [0]*(MAX+2)
N.times do
    k = gets.to_i
    k.times do
        s, e = gets.split.map &:to_i
        S[s] += 1
        S[e] -= 1
    end
end
(1..MAX).each do |i|
    S[i] += S[i-1]
end
max = S[0...T].sum
cur = max
lo, hi = 0, T
(0..MAX-T).each do |i|
    cur = cur + S[i+T]-S[i]
    if max < cur
        max = cur
        lo = i+1
        hi = i+T+1
    end
end
print lo, " ", hi
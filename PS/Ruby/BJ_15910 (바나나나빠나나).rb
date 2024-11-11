def ini() gets.to_i end
def ins() gets.split.map &:to_i end
INF = 1<<63
=begin
15910번 바나나나빠나나

유사 바나나 문자열은 유사 바나나 단어를 한 개 이상 이어붙인 문장이다.
유사 바나나는 아래 조건을 따른다.
 - BANANA는 유사 바나나 단어이다.
 - 유사 바나나 단어 앞에 B를 붙여도 유사 바나나 단어이다.
 - 유사 바나나 단어 뒤에 NA를 붙여도 유사 바나나 단어이다.
주어진 문자열에서 문자 몇 개를 바꾸어 유사 바나나 문자열로 바꾸려한다.
적어도 몇 개의 문자를 바꾸어야할까?

그냥 머 이런류문제는..
dp거나 투포인터거나 하는게 너무 일반적이라서 그냥 dp로 고정하고 풀어봤습니다.
솔직히 투포인터로 각 재는거는 그냥 생각도 안해봤습니다.

dp[i][j] = i번째 문자까지 진행률이 j일 때 바꾼 횟수.
간단한 문제였네요. 30분만에 풀었으면 뭐..
=end
X = 'BANANA'
S = gets.strip
N = S.length
dp = (0..N).map do [INF]*6 end
dp[0] = [INF, INF, INF, INF, INF, 0]
(1..N).each do |i|
  ch = S[i-1]
  6.times do |j|
    idx = (j+5)%6
    dp[i][j] = ch == X[j] ? dp[i-1][idx] : dp[i-1][idx]+1 if dp[i-1][idx] != INF
  end
  isNotB = ch == 'B' ? 0 : 1
  isNotN = ch == 'N' ? 0 : 1
  if i-1 != 0
    dp[i][0] = [dp[i][0], dp[i-1][0]+isNotB].min
    dp[i][4] = [dp[i][4], dp[i-1][5]+isNotN].min
  end
end
p dp[-1][-1]

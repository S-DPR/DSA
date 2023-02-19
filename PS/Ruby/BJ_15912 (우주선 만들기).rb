=begin
15912번 우주선 만들기

물건을 N개 파는 상점이 있다.
각 물건은 1부터 N번으로 번호가 있으며, i번(i > 1)을 사기 위해서는 1부터 i-1번까지 물건을 모두 사두어야한다.
또, 물건마다 가치와 무게가 있는데 이 둘의 곱이 가격이 된다.
당신은 i번부터 j번까지 모든 물건을 살 수도 있다. 이 경우 (i번~j번 물건 가치의 최댓값)*(i번~j번 물건 무게의 최댓값)이 그 가격이 된다.
1번부터 N번까지 모든 물건을 구매했을 때 가격의 최솟값을 구해보자.

N이 1000밖에 안돼서 O(N^3) 무지성으로 박아도 풀리는 문제.
이 문제 처음볼땐 세그트리인줄알았는데 그냥 dp더라고요.
dp혐오증 걸려있을때라 한동안 못 풀다가 이제 풉니다.

dp[i]를 i번째 물건까지 구매했을 때 최댓값으로 둡니다.
dp[i]를 갱신하기 위해, i보다 작은 음이 아닌 정수인 j를 정의하고,
dp[j]와 j+1~i번째 물건의 최대 가치*최대 무게를 더한 뒤,
dp[i]보다 작다면 그 값을 갱신합니다.

마지막으로 dp[N]이 정답이 됩니다.
=end
INF = 1_000_000_000_000
N = gets.to_i
W = [0]+(gets.split.map &:to_i)
V = [0]+(gets.split.map &:to_i)
dp = [INF]*(N+1)
(1..N).each do |i|
  dp[i] = W[0..i].max * V[0..i].max
  (1...i).each do |j|
    dp[i] = [dp[i], dp[j]+W[j+1..i].max*V[j+1..i].max].min
  end
end
p dp[N]
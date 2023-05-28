=begin
LeetCode - Minimum Cost to Cut a Stick

길이가 n인 막대가 있고, 막대를 자를 x좌표의 모임인 cuts배열이 있다.
막대를 자르려 한다면, 해당 부분을 자르기 직전에 해당 막대의 길이만큼의 비용이 든다.
cuts를 모두 잘랐을 때 최소비용을 구해보자.

와!!!!!!!!!!!!!!!!!!!!!!!! 파일합치기 아시는구나!!!!!!!!!!!!!!!
정말정말어렵습니다!!!!!!!!!!!!!!!!

동아리 형에게 들어온 '이거 난이도 몇같아?'
대충 문제 들어보니 그리디+이분탐색인줄알고 '실버 상위~골드 하위?'

현실은 참혹하게도, 골드3으로 시작하는 파일 합치기 아종문제.
파일합치기의 좌표부분있죠.. 그거 구현은 아직도 너무 어렵습니다.
할때마다 한 시간씩 걸려요. 별거 아닌데도..

어쩄든 핵심은 cuts에서 쪼갤생각 하지말고 cuts에서 합칠생각을 하라.
파일합치기와 같은 난이도일텐데, 역으로 생각하는게 힘들었..다기보단,
제가 그냥 N제한이 100만인거 보고 dp아니겠거니 했던게 더 컸습니다.
cuts는 최대 100이라서 O(N^3) DP도 소화가 되는 크기였거든요.
앞으로 입력 제한 더 확실하게 보는거로..
=end
INF = 1<<30
def min_cost(n, cuts)
  cuts << n << 0
  cuts.sort!
  cuts = cuts.each_cons(2).map do |i, j| j-i end
  pf = cuts.reduce([0]) { |ret, i| ret << ret[-1] + i }
  len = cuts.length
  dp = len.times.map do [INF]*len end
  len.times do |i| dp[i][i] = 0 end
  len.times do |i|
    (0..i-1).reverse_each do |j|
      (1..i-j).each do |k|
        item = dp[j+k][i] + dp[j][j+k-1] + pf[i+1] - pf[j]
        dp[j][i] = [dp[j][i], item].min
      end
    end
  end
  dp[0][-1]
end

p min_cost(7, [1, 3, 4, 5])
p min_cost(9, [5, 6, 1, 4, 2])
p min_cost(30, [18,15,13,7,5,26,25,29])

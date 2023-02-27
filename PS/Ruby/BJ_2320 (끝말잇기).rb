require 'set'
=begin
2320번 끝말잇기

AEIOU가 무작위로 나열된 단어가 N개 주어진다.
중복된 단어는 없다고 할 때, 끝말잇기처럼 이었을 때 최대 길이를 만들려 한다.
최대 길이가 되었을 때, 그 길이를 출력하자. 첫 글자로는 아무거나 와도 상관없다.

손에 잡히면 뭐든 DP
이쯤되면 억까가 아닐까??

N이 16이하라서 너무너무 쉬운 문제입니다.
사실 순열이라서 그냥 대놓고 브루트포스하면 16!라는 놀라운 시간복잡도가 나타납니다.
이걸 대충 O(N^2)정도로 줄일 수 있는게 신기하네요.. 저는 좀 비효율적인듯 하지만..

정해는 비트필드를 활용한 dp라고 합니다.
사실 뭔지 몰라서 저는 dp[i][j]를 i번째 문자로 끝날 때 길이가 j인 녀석들의 모임, 이렇게 잡았고.
사실상 백트래킹 돌려서 맞춘느낌입니다. 기분탓인가..

아, 비트마스킹을 안한거는 아닙니다. Set에 넣을 때 비트마스킹을 했거든요.
=end
N = gets.to_i
len = N*100+1
arr = N.times.map do gets.rstrip end
dp = 5.times.map do len.times.map do Set.new end end
alpha = {'A' => 0, 'E' => 1, 'I' => 2, 'O' => 3, 'U' => 4}
N.times do |i|
  dp[alpha[arr[i][-1]]][arr[i].length] << (1 << i)
end
N.times do
  N.times do |i|
    len.times.reverse_each do |j|
      start_alpha = alpha[arr[i][0]]
      last_alpha = alpha[arr[i][-1]]
      dp[start_alpha][j].each do |k|
        next if (k & (1 << i)).nonzero?
        dp[last_alpha][j+arr[i].length] << (k | (1 << i))
      end
    end
  end
end

puts(5.times.map do |i|
       dp[0].length.times.map do |j|
         !dp[i][j].empty? ? j : 0
       end.max
     end.max)

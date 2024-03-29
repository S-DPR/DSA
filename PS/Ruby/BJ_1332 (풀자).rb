=begin
1332번 풀자

N, V, 수열이 주어진다.
수열의 제일 앞 수부터 시작해, 1칸 혹은 2칸씩 앞으로 뛰어나가자.
이 떄 밟아온 칸의 최댓값과 최솟값의 차가 V 이상이라면 그 자리에서 멈추자.
만약 그런게 없다면 모든 발판을 밟아야한다.
이 규칙으로 최소한의 수만 밟았다 할 때, 몇 개를 밟게되는지 구해보자.
시작점과 끝점도 당연히 밟은 것 취급이다.

수가 작으면 DP의심증을 일으키던 문제를 바로 해결해준 문제
분류보고 브루트포스요??? 이러고 dfs로 짰다가 망하고,
조금 더 지능적으로 생각해보자.. 하면서 풀었습니다.

첫 인덱스부터 보면서, 뒤의 모든 인덱스를 봅시다.
보다가 첫 값과 본 인덱스의 차이가 V 이상인 최초의 인덱스에 대해서,
몇 번 밟아야 거기까지 갈 수 있는지 계산을 합니다.
이 과정을 모든 인덱스에 대해 반복합니다.

참.. 분류 안봤으면 평생 DP로 어캐하나 고민할뻔했네요..
=end
N, V = gets.split.map &:to_i
arr = gets.split.map &:to_i
result = N
N.times do |i|
  (i+1...N).each do |j|
    if (arr[j]-arr[i]).abs >= V
      result = [result, 1+(i/2+i%2)+((j-i)/2+(j-i)%2)].min
      break
    end
  end
end
puts result

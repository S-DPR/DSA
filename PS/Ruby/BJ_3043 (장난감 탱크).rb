ini = ->() { gets.to_i }
ins = ->() { gets.split.map &:to_i }
INF = 1<<63
=begin
3043번 장난감 탱크

N*N 체스보드에 룩이 N개 있다.
룩은 한 번에 한 칸씩 움직일 수 있다.
룩이 서로 공격받지않게 이동하는 방법이 뭘까?
단, 이동중에 룩이 겹치면 안된다.

어캐풀까~하다가 N=500보고 에이 설마 그리디겠어 방심하고 바로 그리디 철퇴 맞은 문제.
아이고..

행이든 열이든 겹치지 않게 두면 그 이후는 중요하지 않습니다.
그러므로, 일단 뭐 한쪽만 잘 둬봅시다.
1번행, 2번행, ... 에서 각 열에 가장 가까운 룩 하나씩 두도록 합시다.
단, 같은 방향으로 이동해야하는놈은 반드시 가장 그 방향쪽에 있는애를 먼저 옮겨야 안겹칩니다.
=end

N = ini.()
X, Y, R = [], [], [[], []]
N.times do |i|
  y, x = ins.()
  X << [x, i+1]
  Y << [y, i+1]
end
X.sort!
Y.sort!
(1..N).each do |i|
  x, kth = X[i-1]
  (i-x).times do R[0] << [kth, 'R', i] end
  (x-i).times do R[0] << [kth, 'L', i] end
end
(1..N).each do |i|
  y, kth = Y[i-1]
  (i-y).times do R[1] << [kth, 'D', i] end
  (y-i).times do R[1] << [kth, 'U', i] end
end
R[0].sort_by! do |_, j, i| j == 'L' ? i : -i end
print R[0].count+R[1].count, "\n"
print ((R[0]+R[1]).map do |i, j, _| "#{i} #{j}" end.join("\n"))

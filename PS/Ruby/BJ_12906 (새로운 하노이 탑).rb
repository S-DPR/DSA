=begin
12906번 새로운 하노이 탑

A, B, C 세 막대가 있고, 총 N(1<=N<=10)개의 A, B, C 원반이 거기에 꽂혀있다.
A, B, C 원반을 각각 제자리의 A, B, C 막대로 옮기려고 할 때, 옮겨야하는 최소횟수를 구해보자.
'옮긴다'의 정의는 "한 막대의 제일 위 원반을 다른 막대의 가장 위로 옮긴다"이다.

재귀인가? 하다가 점점 dfs로 가더니, 스택에러나서 그냥 BFS로 전환한 문제.
visit을 특이하게 한다는 점이 특징입니다.
시간초과날줄알았더니 시간제한이 5초더라고요. 5100ms정도 나온걸 보니 2초여도 아슬아슬하게 통과했을듯. (2048+2048+1024)
visit을 배열로 안하는 특이한 문제였습니다.
=end
arr = 3.times.map do
  _, *r = gets.split
  r[0] || ''
end
visit = {}
Q = [[arr, 0]]
until Q.empty?
  curA, curT = Q.shift
  curA[0] = curA[0][1..] while curA[0][0] == 'A'
  curA[1] = curA[1][1..] while curA[1][0] == 'B'
  curA[2] = curA[2][1..] while curA[2][0] == 'C'
  if (curA[0].length + curA[1].length + curA[2].length).zero?
    p curT
    break
  end
  hash = curA.join(" ")
  next if visit[hash] && visit[hash] < curT
  3.times do |i|
    next if curA[i].length.zero?
    3.times do |j|
      next if i == j
      tmp = curA.clone
      tmp[j] += tmp[i][-1]
      tmp[i] = tmp[i][0...tmp[i].length-1]
      new_hash = tmp.join(" ")
      if !visit[new_hash] || visit[new_hash] > curT+1
        visit[new_hash] = curT+1
        Q << [tmp, curT+1]
      end
    end
  end
end
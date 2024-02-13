ini = ->() { $stdin.gets.to_i }
ins = ->() { $stdin.gets.split.map &:to_i }
INF = 1<<64
=begin
1113번 수영장 만들기

숫자로 이루어진 맵이 주어진다.
만약에 어떤 수 K보다 큰 수가 테두리를 두를 수 있다면,
그 내부는 모두 K로 채울 수 있다. 이 경우 그 내부의 원래 합이 S일 때 (채운 칸)*K-S만큼 물을 채운게 된다.
이 때, 맵에 물을 얼마나 채울 수 있나 구해보자.

이거 상위호환 두개 북마크에 있는데 언제 풀려나..
어쨌든 열심히 BFS 굴리면 됩니다.
수가 전부 9 이하라는 강력한 조건이 있어서 가볍게 풀 수 있고..
이정도면 난이도 쉽다 생각해요.
실수 두 번 해서 두 번 틀리긴 했는데..
=end
dr = [1, -1, 0, 0]
dc = [0, 0, 1, -1]
R, C = ins.()
M = R.times.map do gets.strip.chars.map &:to_i end
GV = R.times.map do [false]*C end
p(10.times.reverse_each.map do |h|
  edge = R.times.map do [false]*C end
  R.times.map do |r|
    C.times.map do |c|
      next if M[r][c] >= h
      next if GV[r][c] || edge[r][c]
      vis = GV.map do |i| i.clone end
      isEdge = r == 0 || r == R-1 || c == 0 || c == C-1
      area = 1
      s = M[r][c]
      q = [[r, c]]
      vis[r][c] = true
      until q.empty?
        curR, curC = q.shift
        4.times do |i|
          nxtR, nxtC = curR+dr[i], curC+dc[i]
          next unless 0 <= nxtR && nxtR < R
          next unless 0 <= nxtC && nxtC < C
          next if M[nxtR][nxtC] >= h
          next if vis[nxtR][nxtC]
          isEdge = isEdge || nxtR == 0 || nxtR == R-1 || nxtC == 0 || nxtC == C-1
          vis[nxtR][nxtC] = true
          s += M[nxtR][nxtC]
          q << [nxtR, nxtC]
          area += 1
        end
      end
      if isEdge
        R.times do |rr|
          C.times do |cc|
            edge[rr][cc] = edge[rr][cc] || vis[rr][cc]
          end
        end
        0
      else
        R.times do |rr|
          C.times do |cc|
            GV[rr][cc] = GV[rr][cc] || vis[rr][cc]
          end
        end
        area*h-s
      end
    end.compact.sum
  end.compact.sum
end.compact.sum)

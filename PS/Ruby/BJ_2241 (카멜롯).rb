$stdin.sync = false
$stdout.sync = false
ini = ->() { $stdin.gets.to_i }
ins = ->() { $stdin.gets.split.map &:to_i }
INF = 1<<64
=begin
2241번 카멜롯

하나의 킹과 여러 나이트가 있다.
나이트는 킹을 태울 수 있다.
이 때, 모든 킹과 나이트를 한 지점에 최소 횟수로 모아보자.

하아
입력조건 아니었으면 한 3~4번만에 맞추는데..

세번쨰줄에 모든 나이트가 주어지는지 알고 망했습니다.
그냥.. 그냥 좀 생각하니까 푸는 방법 나온 문제였는데..
=end
parsing = ->(arr) do
  ret = []
  until arr.empty?
    col = arr.shift.ord - 'A'.ord
    row = arr.shift.to_i - 1
    ret << [row, col]
  end
  ret
end

bfs = -> (r, c, t, q) do
  dr = [2, 2, 1, -1, -2, -2, -1, 1]
  dc = [1, -1, -2, -2, 1, -1, 2, 2]
  until q.empty?
    curR, curC = q.shift
    curT = t[curR][curC]
    8.times do |i|
      nxtR, nxtC = curR+dr[i], curC+dc[i]
      next unless 0 <= nxtR && nxtR < r
      next unless 0 <= nxtC && nxtC < c
      next if t[nxtR][nxtC] <= curT+1
      t[nxtR][nxtC] = curT+1
      q << [nxtR, nxtC]
    end
  end
  t
end

R, C = ins.()
king = parsing.(gets.strip.split)[0]
knight = []
while true
  str = gets.strip
  break if str[0] == '-'
  parsing.(str.split).each do |i|
    knight << i
  end
end

king_pos = []
(-2..2).each do |i|
  (-2..2).each do |j|
    r, c = king[0]+i, king[1]+j
    next unless 0 <= r && r < R
    next unless 0 <= c && c < C
    king_pos << [r, c]
  end
end
king_time = king_pos.map do |r, c|
  kt = R.times.map do [INF]*C end
  kt[r][c] = [(r-king[0]).abs, (c-king[1]).abs].max
  [bfs.(R, C, kt, [[r, c]]), r, c]
end

ret = R.times.map do |r|
  C.times.map do |c|
    if knight.empty?
      0
    else
      time = R.times.map do [INF]*C end
      time[r][c] = 0
      time = bfs.(R, C, time, [[r, c]])
      all_time = knight.sum do |rr, cc| time[rr][cc] end
      with_king = knight.map do |rr, cc|
        king_time.map do |t, hr, hc| # [h]ijacking
          all_time-time[rr][cc]+t[rr][cc]+time[hr][hc]
        end.min
      end.min
      [all_time+[(king[0]-r).abs+(king[1]-c).abs].max, with_king].min
    end
  end.min
end.min
p (ret == INF ? -1 : ret)

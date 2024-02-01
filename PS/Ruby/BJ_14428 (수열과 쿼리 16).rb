$stdin.sync = false
$stdout.sync = false
ini = ->() { $stdin.gets.to_i }
ins = ->() { $stdin.gets.split.map &:to_i }
INF = 1<<64
=begin
14428번 수열과 쿼리 16

두 쿼리를 처리해보자.
1 u v : u번 인덱스의 값을 v로 바꾼다.
2 u v : u부터 v까지의 수 중 가장 작은 값의 인덱스를 출력한다.

에휴
플레5따리 문제에 매달리다가, 될거같은데 안되네 하면서 시간다뺏기고
골드1 쉬운거 풀려다가 멘탈나가서 실패하고
그냥 결국 이악물고 안먹었던 비상식량 섭취..
ㅠㅠ
한방에성공...
=end
def update(seg, idx, val)
  idx += N
  seg[idx] = val
  while idx > 1
    seg[idx>>1] = [seg[idx], seg[idx^1]].min
    idx >>= 1
  end
end

def query(seg, l, r)
  ret = [INF, INF]
  l, r = l+N, r+N
  while l <= r
    if l&1 == 1
      ret = [ret, seg[l]].min
      l += 1
    end
    if r&1 == 0
      ret = [ret, seg[r]].min
      r -= 1
    end
    l, r = l >> 1, r >> 1
  end
  ret
end

N = ini.()
A = [0] + ins.()
seg = (N*2+2).times.map do [INF, INF] end
(1..N).each do |i|
  update(seg, i, [A[i], i])
end
puts(ini.().times.map do
  q, u, v = ins.()
  if q == 1
    update(seg, u, [v, u])
  else
    query(seg, u, v)[1]
  end
end.compact)

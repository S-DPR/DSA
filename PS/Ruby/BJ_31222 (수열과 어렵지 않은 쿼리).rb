ini = ->() { gets.to_i }
ins = ->() { gets.split.map &:to_i }
INF = 1<<64
=begin
31222번 수열과 어렵지 않은 쿼리

수열 A와 두 쿼리가 주어진다. 모두 처리해보자.
1 i x : i번째 원소를 x로 바꾼다.
2 l r : A[l..r] 부분수열에서, i번째 숫자와 i+1번째 숫자가 달라지는 구간의 개수를 센다.

간단한 세그트리
대충 진짜 세그트리 긁으면 끝납니다.
다만 A[l]을 보면서 하는거로 했는데 A[l]업데이트를 제대로 안해서 두 번 틀린..
후..
=end
def update(idx, val)
  idx += N
  @segT[idx] = val
  while idx > 1
    @segT[idx>>1] = @segT[idx]+@segT[idx^1]
    idx >>= 1
  end
end

def query(l, r)
  l, r = l+N, r+N
  ret = 0
  while l <= r
    if l&1 == 1
      ret += @segT[l]
      l += 1
    end
    if r&1 == 0
      ret += @segT[r]
      r -= 1
    end
    l, r = l>>1, r>>1
  end
  ret
end

N, Q = ins.()
@segT = [0]*(N*2+2)
A = [-1] + ins.()
(1..N).each do |i|
  update(i, 1) if A[i] != A[i-1]
end
res = []
Q.times do
  q, l, r = ins.()
  if q == 1
    update(l, 0)
    update(l, 1) if A[l-1] != r
    if l+1 <= N
      A[l+1] == r ? update(l+1, 0) : update(l+1, 1)
    end
    A[l] = r
  else
    ret = query(l, r)
    ret += 1 if query(l, l) == 0
    res << ret
  end
end
puts res

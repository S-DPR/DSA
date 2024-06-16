ini = ->() { gets.to_i }
ins = ->() { gets.split.map &:to_i }
INF = 1<<64
=begin
27991번 고장난 프린터

프린터기에 순서대로 출력할 문서가 N개 있다. 프린터기는 반드시 순서대로 출력한다.
이 때, 모든 문서에 똑같은 잉크 X를 사용 할 예정이다. 또, 프린터기에는 현재 K만큼의 잉크가 있다.
다만, 각 문서는 적어도 x_i만큼의 잉크를 사용해야 제대로 출력이 된다.
이 때, 최대한 많은 문서가 제대로 출력이 되기 위해 설정해야하는 최소 K를 구해보자.
모든 잉크를 사용한 뒤에는 0의 잉크로 문서를 출력한다.

보자마자 어 이거 매개변수아닌가? 하다가,
생각해보니 그게 아니네?? 하고 조금 더 생각해보니.
아하! 세그트리 또 너야??
오랜만이네, 한 번 풀어보자! 가 되었습니다.

일단 좌표압축 해주고,
큰 수부터 작은수로 들어가면 항상 K의 잉크를 사용 가능한 문서 수가 늘어나므로,
큰 수부터 천천히 체크해봅니다.
이 때, 스코프 바깥쪽에서 A의 인덱스를 몇까지 업데이트했나 체크하면서요.
그러면 대충.. NlogN인가요? 잘 풀리네요.
=end
def int_query(seg, idx)
  ret = 0
  while idx > 0
    ret += seg[idx]
    idx -= idx&-idx
  end
  ret
end

def query(seg, l, r)
  int_query(seg, r) - int_query(seg, l-1)
end

def update(seg, idx)
  while idx <= N
    seg[idx] += 1
    idx += idx&-idx
  end
end

N, K = ins.()
A = ins.()
C = A.uniq.sort.each_with_index.map do |val, idx|
  [val, idx + 1]
end.to_h
seg = [0]*(N+1)
u_idx, mx, ret = 0, -1, 0
C.keys.reverse_each do |i|
  can = K/i
  while u_idx < N && u_idx < can
    update(seg, C[A[u_idx]])
    u_idx += 1
  end
  bonus = can < N && K-can*i >= A[can] ? 1 : 0
  cnt = query(seg, 1, C[i])+bonus
  if cnt != 0 && mx <= cnt
    mx = cnt
    ret = i
  end
end
p (mx == -1 ? 0 : ret)

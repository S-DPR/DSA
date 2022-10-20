import sys
input = sys.stdin.readline
"""
12844번 XOR

일반 Lazy-SegmentTree가 P4정도에 책정이되어있는데,
이 문제는 교환법칙이 성립하는 경우에 대하여 Lazy가 적용 가능함을 이용한 문제입니다.
단순히 Lazy되는게 +연산에서 XOR연산이 되었다고 난이도가 오른 케이스입니다.

교환법칙이 성립되지 않는 Lazy-Propagate문제는 대부분 P1 이상 난이도가 책정된 상황입니다.
특히, Segment Tree Beats의 경우(수열과 쿼리 26-30), 모든 문항이 다이아로 떡칠되어있습니다.
개인적으로 이 친구 활용이랑 PST 활용이 세그트리중 난이도가 제일 높지 않나 생각해봅니다..
"""
class Node:
  def __init__(self, v = 0):
    self.val = v
    self.lazy = 0

def init(n, s, e):
  if s == e:
    seg[n] = Node(arr[s])
  else:
    m = s + e >> 1
    seg[n] = Node(init(n*2, s, m).val ^ init(n*2+1, m+1, e).val)
  return seg[n]

def propagation(n, s, e):
  if seg[n].lazy:
    if (e - s + 1) % 2:
      seg[n].val ^= seg[n].lazy
    if s != e:
      seg[n*2].lazy ^= seg[n].lazy
      seg[n*2+1].lazy ^= seg[n].lazy
    seg[n].lazy = 0

def query(n, s, e, l, r):
  propagation(n, s, e)
  if s > r or e < l:
    return 0
  if s >= l and e <= r:
    return seg[n].val
  m = s + e >> 1
  return query(n*2, s, m, l, r) ^ query(n*2+1, m+1, e, l, r)
  
def update(n, s, e, l, r, k):
  propagation(n, s, e)
  if s > r or e < l: return
  if s >= l and e <= r:
    if (e - s + 1) % 2:
      seg[n].val ^= k
    if s != e:
      seg[n*2].lazy ^= k
      seg[n*2+1].lazy ^= k
    return
  m = s + e >> 1
  update(n*2, s, m, l, r, k)
  update(n*2+1, m+1, e, l, r, k)
  seg[n].val = seg[n*2].val ^ seg[n*2+1].val

n = int(input())
arr = tuple(map(int, input().split()))
seg = [0] * (n*4)
init(1, 0, n-1)
for _ in ' '*int(input()):
  c = tuple(map(int, input().split()))
  if c[0] == 1:
    update(1, 1, n, c[1]+1, c[2]+1, c[3])
  else:
    print(query(1, 1, n, c[1]+1, c[2]+1))

import sys
input = sys.stdin.readline
"""
16979번 수열과 쿼리 23

사실 이런 고난이도 쿼리문제쯤 되면 PyPy로 푸는 사람이 현저하게 적어집니다.
C에 비해 시간을 3배하고도 2초 더 늘려줘도 PyPy가 현저하게 느리단거죠.
이 문제의 시간제한은 5초입니다. 즉 파이썬으로 17초이며,
제가 푼 방법은 PyPy로 16328ms, 즉 16.3초라는 아슬아슬한 시간으로 마무리한 코드입니다.
현재(220924) 주석을 다는 이 날까지, 이 문제를 PyPy로 푼 사람은 절 포함 단 3명입니다.

쿼리문제죠. 세그먼트 트리, Mo's를 이해하고 있다고 가정하겠습니다.
문제는 간단합니다.
"수열을 드릴게, Inversion Counting을 해봐."
정말정말 쉽게 말해서, P5 1517번 '버블 소트'를 매 쿼리마다 처리하라는 소리입니다.
매 쿼리마다 처리를 해야하는데 쿼리가 10만개라서,
Merge Sort Tree를 사용하면 그대로 시간초과가 '펑'하고 터집니다.
"""

# 노드입니다. Mo's에 사용됩니다.
class Node:
    def __init__(self, s, e, n):
        self.s = s; self.e = e; self.n = n

# lowerbound입니다. 제가 이때는 C처럼 좌표압축하는거에 맛들려서..
# 이 방법이 꽤 느리단건 최근에 알았습니다. 빠른거에 비해 10배까지도 더 느리더군요.
def lowerbound(n):
    l = 0; r = len(brr)
    while l < r:
        m = l + r >> 1
        if brr[m] >= n:
            r = m
        else:
            l = m + 1
    return r+1

# 업데이트입니다. Fenwick-Tree로 하면 빨랐겠지만,
# 여기선 Bottom-Up Segment Tree를 사용했습니다.
def update(idx, val = 1):
    idx += MAX - 1; seg[idx] += val
    while idx > 1:
        seg[idx>>1] = seg[idx] + seg[idx^1]
        idx >>= 1

# 쿼리입니다. 네.
def query(l, r):
    res = 0
    l += MAX - 1; r += MAX - 1
    while l <= r:
        if l & 1:
            res += seg[l]; l += 1
        if not r & 1:
            res += seg[r]; r -= 1
        l >>= 1; r >>= 1
    return res

# 입력을 받는 곳입니다.
n, m = map(int, input().split())
arr = list(map(int, input().split()))

# 좌표압축을 하는 곳입니다.
brr = sorted(list(set(arr)))
for i in range(n):
    arr[i] = lowerbound(arr[i])

# 쿼리 입력 및 Mo's를 처리하는 곳입니다.
nsqrt = int(n**.5)
Q = list()
for _ in ' '*m:
    x, y = map(int, input().split())
    Q.append(Node(x-1, y-1, len(Q)))
Q.sort(key = lambda x: (x.s//nsqrt, x.e))

# 세그먼트 구축하는 곳입니다.
MAX = len(brr)
seg = [0]*(MAX*2)

# 첫 쿼리인데요..
# 생각을 해봅시다. 사실 Mo's쓰면 Inversion Counting정도는 쉽게 되잖아요.
# 원리는 간단합니다. 시작부분부터 처리를 하면서, '나보다 큰 수가 몇개있지?'를 보면 되겠죠.
# 쿼리의 l을 arr[i]+1로, 끝을 최댓값으로 잡으면 끝납니다.
ans = [0]*len(Q)
s = Q[0].s; e = Q[0].e; res = 0
for i in range(s, e+1):
    update(arr[i])
    res += query(arr[i]+1, MAX)
ans[Q[0].n] = res

# 이제 지울때가 문제겠죠. 지울때는 '나보다 작은 수가 몇개가있었지?'를 보면서 그 수를 다 지우면됩니다.
# query(1, arr[s]-1)이런식으로 해서 나온 결괏값을 빼면 된다는거죠.
for x in Q:
    while x.e < e:
        update(arr[e], -1)
        res -= query(arr[e]+1, MAX)
        e -= 1
    while x.s > s:
        update(arr[s], -1)
        res -= query(1, arr[s]-1)
        s += 1
    while x.s < s:
        s -= 1
        update(arr[s])
        res += query(1, arr[s]-1)
    while x.e > e:
        e += 1
        update(arr[e])
        res += query(arr[e]+1, MAX)
    ans[x.n] = res
for i in ans: # 답을 출력합니다.
    print(i)

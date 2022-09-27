import sys
input = sys.stdin.readline
MAX = 10**9

"""
20212번 나무는 쿼리를 싫어해~

언제나 짜릿한 쿼리문제입니다.
역시, 파이썬으로 푼 사람은 많이 없지만, 뭐 어때요.
풀리면 된겁니다.

이 문제를 푸는 정해는 "좌표 압축"을 이용하는거지만,
사실 전 어떻게 하는건지 모르겠습니다.
대신, "Dynamic Segment Tree", 다시말해 "동적 세그먼트 트리"를 써보겠습니다.
역시, 자료구조에 대한 설명은 없습니다.
"""

"""
동적 세그먼트 트리 (이하 DST)는 PST를 배우기 전에 한번 거치는(게 편한) 자료구조입니다.
사실 이걸 문제에 직접 쓰는건 이문제 말고는 못봤습니다.
이 문제는 DST를 그냥 쓰는게 아니라, Lazy-Propagation도 합쳤습니다.
그래서, lazy값까지 합쳐 넣어줍시다.
"""
class Node:
    def __init__(self, val = 0, l = 0, r = 0, lazy = 0):
        self.v = val
        self.lazy = lazy
        self.l = l; self.r = r

"""
사실 다이나믹 세그트리를 만들 때 노드를 어디서 추가해야하나 고민 많이했는데.
한시간정도 고민하다 이 propagate자리가 딱 맞다는걸 알게되었습니다.
"""
def propagate(n, s, e):
    if not seg[n].l:
        seg[n].l = len(seg)
        seg.append(Node())
        
    if not seg[n].r:
        seg[n].r = len(seg)
        seg.append(Node())
        
    if seg[n].lazy:
        seg[n].v += (e - s + 1) * seg[n].lazy
        if s != e:
            seg[seg[n].l].lazy += seg[n].lazy
            seg[seg[n].r].lazy += seg[n].lazy
        seg[n].lazy = 0

"""
업데이트입니다. 그냥 lazy-propagate에서 하던대로 하면 됩니다.
어차피 DST 처리는 propagate함수가 도맡아 하고있으니까요.
"""
def update(l, r, val, n = 1, s = 1, e = MAX):
    propagate(n, s, e)
    
    if s > r or e < l:
        return

    if s >= l and e <= r:
        seg[n].v += (e - s + 1) * val
        if s != e:
            seg[seg[n].l].lazy += val
            seg[seg[n].r].lazy += val
        return

    m = s + e >> 1
    update(l, r, val, seg[n].l, s, m)
    update(l, r, val, seg[n].r, m+1, e)
    seg[n].v = seg[seg[n].l].v + seg[seg[n].r].v

"""
쿼리입니다.
사실, lazy-propagation도 bottom-up 방식으로 가능한거로 아는데,
저는 적어도 이거만큼은 up-down방식으로 합니다.
그게 이해하기 편하고, 디버깅도 편하고..

n == 0인건, 노드가 처음위치인겁니다.
그니까, 예외 잡아준겁니다. 계속 돌지 않도록.
"""
def query(l, r, n = 1, s = 1, e = MAX):
    propagate(n, s, e)
    if s > r or e < l or n == 0:
        return 0
    if s >= l and e <= r:
        return seg[n].v
    m = s + e >> 1
    return query(l, r, seg[n].l, s, m) + query(l, r, seg[n].r, m+1, e)

seg = [Node(), Node()] # PST가 아니기때문에 노드를 두개 만들어주고요.
n = int(input())
Q = list()

# Offline-Query를 사용하지 않으면 못푸는 문제니까, 꼭 사용해줍시다.
# 이거 안쓰면 예제도 못합니다.
U = list()
for _ in ' '*n:
    a, b, c, d = map(int, input().split())
    if a == 1:
        U.append([b, c, d])
    else:
        Q.append([b, c, d, len(Q)])

Q.sort(key = lambda x: x[2])
ans = [0]*len(Q)
upd = 0
for i, j, k, u in Q:
    while upd < k:
        # 업데이트를 해야한다면, 업데이트를 해주고.. 네, 그런방식입니다.
        # 주의점은 while로 안하고 if로 하면 그대로 망한다는거..?
        x, y, z = U[upd]
        update(x, y, z)
        upd += 1
    ans[u] = query(i, j)

# 답을 출력합니다.
print(*ans, sep='\n')

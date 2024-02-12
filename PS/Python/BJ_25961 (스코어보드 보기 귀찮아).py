import sys
input = sys.stdin.readline
sys.setrecursionlimit(100001)
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
25961번 스코어보드 보기 귀찮아

쿼리가 Q개 주어진다.
각 쿼리당 문제를 푼 사람이 순서대로 주어지는데,
각 쿼리마다 1번이 몇 문제를 더 풀어야 S등 이내로 들지, 현재 몇등인지 출력해보자.

연휴 마지막이라 우울해서 푼 세그트리문제
근데 이분탐색+누적합이 정해같다네요..
신기하다..
"""
def update(idx, val, Q, F):
    idx += Q
    F[idx] += val
    while idx > 0:
        F[idx >> 1] = F[idx] + F[idx ^ 1]
        idx >>= 1

def query(l, r, Q, F):
    l, r = l + Q, r + Q
    ret = 0
    while l <= r:
        if l & 1:
            ret += F[l]
            l += 1
        if r & 1 == 0:
            ret += F[r]
            r -= 1
        l, r = l >> 1, r >> 1
    return ret

N, S = ins()
Q = ini()
F = [0] * (Q * 2 + 2)
fast = [True] * (N + 1)
prob = [0] * (N + 1)
kth = N - 1
update(0, N, Q, F)
ret = []

for _ in range(Q):
    x = ini()
    if x != 1:
        update(prob[x], -1, Q, F)
        kth -= 1 if fast[x] and prob[x] == prob[1] else 0
        prob[x] += 1
        fast[x] = prob[x] > prob[1]
        update(prob[x], 1, Q, F)
    else:
        prob[1] += 1
        kth = query(prob[1], prob[1], Q, F)
    
    lo, hi = 0, Q+1
    while lo < hi:
        mid = (lo + hi) >> 1
        if query(mid, Q, Q, F) < S:
            hi = mid
        else:
            lo = mid + 1
    
    current = query(prob[1] + 1, Q, Q, F) + kth + 1
    forS = hi - prob[1] if current > S else 0
    ret.append(f"{current} {forS}")

print("\n".join(ret))

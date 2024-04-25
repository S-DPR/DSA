import sys
input = sys.stdin.readline
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
25544번 X 만들기 (Hard)

격자판에 P개의 점이 있다.
점 하나를 중심으로 하여 사분면을 나눌 때,
2사분면에서 4사분면으로 가는데는 x와 y 모두 감소하는 방향으로,
3사분면에서 1사분면으로 가는데는 x와 y 모두 증가하는 방향으로 두고싶다.
최소 몇 개의 점을 제거해야할까?

이야..
1. LIS 길이 어떻게 저장해둘래? 각 점에서 시작할 때 / 끝날 때 몇인지 알아야해.
2. 1번 했어? 중복되는 X좌표 어떻게 처리할래?
를 묻는 문제.
2번은.. 좀 lazy하게 업데이트해서 처리.
1번이 플레 4로 만든 문제였는데, 되게 자신은 없고 그냥 생각나는거 구현해봤는데 됐습니다.
솔직히 퍼센트 올라갔을 때 당황..
"""
def bisect(A, x):
    lo, hi = 0, len(A)
    while lo < hi:
        mid = (lo + hi) >> 1
        if A[mid] >= x:
            hi = mid
        else:
            lo = mid + 1
    return hi

def lis(prod):
    dp = [0]*N
    lis_ = [-inf]
    a_idx = 0
    while a_idx < len(A):
        res = []
        cur_x = A[a_idx][0]
        while a_idx < len(A):
            _, y, idx = A[a_idx]
            y *= prod
            res.append([y, idx, bisect(lis_, y)])
            a_idx += 1
            if a_idx < len(A) and cur_x != A[a_idx][0]: break
        for y, idx, idx_ in res:
            if idx_ == len(lis_):
                lis_.append(inf)
            lis_[idx_] = min(lis_[idx_], y)
            dp[idx] = idx_
    return dp

N = ini()
A = sorted([*ins(), i] for i in range(N))
f = [lis(1), lis(-1)]
A.sort(key=lambda x: (-x[0], x[1]))
f.extend([lis(1), lis(-1)])
mx = 0
for i in range(N):
    k, cnt = 1, 0
    for j in range(4):
        k &= f[j][i] > 1
        cnt += f[j][i]
    if k: mx = max(mx, cnt)
print(N-mx+3 if mx else -1)

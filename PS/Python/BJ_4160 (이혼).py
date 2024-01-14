import sys
input = sys.stdin.readline
ini = lambda: int(input())
ins = lambda: map(int, input().split())
inf = float('inf')
"""
4160번 이혼

수열에서 수를 적절히 두 그룹으로 골라, 남은 수의 합이 최소가 되게 하자.
단, 두 그룹의 합은 같아야한다.

옛날에 와 이게 MITM이네 하고 넘겼던거 보이길래 들고왔습니다.
MITM 문제에서 머리 한번 깨진적 있기도하고..
처음에는 이 문제 볼 때 '와 이걸 경우를 세개로 나누네'했던 것 같습니다.
머리 깨졌던 문제도 두그룹으로 나누다가 답지 결국 봤는데 세그룹이었고..
앞으로 MITM에 속수무책으로 당하지 않기를..
"""
from bisect import bisect_left

def dfs(arr, x, e, diff, pf):
    if x == e:
        arr.append([diff, pf])
        return
    dfs(arr, x+1, e, diff-A[x], pf+A[x])
    dfs(arr, x+1, e, diff+A[x], pf+A[x])
    dfs(arr, x+1, e, diff, pf)

while True:
    N = ini()
    if not N: break
    A = [ini() for _ in ' '*N]
    P, Q = [], []
    dfs(P, 0, N//2, 0, 0)
    dfs(Q, N//2, N, 0, 0)
    k = lambda x: (x[0], -x[1])
    P.sort(key=k)
    Q.sort(key=k)
    S = sum(A)
    ret = S
    for pdiff, ppf in P:
        idx = bisect_left(Q, [pdiff, -1<<63])
        if idx == len(Q): continue
        qdiff, qpf = Q[idx]
        if pdiff != qdiff: continue
        ret = min(ret, S-ppf-qpf)
    print(ret)

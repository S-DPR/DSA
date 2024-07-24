import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
26652번 성향 성장의 비약

수열 A와 X가 주어진다.
i번째 요소의 레벨은 A[i]이고, 경험치는 X[i]이다.
레벨업을 하기 위해서는 경험치를 레벨만큼 쌓아야하며, 남은 경험치는 이월된다.
원한다면 레벨업을 하지 않을 수 있다.
또, 비약을 사용해 경험치에 관계없이 레벨을 1 올릴 수 있다. 이는 최대 K번 사용 가능하다.
모든 요소의 레벨을 같은 레벨로 맞추려 한다. 가능한 최대 레벨을 구해보자.

수학으로 식 정리 한번 한 다음 매개변수 1번
아니면 매개변수 2번으로 풀 수 있는 문제.
저는 식 정리 한 번 했습니다.

대충.. A[i]를 일단 최대 레벨로 맞춰둔 뒤 시작합니다.
이후 가능한 레벨인지 테스트를 시도하여 처리하는데..
해당 레벨로 맞추기 위한 K값이 얼마인지 세어 hi와 lo를 적절히 조절합니다.
보자마자 매개변수인건 알았는데 저 K도 같은 경험치인줄알아서 틀렸네..
"""
from math import isqrt
N, K = ins()
A = ins()
X = ins()
mx = max(A)
for i in range(N):
    X[i] += A[i]*(A[i]-1)//2
    A[i] = (isqrt(1+8*X[i])-1)//2+1
lo, hi = 0, 1<<62
while lo < hi:
    mid = (lo + hi) >> 1
    need = sum(max(0, mid-A[i]) for i in range(N))
    if need > K:
        hi = mid
    else:
        lo = mid+1
print([-1, hi-1][hi-1 >= mx])

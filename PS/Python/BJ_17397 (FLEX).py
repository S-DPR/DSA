import sys
input = lambda: sys.stdin.readline().strip("\n")
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
17397번 FLEX

길이가 N인 수열이 주어진다.
수열의 값이란, 인접한 원소들에 대해 아래 규칙을 적용한 뒤 모든 값을 더하는 것이다.
1. A[i] <= A[j] 일경우 0
2. A[i] > A[j] 일경우 (A[j]-A[i])^2
수열의 원소에 1을 M번 더할 수 있다.
같은 원소에 대해서도 이 행위가 가능할 때, 수열의 값의 최솟값을 구해보자.

그냥 dp딸깍
lru cache 쓴 뒤로 이제 dp테이블도 안만들어서 편안합니다.
그냥 머..
원소의 크기가 10을 넘지 않는다.. 이 사실만 잘 파악하면 금방 맞출 수 있습니다.
"""
from functools import lru_cache
sys.setrecursionlimit(10**6)
@lru_cache(None)
def loop(idx, prv, m):
    if idx == N: return 0
    ret = inf
    for i in range(min(prv, m)+1):
        cur = i+A[idx]
        if cur > 10: break
        ret = min(ret, loop(idx+1, cur, m-i)+max(0, prv-cur)**2)
    return ret

N, M = ins()
A = ins()
print(loop(0, 0, M))

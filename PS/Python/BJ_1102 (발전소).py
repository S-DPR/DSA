import sys
input = lambda: sys.stdin.readline().strip("\n")
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
1102번 발전소

발전소가 N개 있고, 이 중 적어도 P개가 작동되게 하려고 한다.
발전소는 켜져있는 발전소 하나를 골라 꺼져있는 발전소를 킬 수 있다.
각 발전소에서 다른 발전소를 키는데 걸리는 비용들이 모두 주어진다.
적어도 P개가 켜지게 하는 최소 비용을 구해보자.

그냥 보자마자 비트dp냄새 풀풀
그래서 N 보니까 16
바로 비트dp ON

그냥 머 내부에서 N^2으로 돌리면됩니다.
정직하게.
어려울거 없는 플레5네요.
"""
from functools import lru_cache
@lru_cache(None)
def loop(v):
    if v.bit_count() >= P: return 0
    ret = inf
    for i in range(N):
        if not v&(1<<i): continue
        for j in range(N):
            if i == j or v&(1<<j): continue
            ret = min(ret, loop(v|(1<<j))+G[i][j])
    return ret

N = ini()
G = [ins() for _ in ' '*N]
V = sum(1<<idx for idx, i in enumerate(input()) if i == 'Y')
P = ini()
print(loop(V) if loop(V) != inf else -1)

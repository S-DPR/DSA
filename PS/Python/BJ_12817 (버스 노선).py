import sys
sys.setrecursionlimit(10**6)
input = sys.stdin.readline
ini = lambda: int(input())
ins = lambda: map(int, input().split())
inf = float('inf')
"""
12817번 버스 노선

정점이 N개인 트리가 주어진다.
모든 (i, j) (i < j)에 대해 해당 정점을 몇 번 지나가는지 모든 정점에 대해 구해보자.

문제 이해가 더 어려웠던 문제. 그냥 트리dp굴리면됩니다.
일본여행 2일차라 짧게 마칩니다.
"""
def dfs(x):
    dp[x], child = 1, []
    for i in G[x]:
        if dp[i]: continue
        k = dfs(i)
        dp[x] += k
        child.append(k)
    sm = sum(child)
    ret[x] += (N-dp[x])*(sm+1)
    for i in child:
        sm -= i
        ret[x] += i*(sm+1)
    return dp[x]

N = ini()
G = [[] for _ in [0]*(N+1)]
dp = [0]*(N+1)
ret = [0]*(N+1)
for _ in ' '*(N-1):
    u, v = ins()
    G[u].append(v)
    G[v].append(u)
dfs(1)
print(*map(lambda x: x*2, ret[1:]), sep='\n')

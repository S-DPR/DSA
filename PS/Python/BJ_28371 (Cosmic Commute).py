import sys
input = lambda: sys.stdin.readline()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
28371번 Cosmic Commute

N개의 노드와 가중치가 0인 웜홀이 K개, 노드를 잇는 가중치가 1인 간선이 M개 있다.
웜홀을 탈 때는 목적지를 정할 수는 없고, 탄 곳을 제외하고 다른 웜홀 K-1개중 랜덤한 곳에서 나오게 된다.
웜홀은 최대 한 번 탈 수 있다.
최적으로 이동 할 때 1에서 N으로가는 최소 가중치 기댓값을 구해보자.

BFS인거야 대충봤고..
사실 웜홀이 가중치 0이라고 생각하는건 지금 풀고 문제 후기 작성하면서 생각해냈고..
1에서 시작하는 BFS, N에서 시작하는 BFS 둘 다 한번씩 굴리면됩니다.
그럼 이제 1에서 N으로 웜홀 안타고갈때를 ret으로 두고,
다른 웜홀이 있는곳은 웜홀에서 목적지까지의 거리를 들고있으면서 하나씩 확인해 처리하면 됩니다.
"""
from collections import deque
from math import gcd
def bfs(s):
    Q = deque([s])
    D = [inf]*(N+1)
    D[s] = 0
    while Q:
        x = Q.popleft()
        for i in G[x]:
            if D[i] <= D[x]+1: continue
            D[i] = D[x]+1
            Q.append(i)
    return D

N, M, K = ins()
W = [0]*(N+1)
for i in ins(): W[i] = 1
G = [[] for _ in ' '*(N+1)]
for _ in ' '*M:
    u, v = ins()
    G[u].append(v)
    G[v].append(u)
f, l = bfs(1), bfs(N)
up = f[N]*(K-1)
warp_total = 0
for i in range(1, N):
    if not W[i]: continue
    warp_total += l[i]
for i in range(1, N):
    if not W[i]: continue
    up = min(up, (warp_total-l[i])+f[i]*(K-1))
g = gcd(up, K-1)
print(f"{up//g}/{(K-1)//g}")

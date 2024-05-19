import sys
input = sys.stdin.readline
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
fini = lambda: int(input()[1:])
fins = lambda: [*map(int, input()[1:].split())]
inf = float('inf')
"""
27455번 카드캡터 한별

N개의 중요노드, V개의 노드, E개의 간선이 있는 방향그래프가 있다.
각 정점마다 이전에 중요노드를 K개 이상 방문해야하는 조건이 있을 때,
모든 중요노드를 방문하는 최단거리를 구해보자.

음..
다익스트라 하나만 해도 될줄알고 7트는 박은거같은데,
다시보니 N = 14, V = 1000, E = 5000으로 볼 때..
다익스트라 시간복잡도가 1000*2^14*log5000.
어.. 다시 계산해보니 1.6억정도인가? 그래서 할 수가 없네요.
너무 늦게 깨달아버렸다..

대신 정점의 개수로 다익스트라를 돌려봅시다.
다시말해, 정점의 개수가 c개일 때 각 중요정점마다 최단경로를 구하고,
중요노드끼리만 써서 그래프를 재구성합니다.
그러면 그래프 구성이 15*1000*log5000정도로 확줄어들고,
나머지는 외판원으로 (2^N)*(N^2) 박으면 AC.
"""
import heapq

def dij(s, c):
    dist = [inf]*(V+1)
    pq = [[0, s]]
    dist[s] = 0
    while pq:
        w, n = heapq.heappop(pq)
        if dist[n] < w: continue
        for nn, nw in G[n]:
            if c < C[nn]: continue
            if w+nw < dist[nn]:
                dist[nn] = w+nw
                heapq.heappush(pq, [dist[nn], nn])
    return dist

def loop(vis, cur):
    cnt = vis.bit_count()-1
    if vis+1 == 1<<(N+1): return 0
    if dp[vis][cur] != inf: return dp[vis][cur]
    for i in range(N+1):
        if vis&(1<<i): continue
        if NG[cnt][cur][i] == inf: continue
        dp[vis][cur] = min(dp[vis][cur], loop(vis|(1<<i), i)+NG[cnt][cur][i])
    return dp[vis][cur]

N, V, E = ins()
C = [0] + ins()
K = [1] + ins()
G = [[] for _ in ' '*(V+1)]
for _ in ' '*E:
    u, v, w = ins()
    G[u].append([v, w])
NG = [[[inf]*(N+2) for _ in ' '*(N+2)] for _ in ' '*(N+2)]
for c in range(N+1):
    for sidx, s in enumerate(K):
        dist = dij(s, c)
        for jidx, j in enumerate(K):
            NG[c][sidx][jidx] = dist[j]
dp = [[inf]*(N+1) for _ in ' '*(1<<(N+1))]
ret = loop(1, 0)
print(-1 if ret == inf else ret)

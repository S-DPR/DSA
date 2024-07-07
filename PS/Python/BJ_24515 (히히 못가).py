import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
24515번 히히 못가

(0, 0)과 (N-1, N-1)을 이동하지 못하도록 벽을 세우려 한다.
상하좌우로 알파벳이 같은 곳은 한 번에 벽을 세워야한다고 할 때,
경로가 없도록 벽을 세우는 벽의 최소 개수를 구해보자.

음..
아
좌하단 + 우상단을 이으면 된다는 문제 한번 어디서 풀었는데,
까먹고 그냥 하나하나 다익스트라 4번 했다가 tle..
아니 근데 4N^2logN^2은 TLE시키네..

사람들이 가중치를 어떻게 구했는지 모르겠습니다.
가중치는 dfs든 bfs든 분리집합이든 썼을텐데,
의외로 저 세 태그중 하나도 없네요..?
아니 어캐했냐? 난 분리집합썼는데?
"""
sys.setrecursionlimit(10**6+2)
import heapq

def union(u, v):
    up, vp = find(u), find(v)
    if up == vp: return 0
    mn, mx = min(up, vp), max(up, vp)
    S[mx] += S[mn]
    U[mn] = U[mx]
    return 1

def find(x):
    if U[x] != x:
        U[x] = find(U[x])
    return U[x]

def dij(h):
    heapq.heapify(h)
    dist = [[inf]*N for _ in ' '*N]
    for w, r, c in h:
        dist[r][c] = w
    while h:
        w, r, c = heapq.heappop(h)
        if dist[r][c] < w: continue
        for i in range(8):
            nr, nc = r+dr[i], c+dc[i]
            if not 0 <= nr < N: continue
            if not 0 <= nc < N: continue
            if M[nr][nc] == '.': continue
            nw = 0 if M[nr][nc] == M[r][c] and i < 4 else S[find(nr*N+nc)]
            if w+nw < dist[nr][nc]:
                dist[nr][nc] = w+nw
                heapq.heappush(h, [dist[nr][nc], nr, nc])
    return dist

N = ini()
M = [[*input()] for _ in ' '*N]
V = [[0]*N for _ in ' '*N]
U = [*range(N*N)]
S = [1]*(N*N)
dr = [1, 0, -1, 0, 1, 1, -1, -1]
dc = [0, 1, 0, -1, 1, -1, 1, -1]
for r in range(N):
    for c in range(N):
        for i in range(2):
            nr, nc = r+dr[i], c+dc[i]
            if not 0 <= nr < N: continue
            if not 0 <= nc < N: continue
            if M[r][c] == M[nr][nc]:
                union(r*N+c, nr*N+nc)
h = [[S[find(i*N)], i, 0] for i in range(N) if M[i][0] != '.']
h.extend([[S[find((N-1)*N+i)], N-1, i] for i in range(N) if M[-1][i] != '.'])
dist = dij(h)
print(min(*[dist[0][i] for i in range(N)],
          *[dist[i][-1] for i in range(N)]))

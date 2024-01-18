import sys
sys.setrecursionlimit(10**4)
input = sys.stdin.readline
ini = lambda: int(input())
ins = lambda: map(int, input().split())
inf = float('inf')
"""
25050번 최고의 간선

모든 정점 (i, j)의 최단경로에 사용되는 간선에 대해,
가장 많이 사용되는 간선과 그 번호들을 구해보자.
"""
import heapq
from collections import deque

def dij(x):
	dist = [inf]*(N+1)
	dist[x] = 0
	h = [[0, x]]
	while h:
		curW, curN = heapq.heappop(h)
		if dist[curN] != curW: continue
		for nxtN, nxtW, _ in G[curN]:
			if curW+nxtW < dist[nxtN]:
				dist[nxtN] = curW+nxtW
				heapq.heappush(h, [dist[nxtN], nxtN])
	return dist

def dfs(dp, dist, x):
	dp[x] = 1
	for nxtN, nxtW, kth in G[x]:
		if dist[nxtN]-dist[x] != nxtW: continue
		nxt = dfs(dp, dist, nxtN)
		R[kth] += nxt
		dp[x] += nxt
	return dp[x]

N, K = ins()
G = [[] for _ in ' '*(N+1)]
R = [0]*(K+1)
for i in range(1, K+1):
	u, v, w = ins()
	G[u].append([v, w, i])
for i in range(1, N+1):
	dfs([0]*(N+1), dij(i), i)
mx = max(R)
ret = [*filter(lambda x: R[x] == mx, range(1, K+1))]
print(len(ret))
print(*ret)

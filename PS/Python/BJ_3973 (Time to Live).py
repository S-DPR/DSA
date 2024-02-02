import sys
input = sys.stdin.readline
sys.setrecursionlimit(100001)
ini = lambda: int(input())
ins = lambda: map(int, input().split())
inf = float('inf')
"""
3973번 Time to Live

모든 노드와의 거리의 최댓값이 최소인 노드의 최대거리를 구해보자.

처음엔 간선타는 가중치가 최소인 최대인줄알고 다익매개 돌리려다가 보니 모든노드와의 거리 최대의 최소거리였네..
트리dp 하려다가 그냥 트리지름의 절반인거같아서 해보니까 맞네요.
쉬워따
"""
def farthest(x, dist, vis):
	vis[x] = 1
	fnode, fdist = x, dist
	for i in G[x]:
		if vis[i]: continue
		nnode, ndist = farthest(i, dist+1, vis)
		if fdist < ndist:
			fdist = ndist
			fnode = nnode
	return fnode, fdist

T = ini()
for _ in ' '*T:
	N = ini()
	G = [[] for _ in " "*N]
	for _ in ' '*(N-1):
		u, v = ins()
		G[u].append(v)
		G[v].append(u)
	f, _ = farthest(1, 0, [0]*N)
	_, d = farthest(f, 0, [0]*N)
	print((d+1)//2)

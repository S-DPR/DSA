import sys
input = sys.stdin.readline
spl = lambda: map(int, input().split())
"""
2887번 행성 터널

3차원 점들이 있다.
이 점을 최소 거리로 이어보자.
점들의 거리는 min(|xi-xj|, |yi-yj|, |zi-zj|)이다.

히히 일이 생겨따 빨리 쓰고 가야지
무자비한 최단경로에서 배워온 테크닉입니다.
한 정점에서 다른 모든 정점으로 가는 거리가 계산식으로 주어졌을 때,
어느 조건을 만족하면 그냥 가장 가까운 정점끼리만 이어도 충분하다.

그래서, 정렬 네번정도 합니다. 세번은 간선만드느라, 한번은 크루스칼쓰느라.
그거 하면 AC입니다.
"""
def union(x, y):
	U[find(x)] = U[find(y)]

def find(x):
	if U[x] != x:
		U[x] = find(U[x])
	return U[x]

N = int(input())
items = []
for i in range(N):
	item = spl()
	items.append([*item, i])
graph = []
for idx in range(3):
	tmp = sorted(items, key=lambda x: x[idx])
	for j in range(N-1):
		*fc, fidx = tmp[j]
		*sc, sidx = tmp[j+1]
		dist = sc[idx]-fc[idx]
		graph.append([dist, fidx, sidx])
graph.sort()
U = [i for i in range(N)]
ret = 0
for dist, x, y in graph:
	if find(x) == find(y):
		continue
	union(x, y)
	ret += dist
print(ret)
import sys
sys.setrecursionlimit(10**4)
input = sys.stdin.readline
"""
16562번 친구비

노드의 개수 n, 간선의 개수 m, 감당되는 최대비용 k가 주어지고,
노드를 선택하는 비용의 배열 arr이 주어진다.
이후, 간선이 m개 주어진다.
비용이 가장 적게 노드를 골랐을 때, 그 최소비용을 구하여라.

dfs로 이어진 간선을 다 구한뒤, 최소비용을 그대로 구하면 됩니다.
별로 특별할건 없는 문제인데..
원래는 유니온파인드를 쓰는게 정석인것같네요.
"""
def dfs(x, visited):
    if visited[x]: return
    visited[x] = 1
    V.add(x)
    for i in G[x]:
        dfs(i, visited)
    return visited

n, m, k = map(int, input().split()); n+=1
arr = list(map(int, input().split()))
G = [set() for _ in ' '*n]
for _ in ' '*m:
    x, y = map(int, input().split())
    G[x].add(y)
    G[y].add(x)
connected = []
V = set()
ans = 0
for i in range(1, n):
    if i in V: continue
    tmp = 10**9
    for idx, j in enumerate(dfs(i, [0]*n)):
        if j: tmp = min(tmp, arr[idx-1])
    ans += tmp
if ans > k: print('Oh no')
else: print(ans)

import sys
input = sys.stdin.readline
spl = lambda: map(int, input().split())
"""
25691번 k개 트리 노드에서 사과를 최대로 수확하기

각 노드의 가중치가 0 혹은 1인 트리가 주어진다.
루트노드에서 출발해, 정점을 k개 이하로 탐색해보자.
그러면서 이동한 노드들의 가중치를 최대로 해보자.

새로 산 노트북으로 푸는 눈물겨운 문제.
내 130만원..

그냥 어제 했던 게리맨더링 하위호환입니다.
대충 루트가 0번이라서 너무 편하게 O(2^N)돌리면 돼요.
완탐+비트마스킹+DFS라는 재미있는 조합이네요.
이 문제를 품으로, 북마크에 있던 골드5는 1개 남았네요.
"""
vis, ret = 0, 0
def dfs(bit, cur):
    global vis
    if vis&(1<<cur): return vis
    if bit&(1<<cur) == 0: return vis
    vis |= 1<<cur
    for i in G[cur]:
        dfs(bit, i)
    return vis

n, k = spl()
G = [[] for _ in ' '*n]
for _ in range(1, n):
    u, v = spl()
    G[u].append(v)
    G[v].append(u)
T = [*spl()]
for i in range(1<<n):
    if ~i&1: continue
    vis = 0
    flg = dfs(i, 0) == i
    s = 0
    bitcnt = 0
    for j in range(n):
        if not i&(1<<j): continue
        s += T[j]
        bitcnt += 1
    if flg and bitcnt <= k:
        ret = max(ret, s)
print(ret)

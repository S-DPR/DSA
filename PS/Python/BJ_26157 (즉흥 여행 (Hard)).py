import sys
from collections import deque as d
input = sys.stdin.readline
sys.setrecursionlimit(10**5*2)
spl = lambda: map(int, input().split())
inf = float('inf')
"""
26157번 즉흥 여행 (Hard)

방향 그래프가 주어진다.
어떤 한 점에서 출발해 다른 모든 점으로 가보려 한다.
같은 간선을 여러번 타도 상관 없고 방문한 곳을 다시 방문해도 된다.
이 때, 어떤 정점에서 출발해야 모든 정점을 방문할 수 있을까?
그 모든 정점의 개수와 그 정점을 구해보자.

이게 그냥 쭉 보면 대충 '위상정렬이구나' 하는데,
예제 한번 보니까 '아 이거 SCC구나'
그래서 대충 SCC 구현을 했는데 괜히 매개변수로 다 보내야지! 하다가 망한..

SCC 긁고 위상정렬 긁으면 됩니다. 간단하죠.
"""
def dfs(x):
    tree[x] = curN[0]
    ret = tree[x]
    stk.append(x)
    curN[0] += 1
    for i in G[x]:
        if not tree[i]:
            ret = min(ret, dfs(i))
        elif not isScc[i]:
            ret = min(ret, tree[i])
    if ret == tree[x]:
        cScc = []
        while True:
            item = stk.pop()
            cScc.append(item)
            isScc[item] = True
            if item == x: break
        scc.append(cScc)
    return ret

N, E = spl()
G = [[] for _ in ' '*(N+1)]
for _ in ' '*E:
    u, v = spl()
    G[u].append(v)
tree = [0]*(N+1)
isScc = [0]*(N+1)
scc = []
curN = [1]
stk = []
for i in range(1, N+1):
    if tree[i]: continue
    dfs(i)
newN = [0]*(N+1)
for idx, i in enumerate(scc, 1):
    for j in i:
        newN[j] = idx
edge = [[] for _ in ' '*(N+1)]
indep = [0]*(N+1)
for i in range(1, N+1):
    for j in G[i]:
        if newN[i] == newN[j]: continue
        edge[newN[i]].append(j)
        indep[newN[j]] += 1
vis = [0]*(N+1)
deq = d()
ret, retN = [], -1
for i in range(1, N+1):
    if indep[newN[i]]: continue
    deq.append(i)
    retN = newN[i]
    break
while deq:
    x = deq.popleft()
    if vis[newN[x]]: continue
    vis[newN[x]] = 1
    cnt = 0
    for i in edge[newN[x]]:
        indep[newN[i]] -= 1
        if indep[newN[i]]: continue
        deq.append(i)
        cnt += 1
    if cnt > 1: retN = -1
if vis[1:len(scc)+1].count(0): retN = -1
for i in range(1, N+1):
    if retN != newN[i]: continue
    ret.append(i)
print(len(ret))
print(*ret)

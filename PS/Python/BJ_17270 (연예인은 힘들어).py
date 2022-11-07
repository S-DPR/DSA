import sys, heapq
input = sys.stdin.readline
inf = 10**9
"""
17270번 연예인은 힘들어

정점의 개수 N과 간선의 개수 K가 첫 줄에 주어집니다.
두 번째 줄부터 x와 y를 잇는 가중치가 w인 간선이 x y w꼴로 K줄 주어진 이후,
두 정점 a, b가 마지막 줄에 주어집니다.
두 정점 a, b에 대해 아래 조건에 맞추어 정점 c를 출력해주세요.
1. 정점 a와 b는 정점 c가 될 수 없습니다.
2. 정점 a와 b에서 각각 c로 가는 가중치의 합은 모든 정점중 최소여야 합니다.
3. 정점 a와 b에서 c로 갈 때, a->c가 b->c보다 짧아야합니다.
4. 만약 이런 c의 후보가 여러개라면, a->c가 가장 짧은 c를 골라야합니다.
5. 만약에 그런 c도 여러개라면 정점의 번호가 가장 작은 것이 c입니다.
a에서 b로 가는 길은 반드시 존재하지만, 위 조건에 맞는 c가 없다면 -1을 출력해주세요.

전형적인 다익스트라 활용문제입니다.
x와 y에서 다익스트라를 쓴 뒤, 조건에 맞추어 코드를 짜면 됩니다.
"""
def dij(x):
    dist = [inf]*n; dist[x] = 0
    prio = []; heapq.heappush(prio, (0, x))
    while prio:
        curW, curN = heapq.heappop(prio)
        for newW, newN in G[curN]:
            if curW+newW < dist[newN]:
                dist[newN] = curW+newW
                heapq.heappush(prio, (dist[newN], newN))
    dist[0] = 0
    return dist

n, k = map(int, input().split()); n+=1
G = [[] for _ in ' '*n]
for _ in ' '*k:
    x, y, w = map(int, input().split())
    G[x].append((w, y))
    G[y].append((w, x))
x, y = map(int, input().split())
x = dij(x); y = dij(y)
short_path = inf
candidate = []
for idx, (dx, dy) in enumerate(zip(x, y)):
    if not dx or not dy: continue
    if short_path > dx+dy:
        short_path = dx+dy
        if dx <= dy: candidate = [idx]
        else: candidate = []
    elif short_path == dx+dy and dx <= dy:
        candidate.append(idx)
ans = inf; short_path = inf
for i in candidate:
    if short_path > x[i]:
        ans = i
        short_path = x[i]
print(ans if ans != inf else -1)

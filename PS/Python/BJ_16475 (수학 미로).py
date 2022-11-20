import sys
from collections import deque
input = sys.stdin.readline
inf = 10**10
"""
16475번 수학 미로

간선의 개수 N, 간선의 개수 M, 스위치 지역의 개수 K,
반전 간선의 개수 L, 스위치로 반전시킬 때 필요한 횟수 P가 주어집니다.
두 번째 줄에는 스위치 지역 K개가 공백으로 구분되어 주어집니다.
이후 M개의 줄에 간선이 주어집니다.
모양은 x, y, w이며 x에서 y로가는 가중치가 w인 간선입니다.
M-L개의 줄은 일반 간선이며, L개의 줄은 반전 간선입니다.
만약 스위치 지역에 P번 도달하면 마지막 L개의 간선이 x->y에서 y->x로 바뀌게됩니다.
이후 스위치를 누른 횟수가 초기화되며, 다시 P번 도달하면 y->x에서 x->y로 바뀌게 됩니다.
마지막으로 출발점과 도착점 s, e가 주어집니다.
s에서 e로 갈 때 가장 짧은 거리를 구해주세요.
출발지역은 함정지역이 아님이 보장됩니다.
(1 <= N, M <= 10000, 0 <= K <= N-1, 0 <= L >= M, 1 <= P <= 10)

전형적인 그래프문제입니다.
BFS쓸지 다익스트라쓸지 고민했는데, BFS의 탈을 쓴 다익스트라가 되어버렸습니다.
별로 어려운진 모르겠습니다.. 그래도 다익스트라 변형이라 높은 난이도가 책정되었습니다.
"""
n, m, k, l, p = map(int, input().split())
n+=1
switch_place = set(map(int, input().split()))
G_origin = [[] for _ in ' '*n]
G_changed = [[] for _ in ' '*n]
for _ in ' '*(m-l):
    x, y, w = map(int, input().split())
    G_origin[x].append([w, y])
    G_changed[x].append([w, y])
for _ in ' '*l:
    x, y, w = map(int, input().split())
    G_origin[x].append([w, y])
    G_changed[y].append([w, x])
G = [G_origin, G_changed]
s, e = map(int, input().split())

res = inf
d = deque([[0, s, 0, 0]])
visit = [[[inf]*p, [inf]*p] for _ in ' '*n]
while d:
    # t means times, which pressed switch.
    curW, curN, t, i = d.popleft()
    if curN == e:
        res = min(res, curW)
        continue
    for nextW, nextN in G[i][curN]:
        if curW+nextW >= visit[nextN][i][t]: continue
        visit[nextN][i][t] = curW+nextW
        if nextN in switch_place:
            d.append([nextW+curW, nextN, (t+1)%p, i^((t+1)//p)])
        else:
            d.append([nextW+curW, nextN, t, i])
print(res if res != inf else -1)

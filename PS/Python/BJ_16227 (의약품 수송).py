import sys, heapq
from collections import defaultdict
input = sys.stdin.readline
inf = 10**9
"""
16227번 의약품수송

연료 한번 충전에 정확하게 100분만 이동할 수 있는 차가 있습니다.
첫째줄에 주유소 N개와 도로의 수 K가 주어집니다.
이후 K개의 줄에 걸쳐 출발정점 u, 도착정점 v, 도로의 길이 t가 주어집니다.
모든 도로는 무향이며, 도로의 길이 1당 1분동안 타고 가야 합니다.
주유소에서 주유하는데에는 5분의 시간이 추가로 걸리고, 완전충전하게 됩니다.
이 때, 정점 0에서 N+1로 가는데에 걸리는 최소의 시간을 구해주세요.
모든 입력은 정답을 도출할 수 있게 주어짐을 보장합니다.
그리고 출발할 때 차에는 연료가 최대로 충전되어있습니다.
(0 <= u, v <= N+1, 1 <= t <= 1000, 0 <= N <= 1000, 1 <= K <= 1000)

딱 보면 알겠지만, 다익스트라입니다.
그런데 다익스트라 식을 약간 변형해야합니다.
그냥 별건 없고 현재 연료만 추가해주면됩니다.
다음으로 갈 거리가 현재 연료보다 크다면 현재 정점에서 5분 들여서 충전하고 가면되고,
작거나 같다면 현재 정점에서 바로 출발하면 최소거리가 됩니다.
"""
def dij(x):
    dist = [inf]*n; dist[x]=-1
    priority = []
    heapq.heappush(priority, (0, x, 100))
    while priority:
        curW, curN, curF = heapq.heappop(priority)
        for newN, newW in G[curN].items():
            if newW+curW < dist[newN] and newW <= curF:
                dist[newN] = newW+curW
                heapq.heappush(priority, (dist[newN], newN, curF-newW))
            elif newW+curW+5 < dist[newN] and curF < newW:
                dist[newN] = newW+curW+5
                heapq.heappush(priority, (dist[newN], newN, 100-newW))
    return dist

n, k = map(int, input().split()); n+=2
# 놀랍게도 문제에선 중복된 u, v가 나오지 않는다고 한 적이 없습니다.
# 최솟값만 받기위해 리스트로 받는대신 딕셔너리로 받읍시다.
G = [defaultdict(lambda: 100) for _ in ' '*n]
for _ in ' '*k:
    x, y, w = map(int, input().split())
    if w > 100: continue
    G[x][y] = min(G[x][y], w)
    G[y][x] = min(G[y][x], w)
print(dij(0)[-1])

import sys, heapq
input = sys.stdin.readline
inf = 10**9
"""
9694번 무엇을 아느냐가 아니라 누구를 아느냐가 문제다

쭉 읽다보면..
최측근이면 1, 측근이면 2, .. 뭐 어쨌든 그렇게 해서
가능하면 저 값을 다 합쳐 최소로 해 그 경로를 출력하는 문제입니다.

.. 네 그냥 다익스트라입니다.
돌릴때 경로 추적해줄 배열 하나 더 만들어서 거리 갱신할 때
경로업데이트를 같이 해주면 끝나는 문제입니다.
"""
def dij(x, G):
    priority = []; heapq.heappush(priority, (0, x))
    distance = [inf]*len(G); distance[x] = -1
    trace = [[] for _ in ' '*len(G)]; trace[0].append(0)
    while priority:
        curW, curN = heapq.heappop(priority)
        for newW, newN in G[curN]:
            if newW + curW < distance[newN]:
                distance[newN] = newW + curW
                trace[newN] = trace[curN] + [newN]
                heapq.heappush(priority, (newW+curW, newN))
    return trace

# 이번엔 한번 그냥 이렇게 해봤습니다~
def main(tc):
    n, m = map(int, input().split())
    G = [[] for _ in ' '*m]
    for _ in ' '*n:
        x, y, w = map(int, input().split())
        G[x].append((w, y))
        G[y].append((w, x))
    print(f"Case #{tc}:", *w if (w:=dij(0, G)[-1]) else [-1])

for tc in range(1, int(input())+1):
    main(tc)

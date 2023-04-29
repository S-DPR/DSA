import sys, heapq
from collections import defaultdict
from collections import deque
input = sys.stdin.readline
"""
5719번 거의 최단 경로

그래프가 주어진다. S에서 출발하여 E로 가려고 한다.
단, S에서 E까지 최단거리로 가는 경로는 제외하고 가려고 한다.
그 경로의 최단 길이를 구하시오.

Rust로 다익스트라 하려다가 힙에서 입구컷당하고,
파이썬으로 도망쳤습니다.
그리고 문제에 된통 얻어맞았습니다.

처음엔 하던대로 다익스트라 내부에서 경로추적하면 되겠지~ 했다가,
와.. MLE TLE 얻어맞고 기절했습니다.
그리고 결국 질문게시판 코드 보면서 풀었습니다..

결국 문제 난이도 원천은, 최단경로 지우기. 지우면 다익스트라 한번으로 끝나니까요.
이 부분을 생각해야하는데, 아래와 같습니다.
1. 다익스트라를 굴립니다.
2. 그래프를 반대방향으로 받은걸 이용하여 bfs를 돌립니다.
 - 다익스트라 배열을 dist라고 합시다.
 - 현재가 cur이고 다음이 nxt라면, cur에서 nxt로 가는 길이는 dist[nxt]-dist[cur]이어야합니다.
 - 이전에 어떤 노드 k를 방문했다면, 그곳을 다시 방문할 필요는 없습니다.
 - 즉, BFS가 나오게됩니다.
3. 위 조건을 이용하여 경로를 제거하고 다익스트라를 돌립니다.

아... 진짜 대단하네..
"""
INF = 1<<63
def dij(G, S, E):
    dst = [INF]*len(G)
    dst[S] = 0
    heap = []
    heapq.heappush(heap, [0, S])
    while heap:
        curW, curN = heapq.heappop(heap)
        if dst[curN] < curW: continue
        for nxtN, nxtW in G[curN].items():
            if curW+nxtW < dst[nxtN]:
                dst[nxtN] = curW+nxtW
                heapq.heappush(heap, [dst[nxtN], nxtN])
    return dst

while True:
    N, M = map(int, input().split())
    if not N: break
    S, E = map(int, input().split())
    G = [dict() for _ in ' '*N]
    R = [dict() for _ in ' '*N]
    for _ in ' '*M:
        u, v, w = map(int, input().split())
        G[u][v] = w
        R[v][u] = w
    dst = dij(G, S, E)
    d = deque([E])
    V = [False]*N
    while d:
        cur = d.popleft()
        if V[cur]: continue
        V[cur] = True
        for n, w in R[cur].items():
            if dst[cur]-dst[n] != w: continue
            del G[n][cur]
            d.append(n)
    res = dij(G, S, E)[E]
    print(res if res < INF else -1)

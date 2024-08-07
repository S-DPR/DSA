import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
20128번 Parity Constraint Shortest Path

그래프가 주어진다.
1번에서 다른 모든 노드로 가는 최종적으로 홀수/짝수인 최단거리를 모두 구해보자.

그냥 쉬운 다익스트라.
홀짝 처리만 잘해주면 됩니다.
되게 직관적이네..
"""
import heapq
N, M = ins()
H = [[0, 1]]
D = [[inf, inf] for _ in ' '*(N+1)]
G = [[] for _ in ' '*(N+1)]
D[1][0] = 0
for _ in ' '*M:
    u, v, w = ins()
    G[u].append([v, w])
    G[v].append([u, w])
while H:
    curW, curN = heapq.heappop(H)
    if D[curN][curW&1] < curW: continue
    for nxtN, nxtW in G[curN]:
        if curW+nxtW < D[nxtN][(curW+nxtW)&1]:
            D[nxtN][(curW+nxtW)&1] = curW+nxtW
            heapq.heappush(H, [D[nxtN][(curW+nxtW)&1], nxtN])
for i in D[1:]:
    for j in i[::-1]:
        print(-1 if j == inf else j, end=" ")
    print()

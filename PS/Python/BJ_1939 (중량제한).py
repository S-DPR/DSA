import sys
from collections import defaultdict, deque
input = sys.stdin.readline
"""
1939번 중량제한

정점의 개수 N과 간선의 개수 K가 주어집니다.
이후 K줄에 간선이 x, y, w 모양으로 주어집니다. x에서 y로 가는 중량이 w인 간선이라는 뜻입니다.
마지막으로 시작점과 끝점 s, e가 주어집니다.
s에서 e로 갈 때 가능한 최대 중량을 출력해주세요. 모든 간선은 양방향입니다.
(2 <= N <= 10000, 1 <= K <= 100000, 1 <= w <= 1000000000)
(이미 나왔던 x, y가 다시 나올 수 있습니다.)

일단 정해는 BFS+매개변수탐색입니다.
아래처럼 하면 BFS만으로 가능하긴 합니다.
물론 좀 비효율적인 방법이라.. Python3로는 이렇게 풀 수 없습니다.
PyPy3밖에 못써요.
"""
def bfs(n, w):
    for i in G[n]:
        tmp = min(w, G[n][i])
        if trace[i] >= tmp: continue
        trace[i] = tmp
        d.append([i, tmp])

n, m = map(int, input().split()); n+=1
G = [defaultdict(int) for _ in ' '*n]
for _ in ' '*m:
    x, y, w = map(int, input().split())
    G[x][y] = max(G[x][y], w)
    G[y][x] = max(G[y][x], w)
s, e = map(int, input().split())
trace = [0]*n

d = deque([[s, 10**10]])
ans = 0
while d:
    n, w = d.popleft()
    if n == e: ans = max(ans, w)
    else: bfs(n, w)
print(ans)

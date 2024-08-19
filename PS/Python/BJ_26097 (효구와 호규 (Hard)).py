import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
26097번 효구와 호규 (Hard)

0과 1로 이루어진 맵이 주어진다.
같은 수가 상하좌우에 두 개 이어진 경우, 그 둘을 제거할 수 있다.
수 하나를 인접한 빈 칸(요소가 제거된 칸)에 옮겨 배치할 수 있다.
모든 수를 제거할 수 있을까? 할 수 있으면, 어떻게 수를 제거해야할까?

난이도는 꽤 쉽습니다.
골드1치곤 많이.

생각해보면..
대충 이어져있는거 하나 제거하고 그 근처에 있는거 싹다 큐에 넣은 뒤 처리하면 됩니다.
단, 같은 수가 이어져있을 경우 그 수는 쭉 이어주고..
라고 생각해서 코드를 짰지만 별로 필요없는 과정같긴 하더라고요.

근거는 간단한데..
초기에 한 묶음을 제거하면 6개가 튀어나오는데 뭘해도 새로 이을 수 있는 녀석들이 생깁니다.
그 6개를 기반으로, 항상 하나를 제거하면 모든 수를 제거할 수 있게 됩니다.
"""
from collections import deque
R, C = ins()
M = [ins() for _ in ' '*R]
X = sum(map(lambda x: x.count(0), M))
Y = sum(map(lambda x: x.count(1), M))
Q = deque()
V = [[0]*C for _ in ' '*R]
items = [deque(), deque()]
dr = [1, -1, 0, 0]
dc = [0, 0, 1, -1]
for r in range(R):
    for c in range(C):
        for i in range(4):
            nr, nc = r+dr[i], c+dc[i]
            if not 0 <= nr < R: continue
            if not 0 <= nc < C: continue
            if M[r][c] != M[nr][nc]: continue
            V[r][c] = V[nr][nc] = 1
            Q.append([r, c, nr, nc])
            break
        else: continue
        break
    else: continue
    break
if X&1 or Y&1 or not Q:
    print(-1)
    exit(0)
print(1)
while Q:
    r1, c1, r2, c2 = Q.popleft()
    print(r1+1, c1+1, r2+1, c2+1)
    innerQ = deque()
    for r, c in [[r1, c1], [r2, c2]]:
        for i in range(4):
            nr, nc = r+dr[i], c+dc[i]
            if not 0 <= nr < R: continue
            if not 0 <= nc < C: continue
            if V[nr][nc]: continue
            V[nr][nc] = 1
            innerQ.append([nr, nc])
    while innerQ:
        r, c = innerQ.popleft()
        items[M[r][c]].append([r, c])
        for i in range(4):
            nr, nc = r+dr[i], c+dc[i]
            if not 0 <= nr < R: continue
            if not 0 <= nc < C: continue
            if M[r][c] != M[nr][nc]: continue
            if V[r][c]: continue
            V[nr][nc] = 1
            innerQ.append([nr, nc])
    for i in items:
        while len(i) > 1:
            Q.append([*i.popleft(), *i.popleft()])

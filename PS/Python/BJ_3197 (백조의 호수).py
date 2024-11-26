import sys
input = lambda: sys.stdin.readline().strip("\n")
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
3197번 백조의 호수

L이 두 개, 나머지는 X 또는 .이 주어진 맵이 주어진다.
매 초마다 .은 상하좌우 X를 .로 바꾼다.
L이 .만을 이용해 서로 만날 수 있게 되는 최단시간을 구해보자.

옛날부터 봐온 날먹플레5
그런데 유니온파인드에서 구현실수해서 2시간은 박았습니다..
지능이슈..
"""
from collections import deque
def union(u, v):
    up, vp = find(u), find(v)
    if up == vp: return 0
    U[up] = vp
    return 1

def find(u):
    if U[u] != u:
        U[u] = find(U[u])
    return U[u]

R, C = ins()
M = [[*input()] for _ in ' '*R]
U = [*range(R*C)]
L = []
Q = deque()
dr = [1, -1, 0, 0]
dc = [0, 0, 1, -1]
for r in range(R):
    for c in range(C):
        if M[r][c] == 'L':
            M[r][c] = '.'
            L.append(r*C+c)
            for i in range(4):
                nr, nc = r+dr[i], c+dc[i]
                if not 0 <= nr < R: continue
                if not 0 <= nc < C: continue
                if M[nr][nc] == 'L':
                    print(0)
                    exit(0)
        if M[r][c] != 'X': Q.append([r, c])
cnt = 0
while find(L[0]) != find(L[1]):
    cnt += 1
    nxtQ = deque()
    while Q:
        r, c = Q.popleft()
        for i in range(4):
            nr, nc = r+dr[i], c+dc[i]
            if not 0 <= nr < R: continue
            if not 0 <= nc < C: continue
            union(r*C+c, nr*C+nc)
            if M[nr][nc] != 'X': continue
            M[nr][nc] = '.'
            nxtQ.append([nr, nc])
    Q = nxtQ
    for r, c in Q:
        for i in range(4):
            nr, nc = r+dr[i], c+dc[i]
            if not 0 <= nr < R: continue
            if not 0 <= nc < C: continue
            if M[nr][nc] == 'X': continue
            union(r*C+c, nr*C+nc)
print(cnt)

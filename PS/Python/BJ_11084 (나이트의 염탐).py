import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
11084번 나이트의 염탐

R*C크기의 맵이 있고, 나이트가 (1, 1)에 있다.
(R, C)에 최단경로로 가는 경우의 수를 구하시오.

어떻게 해야할지 모르겠어서 모듈러 매번했는데 이게 되네 ㅋㅋ
그냥 대충 BFS+DP 굴리면 됩니다.
이정도면 거의 골3아닌가?
"""
from collections import  deque
R, C = ins()
M = [[0]*(C+1) for _ in ' '*(R+1)]
T = [[inf]*(C+1) for _ in ' '*(R+1)]
T[1][1] = 0
M[1][1] = 1
Q = deque([[1, 1, 0, 1]])
dr = [-2, -1, 1, 2, -2, -1, 1, 2]
dc = [1, 2, 2, 1, -1, -2, -2, -1]
while Q:
    r, c, t, x = Q.popleft()
    if x < M[r][c]: continue
    for i in range(8):
        nr, nc = r+dr[i], c+dc[i]
        if not 1 <= nr <= R: continue
        if not 1 <= nc <= C: continue
        if T[nr][nc] < t+1: continue
        M[nr][nc] += x
        T[nr][nc] = t+1
        Q.append([nr, nc, t+1, M[nr][nc]])
if T[-1][-1] != inf:
    print(T[-1][-1], M[-1][-1]%1000000009)
else:
    print('None')

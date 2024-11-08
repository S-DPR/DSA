import sys
input = lambda: sys.stdin.readline().strip("\n")
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
14948번 군대탈출하기

숫자가 있는 맵에서, 숫자가 k 이하인 값만 밟아 좌측 상단에서 우측 하단으로가려 한다.
단, 딱 한번 숫자가 k 이상이어도 지나가던 방향으로 한번 점프할 수 있다.
가장 작은 k를 구하시오.

그냥 대충 1분정도 읽어보니 매개변수+bfs
2분만에 코드 짜서 제출했더니 실패해서 조금 슬펐고..

대충 집가는길에 제대로 안읽은 조건 추가해서 풀었습니다.
한 5~7분이면 풀었을거같은데 아깝네
"""
from collections import deque
dr = [1, -1, 0, 0]
dc = [0, 0, 1, -1]
R, C = ins()
M = [ins() for _ in ' '*R]
lo, hi = 0, 10**9+1
while lo < hi:
    mid = (lo + hi) >> 1
    q = deque([[0, 0]])
    V = [[0]*C for _ in ' '*R]
    V[0][0] = 1
    while q:
        r, c = q.popleft()
        for i in range(4):
            nr, nc = r+dr[i], c+dc[i]
            if not 0 <= nr < R: continue
            if not 0 <= nc < C: continue
            if M[nr][nc] > mid: continue
            if V[nr][nc]: continue
            q.append([nr, nc])
            V[nr][nc] = 1
    if V[-1][-1]: hi = mid
    else: lo = mid + 1
print(hi-1)

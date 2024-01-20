import sys
input = sys.stdin.readline
ini = lambda: int(input())
ins = lambda: map(int, input().split())
inf = float('inf')
"""
30894번 유령의 집 탈출하기

유령이 어떤 방향을 바라보고있다. 유령은 다른 유령이나 벽은 투시하지 못한다.
유령이 매 초마다 90도씩 시계방향으로 회전할 때, 시작지점에서 도착지점까지 가는 최단거리를 구해보자.
단, 이동하는 동안 유령이 보는 방향에 있으면 안된다.

대충 BFS 슥슥 구현하는 문제.
오랜만에 맵 BFS풀어서 재밌었네요.. 좀 귀찮긴 했지만.
"""
from collections import deque as d

R, C = ins()
SR, SC, ER, EC = ins()
M = [[*input()] for _ in ' '*R]
T = [[i[:] for i in M] for _ in ' '*4]
dr = [0, 1, 0, -1, 0]
dc = [1, 0, -1, 0, 0]
for i in range(4):
	for r in range(R):
		for c in range(C):
			if T[i][r][c] in {'.', '#', 'X'}: continue
			curN = int(T[i][r][c])
			nr = r+dr[(curN+i)%4]
			nc = c+dc[(curN+i)%4]
			while True:
				if not 0 <= nr < R: break
				if not 0 <= nc < C: break
				if T[i][nr][nc] in {'#', '0', '1', '2', '3'}: break
				T[i][nr][nc] = 'X'
				nr += dr[(curN+i)%4]
				nc += dc[(curN+i)%4]
deq = d()
deq.append([SR-1, SC-1, 0])
V = [[[0]*C for _ in ' '*R] for _ in ' '*4]
while deq:
    curR, curC, curT = deq.popleft()
    nxtRt = (curT+1)%4
    if ER-1 == curR and EC-1 == curC:
        print(curT)
        break
    for i in range(5):
        nxtR, nxtC = curR+dr[i], curC+dc[i]
        if not 0 <= nxtR < R: continue
        if not 0 <= nxtC < C: continue
        if T[nxtRt][nxtR][nxtC] in {'X', '#'}: continue
        if T[nxtRt][nxtR][nxtC] in {'0', '1', '2', '3'}: continue
        if V[nxtRt][nxtR][nxtC]: continue
        V[nxtRt][nxtR][nxtC] = 1
        deq.append([nxtR, nxtC, curT+1])
else:
    print('GG')

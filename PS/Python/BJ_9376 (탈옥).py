import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
9376번 탈옥

.는 길, #는 문, *는 벽이다. 이 셋으로 이루어진 R*C 그리드가 주어진다.
두 죄수 $를 그리드 밖으로 보내주려한다. 이 때, 문을 최소 몇개 지나는지 구하시오.
그리드 바깥쪽 땅을 밟으면 밖으로 나간것으로 간주한다.

BFS 3번.
풀기싫어서 뒤적뒤적하다가 북마크에 있는거 가져왔습니다.
공통된 경로의 길이를 빼주어야해서 곤란한 문제네요.

요는.. 죄수 둘에서 다익스트라, 모든 바깥쪽에서 다익스트라.
그리고 마지막에 완탐느낌 섞어서 최적값 찾아주기.
간단하죠. 단, 두 죄수가 따로 나가는건 알아서 구해줍시다.
바깥쪽 다익스트라의 결과로 가볍게 구할 수 있으니.
"""
from collections import deque as d
dr = [1, -1, 0, 0]
dc = [0, 0, 1, -1]
for _ in ' '*ini():
    R, C = ins()
    M = [[*input()] for _ in ' '*R]
    V = [[[inf]*C for _ in ' '*R] for _ in ' '*3]
    pr, out = [], []
    for r in range(R):
        for c in range(C):
            if M[r][c] == '$':
                M[r][c] = '.'
                pr.append([r, c, 0])
            if M[r][c] != '*' and (r in [0, R-1] or c in [0, C-1]):
                out.append([r, c, [0, 1][M[r][c] == '#']])
    out.sort(key=lambda x: x[2])
    items = [[pr[0]], [pr[1]], out]
    for idx, item in enumerate(items):
        Q = d(item)
        for r, c, w in item:
            V[idx][r][c] = w
        while Q:
            r, c, w = Q.popleft()
            if V[idx][r][c] < w: continue
            for i in range(4):
                nr, nc = r+dr[i], c+dc[i]
                if not 0 <= nr < R: continue
                if not 0 <= nc < C: continue
                if M[nr][nc] == '*': continue
                nw = V[idx][r][c] + (M[nr][nc] == '#')
                if V[idx][nr][nc] <= nw: continue
                V[idx][nr][nc] = nw
                if M[nr][nc] == '#':
                    Q.append([nr, nc, nw])
                else:
                    Q.appendleft([nr, nc, nw])
    ret = V[2][pr[0][0]][pr[0][1]]+V[2][pr[1][0]][pr[1][1]]
    for r in range(R):
        for c in range(C):
            if M[r][c] == '*': continue
            ret = min(ret, V[0][r][c] + V[1][r][c] + V[2][r][c] - [0, 2][M[r][c] == '#'])
    print(ret)

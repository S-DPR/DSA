import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
20664번 Patkice II

o에서 x로 가려고 한다. 
맵에 그려진 방향대로만 갈 수 있다고 할 때,
o, x를 제외한 화살표와 점을 적절히 바꾸어 o에서 x로 갈 수 있게 해보자.
단, 바꾸는데는 1의 비용이 소모되며, 최소비용의 맵을 출력한다.

대충 BFS문제.
로직파악은 20분만에 했고 구현도 10분만에 했는데
언어 이상한거골라서 1시간 3분걸렸네 이게 맞냐
"""
from collections import deque
R, C = ins()
M = []
MM = [['.'] * C for _ in range(R)]
o = []
x = []

# 방향 매핑 딕셔너리와 역방향 딕셔너리
pos_dic = {
    '>': 2,
    'v': 0,
    '<': 3,
    '^': 1,
    '.': 4,
    'o': 4,
    'x': 4
}
repos_dic = ['v', '^', '>', '<', '.']

# 지도 입력 받기
for r in range(R):
    s = input()
    arr = []
    for c in range(C):
        if s[c] == 'o':
            o = [r, c]
        if s[c] == 'x':
            x = [r, c]
        arr.append(pos_dic[s[c]])
        MM[r][c] = s[c]  # MM에는 문자 그대로 저장
    M.append(arr)

# 이동 방향
dr = [1, -1, 0, 0]
dc = [0, 0, 1, -1]

inf = sys.maxsize
V = [[[inf, 5] for _ in range(C)] for _ in range(R)]

# BFS를 위한 deque 큐
Q = deque([[o[0], o[1], 0]])

V[o[0]][o[1]][0] = 0

# BFS 탐색
while Q:
    r, c, t = Q.popleft()
    if V[r][c][0] < t:
        continue
    for i in range(4):
        nr, nc = r + dr[i], c + dc[i]
        if not (0 <= nr < R and 0 <= nc < C):
            continue
        eq = 0 if M[r][c] == i else 1
        if V[nr][nc][0] <= t + eq:
            continue
        V[nr][nc] = [t + eq, i ^ 1]
        if eq == 0:
            Q.appendleft([nr, nc, t + eq])
        else:
            Q.append([nr, nc, t + eq])

# 경로 복원
curR, curC = x
while o[0] != curR or o[1] != curC:
    _, pos = V[curR][curC]
    curR += dr[pos]
    curC += dc[pos]
    MM[curR][curC] = repos_dic[pos ^ 1]

MM[o[0]][o[1]] = 'o'
MM[x[0]][x[1]] = 'x'

# 출력
print(V[x[0]][x[1]][0] - 1)
print("\n".join("".join(row) for row in MM))

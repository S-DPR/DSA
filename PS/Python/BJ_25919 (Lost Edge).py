import sys
input = sys.stdin.readline
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
fini = lambda: int(input()[1:])
fins = lambda: [*map(int, input()[1:].split())]
inf = float('inf')
"""
25919번 Lost Edge

맵이 주어진다. 맵에는 몬스터, 벽, 목표장소, 현재위치가 있다.
현재 레벨만큼의 경험치를 얻으면 레벨업하며, 남은 경험치는 이월된다.
각 몬스터에는 레벨이 있는데, 내 레벨보다 낮은 몬스터만 잡을 수 있으며,
잡을 경우 그 몬스터의 레벨만큼 경험치가 오른다.
목표장소에는 적어도 E레벨 이상을 찍고 와야한다. E레벨 이상을 찍고 목표장소에 갈 수 있을까?

대충 BFS를 돌리는데,
이중으로 BFS를 돌리면 됩니다.
솔직히 어렵진 않습니다. AWS 행사장가서 쉬는시간에 할거 없는김에 풀었습니다.
랜덤마라톤에 나오기도 했고.
"""
from collections import  deque
import heapq
R, C = ins()
S, K, E = ins()
M = [ins() for _ in ' '*R]
V = [[0]*C for _ in ' '*R]
deq = deque()
end = []
for r in range(R):
    for c in range(C):
        if M[r][c] == -3:
            M[r][c] = 0
            deq.append([r, c])
        if M[r][c] == -2:
            end = [r, c]
            M[r][c] = 0
dr = [1, -1, 0, 0]
dc = [0, 0, 1, -1]
h = []
while deq:
    while deq:
        r, c = deq.popleft()
        for i in range(4):
            nr, nc = r+dr[i], c+dc[i]
            if not 0 <= nr < R: continue
            if not 0 <= nc < C: continue
            if M[nr][nc] == -1: continue
            if V[nr][nc]: continue
            V[nr][nc] = 1
            h.append([M[nr][nc], nr, nc])
    heapq.heapify(h)
    while h and h[0][0] < S:
        l, r, c = heapq.heappop(h)
        deq.append([r, c])
        K += l
        while K >= S:
            K -= S
            S += 1
print('XO'[S >= E and V[end[0]][end[1]]])

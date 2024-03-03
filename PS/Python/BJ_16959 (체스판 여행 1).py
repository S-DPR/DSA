import sys
input = sys.stdin.readline
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
16959번 체스판 여행 1

숫자가 하나씩 써진 N*N 보드가 주어진다.
1에서 출발해 2, 3, ..., N*N-1, N*N까지 도달할 것이다.
1에서는 아무 말이나 둘 수 있고, 이후 그 말을 이동하거나 말을 다른 것으로 바꿀 수 있다.
말이 Knight, Bishop, Rook뿐일 때 적어도 몇 칸 이동해야 할까?

아니
왜 Ruby로 짜면 틀렷다고하면서 파이썬으로 짜니까 맞다고하는거야
이거 루혐이야~

방문배열을 좀 자세히 봐야하는 문제.
V[다음목표][행][열][현재기물] 이렇게 했습니다.
그러면 풀려요. 참 보자마자 생각난건데 루비로했다고 틀려버리네 억울해
"""
# 입력 받기
N = ini()
M = [ins() for _ in range(N)]
V = [[[[False] * 3 for _ in range(N)] for _ in ' '*N] for _ in range(N * N + 2)]

# 시작 위치 찾기
start = None
for r in range(N):
    for c in range(N):
        if M[r][c] == 1:
            start = (r, c)

# 큐 초기화
Q = [(start[0], start[1], 2, i, 0) for i in range(3)]
for i in range(3):
    r, c = start
    V[2][r][c][i] = True

# 말의 이동 방향 정의
dr = [[-2, -1, 1, 2, 2, 1, -1, -2], [1, 1, -1, -1], [1, -1, 0, 0]]
dc = [[1, 2, 2, 1, -1, -2, -2, -1], [1, -1, 1, -1], [0, 0, 1, -1]]

# BFS 실행
while Q:
    r, c, t, p, curT = Q.pop(0)  # 0 = knight, 1 = bishop, 2 = rook
    if t == N * N + 1:
        print(curT)
        break
    for i in range(3):
        if not V[t][r][c][i]:
            V[t][r][c][i] = True
            Q.append((r, c, t, i, curT + 1))
    for i in range(len(dr[p])):
        nr, nc = r, c
        while True:
            nr += dr[p][i]
            nc += dc[p][i]
            if not (0 <= nr < N) or not (0 <= nc < N):
                break
            nxtT = t + 1 if M[nr][nc] == t else t
            if not V[nxtT][nr][nc][p]:
                V[nxtT][nr][nc][p] = True
                Q.append((nr, nc, nxtT, p, curT + 1))
            if p == 0:
                break

import sys
from collections import deque
input = sys.stdin.readline
"""
24727번 인지융~

지도의 가로길이 N이 주어집니다. 지도는 항상 정사각형입니다.
이후, C와 E가 주어집니다.
지도에 1을 C개, 2를 E에 채워넣을 생각입니다.
단, 1과 2는 서로 맞닿아있을 수 없습니다.

위 규칙에 맞춰 지도를 채워넣을 수 있다면 1과 그 지도를,
채워넣을 수 없다면 -1을 출력합시다.

당연히 BFS인줄알았는데 BFS태그는 없습니다.
구성적태그가 있던데, 이게 뭔진 잘 모르겠지만.
0, 0과 n-1, n-1, 혹은 n-1, 0과 0, n-1에 1과 2를 때려넣고 bfs때리면 됩니다.
"""
vec = ((1, 0), (-1, 0), (0, 1), (0, -1))

# 입력받은 x, y 주변에 cn과 0을 제외한 수가 있는지 체크하는 함수입니다.
def check_surround(M, x, y, cn):
    cn = 1 if cn == 2 else 2
    for dx, dy in vec:
        nx, ny = x+dx, y+dy
        if not (0 <= nx < n and 0 <= ny < n): continue
        if M[ny][nx] == cn: return 1
    return 0

# 평범한 bfs입니다. obj에는 입력받은 C, E중 하나 입력하면됩니다.
# bfs 도는중, 개수가 이미 충족되었다면 중간에 멈추는거만 추가했습니다.
def bfs(M, x, y, cn, obj):
    global cnt
    for dx, dy in vec:
        if cnt == obj: return
        nx, ny = x+dx, y+dy
        if not (0 <= nx < n and 0 <= ny < n): continue
        if M[ny][nx] or check_surround(M, nx, ny, cn): continue
        M[ny][nx] = cn
        cnt += 1
        d.append([nx, ny])

n = int(input())
x, y = map(int, input().split())
M = [[0]*n for _ in ' '*n]
M[0][0] = 2
M[-1][-1] = 1
flg = 1
# n = 1인경우를 목적으로 한 예외처리
if x+y >= n*n: flg = 0

# 나머지는 그냥 bfs 두번 돌린겁니다.
cnt = 1
d = deque([[0, 0]])
while d:
    if cnt == y: break
    a, b = d.popleft()
    bfs(M, a, b, 2, y)
else: flg = 0

cnt = 1
d = deque([[n-1, n-1]])
while d:
    if cnt == x: break
    a, b = d.popleft()
    bfs(M, a, b, 1, x)
else: flg = 0

if not flg: print(-1)
else:
    print(1)
    for i in M: print(*i, sep="")
    

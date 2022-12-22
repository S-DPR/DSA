import sys
from collections import deque
input = sys.stdin.readline
inf = 10**4+1
"""
15806번 영우의 기숙사 청소

N*N 방에 곰팡이가 M개 있다.
이 곰팡이는 매일 체스의 나이트처럼 퍼뜨리고 자신은 사라진다.
그리고, T일 이후에 청소했나 확인할 장소가 K개 주어진다.
T일 후 K개의 장소중 한곳에라도 곰팡이가 피어있을경우 YES를, 아닐경우 NO를 출력하시오.

상당히 흥미로운 문제였습니다.
매겨진 난이도가 좀 높다는 생각도 들지만 개인편차가 있으니 뭐..
방법은 이렇습니다.
 1. visit을 3차원으로 만듭니다. 2차원은 당연히 x, y를 담당하고 하나는 홀짝을 담당합니다.
 2. 처음 있는 것들은 짝을 담당하는 부분에 0을 칠해둡니다.
 3. bfs를 돌립니다. 3차원 홀짝부분에 유념하여 t를 집어넣습니다.
 4. 확인해야할곳을 확인하고, 답을 출력합니다.
"""
vec = ((1, 2), (2, 1), (-2, -1), (-1, -2), (-1, 2), (-2, 1), (1, -2), (2, -1))
def bfs(x, y, t):
    stack = 0
    for dx, dy in vec:
        nx, ny = x+dx, y+dy
        if not (1 <= nx <= n and 1 <= ny <= n):
            stack += 1
            continue
        if M[ny][nx][t&1] <= t: continue
        M[ny][nx][t&1] = t
        cur.append([nx, ny, t+1])
    if stack == 8:
        M[y][x][0] = inf

n, m, k, T = map(int, input().split())
cur = deque(list(map(int, input().split()))+[1] for _ in ' '*m)
obj = [list(map(int, input().split())) for _ in ' '*k]
M = [[[inf, inf] for _ in ' '*(n+1)] for _ in ' '*(n+1)]

for x, y, _ in cur: M[y][x][0] = 0
while cur:
    x, y, t = cur.popleft()
    if t > T: continue
    bfs(x, y, t)

for x, y in obj:
    if M[y][x][T&1] <= T:
        print('YES')
        break
else:
    print('NO')

import sys
from collections import deque
input = sys.stdin.readline
"""
16933번 벽 부수고 이동하기 3

N*M 크기 맵이 주어진다. 이 맵은 1과 0으로 이루어져있고, 1은 갈 수 없는 위치이다.
1로 된 곳은 벽으로, 통과할 수 없다.
그러나 당신은 이 1을 K번 통과할 수 있다. 다만, 낮에만 통과할 수 있다.
낮과 밤은 좌표를 한번 움직일 때마다 반복된다.
이 때, 좌측 상단에서 우측 하단으로 이동하는 최단시간을 출력하자.

기본 BFS문제의 최정점에 서있는 문제중 하나라 보는 문제입니다.
정말 피눈물나는 문제였고요..
Swift로 하려다가 1%도 못가고 시간초과를 받고,
와 이게 진짜 맞나 하면서 파이썬으로 도망쳤습니다. 맞춘놈들은 어캐한거지?
이게 Swift는 추가시간을 못받는데, 아무리생각해도 Go처럼 1초에서 2초는 받아야한다 생각..

어쨌든 방문표시는 2차원으로 합니다.
그리고 K+(낮/밤여부, 11 혹은 0)로 비트미스킹을 합니다.
이후 그거로 BFS를 쭉 하는데..
BFS 함수호출을 없애니까 6000ms로 통과가 됐습니다.. 함수호출오버헤드가 이렇게 큰가..

이 방법 외에 시도해본 방식은 이겁니다.
1. N*M*2크기의 visit배열을 만듭니다.
2. 낮일경우 0번 인덱스에, 밤일경우 1번 인덱스에 해당 지점에 도착했을 때 벽 부술 수 있는 기술이 최대한 많이 남아있는 경우를 기록합니다.
3. 해당 지점에서, 자신이 갖고있는 부술 수 있는 횟수가 visit에 적혀있는 것보다 적은 경우는 시도하지 않습니다.
.. 인데, 구현을 잘못했나.. 실패..
"""
vec = ((1, 0), (-1, 0), (0, 1), (0, -1), (0, 0))

n, m, k = map(int, input().split())
M = list(list(input().rstrip()) for _ in ' '*n)
visit = [[0]*m for _ in ' '*n]
deq = deque()
deq.append([0, 0, 0, k, 0])
while deq:
    x, y, d, k, t = deq.popleft()
    if x == m-1 and y == n-1:
        print(t+1)
        break
    plus = 11 if d == 0 else 0
    bit = pow(2, plus + k)
    for dx, dy in vec:
        nx, ny = x+dx, y+dy
        if not (0 <= nx < m and 0 <= ny < n): continue
        if visit[ny][nx] & bit: continue
        if dx+dy and M[ny][nx] == "1":
            if d == 1 or k == 0: continue
            visit[ny][nx] |= bit
            deq.append([nx, ny, d^1, k-1, t+1])
        else:
            visit[ny][nx] |= bit
            deq.append([nx, ny, d^1, k, t+1])
else:
    print(-1)

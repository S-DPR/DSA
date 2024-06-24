import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
30627번 삼월 초하루

물이 100만큼 차있는 그릇이 3개 있다.
물을 한 번 다른 그릇에 부울 때마다 5도씩 온도가 내려간다.
1번 그릇에 있던게 특정 위치로, 2번 그릇에 있던게 특정 위치로 가게 할 수 있을까?
동시에, 1번 그릇에 있던 물의 양과 2번 그릇에 있던 물의 양을 특정한 값으로 맞출 수 있을까?
가능하면 어떻게 부어야하는지 구해보자.

N = 3 보고 하 이건 또 머야 했는데 3이면..
3이면 진짜 아무리생각해도 브루트포스인데 재귀인가.. 하다가.
태그까니 BFS.. 아..
그치.. 최단거리구나..

솔직히 4차원 방문배열은 걍 지겹고.
역추적이 조금 귀찮았습니다.
조금 비효율적으로 역추적한거같네요.
"""
from collections import deque
N = ini()
A, B = map(lambda x: x//5, ins())
R = ins()
vis = [[[[-1]*4 for _ in ' '*4] for _ in ' '*21] for _ in ' '*21]
trace = [[[[(-1, -1, -1, -1, -1, -1) for _ in ' '*4] for _ in ' '*4] for _ in ' '*21] for _ in ' '*21]
deq = deque([[20, 20, 1, 2]])
vis[20][20][1][2] = 0
while deq:
    a, b, u, v = deq.popleft()
    t = vis[a][b][u][v]
    go = 1^2^3^u^v
    if a-1 >= 0 and vis[a-1][b][go][v] == -1:
        trace[a-1][b][go][v] = (a, b, u, v, u, go)
        vis[a-1][b][go][v] = t+1
        deq.append([a-1, b, go, v])
    if b-1 >= 0 and vis[a][b-1][u][go] == -1:
        trace[a][b-1][u][go] = (a, b, u, v, v, go)
        vis[a][b-1][u][go] = t+1
        deq.append([a, b-1, u, go])
objA, objB = -1, -1
for idx, i in enumerate(R, 1):
    if i == 1: objA = idx
    if i == 2: objB = idx
ret = vis[A][B][objA][objB]
if ret == -1:
    print(-1)
else:
    print(ret)
    a, b, u, v = A, B, objA, objB
    stk = []
    for _ in ' '*ret:
        x = trace[a][b][u][v]
        stk.append([x[4], x[5]])
        a, b, u, v, *_ = x
    for i in stk[::-1]:
        print(*i)

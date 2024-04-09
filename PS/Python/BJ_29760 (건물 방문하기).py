import sys
input = sys.stdin.readline
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
fini = lambda: int(input()[1:])
fins = lambda: [*map(int, input()[1:].split())]
inf = float('inf')
"""
29760번 건물 방문하기

H*W크기의 직사각형 건물이 있다.
N개의 h층 w호를 방문할건데, 방문 순서는 상관 없다.
같은 층의 인접한 다른 방을 가는데는 1초,
같은 방의 다른 층을 가는데는 100초가 걸린다.
W가 최대 100일 때, 1층 1호에서 출발해 모든 방을 방문하기 위한 최단시간을 구해보자.

W가 최대 100인게 핵심.
만약 더 컸다면 그리디가 안통해서 많이 까다로워졌을겁니다.
대충 dp[h][w]로 해서 위에 얼마나 더 남아야하는지 체크해야하는데..
여기서 중요한건 해당 층의 가장 왼쪽/오른쪽 요소만 체크하면 된다는 점.
위로 올라왔다가 아래로 내려가면 무조건 손해가 납니다.
한 층에서 할 일을 다 한 뒤 올라가는 시간을 계산하면 됩니다.
"""
sys.setrecursionlimit(10**5)
def loop(h, w):
    if h == mxh+1: return 0
    if dp[h][w] != inf: return dp[h][w]
    l, r = floor[h]
    ldist, rdist = abs(w-l), abs(w-r)
    if l == inf and r == -inf:
        dp[h][w] = loop(h+1, w)
    elif w < l and w < r:
        dp[h][w] = loop(h+1, r)+rdist
    elif w > l and w > r:
        dp[h][w] = loop(h+1, l)+ldist
    else:
        dp[h][w] = min(
            loop(h+1, l)+ldist+rdist*2, 
            loop(h+1, r)+ldist*2+rdist
        )
    if h != mxh: dp[h][w] += 100
    return dp[h][w]

N, H, W = ins()
floor = [[inf, -inf] for _ in ' '*(H+1)]
mxh = 0
for _ in ' '*N:
    h, w = ins()
    floor[h][0] = min(floor[h][0], w)
    floor[h][1] = max(floor[h][1], w)
    mxh = max(mxh, h)
dp = [[inf]*(W+1) for _ in ' '*(H+1)]
print(loop(1, 1))

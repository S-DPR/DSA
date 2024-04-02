import sys
input = sys.stdin.readline
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
14932번 금고 (SAFE)

정점 하나를 골라 시작해 모든 정점을 한 번씩 순회하려한다.
각 정점은 (수)(ULRD중 하나)로 이루어져있으며,
해당 정점에서는 그 방향으로 수만큼 이동하게된다.
맵 밖으로 나가는 일은 없다. 몇 개의 점에서 위 행위가 가능할까?
2개 이상인 경우, 0개인 경우, 1개인 경우를 나누는데, 1개인 경우만 그 정점을 출력하자.

문제 관찰을 하면 더 쉬웠나봐요.
저는 그냥 '사이클체크 잘하는 DFS DP 굴리면 되겠다' 했는데,
진입점의 개수..를 이용하더라고요.
함수형그래프의 특징인가..? 음.. 이러고만 있었는데..
이건 좀 보고가야할거같네요.
"""
sys.setrecursionlimit(10**6+2)
def dfs(x, cycleNumber, depth):
    if cycleN[x]:
        isCycle = False
        if cycleN[x] == cycleNumber:
            cnt[x] = depth-cnt[x]
            isCycle = True
        return [cnt[x], x, isCycle]
    cycleN[x] = cycleNumber
    cnt[x] = depth
    c, end, isCycle = dfs(G[x], cycleNumber, depth+1)
    if end != x: cnt[x] = c if isCycle else c+1
    return [cnt[x], end, isCycle and end != x]

d = {
    'U': (-1, 0),
    'R': (0, 1),
    'D': (1, 0),
    'L': (0, -1)
}
N = ini()
G = [0]*(N*N)
cnt = [1]*(N*N)
cycleN = [0]*(N*N)
for r in range(N):
    A = input().strip().split()
    for c in range(N):
        k, pos = int(A[c][:-1]), A[c][-1]
        dr, dc = d[pos]
        nr, nc = k*dr, k*dc
        nxt = r*N+c + nr*N+nc
        G[r*N+c] = nxt
cycleNumber = 1
for i in range(N*N):
    if cycleN[i]: continue
    dfs(i, cycleNumber, 1)
    cycleNumber += 1
ret = [*filter(lambda x: cnt[x] == N*N, range(N*N))]
if len(ret) > 1:
    print("THIEF LOVE IT!")
elif len(ret) == 0:
    print("TOO SAFE")
else:
    kth = ret[0]
    print((kth//N)+1, (kth%N)+1)

import sys
input = sys.stdin.readline
inf = 10**15
"""
17498번 폴짝 게임

제가 아무리 DP에 등을 졌다지만,
처음부터 등을 진건 아닙니다.
적어도 정수삼각형(1932) 모양의 DP를 풀줄은 압니다.

처음엔 습관적으로 DP+DFS로 짰는데,
시간초과빔 몇 번 맞고나니 정신차리게 되더라고요.
그렇게 문제를 다시보니 그냥 함수 하나 잘 정의하면 되는 쉬운 DP였습니다.

.. 솔직히 말하면 시간초과날까봐 for문으로 테러하고 시간초과내야지~ 하고 냈더니 맞은문제라..
근데 다시봐도 그냥 정수삼각형에 거리 하나 붙은건데 난이도가 1 올랐네..
"""
def move(x, y):
    # 다시 올라갈 수 없고, 무조건 내려가야하기때문에 i는 1로 시작합니다.
    for i in range(1, d+1):
        for j in range(-(d-i), d-i+1):
            nx, ny = x+j, y+i
            if not (0 <= nx < m and 0 <= ny < n): continue
            dp[ny][nx] = max(dp[ny][nx], M[ny][nx]*M[y][x]+dp[y][x])

n, m, d = map(int, input().split())
M = [list(map(int, input().split())) for _ in ' '*n]
dp = [[-inf]*m for _ in ' '*n]
dp[0] = [0]*m
for i in range(n-1):
    for j in range(m):
        move(j, i)
print(max(dp[-1]))

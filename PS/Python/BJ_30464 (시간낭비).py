import sys
input = sys.stdin.readline
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
fini = lambda: int(input()[1:])
fins = lambda: [*map(int, input()[1:].split())]
inf = float('inf')
"""
30646번 시간낭비

1에서 출발해 N에 가려한다. 그런데 최대한 늦게 가려한다.
일단, 이동 방식은 간단하다. 이번 칸에 서있는 숫자만큼 이동한다. 이에는 1분이 걸린다.
최대 두 번 방향을 반대로 꺾을 것이다. 그리고 이동해야 시간이 걸린다. 제자리에선 시간이 흐르지 않는다.
이 때, N으로 가기 위해서는 몇 번 방향을 꺾어야할까?

간단한 dp
그냥 보자마자 이거 dp네 하고 폰으로 타닥타닥 완료.
간단하게 dp[i][j] = i번 뒤돌았고 j에 있는 경우로 하면 됩니다.
대부분 이런건 최솟값찾으라고하는데 최댓값 찾는 문제는 귀하네요.
"""
N = ini()
A = ins()
dp = [[-inf]*N for _ in ' '*3]
dp[0][0] = 0
for i in range(3):
    sign = (-1)**i
    R = range(N-1) if ~i&1 else range(N-2, -1, -1)
    for j in R:
       nxt = j+A[j]*sign
       if 0 <= nxt < N and j != nxt:
           dp[i][nxt] = max(dp[i][nxt], dp[i][j]+1)
       if i < 2:
           dp[i+1][j] = max(dp[i+1][j], dp[i][j])
print(-1 if dp[-1][-1] == -inf else dp[-1][-1])
import sys
input = lambda: sys.stdin.readline().strip("\n")
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
1126번 같은 탑

배열이 주어진다.
원소를 둘로 나누자. 모든 원소가 포함되지 않아도 된다.
그리고 그 둘의 합이 같아질 때, 그 합의 최대를 구해보자.

플레3은 그 이유가 있다 진짜

dp[i][j] = i번째까지, 두 집합의 차이가 j일 때 가능한, 더 작은 집합의 합

둘의 차이를 dp배열에 넣는다라..
쉽지않네요 진짜.
"""
N = ini()
A = ins()
lim = sum(A)//2
dp = [[-1]*(lim+1) for _ in ' '*(N+1)]
dp[0][0] = 0
for i in range(N):
    for j in range(lim+1):
        if dp[i][j] == -1: continue
        if j+A[i] <= lim:
            dp[i+1][j+A[i]] = max(dp[i+ 1][j+A[i]], dp[i][j])
        diff = abs(j-A[i])
        if diff <= lim:
            dp[i+1][diff] = max(dp[i+1][diff], dp[i][j]+min(j, A[i]))
        dp[i+1][j] = max(dp[i+1][j], dp[i][j])
print(dp[-1][0] if dp[-1][0] else -1)

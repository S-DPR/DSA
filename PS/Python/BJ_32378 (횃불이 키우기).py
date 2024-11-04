import sys
input = lambda: sys.stdin.readline().strip("\n")
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
32378번 횃불이 키우기

초기 숫자 S와 정수 배열 A가 있다. 아래 두 행위를 적절히 하여 그 값을 최대화해보자.
1. A[i]를 더한다.
2. 2를 곱한다.
단, 2번은 최대 K번만 사용 가능하며 답이 10^11을 넘는다면 'MEGA'를 대신 출력한다.

log(10^11) = 약 37
그러므로, A의 최대 크기가 20만이지만 dp로 처리할 수 있는 수준의 크기가 됩니다.
이 애드혹 하나 읽어주고 dp 슥 긁어주면 끝.
잡다한 실수때문에 늦게 맞춰버리긴 해서 아쉽네요.
"""
MX = 10**11
N, K, S = ins()
if S*(1<<K) > MX:
    print('MEGA')
    exit(0)
A = [0] + ins()
dp = [[-inf]*(K+1) for _ in ' '*(N+2)]
dp[0][0] = S
for i in range(N+1):
    for k in range(K+1):
        if dp[i][k] <= 0: continue
        dp[i+1][k] = max(dp[i+1][k], dp[i][k]+A[i])
        if i != 0 and k+1 <= K:
            dp[i+1][k+1] = max(dp[i+1][k+1], dp[i][k]*2)
ret = max(dp[-1])
if ret > MX:
    print('MEGA')
else:
    print(-1 if ret <= 0 else ret)

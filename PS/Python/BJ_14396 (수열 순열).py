import sys
input = sys.stdin.readline
MOD = 1000000009
"""
14396번 수열 순열

1부터 N까지 있는 수열이 있다.
정확히 M번 인접한 원소 두개의 위치를 바꾸어 만들 수 있는 수열의 개수를 구하여라.
단, 수가 너무 커지니 1000000009로 나눈 나머지를 구하여라.

푼 사람이 적어서 그런가 플레난이도를 받은 DP문제입니다.
어쨌든 그냥 보면 규칙이 없어보이고, 노가다를 좀 해야하는데..

원소가 두개인 경우
a b
 : 1 : b a (1)
 : 2 : a b (1)

원소가 세개인 경우
a b c
 : 1 : b a c / a c b (2)
 : 2 : a b c / b c a / c a b (3)
 : 3 : b a c / a c b / c b a (3)
 : 4 : a b c / b c a / c a b / a b c / b c a / c a b / a b c / b c a / c a b / a b c / b c a / c a b (3)

원소가 네개인 경우
a b c d
 : 1 : b a c d / a c b d / a b d c (3)
 : 2 : a b c d / b c a d / b a d c / c a b d / a c d b / a d b c (6)
 : 3 : b a c d / a c b d / a b d c / c b a d / b c d a / b d a c / c a d b / a d c b / d a b c (9)
 : 4 : (생략) (11)

.. 이건데요
수를 보다보면.. 규칙이 있습니다.
문제대로 수열이 1 2 3 .. N이 있다고 하고, 스왑이 M번이라고 합시다.
그러면 1이 자기 자리를 유지한 다음 스왑이 일어난 횟수는 1~N-1까지 있는 수열을 M번 스왑하는것과 같고..
2가 첫 자리에 간다면 1~N-1까지 있는 수열을 M-1번 스왑하는 경우..
3이 첫 자리에 간다면 1~N-1까지 있는 수열을 M-2번 스왑..
..
이걸 다 더하면 됩니다.
DP네요.

아래 코드는 첫 제출 코드입니다.
"""
n, k = map(int, input().split()); n+=1; k+=1
dp = [[0]*k for _ in ' '*n]
for i in range(k): dp[2][i] = 1
for i in range(n): dp[i][0] = 1
for i in range(3, n):
    for j in range(1, k):
        tmp = max(j-i+1, 0)
        dp[i][j] = sum(dp[i-1][tmp:j+1])%MOD
print(dp[-1][-1])

"""숏코딩 203B
n,k=map(int,input().split());n+=1;k+=1
dp = [[0]*k for _ in' '*n]
for i in range(k):dp[2][i]=1
for i in range(3,n):
 for j in range(k):dp[i][j]=sum(dp[i-1][max(j-i+1, 0):j+1])%(10**9+9)
print(dp[-1][-1])
"""

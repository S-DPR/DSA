import sys
input = sys.stdin.readline
inf = 4000000
"""
15487번 A[j]-A[i]+A[l]-A[k]

길이가 n인 수열 A가 주어진다.
i < j < k < l에 대해, A[j]-A[i]+A[l]-A[k]의 최댓값을 구하여라.
(4 <= n <= 1000000)

간결한 문제입니다.
간단함에 괜히 매료되어 그리디적으로 세그트리 갖다박았다가 실패 엄청 하고 손 놨었는데,
문제 보다가 이거 이건가? 하고 그냥 장난으로 박았더니 AC가 나왔습니다..

dp를 dp[4][n]으로 정의한 뒤,
dp[0]에는 A[i]에 대해 최솟값을,
dp[1]에는 A[j]-A[i]의 최댓값,
dp[2]에는 A[j]-A[i]-A[k]의 최댓값,
dp[3]에는 A[j]-A[i]+A[l]-A[k]의 최댓값을 저장합니다.
이제 dp[3]의 최닷값이 정답입니다..
내고 시작하자마자 틀렸습니다 뜨겠거니 했는데 퍼센트 올라가는거보고 현타가..
"""
n = int(input())
arr = list(map(int, input().split()))
dp = [[-inf]*n for _ in ' '*4]
for i in range(4):
    for j in range(i+1, n):
        dp[i][j] = 
dp[0][0] = arr[0]
for i in range(1, n):
    dp[0][i] = min(dp[0][i-1], arr[i])
dp[1][1] = arr[1] - arr[0]
for i in range(2, n):
    dp[1][i] = max(dp[1][i-1], arr[i] - dp[0][i-1])
dp[2][2] = arr[1] - arr[0] - arr[2]
for i in range(3, n):
    dp[2][i] = max(dp[2][i-1], dp[1][i-1] - arr[i])
dp[3][3] = arr[1] + arr[3] - arr[0] - arr[2]
for i in range(4, n):
    dp[3][i] = max(dp[3][i-1], dp[2][i-1] + arr[i])
print(max(dp[-1]))

import sys
input = sys.stdin.readline
"""
2253번 점프

1로 시작하는 수열이 있습니다.
수열의 마지막값과 step+1, step, step-1 만큼의 차이가 나는 숫자를 뒤에 붙일 수 있습니다.
단, 직전 수와 같거나 작은 숫자는 뒤에 붙일 수 없습니다.
초기 step값은 0이고 입력의 첫 줄에 n과 m이 주어집니다.
이후 m줄에 [수열에 포함되어선 안되는 숫자]가 하나씩 주어집니다.
이 때, 마지막 수가 정확하게 n인 수열의 가장 짧은 길이를 출력해주세요.
만약 마지막 수가 n인 수열을 만들 수 없다면, -1을 출력합니다.
step은 직전에 뛰었던 step값을 유지합니다. 즉, step+1로 뛰었다면 다음 step은 step+1입니다.

정해는 세개인데 그중 두개가 DP입니다.
하나는 BFS인데 저의 조악한 BFS실력으로는 10000 0을 넣었을 때 시간내에 되지 않더라고요..
남은 두개는 재귀DP와 이차원배열DP인데, 이차원배열로 풀었습니다.
이차원배열DP는 첨해봤는데 음..

dp[현재 마지막 수][현재 step] 입니다.
질문글에서 힌트 받아서 만들어봤어요.
w값은 1~141의 합이 10011로, 처음으로 10000을 넘는수길래 저렇게 설정했습니다.
"""
n, m = map(int, input().split()); n+=1
w = 145
s = set(int(input()) for _ in ' '*m)
dp = [[n]*w for _ in ' '*n]
dp[1][0] = 0
for i in range(1, n):
    if i in s: continue # 있어선 안되는 수
    for j in range(w):
        if dp[i][j] == n: continue # 도달하지 못함
        for k in [-1, 0, 1]:
            if j+k > 0 and i+j+k < n and j+i+k not in s and dp[j+i+k][j+k] > dp[i][j]+1:
                dp[j+i+k][j+k] = dp[i][j]+1
print(k if (k:=min(dp[-1])) != n else -1)
# DP 우우욱....

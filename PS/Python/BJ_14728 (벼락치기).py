"""
14728번 벼락치기

배낭문제를 푸시오.

시간이 없어서 쉬운 골드5 뭐있나.. 찾아보다가,
ㄹㅇ 벼락치기로 좋은 배낭을 만났습니다.
폰으로 풀고 컷.
"""
import sys
input = sys.stdin.readline
split = lambda: list(map(int, input().split()))

N, M = split()
dp = [0]*(M+1)
for _ in ' '*N:
	x, y = split()
	if x > M: continue
	for i in range(M-x, -1, -1):
		dp[i+x] = max(dp[i+x], dp[i]+y)
	dp[x] = max(dp[x], y)
print(max(dp))
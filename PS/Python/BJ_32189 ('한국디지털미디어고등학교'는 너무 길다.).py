import sys
input = lambda: sys.stdin.readline().strip("\n")
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
32189번 '한국디지털미디어고등학교'는 너무 길다.

문자열을 S[:k]와 S[k:]로 나누어보자.
적절한 k에서의 둘의 LCS는 몇일까?

이유는 모르겠는데 길이가 N일 때 적절한 k가 반드시 k/2와 k/2+1중 하나라고 하네요??
???
GPT한테 물어봤는데도 이해 안됨
머 암튼 그렇다고하네요
난모르겠다~
"""
def lcs(s, t):
    sln = len(s)
    tln = len(t)
    dp = [[0]*(tln+1) for _ in ' '*(sln+1)]
    for i in range(1, sln+1):
        for j in range(1, tln+1):
            if s[i-1] == t[j-1]:
                dp[i][j] = max(dp[i][j], dp[i-1][j-1]+1)
            else:
                dp[i][j] = max(dp[i][j-1], dp[i-1][j])
    return dp[-1][-1]

S = input()
N = len(S)
ret = lcs(S[N//2:], S[:N//2])
if N&1: ret = min(ret, lcs(S[N//2+1:], S[:N//2+1]))
print(N//2-ret)

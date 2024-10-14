import sys
input = lambda: sys.stdin.readline().strip("\n")
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
12758번 천상용섬

수열 A가 주어진다.
새로운 수열 B를 만들려한다. B[i]는 A[i]의 약수여야한다.
B를 비오름차순으로 만드는 경우의 수를 구해보자.

쉬운 골드1.
100만 이하 약수 개수가 대충 270개정도, 수열 A 길이가 300.
dp[i][j] = A[i]의 오름차순으로 div[A[i]][j]를 B[i]에 배치할 때 경우의 수.
"""
from bisect import bisect_left

MAX = 1_000_000
MOD = 1_000_000_007

def loop(idx, num):
    if idx == N: return 1
    kth = bisect_left(div[A[idx]], num)
    if kth == len(div[A[idx]]): return 0
    if dp[idx][kth] != -1 : return dp[idx][kth]
    k = div[A[idx]][kth]
    dp[idx][kth] = loop(idx+1, k)
    if kth+1 != len(div[A[idx]]):
        dp[idx][kth] = (dp[idx][kth] + loop(idx, div[A[idx]][kth+1]))%MOD
    return dp[idx][kth]

div = [[] for _ in ' '*(MAX+1)]
for i in range(1, MAX+1):
    for j in range(i, MAX+1, i):
        div[j].append(i)
for _ in ' '*ini():
    N = ini()
    A = ins()
    dp = [[-1]*301 for _ in ' '*301]
    print(loop(0, 0))

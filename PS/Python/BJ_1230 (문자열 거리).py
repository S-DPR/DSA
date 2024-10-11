import sys
input = lambda: sys.stdin.readline().strip("\n")
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
1230번 문자열 거리

문자열 S의 어떤 위치에 어떤 문자열을 삽입하여 T로 만들려 한다.
문자열의 최소 삽입 횟수를 구해보자.

보자마자 길이 보고 일단 dp 잡고,
뭐 이런 문자열dp면 S랑 T 인덱스 잡고,
그렇게 2차원으로 해보려다가 때려치고 [2] 추가해서 삽입중인지까지 처리.
이러면 쉽게 AC 나오네요. 30분걸렸나?
최근에 골드1중 이렇게 빨리푼건 되게 적었는데 이건 빨리풀었네
"""
sys.setrecursionlimit(10**6)
def loop(sidx, tidx, inserting):
    if sidx == len(S) and tidx == len(T):
        return 0
    if tidx == len(T):
        return inf
    if dp[sidx][tidx][inserting] != -1:
        return dp[sidx][tidx][inserting]
    dp[sidx][tidx][inserting] = loop(sidx, tidx+1, 1) + (inserting^1)
    if sidx < len(S) and S[sidx] == T[tidx]:
        dp[sidx][tidx][inserting] = min(dp[sidx][tidx][inserting], loop(sidx+1, tidx+1, 0))
    return dp[sidx][tidx][inserting]

S = input()
T = input()
dp = [[[-1]*2 for _ in ' '*(len(T)+1)] for _ in ' '*(len(S)+1)]
# dp[i][j][k] = sidx가 i이고 tidx가 j이고 현재 새로넣고있는문자열인지(k)
print([loop(0, 0, 0), -1][loop(0, 0, 0) == inf])

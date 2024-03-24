import sys
input = sys.stdin.readline
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
1017번 소수 쌍

N개의 수가 주어진다.
N은 항상 짝수이며, N/2개의 그룹으로 나누려고 한다.
각 그룹의 합은 소수가 되어야할 때, 첫번째 숫자와 매칭 가능한 수들을 모두 구하시오.

음..
매칭..이니까 이분매칭같은데..
근데 이거 이분그래프긴한가..?에서 고민을 많이 한 문제.

그런데 생각해보니, 홀수+홀수는 항상 소수가 아니고, 짝수+짝수도 홀수가 항상 아니네?
아, 그렇구나! 홀수와 짝수로 구분지을 수 있구나!
매칭도 몇개는 직접 설정해둘 수 있구나.

네, 이분탐색 활용은 처음이었네요.
사실 기본문제 하나만 해결해봤었던건데..
이분매칭을 어렵게하려면 이분그래프임을 숨겨라, 메모..
"""
def dfs(x):
    vis[x] = 1
    for i in G[x]:
        if RV[i] == -1 or (not vis[RV[i]] and dfs(RV[i])):
            LV[x] = i
            RV[i] = x
            return 1
    return 0

N = ini()
A = ins()
G = [[] for _ in ' '*N]
L, R = [], []
for i in range(N):
    target = L if A[i]&1 else R
    target.append(i)
for i in range(N):
    for j in range(i+1, N):
        sm = A[i]+A[j]
        isPrime = 1
        for k in range(2, sm):
            if k*k > sm: break
            if sm%k: continue
            isPrime = 0
        if isPrime:
            G[i].append(j)
            G[j].append(i)
first = L if A[0]&1 else R
ret = set()
for i in G[0]:
    LV, RV = [-1]*N, [-1]*N
    match = 1
    LV[0] = i
    RV[i] = 0
    for j in first:
        if LV[j] != -1: continue
        vis = [0]*N
        vis[0] = 1
        vis[i] = 1
        if dfs(j): match += 1
    if match*2 == N:
        ret.add(A[i])
if ret: print(*sorted(ret))
else: print(-1)

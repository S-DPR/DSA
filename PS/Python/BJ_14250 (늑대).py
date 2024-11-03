import sys
input = lambda: sys.stdin.readline().strip("\n")
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
14250번 늑대

일렬로 있는 점의 개수 N, 구간이 M개 주어진다. 구간은 [L, R]로 나타내진다.
점에 색을 칠할 수 있다. 각 구간은 적어도 하나 이상의 색칠된 점이 포함되어야 한다.
이 때, 가능한 점 배열의 경우의 수를 구해보자.

이게 대체 머시당가 하고 구경만 했던 문제
GPT에게 답을 받고 이게 왜 될까 하면서 고민해봤습니다.

경우의수니까 조합론/dp중에 적절히 선택해야하고.. 이 문제는 N이 작으니 일반적인 dp로.
dp[i][j] = i번째 점까지 봤을 때, j번째가 마지막으로 선택된 점일경우의 경우의 수.
라고하네요 음...

우선 끝점이 같은 점들은 시작점이 가장 끝점과 가까운 요소만 가져갑니다.
[L1, R], [L2, R] (L1 < L2) 인 경우 [L2, R] 가 만족될 경우 [L1, R]이 만족되기 때문입니다.
그리고 이전의 dp[i-1]의 L부터 R-1까지 합해 dp[i][R]에 넣어줍니다.

어떤 점의 경우 구간의 끝 점으로 사용되지 않을 수 있습니다.
이 경우, 있든 없든 큰 상관이 없으므로 구간 0부터 R-1까지 합을 가져오면 됩니다.

음..
너무어려운데.
"""
MOD = 10**9 + 7

N, K = ins()
M = [0]*(N+1)
for _ in ' '*K:
    u, v = ins()
    M[v+1] = max(M[v+1], u+1)
dp = [0]*(N+1)
dp[0] = 1
for i in range(1, N+1): # 현재 늑대를 배치 할 인덱스
    new = [0]*(N+1)
    new[i] = sum(dp)%MOD
    for j in range(M[i], i):
        new[j] = (new[j]+dp[j])%MOD
    dp = new
print(sum(dp)%MOD)

# class Fenwick:
#     def __init__(self, sz):
#         self.f = [0]*(sz+1)
#         self.sz = sz
        
#     def update(self, idx, val):
#         while idx <= self.sz:
#             self.f[idx] += val
#             idx += idx&-idx
    
#     def int_query(self, idx):
#         ret = 0
#         while idx > 0:
#             ret += self.f[idx]
#             idx -= idx&-idx
#         return ret
    
#     def query(self, l, r):
#         return self.int_query(r)-self.int_query(l-1)

# from collections import defaultdict
# N = ini()
# A = [ins() for _ in ' '*N]
# C = sorted(set(j for i in A for j in i))
# D = {i: idx for idx, i in enumerate(C, 1)}
# X = defaultdict(list)
# for idx, (i, j) in enumerate(A):
#     X[D[i]].append(D[j])
# sz = len(C)
# items = sorted(X.items())
# F = Fenwick(sz)
# S = Fenwick(sz)
# E = Fenwick(sz)
# upd = [0]*N
# ret = 0
# for _, i in items:
#     i = i[0]
#     E.update(i, 1)
#     F.update(i, 1)
# for _, i in items[::-1]:
#     i = i[0]
#     print(f"{i} : ", end="")
#     for j in range(1, sz+1):
#         print(S.query(j, j), end=" ")
#     print()
#     print(S.query(i+1, sz))
#     ret += S.query(i+1, sz)
#     S.update(i, E.query(i+1, sz))
#     E.update(i, -1)
# print(ret)

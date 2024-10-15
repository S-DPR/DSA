import sys
input = lambda: sys.stdin.readline().strip("\n")
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
9334번 IQ 테스트

정수 N개가 주어진다. 이 정수는 다음 점화식을 따른다고한다.
F(N) = (a_1)F(n-1)+...+(a_d)F(n-d) (1 <= d <= 3, 모든 수는 정수)
N+1번째는 어떤 수일까?
수열을 만족하는 d가 여러개라면 가장 작은 d가 정답이다.

음..
그냥 GPT 해줘~ 해서 된거긴한데 코드이해가 먼저같아요
어려워 진짜

일단.. 가우스소거법이 기초.
선형대수 및 선형대수학이 기초가 됩니다.
이거 몇 번 본거같은데..

d = 1, 2, 3 모두 해봅니다.
1이 안되면 2로, 2가 안되면 3으로.
그리고 그것을 행렬로 표시하고, 그 방정식을 푼다.
이게 기초적인 알고리즘.

가우스공부법이나 오랜만에 공부해봐야지..
"""
from fractions import Fraction

def find_recurrence(seq, max_d=3):
    n = len(seq)
    for d in range(1, max_d + 1):
        if d >= n:
            continue
        A = []
        B = []
        for i in range(d, n):
            equation = []
            for j in range(1, d + 1):
                equation.append(seq[i - j])
            A.append(equation)
            B.append(seq[i])
        
        try:
            A_frac = [ [Fraction(num) for num in row] for row in A ]
            B_frac = [ Fraction(num) for num in B ]
            for i in range(d):
                pivot = None
                for row in range(i, len(A_frac)):
                    if A_frac[row][i] != 0:
                        pivot = row
                        break
                
                if pivot != i:
                    A_frac[i], A_frac[pivot] = A_frac[pivot], A_frac[i]
                    B_frac[i], B_frac[pivot] = B_frac[pivot], B_frac[i]
                
                factor = A_frac[i][i]
                A_frac[i] = [x / factor for x in A_frac[i]]
                B_frac[i] = B_frac[i] / factor
                
                for row in range(len(A_frac)):
                    if row != i and A_frac[row][i] != 0:
                        factor = A_frac[row][i]
                        A_frac[row] = [ A_frac[row][j] - factor * A_frac[i][j] for j in range(d) ]
                        B_frac[row] = B_frac[row] - factor * B_frac[i]
            
            coeffs = [0] * d
            for i in range(d):
                coeff = B_frac[i]
                if coeff.denominator != 1:
                    raise ValueError("Non-integer coefficient")
                coeffs[i] = coeff.numerator
            
            valid = True
            for i in range(d, n):
                expected = 0
                for j in range(1, d + 1):
                    expected += coeffs[j - 1] * seq[i - j]
                if expected != seq[i]:
                    valid = False
                    break
            if valid:
                return coeffs
        except:
            continue
    return None

def predict_next(seq, coeffs):
    d = len(coeffs)
    next_num = 0
    for i in range(d):
        next_num += coeffs[i] * seq[-(i + 1)]
    return next_num

def main():
    T = ini()
    for _ in ' '*T:
        _, *seq = ins()
        coeffs = find_recurrence(seq, max_d=3)
        next_num = predict_next(seq, coeffs)
        print(next_num)

if __name__ == "__main__":
    main()


# from collections import deque
# def check(m):
#     def bfs(G):
#         level = [[-1]*(T+1) for _ in ' '*(N+2)]
#         Q = deque([[0, 0]])
#         level[0][0] = 0
#         while Q:
#             curN, curT = Q.popleft()
#             for nxtN, nxtT, cap, _ in G[curN][curT]:
#                 if cap <= 0 or level[nxtN][nxtT] != -1: continue
#                 level[nxtN][nxtT] = level[curN][curT]+1
#                 Q.append([nxtN, nxtT])
#         return level
    
#     def dfs(G, level, iter, curN, curT, flow):
#         if curT > T: return 0
#         if curN == N+1: return flow
#         while iter[curN][curT] < len(G[curN][curT]):
#             idx = iter[curN][curT]
#             nxtN, nxtT, cap, rev = G[curN][curT][idx]
#             if cap > 0 and level[curN][curT]+1 == level[nxtN][nxtT]:
#                 push = dfs(G, level, iter, nxtN, nxtT, min(flow, cap))
#                 if push > 0:
#                     G[curN][curT][idx][2] -= push
#                     if rev >= 0: G[nxtN][nxtT][rev][2] += push
#                     return push
#             iter[curN][curT] += 1
#         return 0
    
#     newG = [[[] for _ in ' '*(T+1)] for _ in ' '*(N+2)]
#     flow = 0
#     for u, v, w in G:
#         if w > m: continue
#         for t in range(T):
#             uidx, vidx = len(newG[u][t]), len(newG[v][t+1])
#             newG[u][t].append([v, t+1, 1, vidx])
#             newG[v][t+1].append([u, t, 0, uidx])
#     for i in range(N+1):
#         for t in range(T):
#             newG[i][t].append([i, t+1, inf, -1])
#     newG[0][0].append([1, 0, inf, -1])
#     for t in range(T+1):
#         newG[N][t].append([N+1, t, inf, -1])
#     while level := bfs(newG):
#         if not any(level[N+1][t] != -1 for t in range(T+1)): break
#         iter = [[0]*(T+1) for _ in ' '*(N+2)]
#         while push := dfs(newG, level, iter, 0, 0, inf):
#             flow += push
#     return flow

# N, M, T, K = ins()
# G = [ins() for _ in ' '*M]
# lo, hi = 0, 1<<17
# while lo < hi:
#     mid = (lo + hi) >> 1
#     if check(mid) >= K:
#         hi = mid
#     else:
#         lo = mid + 1
# print(-1 if hi == 1<<17 else hi)

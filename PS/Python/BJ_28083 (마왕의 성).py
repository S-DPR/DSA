import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
28083번 마왕의 성

R*C 각 칸의 높이가 주어진다. 또, 각 칸의 가중치가 주어진다.
칸 하나에서는 자신과 같거나 더 낮은 높이로만 이동할 수 있을 때,
모든 칸에 대해 가능한 가중치의 합을 구해보자.

어캐풀까 BFS일까 DP일까 굴리다가 분리집합이 생각나서 푼 문제.
Python3로는 TLE나서 PyPy3로 처리했습니다.
그냥.. 분리집합 간단한 문제였네요.
"""
def union(u, v):
    up, vp = find(u), find(v)
    if up == vp: return 0
    S[vp] += S[up]
    U[up] = U[vp]
    return 1

def find(u):
    if U[u] != u:
        U[u] = find(U[u])
    return U[u]

from collections import defaultdict as d
R, C = ins()
M = [ins() for _ in ' '*R]
S = [i for _ in ' '*R for i in ins()]
ret = [[0]*C for _ in ' '*R]
U = [*range(R*C)]
dic = d(list)
for r in range(R):
    for c in range(C):
        dic[M[r][c]].append([r, c])
dr = [1, -1, 0, 0]
dc = [0, 0, 1, -1]
for k, v in sorted(dic.items()):
    for r, c in v:
        for i in range(4):
            nr, nc = r+dr[i], c+dc[i]
            if not 0 <= nr < R: continue
            if not 0 <= nc < C: continue
            if k < M[nr][nc]: continue
            union(r*C+c, nr*C+nc)
    for r, c in v:
        ret[r][c] = S[find(r*C+c)]
for i in ret: print(*i)

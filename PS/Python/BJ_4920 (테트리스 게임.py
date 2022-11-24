import sys
input=sys.stdin.readline
inf = 10**9
"""
4920번 테트리스 게임

직사각형의 가로 세로 길이 N, M이 주어진다.
이후 N*M개수의 숫자가 M줄에 걸쳐 주어진다.
여기에 테트리스 블록 5개 (문제 참조)를 오버랩해 가려진 합이 최대가 되도록 하려고 한다.
테트리스 블록은 회전이 가능하다 할 때, 그 최대값을 구하시오.

어렵진 않고 실수하면 망하는 문제입니다.
저는 그냥 각 함수에 블록을 할당하여 모든 칸에서 완전탐색을 진행했습니다.
"""
def Istruct_origin(M, x, y):
    if y < 3: return -inf
    return M[y][x] + M[y-1][x] + M[y-2][x] + M[y-3][x]

def Istruct_90rotate(M, x, y):
    if x < 3: return -inf
    return M[y][x] + M[y][x-1] + M[y][x-2] + M[y][x-3]

def Zstruct_origin(M, x, y):
    if y < 1 or x < 2: return -inf
    return M[y][x] + M[y][x-1] + M[y-1][x-1] + M[y-1][x-2]

def Zstruct_90rotate(M, x, y):
    if y < 2 or x+1 >= n: return -inf
    return M[y][x] + M[y-1][x] + M[y-1][x+1] + M[y-2][x+1]

def Nstruct_origin(M, x, y):
    if y < 1 or x < 2: return -inf
    return M[y][x] + M[y-1][x] + M[y-1][x-1] + M[y-1][x-2]

def Nstruct_90rotate(M, x, y):
    if y < 2 or x+1 >= n: return -inf
    return M[y][x] + M[y][x+1] + M[y-1][x+1] + M[y-2][x+1]

def Nstruct_180rotate(M, x, y):
    if y < 1 or x < 2: return -inf
    return M[y][x] + M[y][x-1] + M[y][x-2] + M[y-1][x-2]

def Nstruct_270rotate(M, x, y):
    if y < 2 or x+1 >= n: return -inf
    return M[y][x] + M[y-1][x] + M[y-2][x] + M[y-2][x+1]

def Hstruct_origin(M, x, y):
    if y+1 >= n or x+2 >= n: return -inf
    return M[y][x] + M[y][x+1] + M[y][x+2] + M[y+1][x+1]

def Hstruct_90rotate(M, x, y):
    if y+2 >= n or x < 1: return -inf
    return M[y][x] + M[y+1][x] + M[y+2][x] + M[y+1][x-1]

def Hstruct_180rotate(M, x, y):
    if y < 1 or x+2 >= n: return -inf
    return M[y][x] + M[y][x+1] + M[y][x+2] + M[y-1][x+1]

def Hstruct_270rotate(M, x, y):
    if y+2 >= n or x+1 >= n: return -inf
    return M[y][x] + M[y+1][x] + M[y+2][x] + M[y+1][x+1]

def Dstruct_origin(M, x, y):
    if y < 1 or x < 1: return -inf
    return M[y][x] + M[y-1][x] + M[y][x-1] + M[y-1][x-1]

def block_place(M, x, y):
    return max(Istruct_origin(M, x, y),\
              Istruct_90rotate(M, x, y),\
              Zstruct_origin(M, x, y),\
              Zstruct_90rotate(M, x, y),\
              Nstruct_origin(M, x, y),\
              Nstruct_90rotate(M, x, y),\
              Nstruct_180rotate(M, x, y),\
              Nstruct_270rotate(M, x, y),\
              Hstruct_origin(M, x, y),\
              Hstruct_90rotate(M, x, y),\
              Hstruct_180rotate(M, x, y),\
              Hstruct_270rotate(M, x, y),\
              Dstruct_origin(M, x, y))

tc = 1
while (n:=int(input())):
    M = list(list(map(int, input().split())) for _ in ' '*n)
    MAX = -inf
    for i in range(n):
        for j in range(n):
            MAX = max(MAX, block_place(M, j, i))
    print(f"{tc}. {MAX}")
    tc += 1

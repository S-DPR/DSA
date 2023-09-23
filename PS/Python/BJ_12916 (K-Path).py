import sys
input = sys.stdin.readline
spl = lambda: map(int, input().split())
MOD = 1_000_000_007
"""
12916번 K-Path

인접행렬 그래프가 주어진다.
길이가 K인 서로 다른 모든 경로의 개수를 구해보자.

자바스크립트에서 이상하게 안되네?
난 똑같이짰는데..

그냥 어.. 본대행렬인가 그거랑 같습니다.
그냥 주어진 행렬 K번 제곱하면 끝나는데..
자바스크립트에서는 오버플로가 났나.. 이상하게 값이 안나와서 두시간 때려박았네요.
파이썬으로 오니까 바로 되는게 더 어이가없네..
"""
def matpow(mat, n):
    size = len(mat)
    ret = [[0]*size for _ in ' '*size]
    for i in range(size):
        ret[i][i] = 1
    while n:
        if n&1: ret = matmul(ret, mat)
        mat = matmul(mat, mat)
        n >>= 1
    return ret

def matmul(a, b):
    size = len(a)
    ret = [[0]*size for _ in ' '*size]
    for i in range(size):
        for j in range(size):
            for k in range(size):
                ret[i][k] = (ret[i][k] + (a[i][j]%MOD)*(b[j][k]%MOD))%MOD
    return ret

N, K = spl()
G = [[*spl()] for _ in ' '*N]
print(sum(map(sum, matpow(G, K)))%MOD)

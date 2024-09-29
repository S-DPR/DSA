import sys
input = lambda: sys.stdin.readline()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
3989번 유행성 독감

초기 배열 A가 있다. 이 배열을 1번이라고 하자.
N(N >= 2)번 배열은 ((초기 배열의 원소)*(N-1번 배열의 원소))%M이다.
K번째 배열은 어떤 배열일까?

그냥.. 사이클이 나올거라는 믿음과 함께 완탐박고 처리하면 되는 문제.
실제로 그렇게 하니 파이썬으로도 꽤 빠르게 성공했습니다.
이렇게 푸는게 정해..같은데 분할정복 거듭제곱으로 깔끔하게 풀리나보네요.
신기하다..
"""
K, M, N = ins()
A = set(ins())
F = [i for i in A]
S = {}
D = []
idx = 0
while (T := tuple(A)) not in S:
    S[T] = idx
    D.append(T)
    newA = set()
    for i in F:
        for j in A:
            newA.add((i*j)%M)
    A = newA
    idx += 1
K -= 1
T = tuple(A)
cycle = S[T]
if K <= cycle:
    print(*sorted(D[K]))
else:
    K -= cycle
    D = D[cycle:]
    K %= len(D)
    print(*sorted(D[K]))

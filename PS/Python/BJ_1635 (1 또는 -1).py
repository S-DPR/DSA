import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
1635번 1 또는 -1

1과 -1로만 이루어진 수열이 있고, 여기에 추가로 1과 -1로만 이루어진 수열을 구성해 둘을 곱할 수 있다.
곱하면 새로운 수열 A[i]는 각 인덱스에 있는 값 둘을 곱한 값이 된다.
수열의 합을 0으로 만들려 한다. 수열을 어떻게 구성해야할까?
단, 수열의 종류는 N개 이하여야한다.

하
수열의 합이 초기에 0이면 바꿀 필요가 없으므로 모두 1을 곱한다.
아니면 어떤 한 인덱스 k에 대해 A[:k] = A[k:]이므로..
1을 k개, -1을 N-k개 출력하면 끝난다..라니..
아 너무 당연한데.. 못봤어..
"""
N, K = ins()
A = [ins() for _ in ' '*K]
for i in A:
    S = sum(i)
    if not S:
        print(*[1]*N)
        continue
    pf = 0
    for j in range(N):
        pf += i[j]
        if pf*2 != S: continue
        print(*[1]*(j+1), *[-1]*(N-j-1))
        break

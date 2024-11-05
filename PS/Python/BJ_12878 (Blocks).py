import sys
input = lambda: sys.stdin.readline().strip("\n")
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
12878번 Blocks

N개의 블록에 빨주노초 색을 칠하려한다.
빨간색과 노란색으로 칠해진 블록의 개수는 각각 반드시 2로 나누어 떨어져야 할 때,
칠하는 방법의 경우의 수를 구해보자.

우선.. n개중 k개를 고릅니다. 여기에 빨간색/노란색을 칠할겁니다.
즉, nCk (k는 짝수).
이제 k개중 x개를 다시 고릅니다. 여기에 빨간색으로 칠할겁니다.
즉, kCx (x는 짝수).
이제 n-k개에는 남은 색중 암거나 칠합니다. 즉, 2^(n-k).
셋을 곱하면 답인데..

kCx (x는 짝수) = 2^(k-1)입니다.
nCk (k는 짝수) = 2^(n-1)인데..
문제는 여기서 k가 0인경우 kCx 계산이 막히게됩니다.
그래서 k는 2 이상의 짝수로 정해버리고 2^(n-1)-1로 대체합니다.
k = 0인경우는 2^n으로 계산할 수 있습니다.

뜬금없지만, kCx = 2^(k-1)이 된 시점에서, 앞에 있던 2^(n-k)와 합쳐집니다.

즉, 정리하면,
(2^(n-1)-1) * 2^(n-1) + 2^n.
분할정복 거듭제곱으로 계산하면 AC.
진짜 얼마만에 직접 푼 수학문제냐..
"""
MOD = 10_007
N = ini()
first = (pow(2, N-1, MOD)-1+MOD)%MOD
second = pow(2, N-1, MOD)
third = pow(2, N, MOD)
print((first*second+third)%MOD)

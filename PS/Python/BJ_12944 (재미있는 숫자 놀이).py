import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
fini = lambda: int(input()[1:])
fins = lambda: [*map(int, input()[1:].split())]
inf = float('inf')
"""
12944번 재미있는 숫자 놀이

수열 A가 주어진다.
1 이상 N 이하의 정수 중, 수열에 있는 요소중 적어도 하나로 나누어 떨어지는 정수는 몇 개일까?

금요일에 바빠서 폰으로 푼 문제.
소수의 뭐 어쩌구 문제랑 똑같은데 골드2라서 놀랐습니다.
덕분에 날먹..
"""
from itertools import combinations
from math import lcm
N, K = ins()
A = ins()
cnt = 0
for i in range(1, K+1):
    sign = (-1)**(i+1)
    for j in combinations(A, i):
        x = 1
        for k in j: x = lcm(x, k)
        cnt += N//x * sign
print(cnt)

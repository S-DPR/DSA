import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
14565번 역원(Inverse) 구하기

N과 A가 주어질 때,
(X+A) mod N = 0
YA mod N = 1
을 만족하는 X, Y를 구해보자.
없는 부분은 -1을 출력하자.

갓-파이썬은 어떤언어일까?
북마크에는 남겨놔야지..
"""
from math import gcd
N, A = map(int, input().split())
print(N-A, pow(A, -1, N) if gcd(N, A) == 1 else -1)

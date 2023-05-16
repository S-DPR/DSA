import sys
from collections import defaultdict
input = sys.stdin.readline
"""
2170번 선긋기

s부터 e까지 그어진 선이 N개 있다.
이를 1차원에서 볼 때 (즉 모두 합칠 때) 그어진 총 길이를 구해보자.

루비 -> 시간초과
코틀린 -> 메모리초과
아.. 파이썬을 이딴데에 쓴다고..? 아......
"""
d = defaultdict(int)
for _ in ' '*int(input()):
    s, e = map(int, input().split())
    d[s] += 1
    d[e] -= 1
c, p, l = 0, -10**10, 0
for i in sorted(d):
    if c > 0: l += i-p
    c += d[i]
    p = i
print(l)

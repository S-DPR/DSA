import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
fini = lambda: int(input()[1:])
fins = lambda: [*map(int, input()[1:].split())]
inf = float('inf')
"""
25823번 조합의 합의 합

M이 주어진다.
(nCr)^2, r = 0~n, n = 3~M
의 값을 구해보자.

그..
GPT힘 써서 푼 문제..
어떻게 저게 2nCn이랑 같지..
나중에 증명 한 번 해봐야할것같습니다.
"""
MOD = 10**9+7
M = ini()
r, x = 0, 1
lo, hi = 1, 1
for i in range(1, M+1):
    while hi <= i*2:
        x = (x*hi)%MOD
        hi += 1
    while lo <= i:
        x = (x*pow(lo, MOD-2, MOD)**2)%MOD
        lo += 1
    if i >= 3: r = (r+x)%MOD
print(r)
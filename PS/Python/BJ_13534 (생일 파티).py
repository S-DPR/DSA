import sys
input = sys.stdin.readline
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
13534번 생일 파티

합이 N이고 길이가 F이며, 배열의 gcd가 1인 경우의 수를 구해보자.

크아악
GPT가 오류 하나 잡아줘서 그거 수정하고 맞춘 문제.
거의 1페이지 다썼네 또..

포함배제가 주가 되고, 그와중에 중복조합 쓰면서 모듈러 역원이 나옵니다.
소수판정은 덤이고..
이게 왜 다른 골드1이랑 같은 난이도인거야..
"""
MAX = 10000
MOD = 1_000_000_007
facto = [1]*(MAX*2+1)
cnt = [0]*(MAX+1)

def comb(n, r):
    if r < 0: return 0
    up = facto[n]
    down = (facto[r]*facto[n-r])%MOD
    return (up*pow(down, MOD-2, MOD))%MOD

def H(n, r):
    return comb(n+r-1, r)

for i in range(1, MAX*2+1):
    facto[i] = facto[i-1]*i
    facto[i] %= MOD
for _ in ' '*ini():
    n, f = ins()
    div = []
    for i in range(1, n+1):
        if i*i > n: break
        if n%i: continue
        div.append(i)
        if n//i != i: div.append(n//i)
    div.sort()
    ret = H(f, n-f)
    for i in div: cnt[i] = 1
    for i in div[1:]:
        h = H(f, (n//i)-f)
        ret = (ret-h*cnt[i]+MOD)%MOD
        for j in div:
            if j%i or i == j: continue
            cnt[j] -= cnt[i]
    for i in div: cnt[i] = 0
    print(ret%MOD)

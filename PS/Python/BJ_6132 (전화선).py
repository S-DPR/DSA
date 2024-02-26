import sys
input = sys.stdin.readline
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
6132번 전화선

수열 A가 있다. A의 원소에 다음과 같은 행위를 할 수 있다.
 - A[i]를 A[i]+x로 바꿀 수 있다. 비용은 x^2이다.
 - A[i]와 A[i+1]을 합칠 수 있다. 값은 A[i+1]로 고정되며, 비용은 |A[i]-A[i+1]|*C이다.
원소를 하나로 합치기 위해 최소 몇의 비용이 들까?

아니 이거 그동안 진짜어렵다 했는데 그냥 문제 잘못읽은거였네
아 근데 어떻게 10^5*10^2*10^2가 통과하지?
진짜 묘합니다 묘해..

정말 문제에 있는거 잘 구현해주면 AC.
이상하게 이런류 dp가 좀 많은거같네요.
분명 더 효율적인 방법이 있을겁니다, 찾아봐야할거같아요..
"""
h = 101
n, c = ins()
dp = [inf]*h
f = ini()
for i in range(f, h):
    dp[i] = (f-i)**2
tmp = [inf]*h
for i in range(1, n):
    x = ini()
    for j in range(1, h): # 이전 층
        if dp[j] == inf: continue
        for k in range(x, h): # 현재 층
            cost = (k-x)**2 + abs(j-k)*c
            tmp[k] = min(tmp[k], cost+dp[j])
        tmp[x] = min(tmp[x], abs(j-x)*c+dp[j])
    for j in range(h):
        dp[j] = tmp[j]
        tmp[j] = inf
print(min(dp))

import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
11796번 수행평가 1

수열 A에 포함되어있지않은 모든 수가 K 이하인 수열의 최소 길이와 그 개수를 구해보자.
A도 모든 수가 K 이하이다.

1년 전에 본 문젠데 해싱 태그 보고도 뭔지 몰랐고,
결국 라빈카프 찾아봐서 풀었습니다.
신기하네..

그래도 보자마자 이분탐색 생각은 바로 났음 ㅋㅋ
"""
BASE = 1000019
def hashing(x):
    s = set()
    hash = 0
    power = 1
    for i in range(N-x+1):
        if i == 0:
            for j in range(x):
                hash = hash + A[x-j-1] * power
                if j < x-1: power = (power * BASE) % MOD
        else:
            hash = (BASE*(hash-A[i-1]*power)) + A[i+x-1]
        hash %= MOD
        s.add(hash)
    return s

MOD = 10**9+7
N, K = ins()
A = ins()
lo, hi = 1, N+1
while lo < hi:
    mid = (lo + hi) >> 1
    if pow(K, mid, MOD) == len(hashing(mid)):
        lo = mid + 1
    else:
        hi = mid
print(hi, pow(K, hi, MOD)-len(hashing(hi)))

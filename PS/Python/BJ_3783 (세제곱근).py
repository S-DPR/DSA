import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
3783번 세제곱근

양의 정수가 주어진다. 세제곱근을 소숫점 아래 10번째까지 구해보자.

음..
이게 그 파이썬 없으면 아마 난이도는 미친듯이 치솟지 않았을까 하는문제.

이게 그..
이유는모르겠는데.
30을 넘는 3의 배수 k를 적당히 10^k로 N에 곱해줘서 이분탐색하면 됩니다..?
하아니 이게 뭔소리야
난 막 Decimal쓰고 난리쳤는데 이게 저거만 곱하면 이분탐색으로된다고?
근데 또 31 32 이런애들은 WA가뜬다고?
진짜 알다가도모르겠다
"""
div = 10**10
powed = div**3
for _ in ' '*ini():
    N = ini()*powed
    lo, hi = 0, N+1
    while lo < hi-1:
        mid = (lo + hi) >> 1
        if mid**3 <= N:
            lo = mid
        else:
            hi = mid
    print(f"{lo//div}.{lo%div:010d}")

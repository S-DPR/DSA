import sys, math
from itertools import combinations
input = sys.stdin.readline
MAX = 10**9
sqrtMAX = 10**5
"""
9359번 서로소

수 A, B, N이 주어진다. A이상 B이하 수중 N과 서로소인 수의 개수를 구하여라.
(1 <= A, B <= 10^15, 1 <= N <= 10^9)

문제가 간단하고 내가 못풀겠다 = 수학
제목 보자마자 히히 정수론 하고 도전! 했는데,
포함배제원리 << 이건 이름은 몰랐지만 중등교육은 받은 놈으로서 대충 생각해냈는데
소인수분해 << 이 기초를 못해내서 아..
눈물을 흘리며 정답코드에 랜덤테스트케이스 때려박아 뭐가 틀렸나 보면서 풀었습니다.
포함배제원리는 좀 생소하긴한데 그래도 배워간다는 느낌으로 문제를 끝내는거로..

근데 솔직히말해 진짜 G4~G5에서 놀 문제인줄알았는데 왜케 높지
"""
def prod(x):
    res = 1
    for i in x: res *= i
    return res

# 정수론중 배운거 썼습니다!
# 에라토스테네스의 체인데 내가 구할 수의 최대크기는 10^9고,
# 10^9의 약수를 판정할 때에는 sqrt(N)까지의 수만 필요하다는 점을 이용해,
# 10^9보다 살짝 큰 10^5까지의 수를 구했습니다.

prime = [True]*(sqrtMAX+1)
prime[0] = prime[1] = False
for i in range(2, sqrtMAX+1):
    if not prime[i]: continue
    for j in range(i+i, sqrtMAX+1, i):
        prime[j] = False

for tc in range(1, int(input())+1):
    A, B, N = map(int, input().split())
    div = set()
    for i in range(2, int(N**.5)+1):
        if N == 1: break
        if not prime[i] or N%i: continue
        while not N%i: N//=i
        div.add(i)
    if N != 1: div.add(N)
    cntA, cntB = A-1, B
    for k in range(1, len(div)+1):
        # 포함배제원리는 이런식으로. 오늘도 배워갑니다.
        for i in map(lambda x: prod(x), combinations(div, k)):
            cntB += ((-1)**k)*(B//i)
            cntA += ((-1)**k)*((A-1)//i)
    print(f"Case #{tc}: {cntB-cntA}")

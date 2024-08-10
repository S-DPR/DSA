import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
20505번 John's Math Problem

수가 주어진다.
0개 이상의 수를 제거한 뒤 그대로 다시 이어붙일 때 나오는 모든 수를 더한 값을 구해보자.

하아
조합론..
11제곱이 어디서 나오는지나 알아봤습니다..

우선 기본은 i번째 자리가 전체 값에 얼마나 영향을 미치는지입니다.
즉, 각 i를 기준으로 보아야 처리가 됩니다.
현재 보는 위치가 i라면, 0~i-1번째는 있든없든 상관없습니다. 그러므로 2^i개가 나옵니다.
뒤쪽에선 r개의 수를 골라야합니다. 나온 값은 2^i개, 뒤에서 뽑은 (n-i-1)Cr개. 
즉, 2^i * (n-i-1)Cr개의 10^r*S[i]만큼 영향을 끼칩니다.

(A+B)^n에서 (A^x)(B^y)의 계수 = nCx * (A계수) * (B계수) 임이 자명합니다.
그리고, r를 0부터 n-i-1까지 본다면,
(n-i-1)C0*10^0 + (n-i-1)C1*10^1 + (n-i-1)C2*10^2 + ... + (n-i-1)C(n-i-1)*10^(n-i-1)
이를 조금 더 변형해보면,
(n-i-1)C0*1^(n-i-1)*10^0 + (n-i-1)C1*1^(n-i-2)*10^1 + ... + (n-i-1)C(n-i-1)*1^0*10^(n-i-1)
즉, A = 1, B = 10인 (A+B)^n으로 바뀜을 알수있으며,
nC0 + nC1 + ... + nCn-1 + nCn = 2^n이라는 공식과 함께,
(1+10)^(n-i-1)로 바꾸어 쓰며 logN으로 각 수를 처리할 수 있게 됩니다.
최종적으로, 2^i * 11^(n-i-1) * S[i]를 모두 더하면 끝납니다.
"""
MOD = 998_244_353
S = input()
N = len(S)
ret = 0
for i in range(N):
    ret = (ret + pow(2, i, MOD)*pow(11, (N-i-1), MOD)*int(S[i]))%MOD
print(ret)

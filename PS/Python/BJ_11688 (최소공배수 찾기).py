import sys
input=sys.stdin.readline
"""
11688번 최소공배수 찾기

a, b, L이 주어진다. LCM(a, b, c) = L을 만족하는 가장 작은 c를 출력하시오.
LCM은 최소공배수를 뜻하며, 1 <= a, b <= 10^6, 1 <= L <=10^12가 보장된다.
가능한 c가 없으면 -1을 출력한다.

쉬운 수학문제입니다.
유클리드 호제법과 O(sqrt(N)) 약수 구하는 알고리즘만으로 바로 풀어낼 수 있습니다.
"""
gcd = lambda x, y: gcd(y, x%y) if y else x
a, b, L = map(int, input().split())
p = (a*b)//gcd(a, b)
share = []
for i in range(1, int(L**.5)+1):
    if L%i: continue
    share.extend([i, L//i])
share.sort()
for i in share:
    if (p*i)//gcd(p, i) != L: continue
    print(i)
    break
else: print(-1)

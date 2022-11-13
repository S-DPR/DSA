import sys
from itertools import permutations
input = sys.stdin.readline
MAX = 10**5
"""
22943번 수 

정수 K, M이 주어진다.
0~9중 K개를 골라 만든 수 중에서 아래 조건과 같은 수의 개수를 출력하라.
조건 1. 서로 다른 소수 두 개의 합으로 나타낼 수 있다.
조건 2. 수가 M으로 나누어 떨어질 때 M으로 계속 나눈다.
       나누어떨어지지 않을 때 나온 수를 소수 두 개의 곱으로 나타낼 수 있다.
       (소수가 같아도 됨)
맨 앞 자리에 0이 나오는 수는 수로 치지 않는다.
(1 <= K <= 5, 2 <= M <= 10^9)

별로 좋은문제인진 잘 모르겠습니다..
일단 에라토스테네스의 체와 브루트포스 짬뽕시킨 문제입니다.
파이썬은 모듈때문에 백트래킹까지 가져가진 않습니다.
그.. 2번 조건에서 'M으로 나누어 떨어지면'부분을 'M으로 나눌 수 있으면'으로 착각해서 몇시간 날렸..
"""
def sieve():
    global prime, prime_list
    prime_list = []
    prime = [True]*(MAX+1)
    prime[0] = prime[1] = False
    for i in range(2, MAX+1):
        if not prime[i]: continue
        prime_list.append(i)
        for j in range(i+i, MAX+1, i):
            prime[j] = False

sieve()
k, m = map(int, input().split())
cnt = 0
# 여기서 처음써본건 filter랑
N = filter(lambda x: x[0] != '0', permutations('0123456789', k))
# 여기 join 함수.
N = map(lambda x: int(''.join(x)), N)
for i in N:
    for j in prime_list:
        if i <= j: continue
        if j == i-j: continue
        if prime[i-j]: break
    else: continue
    while not i%m: i//=m
    for j in prime_list:
        if i%j: continue
        if prime[i//j]: break
    else: continue
    cnt += 1
print(cnt)

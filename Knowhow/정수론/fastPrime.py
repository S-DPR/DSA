import time

N = 10**6

# 에라토스테네스의 체 (O(Nlg(lgN)))
# 10^8까지 소수 구하는데 4.47초
print('에라토스테네스의 체')
s = time.time()
era_sieve = [True]*(N+1)
era_sieve[0] = era_sieve[1] = False
for i in range(2, int(N**.5)+1):
    if not era_sieve[i]: continue
    for j in range(i+i, N+1, i):
        era_sieve[j] = False
print(time.time()-s)

# 오일러의 체 (Linear Sieve, O(N))
# 10^8까지 소수 구하는데 3.32초
# 덤으로 각 인덱스가 가장 작은 소인수를 갖는 배열과
# 소수배열을 얻을 수 있다.
print('오일러의 체')
s = time.time()
linear_sieve = [0]*(N+1)
prime = []
for i in range(2, N+1):
    if not linear_sieve[i]:
        prime.append(i)
        linear_sieve[i] = i
    for j in prime:
        if N < i*j: break
        linear_sieve[i*j] = j
        if not i%j: break
print(time.time()-s)

# 밀러-라빈 소수 판별법
# 위 방법들은 N 이하의 모든 소수를 구하는데 유리하지만,
# N이 소수인지 판정하는데는 유리하지 않다.
# N이 소수인지 판별하는데는 나왔었던 O(sqrt(N))이 있지만,
# 더 빠르게 할 수 있다. O(k(lgN)^3)으로.
# k가 검사횟수라서, int형인경우 O(3(lgN)^3), long은 O(12(lgN)^3)이다.
# 작은 N에 대하여, 당연히 sqrt(N)이 더 작으니 그냥 그거 쓰면된다.
P32 = [2, 7, 61]
P64 = [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37]
def miller(N, mod):
    if N == mod: return True
    if not mod % N: return False
    k = N-1
    while True:
        tmp = pow(mod, k, N)
        if tmp == N-1: return True
        if k&1: return tmp in [1, N-1]
        k >>= 1
# INT 최댓값인 2147483647 (2**31-1)에 대해,
# O(sqrt(N))과 속도비교를 해보자.
# 참고로 이 수는 소수이다.
# 결과 : sqrt(N)은 종종 숫자가 표시되지만, miller는 항상 0.0초로 나옴.
INT_MAX = 2**31-1
print('소수판정 O(sqrt(N)) : INT_MAX')
s = time.time()
for i in range(2, int(INT_MAX**.5)+1):
    if not INT_MAX % i:
        print(i, 'not prime')
        break
print(time.time()-s)

print('소수판정 miller : INT_MAX')
s = time.time()
for i in P32:
    if not miller(INT_MAX, i): break
print(time.time()-s)

# int형 이하 long long 미만은 37 이하의 소수로 충분하다.
# 2**63-1이 LONG 최댓값인데, 아무래도 이 수는 소수가 아니니까,
# 9223372036854775507로 대신해보자.
LONG_MAX = 9223372036854775507
#LONG_MAX = 2**63-1
# sqrt(9223372036854775507)는 30억정도라서, 측정하기엔 시간이 아깝다..
"""
print('소수판정 O(sqrt(N)) : LONG_MAX')
s = time.time()
for i in range(2, int(LONG_MAX**.5)+1):
    if not LONG_MAX % i:
        print('not prime')
        break
print(time.time()-s)
"""

# 결과 : 0.0 출력. 매우매우 빠르다!
print('소수판정 miller : LONG_MAX')
s = time.time()
for i in P64:
    if not miller(LONG_MAX, i):
        print('not prime')
        break
print(time.time()-s)

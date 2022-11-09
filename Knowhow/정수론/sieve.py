import random
# 약수 기초, 에라토스테네스의 체

# https://rkm0959.tistory.com/178
# https://rebro.kr/96
# 두 블로그를 보고 공부해보았다.

# N이 주어질 때 다음을 구해보자.
# N의 소수 여부 : O(sqrt(N)
N = random.randint(2, 100)
for i in range(2, int(N**.5)+1):
    if N%i: continue
    print(f'{N} is not Prime')
    break
else: print(f'{N} is Prime')

# N의 모든 약수 : O(sqrt(N))
N = random.randint(2, 100)
div = []
for i in range(1, int(N**.5)+1):
    if N%i: continue
    div.extend([i, N//i])
    if div[-2]==div[-1]: div.pop()
print(f'{N}\'s divisors :', *sorted(div))

# N 소인수분해 : O(sqrt(N))
from collections import defaultdict
N = tmp = random.randint(2, 100)
div = defaultdict(int)
for i in range(2, int(N**.5)+1):
    if N%i: continue
    while not tmp%i:
        div[i]+=1
        tmp//=i
else:
    if tmp != 1: div[tmp]+=1
print(f'{N}\'s Prime Factorization')
for i, j in div.items():
    print(f'{i}^{j} ', end='')
if not div: print(f'{N} ({N} is Prime)', end='')
print()

# 에라토스테네스의 체
# basic
print(f'1~100 Prime')
MAX = 100
arr = [True]*MAX; arr[0] = arr[1] = False
for i in range(MAX):
    if not arr[i]: continue
    for j in range(i+i, MAX, i):
        arr[j] = False
for i in range(MAX):
    if arr[i]: print(i, end=' ')
print()

# 1부터 시작하지 말고 N부터 N+100까지 찾아보자
# O(sqrt(N)+MlgM)
N = random.randint(1, 1000)
MAX = 100
arr = [True]*(N+MAX); arr[0] = arr[1] = False
print(N, '~', N+MAX, 'Primes')
for i in range(2, int((N+MAX)**.5)):
    if not arr[i]: continue
    for j in range(i+i, N+MAX, i):
        arr[j] = False
for i in range(N, N+MAX):
    if arr[i]: print(i, end=' ')
print()

# 1부터 100까지 제일 작은 소인수를 저장해보자
MAX = 100
arr = list(range(MAX)); arr[0] = 1
for i in range(2, MAX):
    if arr[i] != i: continue
    for j in range(i+i, MAX, i):
        arr[j] = i
print(*arr)

# 위 arr을 이용해 1부터 MAX까지 모든 수를 소인수분해해보자
# 위 O(sqrt(N))으로 N개를 소인수분해하면 O(Nsqrt(N))이지만,
# 아래 방법은 O(NlgN)이다.
MAX = 20
PF = [[] for _ in ' '*MAX]
for i in range(2, MAX):
    tmp = i
    while tmp > 1:
        PF[i].append(arr[tmp])
        tmp//=arr[tmp]
for idx, i in enumerate(PF):
    print(idx, ':', *i)

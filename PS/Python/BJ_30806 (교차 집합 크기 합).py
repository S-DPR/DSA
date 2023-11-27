import sys
from collections import defaultdict
input = sys.stdin.readline
spl = lambda: map(int, input().split())
inf = float('inf')
mod = 998_244_353
"""
30806번 교차 집합 크기 합

N개의 집합이 주어진다.
집합 k(1 <= k <= N)개의 가능한 모든 교집합의 크기의 합을 각 k에 대해 구해보자.

아레나에서 푼 P4? 이건 날이 지나갔지만 올려야지 ㄹㅇㅋㅋ
근데 이전 문제가 G4였는데 갑자기 P4로 난이도 널뛰기하는게 맞나..

nCk mod P를 어떻게 구할까? 에 대한 문제였습니다.
제가 했던건 저거 전단계까지였고 저거 구해준건 gpt가 했는데..
덕분에 이항계수 문제를 안 푼 것을 후회하고 있습니다.

제가 했던 아이디어는..
일단 모든 집합에서 각 원소를 하나씩 세자는 생각.
마침 섞이지도 않는다니 저기까진 한 30분고민해서 냈던 결론같네요.
어쨌든 풀어서 다행이다.. 퍼포먼스 SS 찍었다..
"""
N = int(input())
D = defaultdict(int)
R = [0]*(N+1)
C = [0]*(N+1)
for _ in ' '*N:
    S, *A = spl()
    for i in A:
        D[i] += 1
for i in D.values():
    C[i] += 1
for i in range(1, N+1):
    if not C[i]: continue
    comb = 1
    for j in range(1, i+1):
        comb = (comb * (i - j + 1)) * pow(j, mod-2, mod)
        comb %= mod
        R[j] = (R[j] + comb*C[i]) % mod
for i in range(1, N+1):
    print(R[i])

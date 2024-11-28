import sys
input = lambda: sys.stdin.readline().strip("\n")
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
22899번 오렌지컵 출제하기

수열의 길이 N과 수열이 주어진다. 각 원소는 특정 집합에 속해있다.
각 집합에서 최대 L개의 요소를 꺼내올 수 있다. 그리고 그 합을 최소로 하려 한다.
단, 꺼내는 요소 개수의 합은 반드시 K개여야한다.
L이 1부터 N까지의 자연수일 때, 가능한 합의 최소를 구해보자.
만약 어떤 L에서는 K개를 꺼낼 수 없다면, 그 L에는 -1을 대신 출력하자.

그냥 힙문제
되게 어렵게 접근했다가 와 이거 진짜 아닌거같은데 하고 물린다음 다시 풀었습니다.
대충 각 L마다 추가되는 녀석을 힙으로 관리하는게 포인트.
구현 자체도 딱히 어려울건 없습니다.
"""
import heapq
N, K = ins()
A, B = ins(), ins()
X = [[] for _ in ' '*(N+1)]
exists = set()
ret = [-1]*(N+1)
for i in range(N):
    exists.add(A[i])
    X[A[i]].append(B[i])
for i in range(1, N+1):
    X[i] = sorted(X[i])[::-1]
sm = 0
H = []
for l in range(1, N+1):
    nxt = set()
    for i in exists:
        item = X[i].pop()
        heapq.heappush(H, -item)
        sm += item
        if X[i]: nxt.add(i)
    exists = nxt
    while len(H) > K:
        sm -= -heapq.heappop(H)
    if len(H) == K:
        ret[l] = sm
print(*ret[1:])

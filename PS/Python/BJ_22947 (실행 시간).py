import sys
from collections import deque as d
from itertools import combinations as c
input = sys.stdin.readline
spl = lambda: map(int, input().split())
inf = float('inf')
mod = -1<<62
"""
22947번 실행 시간

각 정점을 지우는데 T[i]의 시간이 든다.
정점을 지우려면 자신을 향한 간선이 없어야한다.
간선은 양쪽 중 한쪽 정점이 없으면 사라진다.
DAG가 주어질 때, 총 K개의 T[i]를 0으로 만든 뒤 그중 최솟값을 구해보자.
1번 노드에서 시작할 수 있음이 보장된다.
또, 1번과 마지막으로 종료되는 정점은 0이 될 수 없다.

마지막 조건이 문제속에 박혀있어서 '어 왜안돼???'만 계속 외쳤던 문제.
어제 비상식량 안먹고 이거먹으려고했는데, 계속 안되서 때려쳤었는데..
.. 아..

문제 잘본다매!! 문제 잘 읽을거라매!!!!!!
"""
N, M, K = spl()
G = [[] for _ in ' '*(N+1)]
I = [0]*(N+1)
T = [0] + [*spl()]
ret = 1<<30
for _ in ' '*M:
    u, v = spl()
    G[u].append(v)
    I[v] += 1
for i in c(range(2, N+1), K):
    last = 0
    VT = T[:]
    R = [0]*(N+1)
    II = I[:]
    for j in i: VT[j] = 0
    deq = d([1])
    while deq:
        x = deq.popleft()
        last = x
        R[x] += VT[x]
        for j in G[x]:
            R[j] = max(R[j], R[x])
            II[j] -= 1
            if not II[j]: deq.append(j)
    if last in i: continue
    ret = min(ret, max(R))
print(ret)

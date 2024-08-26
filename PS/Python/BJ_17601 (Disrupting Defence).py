import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
17601번 Disrupting Defence

배열이 주어진다.
여기서 서로 다른 두 인접한 요소를 제거할 수 있다.
모든 배열을 제거하는 순서를 구해보자. 모든 배열을 제거할 수 없다면 -1을 출력하자.

음..
왜맞았지?
그냥 대충 N^2 완탐 그리디 때려박아 구현했더니 AC..
조금 더 깔끔하고 좋은 방법이 존재하지 않았을까 싶습니다.
"""
from collections import defaultdict as d
N, K = ins()
A = [[i, idx] for idx, i in enumerate(ins())]
count = d(int)
for i, _ in A: count[i] += 1
ret = []
for _ in ' '*(N//2):
    n = len(A)
    dic = d(list)
    for i in range(n):
        if A[i][0] != A[(i+1)%n][0]:
            item = sorted([A[i][0], A[(i+1)%n][0]])
            dic[tuple(item)].append([i, A[i][1], A[(i+1)%n][1]])
    if max(count.values()) > len(A)//2: break
    s = max(dic, key=lambda x: (sorted([count[x[0]], count[x[1]]])[::-1]))
    x, y = s
    idx, lidx, ridx = dic[s].pop()
    if not dic[s]: del dic[s]
    ret.append([lidx, ridx])
    count[x] -= 1
    count[y] -= 1
    newA = []
    for i in range(n):
        if i not in [(idx+1)%n, idx]:
            newA.append(A[i])
    A = newA
else:
    for i, j in ret:
        print(i+1, j+1)
    exit(0)
print(-1)

import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
32207번 꿀잼 루비 문제

R*C모양 크기에 숫자가 적혀있다.
서로 인접하지 않게 K개를 골라낼 때, 그 합을 최대화해보자.

R, C가 1000인데 반해 K가 최대 5.
이거 잘 조지면 완탐인데.. 생각은 했는데,
아쉽게도 직접 풀진 못하고 답지를 봐버렸네요.
근데 흥미롭더라.

전에는 R, C가 최대 10이고 K도 100인가 그랬던 작은 문제였기에 bit-dp가 됐는데
이번에는 살짝 발상을 바꿔서, '어차피 최댓값만 고를거라면..'으로 가야합니다.
최악의경우에도 21개 이상을 고를 필요가 없다고 하고..
하튼 크기순 정렬 한다음 적당한 크기로 슬라이싱 한다음에 거기서 완탐.
진짜 근데 흥미로웠네요. 오늘도 배워따
"""
from itertools import combinations
R, C, K = ins()
M = [ins() for _ in ' '*R]
A = []
for r in range(R):
    for c in range(C):
        A.append([M[r][c], r, c])
A = sorted(A)[::-1][:K*15]
ret = 0
for k in range(1, K+1):
    for i in combinations(A, k):
        near = 0
        for u in range(k):
            for v in range(u+1, k):
                near |= abs(i[u][1]-i[v][1])+abs(i[u][2]-i[v][2]) == 1
        if not near:
            ret = max(ret, sum(j[0] for j in i))
print(ret)

import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
13027번 Clique Problem

X축 위에 가중치가 w인 점이 N개 있다.
두 정점 X, Y에 대해 X.w+Y.w <= |X.x-Y.x|인 경우 두 정점이 이어져있는 것이다.
x w가 N개 주어질 때, 가장 큰 부분그래프 중 완전그래프의 크기를 구해보자.

ㅋㅋ 부등호 방향 반대로봐서 틀렸다
답지 딱 하나있는데 부등호방향 반대여야 나오는 경우 나와서 놀랐는데,
내가 반대로 본거였고..

그리디+스위핑인건 금방 알아서 다행이네요.
휴~
"""
N = ini()
A = []
for _ in ' '*N:
    u, v = ins()
    A.append([u-v, u+v])
ret, k = 0, -inf
for u, v in sorted(A, key=lambda x: x[1]):
    if u < k: continue
    k = v
    ret += 1
print(ret)

import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
16295번 Criss-Cross Cables

N개의 포트와 M개의 케이블의 길이가 주어진다.
케이블을 모두 사용할 수 있을까?
단, 완전히 동일한 포트를 서로 다른 와이어로 이을 수 없다.

이전 기하학 골드1에 머리 깨지고 문제 리셋해서 푼 문제..
하..
사실 푼거도 아니고 그냥 이거말고 한개 더 풀어야해서 답지 바로봐버렸..

근데 문제 푸는 방식은 되게 신기하네요
문제를 정 반대로 이해해서 못떠올린거같긴한데 음..
저는 문제를 '모든 포트에 케이블을 꼽을 수 있을까?'로 이해해서 와 어캐품 이랬는데
반대로 모든 케이블을 모든 포트에 뽑을 수 있을까면..
솔직히 생각했을거같은데?
"""
import heapq
N, M = ins()
A = ins()
H = [[A[i+1]-A[i], i, i+1] for i in range(N-1)]
heapq.heapify(H)
S = set()
for i in sorted(ins()):
    while H and (H[0][1], H[0][2]) in S:
        heapq.heappop(H)
    if not H or i < H[0][0]:
        print('no')
        break
    _, l, r = heapq.heappop(H)
    if l-1 >= 0: heapq.heappush(H, [A[r]-A[l-1], l-1, r])
    if r+1 < N: heapq.heappush(H, [A[r+1]-A[l], l, r+1])
    S.add((l, r))
else:
    print('yes')

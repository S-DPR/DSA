import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
2461번 대표 선수

길이가 K인 배열이 N개 주어진다.
각 배열에서 수를 하나씩 뽑자. 그리고 그 최댓값과 최솟값을 최소화해보자.

와..
이거 태그까고 풀어놓고 골드2라고 하는건 좀..

힙 내부를 '현재 집은 요소'로 치자.
라는 아이디어를 캐치해야했네요.
이 아이디어 못잡아서 시간이 너무 오래 걸려버리고 그냥 답지봤습니다.
혼자 덱으로 이짓저짓 해보다가 망해버렸고..
"""
import heapq
N, K = ins()
A = [sorted(ins()) for _ in ' '*N]
H = [[i[0], idx, 0] for idx, i in enumerate(A)]
mx = max(A[i][0] for i in range(N))
mn = min(A[i][0] for i in range(N))
ret = mx-mn
heapq.heapify(H)
while H:
    _, k, i = heapq.heappop(H)
    if i+1 >= K: break
    heapq.heappush(H, [A[k][i+1], k, i+1])
    mx = max(mx, A[k][i+1])
    mn = H[0][0]
    ret = min(ret, mx-mn)
print(ret)

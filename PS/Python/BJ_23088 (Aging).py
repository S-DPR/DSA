import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
23088번 Aging

t, p, b로 이루어진 프로세스 정보 배열이 주어진다. t는 시간, p는 우선순위, b는 처리시간이다.
처리하던 프로세스를 중간에 끊을 수는 없다.
아래 조건에 맞춰 매번 뽑히는 인덱스를 찾아내보자.
 - 우선순위가 가장 큰 값이 먼저 뽑힌다.
 - 매 단위시간마다 대기중인 프로세스는 우선순위가 1씩 증가한다.
 - 우선순위가 같다면, 처리시간이 가장 작은 값이 먼저 뽑힌다.
 - 처리시간이 같다면, 먼저 들어간 프로세스가 먼저 뽑힌다.
 
어캐푸냐 하면서 타닥타닥하다가,
답지를 보니까 2번에 그냥 속은거더라고요.
어떻게 현재 시간 curT는 상관이 없대..
억울해서우럿서..
어쩄든.. 이런 힙문제는 첨봅니다. 참..
"""
import heapq
N = ini()
H = []
A = [ins() for _ in ' '*N]
ret = []
curT, idx = 0, 0
while idx < N or H:
    while idx < N and A[idx][0] <= curT:
        t, p, b = A[idx]
        heapq.heappush(H, [t-p, b, idx+1])
        idx += 1
    if idx < N and not H:
        curT = A[idx][0]
        continue
    _, b, i = heapq.heappop(H)
    curT += b
    ret.append(i)
print(*ret)

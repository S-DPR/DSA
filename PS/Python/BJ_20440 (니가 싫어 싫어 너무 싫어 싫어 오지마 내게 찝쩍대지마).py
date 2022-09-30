import sys
from collections import defaultdict
input = sys.stdin.readline
"""
20440번 니가 싫어 싫어 너무 싫어 싫어 오지마 내게 찝쩍대지마

제목 상태가..
뭐 어때요 문제만 읽으면되지.
구간을 N개 주는데, 그중 가장 많이 중첩된 구간의 중첩개수와 범위를 구하면 됩니다.
"""
t = defaultdict(int)
for _ in ' '*int(input()):
  x, y = map(int, input().split()); t[x] += 1; t[y] -= 1
  # 딕셔너리를 이용해 들어오는거에 +1, 나가는거에 -1을 해주고요.

M = now_M = 0
sT = eT = 0

for i, j in sorted(t.items()): # 정렬하고..
  now_M += j
  if now_M > M: # 만약 현재 최댓값보다 지금 최댓값이 더 크다면
    M = now_M; sT = i; flg = 1 # M 바꾸고, 시작시간 정하고, flg를 1로.
  elif now_M < M and flg: # flg가 1이고 모기가 나갔다면
    eT = i; flg = 0 # eT값 정하고 flg를 0으로.

print(M)
print(sT, eT)

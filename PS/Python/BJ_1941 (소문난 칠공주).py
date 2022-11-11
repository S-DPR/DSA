import sys
from collections import deque
from itertools import combinations
input = sys.stdin.readline
"""
1941번 소문난 칠공주

Y와 S로 이루어진 문자열이 5*5배열에 있다.
S를 4개 이상 포함하는 길이 7의 경로의 개수를 구하여라.

dfs나 bfs로 골머리를 앓았지만 결국 조합으로 푼 문제입니다.
배열이 25밖에 안되므로 이중 7개를 뽑아봅니다.
그 후 제일 작은 수를 deque에 넣고 bfs돌리듯이 조건 빡세게 넣어서 돌려버립니다.
while문을 빠져나오고 t에 내용물이 없으며,
trace에 S가 4개 이상 있으면 조건에 맞는 경로가 됩니다.
"""
M = list(list(input().rstrip()) for _ in ' '*5)
cnt = 0
visited = set()
for t in combinations(range(25), 7):
    t = set(t)
    d = deque([min(t)])
    trace = ''
    while d:
        N = d.popleft()
        if N < 20 and N+5 in t:
            d.append(N+5)
            t -= {N+5}
            trace += M[(N+5)//5][(N+5)%5]
        if N > 4 and N-5 in t:
            d.append(N-5)
            t -= {N-5}
            trace += M[(N-5)//5][(N-5)%5]
        if N%5 != 0 and N-1 in t:
            d.append(N-1)
            t -= {N-1}
            trace += M[(N-1)//5][(N-1)%5]
        if N%5 != 4 and N+1 in t:
            d.append(N+1)
            t -= {N+1}
            trace += M[(N+1)//5][(N+1)%5]
    if not t and trace.count('S') >= 4: cnt += 1
print(cnt)

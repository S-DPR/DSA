import sys
from collections import deque
input = sys.stdin.readline

"""
BJ_22513 빠른 오름차순 탐색
재밌는 그래프문제입니다.
특정 조건을 만족 할 때마다 visited를 초기화하여 푸는 문제입니다.
저같은 경우, visited 자체를 그냥 복제 후 deque에 넣어 풀었습니다.
"""

vec = ((1,0),(-1,0),(0,1),(0,-1))
# x, y는 좌표, visited는 방문한 점, obj_num은 목표하는 숫자, cnt는 움직인 횟수입니다.
def bfs(x, y, visited, obj_num, cnt):
    global ans, now_obj
    if now_obj > obj_num: return
    # 현재 최대 obj보다 현재 계산중인 obj가 더 작다면 탐색할 필요가 없습니다.
    V = [i[:] for i in visited]
    V[y][x] = 1
    # visited를 복제하고 현재 좌표에 방문표시를 합니다.
    for dx, dy in vec:
        nx, ny = x+dx, y+dy
        if not (0<=nx<5 and 0<=ny<5): continue
        if M[ny][nx] == -1: continue
        if M[ny][nx] == obj_num: # 만약 이동할 곳에 있는 수가 현재 목표 숫자라면
            if obj_num != 6: # 6이 아니면 그냥 계속 d에 넣어 돌립니다.
                now_obj += 1
                #단, visited는 초기화해주어 d에 넣습니다.
                d.append([nx, ny, [[0]*5 for _ in ' '*5], obj_num+1, cnt+1])
            else: ans = cnt+1 # 현재 목표 숫자가 6이었다면 끝이므로 ans에 cnt+1을 넣어 반복을 종료합니다.
        else:
            if not V[ny][nx]: d.append([nx, ny, V, obj_num, cnt+1]) # 그게 아니면 그냥 bfs 돌립니다.
M = list(list(map(int, input().split())) for _ in ' '*5)
ans = 10**9; now_obj = 1
y, x = map(int, input().split())
d = deque([[x, y, [[0]*5 for _ in ' '*5], 1, 0]])
while d and ans==10**9:
    bfs(*d.popleft())
print(ans if ans < 10**9 else -1)

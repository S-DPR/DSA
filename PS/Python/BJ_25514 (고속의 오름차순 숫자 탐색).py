import sys
from collections import deque
input = sys.stdin.readline
"""
25514번 고속의 오름차순 숫자 탐색

제가 깃허브에 문제 번호 잘못 쓴 25513번 변형문제입니다.
움직일 수 있는 반경이 매우 극단적으로 바뀌었습니다.
자신으로부터 한칸 상하좌우나, 갈 수 있는 가장 끝 자리로 상하좌우.
대신 여전히 보드의 길이는 5라서 굳이 3193번(공)처럼 이분탐색까지 집어넣을 필요는 없으며
그저 해당 이동방향과 보드에 7이 나온점을 감안하여 BFS코드를 짜면 됩니다.
아, 짤때는 목적지가 1인 보드로 바꾸고, 다음은 2인 보드로 바꾸고..
이런식으로 한다음, visited만 목적지 바꿀 때 초기화해주면 됩니다.

한번 코드 짜는 스타일을 바꾸어봤습니다. C나 자바나 그런것들 처럼.
"""
def next_tile(d, x, y, t, visited): # 한 방향으로 한칸 이동하는 경우
    if visited[y][x]: return
    visited[y][x] = t
    d.append([x, y, t])

def to_end_tile(d, x, y, dx, dy, t, M, visited): # 한 방향으로 쭉 달리는 경우
    nx, ny = x, y
    while True:
        if (dx and nx+dx in [5, -1]) or (dy and ny+dy in [5, -1]): break
        if M[ny+dy][nx+dx] == -1: break # 가려는곳이 -1이면 그대로 break합니다.
        nx += dx; ny += dy
        if M[ny][nx] == 7: break # 움직인곳이 7이면 그대로 멈춥니다.
    if visited[ny][nx]: return
    visited[ny][nx] = t
    d.append([nx, ny, t])

def bfs(d, x, y, t, visited, M): # bfs 메인부분입니다.
    vec = ((1, 0), (-1, 0), (0, 1), (0, -1))
    t += 1
    for dx, dy in vec:
        nx, ny = x+dx, y+dy
        if not (0 <= nx < 5 and 0 <= ny < 5): continue
        if M[ny][nx] == -1: continue
        next_tile(d, nx, ny, t, visited)
        to_end_tile(d, x, y, dx, dy, t, M, visited)

def bfs_basis(M, n, m): # bfs에서 뜯어온부분입니다.
    visited = [[0]*5 for _ in ' '*5]
    visited[n][m] = -1
    d = deque([[m, n, 0]])
    find_num = 1
    while d:
        x, y, t = d.popleft()
        if M[y][x] == find_num:
            if find_num == 6: return t
            find_num += 1
            d = deque()
            visited = [[0]*5 for _ in ' '*5]
            visited[y][x] = t
        bfs(d, x, y, t, visited, M)
    return -1

def main(): # 메인함수입니다.
    M = [list(map(int, input().split())) for _ in ' '*5]
    n, m = map(int, input().split())
    print(bfs_basis(M, n, m))

main()

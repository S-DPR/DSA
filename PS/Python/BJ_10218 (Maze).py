import sys
input = sys.stdin.readline
spl = lambda: map(int, input().split())
"""
10218번 Maze

지도가 주어진다.
어떤 칸에서 시작하든 O로 갈 수 있는 경로를 구해보자.
단, 한 번 굴리면 벽까지 가야하고, 가장자리는 벽임이 보장된다.

이야
이거 진짜 1년동안 북마크에서 묵혀진 문제였는데
태그는 봤다지만 드디어 풀었네

TC문제인데 TC 안주어져서 4^10은 안되겠지 하고 넘겼었는데..
의외로 4^10이 정해더라고요. 제껀 2^10입니다.
그냥.. 구현을 어떻게 편하고 잘 할 수 있을까에 고려해서 짜봤습니다.
"""
MAX = 10
dx = [1, -1, 0, 0]
dy = [0, 0, 1, -1]
ranges = [[range(MAX), range(MAX)],
          [range(MAX), range(MAX, 0, -1)],
          [range(MAX), range(MAX)],
          [range(MAX, 0, -1), range(MAX)]]
char = ['R', 'L', 'D', 'U']
def loop(M, trace, prv):
    cnt = sum(map(lambda x: x.count('.'), M))
    if not cnt: return trace
    if len(trace) == 10: return 'XHAE'
    for i in range(4):
        if i^1 == prv or i == prv: continue
        nxtM = [M[i][:] for i in range(R)]
        for r in ranges[i][0]:
            for c in ranges[i][1]:
                move(nxtM, r, c, dx[i], dy[i])
        if M == nxtM: continue
        nxtR = loop(nxtM, trace+char[i], i)
        if nxtR != 'XHAE': return nxtR
    return 'XHAE'

def move(M, r, c, dx, dy):
    if r >= R or r+dy >= R: return
    if c >= C or c+dx >= C: return
    if M[r][c] != '.': return
    nxt = M[r+dy][c+dx]
    match nxt:
        case '.' | ' ':
            M[r+dy][c+dx] = '.'
            M[r][c] = ' '
        case 'O':
            M[r][c] = ' '

T = int(input())
for _ in ' '*T:
    R, C = spl()
    M = [[*input()] for _ in ' '*R]
    print(loop(M, '', -1))

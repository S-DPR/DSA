import sys
from collections import deque
input = sys.stdin.readline

"""
18224번 미로에 갇힌 건우 G1
건우덕분에 하루동안 저도 갇혀있었습니다.

문제는 평범(해보이는) BFS입니다.
그런데 약간 다른 BFS인게, 저같은 경우 이분탐색을 섞어 푼 문제입니다.
3089번 네잎클로버를 찾아서, 이 문제를 풀었었는데, 여기에 그래프를 씌운 느낌입니다.
대신, 방향은 직접 찾아가야하는..
하다하다 안돼서 비트미스킹도 갖다부었습니다.

제 풀이는 그렇게 효율이 좋은 풀이도 아닌것같습니다.
근데 문젠 뭐냐면, 푼 사람이 100명대인, 백준 음지를 뒤적여야 발견하든 말든 하는 그런수준이라..
(저같은경우 랜덤 돌리다 나왔습니다.)
그 코테에 잘 나온다는, 유명한 그래프문제인데도 100명대인거라.. 검색해봐도 인터넷엔 두개밖에 없습니다.

어쨌든 이거 푸느라 이 문제의 채점현황 두페이지를 제가 꽉 채웠습니다.
한 1승 39패 (마지막에 이김)이니까 이긴거겠죠.
"""

"""
이분탐색 대표주자 하면 뭡니까. lowerbound, upperbound죠.
저는 lowerbound 만들겠습니다. binsect인가 모듈 머 있는거로 아는데 전 모르겠어요.
이거 어차피 만드는데 1분도 안걸리니까..

매개변수에 arr 붙이면 하나로 합칠 수 있을것같은데, 이미 만들어버려서 그냥 두개 만들었습니다.
"""
def lowerbound_x(x, y):
    l = 0; r = len(void_idx_x[y])-1
    while l < r:
        m = l + r >> 1
        if void_idx_x[y][m] >= x: r = m
        else: l = m + 1
    return r

def lowerbound_y(x, y):
    l = 0; r = len(void_idx_y[x])-1
    while l < r:
        m = l + r >> 1
        if void_idx_y[x][m] >= y: r = m
        else: l = m+1
    return r

"""
미로찾기형 그래프 문제의 꽃, BFS입니다.
이거 먼저 보지 마시고 밑에 main에서 어떻게 흘러가나 먼저 보시는게 좋을 것 같습니다.
"""
vec = ((1,0), (-1,0), (0,1), (0,-1)) # 저는 개인적으로 vector을 이렇게 정의하는것을 좋아합니다.
def bfs(x, y, t, walk):
    next_t = t + (walk+1) // m
    next_m = (walk+1) % m
    for dx, dy in vec: # 그리고 vector을 이렇게 풀어요.
        nx, ny = dx+x, dy+y # 여기서 기존 좌표와 합쳐주고,
        if not (0 <= nx < n and 0 <= ny < n): continue # 만약 이동좌표가 맵을 벗어나면 continue시킵니다.
        if not t%2: # 낮인 경우입니다. t가 짝수면 낮입니다.
            if M[ny][nx] or ((1<<(next_t%2+10))|(1<<next_m) in visited[ny][nx]): continue
            # 낮에는 벽을 못넘죠. 가려는곳이 벽이거나 이미 '낮에' '걸었던만큼' 걸어서 여기 왔었다면 continue시킵니다.
            # 이 부분이 이 문제의 상당한 벽입니다. 이 문제가 골드 1을 받은 이유는 visited에 뭘 넣어야 효과적인지 알기 힘들어서입니다.
            # 저같은경우 비스마스킹으로 현재 몇번째 걸음인지, 현재 낮인지 밤인지 체크를 하였습니다.
            # 쉽게 풀어 말하자면, (1,0)좌표를 처음에 낮에 (0,0)에서 걸어서 오나, (2,0)에서 걸어서 오나 결국 갈 수 있는곳은 같죠.
            # 하지만 (0,0)에서 직접 오는거랑, (3,0)에서 두 번 걸어서 오는거랑은 갈 수 있는 거리의 차이가 있습니다. 밤에는 기동성이 더 높아지니까요.
            # 결국, 현재 몇 번 걸어서 왔냐도 체크를 제대로 해야합니다.
            
            d.append([nx, ny, next_t, next_m]) # 방문하지 않았던데라면 deque에 집어넣고요.
            visited[ny][nx].add(1<<(next_t%2+10)|1<<next_m) # 방문체크를 해줍니다.
        else: # 밤이면요,
            if not M[ny][nx] and not ((1<<(next_t%2+10))|(1<<next_m) in visited[ny][nx]):
                # 만약에 가려는곳에 벽이 없다면, 문제될거 없죠. 그냥 낮처럼 해줍니다.
                visited[ny][nx].add(1<<(next_t%2+10)|1<<next_m)
                d.append([nx, ny, next_t, next_m])
            elif M[ny][nx]:
                # 만약 벽이 있으면요.
                if dx: # dx, dy인 경우를 나눠야합니다. 쉽게말해, x방향으로 이동중인지, y방향으로 이동중인지이죠.
                    k = lowerbound_x(x, y) # x좌표에 대한 lowerbound를 해줍니다. 내가 가려는 방향중 제일 가까운 빈 x좌표를 찾는겁니다.
                    if (dx == -1 and k == 0) or (dx == 1 and k == len(void_idx_x[y])-1): continue
                    # 만약에 -x방향으로 가는데 k가 0이거나, +x로 가는데 k가 최댓값이면 맵 밖으로 나가게되죠. 이 때는 못간다고 문제에 있습니다.
                    elif dx == -1: k = void_idx_x[y][k-1] # 아니라면 이제 x좌표를 저장해줍시다. -x면 k에서 1을 빼서 넣도록합시다. k는 인덱스니까요.
                    else: k = void_idx_x[y][k+1] # 여기도요. 이 때는 +x방향일때입니다.
                    if not ((1<<(next_t%2+10))|(1<<next_m) in visited[ny][k]): # 이제 방문체크를 해줍니다.
                        visited[ny][k].add(1<<(next_t%2+10)|1<<next_m) # 안왔었다면 방문체크를 해주고,
                        d.append([k, ny, next_t, next_m]) # d에 집어넣습니다.
                elif dy: # 위 설명에서 x를 y로 전부 바꾸면 동일합니다.
                    k = lowerbound_y(x, y)
                    if (dy == -1 and k == 0) or (dy == 1 and k == len(void_idx_y[x])-1): continue
                    elif dy == -1: k = void_idx_y[x][k-1]
                    else: k = void_idx_y[x][k+1]
                    if not ((1<<(next_t%2+10))|(1<<next_m) in visited[k][nx]):
                        visited[k][nx].add(1<<(next_t%2+10)|1<<next_m)
                        d.append([nx, k, next_t, next_m])

"""
n은 미로의 크기이고, m은 몇 번 움직여야 밤낮이 바뀌느냐? 입니다.
n이 세로, m이 가로 아닙니다.
M은 맵이구요, visited는 평범한 visited입니다.
(M을 tuple로받으면 시간초과가 초반에 나고, list로 받아야 초과가 안나더라고요. 이유는 모르겠습니다.)
그런데 set형입니다. 1 2 3 이렇게 할 수 없겠더라고요.
set형 말고, 리스트로 받아도 가능은 할겁니..다? 아닌가?
"""
n, m = map(int, input().split())
M = list(list(map(int, input().split())) for _ in ' '*n)
visited = [[set() for _ in ' '*n] for _ in ' '*n]

"""
저는 빈곳을 하나하나 선형탐색으로 찾아가면 시간초과가 날거라고 우려한 나머지,
빈곳에 대한 좌표를 미리 다 적어두기로 했습니다. 위에 lowerbound에서 쓰이는 배열이죠.
"""
void_idx_x = [[] for _ in ' '*n]
void_idx_y = [[] for _ in ' '*n]
for y, i in enumerate(M):
    for x, j in enumerate(i):
        if not j:
            void_idx_x[y].append(x)
            void_idx_y[x].append(y)

"""
저는 개인적으로 >모양 코딩을 좋아하지 않습니다.
그러니까, 탭이 가운데만 기형적으로 많은걸 좋아하지 않는다고 하면 될것같네요.
또, 들여쓰기가 많은걸 지양하고, 가능한 들여쓰기가 적은 코드를 지향합니다. 이 문제는 그러지 못했지만..
여하튼 그래서 bfs의 while부분은 밖에 빼어두는 편입니다.

bfs를 보셨다면 알겠지만, '한곳에 일부러 가만히 있는다'라는 개념이 없습니다.
즉, t는 반드시 어쨌든 순서대로 나올거고, 그중 가장 빨리 도착점에 도달한게 최소의 t겠죠.
도착점에 도달함을 감지하였다면, 바로 while문을 탈출합니다.
"""
d = deque([[0, 0, 0, 0]])
while d:
    x, y, t, walk = d.popleft()
    if x == n-1 and y == n-1:
        print(1+t//2, ('sun','moon')[t%2])
        break
    bfs(x, y, t, walk)
else: print(-1)

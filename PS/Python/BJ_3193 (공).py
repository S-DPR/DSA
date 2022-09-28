import sys
input = sys.stdin.readline

"""
3193번 공

랜덤문제 돌리면 가끔 이렇게 맞힌 사람이 적은 문제도 보게됩니다.
그런걸 보면 재밌어지죠. 그래서 풀어봤습니다.. 는 장난이고,
그냥 문제 풀만해보여서 풀었습니다..

일단 난이도 책정이 G5로 되어있는데, 이거는 맞힌 사람이 적어서 거의 무의미한 난이도입니다.
제 체감 난이도는 골드 3정도입니다.

문제를 봅시다. 어릴때 갖고놀던 구슬장난감이 있고 어느방향으로 돌렸는지 입력을 줄테니 구슬의 마지막 위치를 구해라..
마치 구슬 탈출(13459) 문제와 같아보입니다.
사실 초반만 보면 같죠, 한번 움직인 구슬은 장애물이 나오기 전까지 움직인다.
다른점은, 구슬 탈출은 대충 특정 지점을 들어갈 수 있냐, 를 묻고,
이거는 마지막에 구슬이 어디있냐, 를 묻습니다. 적어도 그래프류는 아니네요.

그리고 배열회전 할 수 있는지도 물어봅니다. 뭐 함수 짜주면 되겠죠.
"""

# 배열회전입니다. 사실 잘 생각해보면 시간복잡도가 얘 혼자 O(N^2)이라..
# 매번 배열회전하면서 아래로 당기면 당연히 망하겠죠.
def rotate(arr):
    size = len(arr)
    res = [[0]*size for _ in ' '*size]
    for i in range(size):
        for j in range(size):
            res[j][size-1-i] = arr[i][j]
    return res

# 이분탐색입니다.
def lowerbound(arr, x):
    l = 0; r = len(arr)-1
    while l < r:
        m = l + r >> 1
        if arr[m] >= x: r = m
        else: l = m + 1
    return r

n, m = map(int, input().split())
M = list(list(input().rstrip()) for _ in ' '*n)
now_rotate = 0 # 0은 원래방향, 1은 왼쪽으로 한번, 2는 두번, 3은 세번.
Xy = [[] for _ in ' '*n] # (i, y) (0 <= i < n)에 장애물이 있는지 위치를 체크.
Xx = [[] for _ in ' '*n] # 이건 x좌표가 기준이겠죠.
for i in range(n): # 일단, 벽에 부딪히는 경우를 위해 -1을 넣어줍시다.
    Xy[i].append(-1)
    Xx[i].append(-1)
for iidx, i in enumerate(M): # 그리고 맵을 쭉 훑어줍시다. L 위치 구하고,
    for jidx, j in enumerate(i):
        if j == 'L':
            now_pos = [jidx, iidx]
            M[iidx][jidx] = '.'
        if j == 'X': # X위치는 Xx와 Xy에 저장해줍니다.
            Xx[iidx].append(jidx)
            Xy[jidx].append(iidx)
            # 이러면 모든 배열은 정렬되어 이분탐색 가능 조건을 충족시킵니다.
for i in range(n): # 벽에 부딪히는 경우를 위해 n을 넣어줍시다.
    Xy[i].append(n)
    Xx[i].append(n)

for _ in ' '*m:
    dx, dy = 0, 0
    """
    회전 한번에 O(N^2)이라고 했습니다.
    그럼 결국 우리는 시뮬레이션을 머리로 돌려야합니다.
    저는 '왼쪽으로 한번 돌리면 x좌표는 실질적으로 어떻게 움직일까.
    두번 돌리면? 세번 돌리면? 원래 방향에서 볼 때 어느방향으로 흐르지?'
    이걸 계속 생각한결과, 아래와 같은 결과를 얻었습니다.
    """
    if input().rstrip() == 'L': # match쓰다가 컴파일오류냈습니다..
        if now_rotate == 0: dx = -1 # pypy는 아직 3.9버전이더라구요.
        elif now_rotate == 1: dy = 1
        elif now_rotate == 2: dx = 1
        else: dy = -1
        now_rotate -= 1 # 시계반대방향으로 돌렸습니다.
        # 시계반대방향이면,
        # 안돌린걸 돌리면 원래방향에서 볼 때 왼쪽,
        # 한번 왼쪽으로 돌아간걸 다시 오른쪽으로 돌리면 위로,
        # 두 번 왼쪽으로 돌아간걸 오른쪽으로 한 번 돌리면 아래로,
        # 세 번 왼쪽으로 돌아간걸 오른쪽으로 한 번 돌리면 오른쪽으로.
        # 이렇게 간다는걸 머리속에서 시뮬레이션 돌려서 얻어냈습니다.
    else:
        if now_rotate == 0: dx = 1
        elif now_rotate == 1: dy = -1
        elif now_rotate == 2: dx = -1
        else: dy = 1
        # 시계방향이면 이렇습니다.
        now_rotate += 1 # 시계방향으로 돌렸습니다.
    now_rotate %= 4 # 파이썬은 음수도 모듈러연산을 할 수 있습니다. -1%4 = 3입니다.
    nx, ny = now_pos # 현재 공의 좌표를 불러오고요.

    """
    맨 위에꺼만 설명하자면,
    dx가 1이란 뜻은, 오른쪽으로 굴러간다는건데,
    그러면 x가 n-1에 있다면 무의미합니다. 그러니 nx < n-1을 달아주고요.
    [X좌표를 기준으로] [해당 Y좌표에 있는 장애물을 모아둔 배열에서]
    [자신과 가장 가까운, 앞에 있는 장애물의 위치를 찾고] [그 앞으로 바로 이동한다.]
    입니다.
    while써서 한칸씩 가니 시간초과 나더라구요.
    """
    if dx == 1 and nx < n-1: nx = Xx[ny][lowerbound(Xx[ny], nx+1)]-1

    # 이경우는 자기 위치를 찾고 인덱스에서 1을 빼면 자기 바로 뒤에있는 장애물의 위치가 나오겠죠.
    elif dx == -1 and nx > 0: nx = Xx[ny][lowerbound(Xx[ny], nx)-1]+1
    elif dy == 1 and ny < n-1: ny = Xy[nx][lowerbound(Xy[nx], ny+1)]-1
    elif dy == -1 and ny > 0: ny = Xy[nx][lowerbound(Xy[nx], ny)-1]+1
    now_pos = [nx, ny]
# 다 돌았다면,
M[now_pos[1]][now_pos[0]] = 'L' # L을 복구하고
for _ in ' '*now_rotate: # 회전을 왼쪽으로 돈 만큼 돌립니다.
    M = rotate(M) # 180도회전, 270도회전도 한번에 할 수 있지만, 0.1초밖에 차이 안나더라구요.
# 방심하면 안되는게 3번 더 돌렸더니 0.1초인겁니다. 50만번 돌리는데 그거 다 돌리면서 구슬 위치 찾으면 시간초과맞습니다.

# 답을 출력합니다.
for i in M:
    for j in i: print(j, end='')
    print()

import sys
input = sys.stdin.readline

"""
백준 12094번 2048 (Hard)
2048 게임을 일부 기능(블럭생성)을 제외한 채로 구현하는 간단한 문제입니다.
실제로 태그에도 완전탐색이 있는만큼 정말 간단하지만, 그냥 구현 및 최적화가 어려운 문제입니다.

난이도가 높게 책정된 완전탐색은 보통 한가지 특징을 달고나오는데,
쓸데없이 재귀를 달고나와서 디버깅을 정말 힘들게 만듭니다.
디버깅이 아니더라도 이상한 자료구조 달고와서 구현 자체가 힘들다거나.
어쨌든, Python Idle로 백준 푸는 저한텐 정말 치명적인 문제입니다.
통합개발환경 있기는한데 그거 쓴다고 재귀가 잡히긴 할지도 잘 모르겠고..

별개로, 2048 (Easy) 문제는 재귀만 잘 쓰면 쉬우니, 한번쯤 해보실만 할거라 생각합니다.
"""

"""
저는 재귀로 풀겁니다. 솔직히 재귀가 아니라 반복문만으로 가능한지도 잘 모르겠습니다.
한 부분이 네 번 반복되기에 그 부분을 함수로 만들어 사용하려 했으나,
실패했습니다. 뭐 이거 엄청 교묘하게 바뀌어서그런가, 그냥 하다가 포기했습니다.

저는 재귀를 move함수로 정의할겁니다.
받는건 arr, M, cnt입니다. arr은 현재 게임 판 상태, M은 현재 게임 판 상태의 최댓값, cnt는 반복횟수입니다.
"""
def move(arr, M, cnt = 0):
    global MaxValue # MaxValue는 정답이 되는 값입니다. 이 값은 함수 실행동안 계속 변경됩니다.
    if cnt >= 10 or (MaxValue >= (M * (1 << (10-cnt)))):
        return max(M, MaxValue)
    """
    풀다보면 시간초과의 지옥에 갇히는 경험을 해볼 수 있습니다. 그걸 어떻게든 탈출해야겠죠.
    이 문제에서 가장 중요한 '최적화'의 키가 되는 부분입니다.
    10번 반복하였거나, 현재 정답이 되는 값에 도달할 수 없는 경우 max(M, MaxValue)를 반환후 종료합니다.
    현재 정답에 도달할 수 없는 경우를 생각해봅시다.

    현재 정답이 1024라고 합시다. 9번 반복째에 512를 최댓값으로 갖고있는 경우를 굳이 더 돌려봐야할까요?
    현재 정답이 1024인데, 8번 반복째에 32를 최댓값으로 갖고있는 경우를 굳이 더 돌려봐야할까요?
    아니요, 필요 없습니다. 어차피 저기서 최댓값이 더 나와봐야 1024가 정답인건 변함이 없습니다.
    이 부분을 끊어야합니다. 그래서 나온 결론이 저 or 뒤에 있는 식입니다.
    파이썬이라  1 << 를 2**로 바꾸어도 됩니다. 속도차이 없더라고요.
    """
    cache = M # M값은 계속 바뀔겁니다. cache에 이 arr의 M값을 저장해둡시다.

    # 처음 당기는 방향은, 블럭들을 위로 들어올리는 방향입니다.
    C = [i[:] for i in arr]
    # copy.deepcopy쓰면 큰일납니다! copy.deepcopy에서 6.7초 걸리던게 이렇게 바꾸니 1.3초로 바뀌었습니다.
    no_move = True
    # 한 방향으로 당겼지만 움직이지 않았다면 굳이 그걸 재귀에 넣을 이유가 없겠죠. 세번째 중단조건입니다.
    for x in range(n):
        add = 0
        # add는 움직이는 블럭의 목표점이 되는 y(혹은, x)좌표입니다.
        # 현 상황에서는 y좌표가 되겠죠. C[add][x]가 어떻게 사용되나 지켜보세요.
        for y in range(1, n):
            # 현재 값이 빈 값(0)이라면 굳이 블럭을 옮기는 시도를 하지 않습니다.
            if not C[y][x]: continue
            """
            먼저 C[add][x]가 현재 움직이려는 값과 같은 경우입니다.
            2048에서는 합쳐지죠. 합칩시다.        
            """
            if C[y][x] == C[add][x]:
                C[add][x] *= 2 # 합치고요,
                M = max(C[add][x], M) # 최댓값 갱신을 시도해봐야합니다.
                C[y][x] = 0 # 움직인건 없애주고,
                add += 1 # add에 1을 더합니다. 그럼 다음 블럭은 이 아래에 쌓이겠죠.
                no_move = False # 그리고 이 경우는 블럭에 변화가 생겼으니 no_move는 꺼야죠.
            elif C[add][x] == 0: # C[add][x]가 빈 경우입니다. 이 경우는 그냥 움직인 블럭을 집어넣으면 되겠죠.
                C[add][x] = C[y][x] # 집어넣고,
                C[y][x] = 0 # 원래 자리엔 0을 넣고,
                no_move = False # 변화가 생겼으니 no_move를 꺼줍니다.
                # 이 경우만 유일하게 add += 1이 없는데, 다음에 밀리는 블럭은 이 블럭과 합쳐질 수 있죠.
                # 그래서 add += 1을 넣지 않은겁니다.
            elif add+1 != y: # C[add][x]가 비어있지도 않고, 그냥 움직이는 블럭과 다른경우입니다.
                add += 1 # 이런 경우는 C[add][x]에 넣으면 큰일납니다. add에 1을 미리 더하고,
                C[add][x] = C[y][x] # 그 위치에 이걸 넣읍시다.
                C[y][x] = 0 # 물론, 움직인 블럭은 없애줘야합니다.
                no_move = False # 변화가 생겼으니 꺼줍니다.
            else:
                add += 1 # 움직이려고 해도, 해당 위치에 이미 자기 자신이 있을수도 있죠. 이경우는 add += 1만 해줍니다.
                # 움직이지 않았으니 변화도 없습니다. 이것으로는 no_move가 꺼지지 않습니다.
    if not no_move: MaxValue = max(move(C, M, cnt+1), MaxValue)
    # no_move가 false라면, 재귀를 태워줍니다.

    #이하 내용은 위 내용에서, 방향만 아래, 왼쪽, 오른쪽으로 바꾼겁니다.
    C = [i[:] for i in arr]
    no_move = True
    M = cache
    for x in range(n):
        add = n-1
        for y in range(n-2, -1, -1):
            if not C[y][x]: continue
            if C[y][x] == C[add][x]:
                C[add][x] *= 2
                M = max(C[add][x], M)
                C[y][x] = 0
                add-=1
                no_move = False
            elif C[add][x] == 0:
                C[add][x] = C[y][x]
                C[y][x] = 0
                no_move = False
            elif add-1 != y:
                add -= 1
                C[add][x] = C[y][x]
                C[y][x] = 0
                no_move = False
            else:
                add -= 1
    if not no_move: MaxValue = max(move(C, M, cnt+1), MaxValue)

    C = [i[:] for i in arr]
    no_move = True
    M = cache
    for y in range(n):
        add = 0
        for x in range(1, n):
            if not C[y][x]: continue
            if C[y][x] == C[y][add]:
                C[y][add] *= 2
                M = max(C[y][add], M)
                C[y][x] = 0
                add += 1
                no_move = False
            elif C[y][add] == 0:
                C[y][add] = C[y][x]
                C[y][x] = 0
                no_move = False
            elif add+1 != x:
                add += 1
                C[y][add] = C[y][x]
                C[y][x] = 0
                no_move = False
            else:
                add += 1
    if not no_move: MaxValue = max(move(C, M, cnt+1), MaxValue)

    C = [i[:] for i in arr]
    no_move = True
    M = cache
    for y in range(n):
        add = n-1
        for x in range(n-2, -1, -1):
            if not C[y][x]: continue
            if C[y][x] == C[y][add]:
                C[y][add] *= 2
                M = max(C[y][add], M)
                C[y][x] = 0
                add-=1
                no_move = False
            elif C[y][add] == 0:
                C[y][add] = C[y][x]
                C[y][x] = 0
                no_move = False
            elif add-1 != x:
                add -= 1
                C[y][add] = C[y][x]
                C[y][x] = 0
                no_move = False
            else:
                add -= 1
    if not no_move: MaxValue = max(move(C, M, cnt+1), MaxValue)
    return MaxValue # 돌려주는건 MaxValue입니다. 어차피 큰 의미는 없는 반환값이거든요.

n = int(input())
arr = [list(map(int, input().split())) for _ in ' '*n]
MaxValue = 0
for i in arr:
    MaxValue = max(MaxValue, max(i))
move(arr, MaxValue) # M값에는 현재 arr에 있는 최댓값을 넣고 재귀를 시작합니다.
print(MaxValue) # 정답을 출력합니다.

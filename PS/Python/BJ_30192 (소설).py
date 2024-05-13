import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
30192번 소설

소문자로 이루어진 문자열이 주어진다.
이 문자열을 길이 L의 문자열 여러개로 자르려고 한다.
동일한 문자가 K번 이상 연속으로 나오지 않게 할 때,
최대 L을 구해보자.

와..
이게 브루트포스라고?
와....
개쩐다..
넌 플레 가라..

에디토리얼 보고 눈물의 감탄을 흘린 문제.
아니, 감탄의 눈물인가? 뭐 어때요.

우선 문자열 중 K번 이상 연속으로 나오는 경우를 모두 잡아봅시다.
그 구간을 [S, E]라고 할 때, S//L과 E//L이 같다면 같은구간입니다!
당연히 K번 이상 연속이니 저게 같으면 안되겠죠. 바로 break 쳐줍시다.

S//L+1 == E//L이라면 구간이 두 개로 쪼개진겁니다.
이 경우가 아니라면, L 자체 길이가 K 이상이 되면 안되겠죠.
그 외, 끝부분 S와 E 구간은 따로 처리해줍시다.

위 경우를 1부터 N까지 모두 보는게 놀랍게도 O(NlogN)만에 돈다고 합니다.
사실 저는 중간에 가망없다 싶으면 바로 break쳐버려서 통과되긴했는데,
다른사람 코드 참고 좀 해봐야 할 것 같습니다.
에디토리얼대로 구현이 제대로 안 된 것 같아요.
"""
N, K = ins()
S = input()
prv, segment = '', []
for i in range(N):
    if prv != S[i]:
        segment.append([i, i])
    else:
        s, e = segment.pop()
        segment.append([s, i])
    prv = S[i]
segment = [*filter(lambda x: x[1]-x[0]+1 >= K, segment)]
for L in range(N, 0, -1):
    able = 1
    for s, e in segment:
        if not able: break
        able = able and s//L != e//L
        if s//L == e//L: continue
        s_cnt = (s//L+1)*L-s
        e_cnt = e-e//L*L+1
        able = able and s_cnt < K and e_cnt < K
        if s//L+1 == e//L: continue
        able = able and L < K
    if not able: continue
    print(L)
    break

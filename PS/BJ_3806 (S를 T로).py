import sys
input = sys.stdin.readline

"""
3806번 S를 T로
길이가 같은 두 문자열이 두개 주어집니다.
첫 문자열 S은 0, 1, ?로 이루어져있고, 두번째 문자열 T는 0, 1로 이루어져있습니다.
첫 문자열 S에 대해 아래 세 연산을 할 수 있습니다.
1. 0을 1로
2. ?를 0 혹은 1로
3. S 내에서 두 char 잡아서 스왑
이 때, 최소한의 연산을 이용해 S를 T로 바꾸려합니다. 그 횟수를 구해보세요.
(S와 T의 길이는 최대 100입니다.)
"""

"""
알고리즘 분류부터 생각해봅시다. 완전탐색을 해볼까요?
최악의경우 ?가 100개 나올겁니다. 그러면 벌써 2의 100승이네요. 탈락입니다.

백트래킹? 잘쳐서 1/(10^10)으로 줄인다해도 경우의수가 너무 많습니다.

DP도 고려해볼 수 있는데, 제가 딴건 몰라도 DP는 지금은 포기해서 모르겠습니다.
분류를 아는 순간 쉬워지는 문제가 되니 천천히 생각해보세요..
"""



""" 
정답은 그리디입니다. 그때그때 최선의 방안으로 가자는거죠.
잘 생각해보면, 1 -> 0은 안되지만 ?|0 -> 1은 됩니다.
이 말은 곧, T의 인덱스를 조사해 1의 위치를 얻어두고, 그 위치에 ?가 있으면 모두 1로 바꾸자는겁니다.
그러다가 S에 있는 1의 개수가 T에 있는 1의 개수만큼 되면, 나머지는 모두 0으로 바꾸면 되겠죠.
이후, 0을 필요한 만큼 바꾸어주면 T가 될겁니다.

이 때 놓치지 말아야할점이, 불가능한 경우가 있다는겁니다.
1은 0으로 돌아갈 수 없다는 점에서, S에 있는 1의 개수가 T에 있는 1의 개수보다 많다면 불가능하겠죠.
예외처리까지 확실하게 해줍시다.
"""
for tc in range(1, int(input())+1): # 문제에서 테스트케이스의 번호에 맞춰 출력하라고 요구하고있습니다. tc로 적어줍시다.
    s = input().rstrip()
    e = input().rstrip()
    s_status = [set(), set(), set()] # S는 0, 1, ?의 상태를 갖고요,
    e_status = [set(), set()] # T는 0, 1의 상태를 갖습니다.
    for idx, i in enumerate(s):
        match i: # switch-case문입니다. 파이썬 3.10부터 생겼습니다.
            case '0': s_status[0].add(idx)
            case '1': s_status[1].add(idx)
            case '?': s_status[2].add(idx)
    for idx, i in enumerate(e):
        match i:
            case '0': e_status[0].add(idx)
            case '1': e_status[1].add(idx)
    if len(s_status[1]) > len(e_status[1]): # 만약 S에있는 1이 더 많다면 그냥 -1 출력하고 끝냅시다.
        print(f'Case {tc}: -1')
    else:
        res = 0
        for i in e_status[1]: # T에 있는 1들이,
            if len(s_status[1]) == len(e_status[1]): break # 과유불급.
            if i in s_status[2]: # S에 있는 ?들의 인덱스와 같은 경우,
                s_status[2].remove(i) # ?를
                s_status[1].add(i) # 1로 바꾸어줍시다.
                res += 1 # 연산을 한번 적용했습니다.
        res += len(s_status[2]) # 남은 ?는 모두 0으로 바꾸어주는 연산을 하고요,
        for i in e_status[1]:
            if i not in s_status[1]: # 0에 대해서, 1로 바꾸어줄거는 모두 바꾸어준 뒤
                res += 1
        print(f'Case {tc}: {res}') # 출력해줍시다.

import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
1511번 숫자 만들기

0부터 9까지 각각 사용한 개수가 주어진다.
수를 인접하지 않게 배치하여, 가장 큰 수를 만들어보자.

아..
그리디 진짜 어렵네
어제부터 왜그러지

결국 답지를 봤는데.. 크게 두 단계인 것 같습니다.
첫번째로 어차피 못쓰는애들 버리기.
작은순을 버리는게 아니라, 가장 많은 수만 적당히 갖다 버리면 됩니다.
0일때를 주의하고요.

두번째로, 반드시 선택해야하는건 바로 선택하면서 처리.

저는 그냥 뭐 1단계도 생각 못하고 혼자 삽질좀 했네요.
앞으로 이런 그리디는 좀 생각을 더 해봐야할것같아요..
플레그리디가 진짜 쉬운건 너무 쉬운데 어려운건 너무 어렵다
이것도 못풀뻔했네..
"""
A = ins()
N = sum(A)
ret = [-1]
mx = max(range(10), key=lambda x: A[x])
if A[mx] > (N+(mx!=0))//2:
    A[mx] = N-A[mx]+(mx!=0)
N = sum(A)
for _ in ' '*N:
    select = max(range(10), key=lambda x: (A[x] != 0, ret[-1] != x, x))
    mx = max(range(10), key=lambda x: A[x])
    if A[mx]*2 > N:
        select = mx
    N -= 1
    A[select] -= 1
    ret.append(select)
print(*ret[1:] if ret[1:] else [0], sep='')

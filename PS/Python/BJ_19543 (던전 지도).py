import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
19543번 던전 지도

(R, C) 크기의 맵이 있다. 그리고 수 K가 주어지는데, 이는 각 행의 종류이다.
다시말해 모든 행은 최대 K개의 문자열로 표현할 수 있다는 의미이다.
모든 문자는 R, U로 이루어져 있으며 R인경우 해당 칸에서 오른쪽으로,
U인경우 해당 칸에서 우쪽으로 이동할 것이라는 뜻이다.
각 행이 어떤 종류로 이루어져있는지 문자열이 주어진다.
좌측 하단에서 우측 상단으로 이동할 수 있는 칸의 개수를 구해보자.

와
와..
진짜 이거 dp인가 아니 아닌데 너무 큰데 이러면서 막 생각했는데
너무 모르겠어서 태그 딱 까니까 두포인터??
하아?

그래서 답지를 보니.. 답지가 이해가 안돼서 한 30분 보니..
각 칸에 대해 구간을 만들 수 있습니다. [lo, hi]는 목표로 갈 수 있는 칸의 구간.
아..
저거 이해하는순간 와 두포인터 너무 신박하다 진짜.. 이생각만 들었습니다.
최근들어 생각하는데 두포인터 문제가 없기도 없는데 너무 똑똑한 문제같아요
두포인터인걸 알기도 힘들고 알아도 너무 어렵네요..
전에 상남자 곽철용인가? 그거도 두포인터인거보고 어이없었는데..
"""
R, C, K = ins()
A = [input() for _ in ' '*K]
L = [*map(lambda x: ord(x)-ord('A'), input())]
lo, hi = C-1, C-1
while 0 <= lo-1 and A[L[-1]][lo-1] == 'R':
    lo -= 1
ret = hi-lo+1
for i in L[:-1][::-1]:
    while 0 <= hi and A[i][hi] != 'U':
        hi -= 1
    if hi < lo: break
    lo = min(lo, hi)
    while 0 <= lo-1 and A[i][lo-1] == 'R':
        lo -= 1
    ret += hi-lo+1
print(ret)

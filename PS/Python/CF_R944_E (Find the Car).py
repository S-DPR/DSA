import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
Round 944 (Div 4) E : Find the Car

한 자동차가 어떤 정수구간마다 몇 초에 이 구간을 지났는지에 대한 정보가 주어진다.
이 차가 Q점을 지날 때 몇초였을까? 이를 내림하여 구해보자.

하
대회때 부동소수점 테러맞고 멸망한문제
다시보니까 가슴 찢어진다..

풀고나서 여기저기 보니 '소숫점 연산 필요없었다' 라고 하더라고요.
하? 어떻게? 이생각만하다가 업솔빙했습니다.
업솔빙이라기도 참 머한게 튜토리얼도 걍 안봐서..

어떤 구간 q를 구한다고 할 때, 일단 이분탐색을 해줍니다.
그리고 거리=속력*시간이라는 아주 예로부터 내려오는 공식을 쓸건데..

이게 지금 q보다 큰 인덱스를 잡으면 A[idx]는 항상 q보다 크거든요?
그러니까 이제 A[idx]-A[idx-1]로 q가 포함된 구간의 거리를 구할 수 있어요.
당연히 그 시간도 B[idx]-B[idx-1]로 구해서,
속력 = (A[idx]-A[idx-1])/(B[idx]-B[idx-1])
이 됩니다.

그럼 거리는? q-A[idx-1]만큼 움직여야합니다. 이게 거리.
시간은? 이제 구해야죠.
거리와 속력이 있으니 구할 수 있습니다.
속력의 분모를 좌변으로 옮겨줍니다.
그리고 이제 A[idx]-A[idx-1]로 나눈 몫을 취하면 내림한 시간이 됩니다.
와..
"""
from bisect import bisect_left as bl
for _ in ' '*ini():
    N, M, Q = ins()
    A = [0] + ins()
    B = [0] + ins()
    for _ in ' '*Q:
        q = ini()
        if q == 0:
            print(0, end = " ")
            continue
        if q == N:
            print(B[-1], end = " ")
            continue
        idx = bl(A, q+1)
        dist = A[idx]-A[idx-1]
        qdist = q-A[idx-1]
        print(B[idx-1]+(qdist*(B[idx]-B[idx-1]))//dist, end= " ")
    print()

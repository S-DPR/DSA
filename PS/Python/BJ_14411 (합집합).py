import sys
from collections import defaultdict
input = sys.stdin.readline
"""
14411번 합집합

원점을 중심으로 하는 직사각형의 가로세로 길이가 주어집니다.
직사각형의 범위를 좌표계 상에 같은 색으로 칠할때 칠해진 넓이를 구해주세요.
직사각형의 개수 N을 제외한 모든 입력은 짝수임이 보장됩니다.

언뜻봐서는 까다로운 문제지만..
오늘 다시보니 쉽더라고요. 거의 보자마자 풀었네요.

먼저 생각해야할건 원점에 대해 대칭이라는 점입니다.
모든 x, y에 대해 생각할 필요 없이 양수 x, y만 생각하면 되므로,
입력받은 수를 2로 나누어 저장합시다.
마지막에 구한 답의 4배를 출력하면 되니까요.

다음으로 생각해야할건 (x1, y1), (x2, y2)에 대해 x1 < x2이고 y1 < y2이면
(x1, y1)은 쓸모가 없다는 점입니다.
그러므로 그런 점은 다 폐기시켜야하고, 여기서 후입선출 Stack이 나오게됩니다.
어쩌면 그저 오큰수 문제의 아종일지도 모르겠네요.
"""
arr = defaultdict(int)
for _ in ' '*int(input()):
    x, y = map(int, input().split())
    arr[x//2] = max(arr[x//2], y//2)
arr = sorted(arr.items())
stack = []
for x, y in arr:
    while stack and stack[-1][1] <= y:
        stack.pop()
    stack.append([x, y])
area = prev_x = 0
for x, y in stack:
    area += (x-prev_x)*y
    prev_x = x
print(area*4)

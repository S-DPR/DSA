import sys
input = lambda: sys.stdin.readline().strip("\n")
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
1701번 Cubeditor

문자열이 주어진다. 부분연속문자열 중, 두 번 이상 나오는 부분연속문자열의 최대길이를 구해보자.

뭐? 골드2인데 누가봐도 KMP나 라빈카프문제라고?
어림도없지 이거 무조건 KMP 라빈카프 아니다 선언

방법은 제일 편하게 떠오른 deque+매개변수.
시간제한 0.5에 문자열 길이 5000이니..
N^2logN정도는 금방 통과할거라는 믿음.

매개변수가 되는 이유는, 길이가 x인 부분연속문자열이 두 번 나오면 무조건 x-1, ..., 1인 문자열도 되기 때문입니다.
이거때문에 조금 망설였는데 생각 좀 해보니 당연한 사실이더라고요.
암튼 금방풀었넹
"""
from collections import defaultdict, deque
S = [*map(lambda x: ord(x)-ord('a'), input())]
lo, hi = 0, len(S)
while lo < hi:
    mid = (lo + hi) >> 1
    s = defaultdict(int)
    c = deque()
    for idx, i in enumerate(S):
        c.append(i)
        if idx >= mid:
            s[tuple(c)] += 1
            c.popleft()
    if max(s.values()) <= 1:
        hi = mid
    else:
        lo = mid + 1
print(hi)

import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
20210번 파일 탐색기

자연정렬을 구현해보자. 자연정렬은 아래와 같이 구현한다.
1. 연속하는 문자열과 연속하는 숫자로 문자열을 구분한다.
2. 숫자는 문자열보다 우선되며, 더 작은 수가 더 앞에 온다.
2-1. 앞에 0의 개수가 적을수록 더 앞에 온다.
3. 문자는 aAbB...zZ 순으로 더 앞에 온다.

cmp_to_key라는게 있구나, 쓸만하네.
어떻게 잘 하면 그냥 sort로 될거같은데 도저히 전 안되겠더라고요.
그래서 gpt 뒤져서 저거 찾아서 썼고..
그리고 문자열이랑 숫자 구분은 정규표현식. 아무래도 이게 좋더라고요.
네.. 그렇게풀었습니다..
"""
from functools import cmp_to_key
import re

def sort_by(i, j):
    b = lambda x: [1, -1][x]
    lni, lnj = len(i), len(j)
    for idx in range(min(lni, lnj)):
        if i[idx].isdigit()^j[idx].isdigit(): # 둘중 하나 숫자 아님
            return b(i[idx].isdigit())
        if i[idx].isdigit(): # 둘다 숫자
            lnii, lnji = len(i[idx]), len(j[idx])
            x, y = int(i[idx]), int(j[idx])
            if x != y: return b(x < y) # 둘이 다르면 무조건 작은쪽
            if lnii != lnji: # 앞에 0 개수 차이남
                return b(lnii < lnji)
            continue # 완전히같음
        # 둘다 문자
        x, y = i[idx], j[idx]
        if x != y: return b(sz[x] < sz[y])
    return b(lni < lnj)

sz = {}
pattern = re.compile(r'\d+|[a-zA-Z]')
for i in range(26):
    a = chr(ord('a')+i)
    A = chr(ord('A')+i)
    sz[A] = len(sz)
    sz[a] = len(sz)
N = ini()
items = []
for _ in ' '*N:
    S = input()
    items.append(pattern.findall(S))
items.sort(key = cmp_to_key(sort_by))
for i in items:
    print(''.join(i))

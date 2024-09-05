import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
13989번 Abbreviation

문자열이 주어진다. 다음 조건을 만족하는 단어들을 축약하여 다시 나타내보자.
 - 단어는 ,. 공백으로 구분된다.
 - 축약 가능 단어는 앞글자가 대문자이고, 다른 모든 문자가 소문자인 단어이다.
 - 축약 가능 단어 두 개 이상이 연속으로 있어야 축약 할 수 있다.

정규표현식 구아악 하면서 배워보려고 한 문제.
정규표현 너무 어려워요..
배워두면 개발자들 사이에서 재미로 뽐내는 용도로는 써볼만할듯합니다.
"""
import re
c = re.compile(r"(?:[,. ]|^)([A-Z][a-z]+(?: [A-Z][a-z]+)+)(?=[,. ]|$)")
while i := input():
    finds = re.findall(c, i)
    ret = i
    for i in sorted(set(finds), key=len):
        k = i
        if i[0] in '., ': k = k[1:]
        if i[-1] in '., ': k = k[:-1]
        x = ''.join(j[0] for j in k.split())
        ret = ret.replace(i, f"{i[0] if i[0] in ',. ' else ''}{x} ({k}){i[-1] if i[-1] in ',. ' else ''}")
    print(ret)
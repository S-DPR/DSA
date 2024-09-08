import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
3650번 암호 해독

길이가 같은 문자열 S, T가 주어진다.
S를 길이 k로 자른다고 하자. 각각을 블럭이라고 하자 (k는 항상 S 길이의 약수이다.)
이제 모든 블럭을 같은 순서로 재배열한다고 할 때,
S를 T로 만들 수 있을까? 있다면 가능한 재배열의 경우는 몇 개일까?

이야
이게 왜 조합론이지? 생각 정말 많이 했는데
진짜 단순하게 어렵네요.
정답코드 보고도 분석해서 풀었..
"""
from collections import defaultdict
from math import factorial
from functools import cache
@cache
def facto(n): return [0, factorial(n)][n > 0]
while K := input():
    K = int(K)
    S = input()
    T = input()
    N = len(S)
    pos = defaultdict(set)
    for i in range(K):
        for j in range(K):
            if S[i] == T[j]:
                pos[i].add(j)
    for i in range(K, N, K):
        for j in range(K):
            for k in range(K):
                if S[i+j] != T[i+k] and k in pos[j]:
                    pos[j].remove(k)
    vis = set()
    for i in range(K): vis |= pos[i]
    ret = [0, 1][len(vis) == K]
    cnt = defaultdict(int)
    sz = defaultdict(int)
    for i in range(K):
        if not pos[i]:
            ret = 0
            break
        key = sorted(pos[i])[0]
        cnt[key] += 1
        sz[key] = len(pos[i])
    for k, v in sz.items():
        ret *= [0, facto(v)][cnt[k] == v]
    print(ret)

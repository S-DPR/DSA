import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
Round 948 (Div 2) C : Nikita and LCM

수열 A가 주어진다.
A의 부분배열중 lcm이 A에 포함되지 않은 부분배열의 최대 길이를 구해보자.
배열의 lcm이란, 배열에 있는 모든 요소의 최소공배수이다.
빈 배열의 lcm은 0이다.

참..
A의 lcm을 구해봅시다. 이 값이 A의 최대값보다 크다면 항상 N이 정답입니다.
그게 아니라면 A의 lcm은 A의 최댓값임을 증명할 수 있습니다. 이 값을 M이라 합시다.
이제 M의 약수이고 A에 없는 수를 뽑아냅니다.
이 수들중에서 A에 있는 요소를 최대한 많이 나눠 떨어지게 만드는 값을 L이라고 할 때,
그 나눠 떨어지게 만드는 값으로만 배열을 구성하면 AC가 됩니다.
왜냐면, 일단 A에 없는게 조건이어서 문제 조건은 만족하고, 그 중 가장 큰 요소니까.

그런데 왜 항상 M의 약수가 lcm이 될 때 답이나올까요?
그야 M이 A의 모든 요소에 의해 나눠지니까요..
사실상 완탐입니다..
아..
"""
from math import lcm
from collections import defaultdict as d
for _ in ' '*ini():
    N = ini()
    A = sorted(ins())
    mx = max(A)
    if lcm(*A) > mx:
        print(N)
        continue
    cnt = d(int)
    for i in A: cnt[i] += 1
    factor = []
    ret = 0
    for i in range(1, mx+1):
        if i*i > mx: break
        if mx%i: continue
        factor.append(i)
        if i != mx//i:
            factor.append(mx//i)
    for i in factor:
        if i in cnt: continue
        items = [j for j in A if not i%j]
        if lcm(*items) in cnt: continue
        ret = max(ret, len(items))
    print(ret if ret > 1 else 0)

import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
Round 950 (Div 3) D : GCD-sequence

수열이 주어진다.
수열에서 임의의 한 수를 제거한 뒤,
두 인접한 수의 gcd를 순서대로 나열할 때,
비내림차순이 되게 할 수 있을까?
단, 정확히 하나의 수를 제거해야 한다.

하아
잘쳐줘도 실버1인문제를..
하..

때려치고싶다

우선 천천히 두 수의 인접한 gcd가 비내림차순인지 확인합시다.
만약 전부 비내림차순이면 그냥 앞이나 뒤 빼면 되니까 항상 가능합니다.

어느 순간 비내림차순이 아니게 되었다면,
가운데 있는 수 i를 기준으로 i-1, i, i+1을 각각 빼보고 나이브하게 다시 테스트합니다.
뺐을 때 gcd들이 비내림차순이면 YES. 셋 다 테스트 해봤는데 안되면 NO.

패인을 분석해보면..
1. '정확히 하나의 수를 제거해야 함'에 꽂혀버려서 멸망.
만약 원래 비내림차면 앞이나 뒤에서 빼면 됨을 찾지 못했습니다.

2. 후보 몇개만 뽑아와서 나이브하게 테스트해도 된다는 사실을 잊음.
백준에서는 거의 안나오는 유형이고, 거기선 애초에 그래프 dp 누적합 이러고있으니까..
너무 당연한데 아..

3. 태그 못맞춤
혼자서 dp? 누적합? 많조분? 이러고있으니 맞을수가없다..
그냥 그리디라는걸 생각하지 못했다..


하.. 너무.. 너무 아쉽다..
"""
from math import gcd

for _ in ' '*ini():
    N = ini()
    A = ins()
    fail = 0
    for i in range(1, N-1):
        if fail: continue
        if gcd(A[i-1], A[i]) <= gcd(A[i], A[i+1]): continue
        for j in [i-1, i, i+1]:
            v = A[j]
            del A[j]
            for k in range(1, N-2):
                if gcd(A[k-1], A[k]) > gcd(A[k], A[k+1]): break
            else: break
            A.insert(j, v)
        else:
            fail = 1
            continue
        break
    if fail: print('NO')
    else: print('YES')

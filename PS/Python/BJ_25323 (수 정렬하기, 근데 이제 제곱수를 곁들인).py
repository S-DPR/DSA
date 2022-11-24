import sys, math
input = sys.stdin.readline
"""
25323번 수 정렬하기, 근데 이제 제곱수를 곁들인

수열 A가 주어진다. A_i * A_j가 제곱수인 경우에만 A_i와 A_j를 스왑할 수 있다고 할 때,
이 수열이 정렬될 수 있는지 가능 여부를 출력하시오.

진짜 피와 눈물의 문제풀이..
배워간게 아예 없진 않습니다.

제일 간단하게 쓸 수 있는 제곱수 체크 함수는,
issquare = lambda x: int(x**.5) == x**.5
로 짤 수 있지만 이건 큰 수의 제곱수판별에서 실수오차를 냅니다.
예를들어, 89011483755109777에서..

다음으로 정밀하게 하는데는
issquare = lambda x: int(x**.5)**2 == x
가 있습니다. 그냥 **2을 넘긴것 뿐이지만 살짝 더 정확해집니다.

하지만 이 문제는 파이썬 야매로 푸는 사람 물좀 먹어봐라, 하고싶었는지,
위 두 함수의 저격데이터를 때려박았습니다..
16777217이라는 데이터인데요,
math.sqrt(16777217)**2와 math.isqrt(16777217)**2를 비교해보면 된다합니다.
이 수는 끝이 7이기때문에 당연히! 제곱수가 될 수 없습니다. 당연해요.
근데 이게 저 두 함수에서는 True를 보내는 트롤짓을 합니다. 실수오차때문에..

그래서 math.isqrt를 쓴다고 합니다. sqrt를 하긴한데 더 가까운 정수를 반환한다고 하네요.
뭐 이걸 딴데서 더 쓸진 모르겠고..
제곱수 판별을 이분탐색으로 하는 방법도 있다고 합니다.

def issquare_bisect(x):
    l, r = 0, 10**18
    while l < r:
        m = (l + r) >> 1
        if m**2 >= x: r = m
        else: l = m+1
    return r**2 == x
.. 참고로 이거로 하면 시간초과납니다.

이걸 제외한 알고리즘은 딱히 신경쓸거라면..
ab가 제곱수이고, bc가 제곱수라면 ac도 제곱수라는 정도..?
"""
issquare = lambda x: math.isqrt(x)**2 == x
n = int(input())
arr = list(map(int, input().split()))
sarr = sorted(arr)
for i in range(n):
    if arr[i] == sarr[i]: continue
    if not issquare(arr[i]*sarr[i]) and (arr[i]*sarr[i])%10 != 7:
        print('NO')
        break
else:
    print('YES')

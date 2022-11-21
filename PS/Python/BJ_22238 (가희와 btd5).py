import sys
from collections import defaultdict
input=sys.stdin.readline
"""
22238번 가희와 btd5

첫 줄에 N, M이 주어집니다.
두 번째 줄부터 N개의 줄에 점의 좌표 x, y와 그 체력 w가 주어집니다.
이후 M개의 줄에 직선상으로 모두 관통하는 빔을 쏩니다. 역시, x, y, w로 주어집니다.
이 때 w는 빔의 대미지입니다.
모든 점은 직선상에 존재할 때, 각 M개의 줄마다 빔을 쏘고 남은 풍선의 개수를 세주세요.

이게 아..
모든 점이 직선상에 존재한다.. 이거 안보면 난이도 급상승하는 기적의 문제입니다.
개인적으로 저거 없으면 플레이상 되지않을까 싶지만..
있네요..

N개의 줄을 받을 때는 어차피 직선상에 있으니 x, y는 무시하고.
마지막에 x, y를 이용해 유클리드 호제법 써서 x, y를 압축해줍니다.
다틀링건의 위치는 0, 0에 고정되었고. 0, 0을 지나는 직선상의 점이니, 이 방향을 제외하면 풍선이 맞을 일이 없죠.
hp 관해서는 그냥 hp를 내림차순 정렬해준뒤 누적 대미지로 빠르게 계산해줍시다.
굳이 hp를 다 까는건 비효율적이니까요..

참고로 BTD5 엄청 재밌습니다. 갓겜임.
"""
def gcd(x, y):
    x, y = abs(x), abs(y)
    while y:
        x, y = y, x%y
    return x

n, m = map(int, input().split())
hp = defaultdict(int)
for _ in ' '*n:
    x, y, w = map(int, input().split())
    hp[w] += 1
direction = abs(gcd(x, y))
direction = x//direction, y//direction
hp = sorted(hp.items())
acc = 0
res = 0
for _ in ' '*m:
    x, y, w = map(int, input().split())
    shoot_direc = abs(gcd(x, y))
    shoot_direc = x//shoot_direc, y//shoot_direc
    if shoot_direc == direction:
        while hp and hp[-1][0] <= acc+w:
            n -= hp.pop()[1]
        acc += w
    print(n)

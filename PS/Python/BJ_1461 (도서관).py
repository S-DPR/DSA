import sys
input = sys.stdin.readline
"""
1461번 도서관

책의 개수 n과 한번에 들 수 있는 책 m이 주어집니다. n과 m은 50을 넘지 않는 자연수입니다.
이후, 책 n개의 원래 자리가 수직선상 정수 좌표로 주어집니다. (절댓값 10000을 넘지 않음)
직원은 좌표 0에서 출발하며, 모든 책의 초기 위치는 0입니다.
직원은 한 걸음당 좌표 1만큼 움직일 수 있으며, 최소걸음으로 책을 다 제자리에 두고싶어합니다.
이때, 움직여야만 하는 최소거리를 구해주세요.
마지막 책을 꽂고난 뒤에는 칼퇴할거기때문에 다시 좌표 0으로 돌아올 필요가 없습니다.

척 보면 느낌 오듯이, 그리그리한 그리디문제입니다.
그리그리라는 단어가 있는진 모르겠지만.

그냥 마이너스좌표와 플러스좌표계를 따로 만들고,
정렬하고, 합 더하고 마지막에 칼퇴조건만 맞춰주면 끝납니다.
그렇게 긴 생각 없이도 알고리즘이 짜지는 문제였습니다.
"""
n, x = map(int, input().split())
m = []; p = []
for i in map(int, input().split()):
    if i < 0: m.append(i)
    else: p.append(i)
m.sort(); p.sort(reverse=True)
ans = 0
for i in range(0, len(p), x):
    ans += p[i]*2
for i in range(0, len(m), x):
    ans += -m[i]*2
if m and p:
    if -m[0] > p[0]: ans -= -m[0]
    else: ans -= p[0]
elif m: ans -= -m[0]
elif p: ans -= p[0]
print(ans)

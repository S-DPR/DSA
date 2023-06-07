import sys
input = sys.stdin.readline
"""
9213번 꽤 좋은 수

s, e 사이의 수 중 자신을 제외한 약수의 합의 절댓값이 b 이하인 수의 개수를 구해보자.

이딴게..골드2..?
진짜로..?

그냥 NlogN으로 1부터 100만까지 자신을 제외한 약수의 합 전처리 한 뒤,
s, e범위 다 훑어서 b 이하면 ret에 1 더하고,
그거 출력하면 끝입니다.

제가 아는 골드 2는 시간 엄청먹는데 이건 쉽네요..
"""
MAX = 1_000_000
items = [*range(1, -MAX, -1)]
for i in range(2, MAX):
    for j in range(i+i, MAX, i):
        items[j] += i
tc = 1
while True:
    s, e, b = map(int, input().split())
    if not s: break
    ret = 0
    for i in range(s, e+1):
        if abs(items[i]) <= b: ret += 1
    print(f"Test {tc}: {ret}")
    tc += 1

import sys, heapq
input = sys.stdin.readline
"""
14698번 전생했더니 슬라임 연구자였던 건에 대하여 (Hard)

수열 arr이 주어진다.
여기서 두 수를 잡아 곱해보자.
이제, 이 값을 다시 arr에 집어넣고 arr의 길이가 1이 될때까지 반복해보자.
마지막으로 지금까지 arr에 도로 집어넣은 값을 다 곱한 뒤 1_000_000_007로 나누어 출력해보자.

파이썬 << 신(진짜임)
제일 편하게 푸는 풀이는 그냥 우선순위큐 쓰는거고, 그게 싫으면 그리디로 해도 된다하네요.
들어오는 수의 크기가 좀 커서 (2*10**18), C++이나 자바에선 어떻게할지 모르겠어요..
"""
T = int(input())
for _ in ' '*T:
    n = int(input())
    arr = list(map(int, input().split()))
    heapq.heapify(arr)
    result = 1
    while len(arr) > 1:
        tmp = heapq.heappop(arr)*heapq.heappop(arr)
        result *= tmp
        result %= 1_000_000_007
        heapq.heappush(arr, tmp)
    print(result)
    
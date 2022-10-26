import sys
input = sys.stdin.readline
MOD = 10**9+7
"""
15824번 너 봄에는 캡사이신이 맛있단다

수열의 길이 n과 수열 A가 주어집니다.
수열 A에서 두 개 이상의 수를 선택해 최댓값과 최솟값의 차를 구할 수 있습니다.
이 때, 두 개 이상을 선택하는 모든 경우의 수에 대해 그 합을 구해주세요.

처음에 봤을땐 이게 뭐시당가 했었는데,
오늘 그냥 심심해서 수좀 갖고 놀다보니 규칙이 있더라고요.
그래서 풀었습니다.

정렬된 수열의 인덱스 i (0 <= i <= n-1)에 대해,
A[i] * (pow(2, i) - pow(2, n-i-1))의 값을 더하면 정답이 나옵니다.
수열을 a b, a b c, a b c d, a b c d e로 두고 직접 경우의수를 계산해보면,
최종 수식이 그렇게 나온단걸 알 수 있습니다.

참고로 C나 Java나, 어쨌든 파이썬도 똑같긴한데,
인덱스가 최대 30만이라서, 2의 30만승을 구해야하는 경우가 나옵니다.
그냥 세상 느긋하게 곱하고있으면 시간이 오래걸려, 분할정복 거듭제곱을 해야합니다.
파이썬의 경우 pow함수에서 분할정복 거듭제곱과 모듈러연산을 지원합니다.
"""
n = int(input())
ans = 0
for idx, i in enumerate(sorted(map(int, input().split()))):
    ans += i * (pow(2, idx, MOD) - pow(2, n-idx-1, MOD))
    ans%=MOD
print(ans)

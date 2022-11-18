import sys
input = sys.stdin.readline
"""
25634번 전구 상태 뒤집기

수열 A와 B가 주어진다.
수열 A는 5000이하의 자연수로, 수열 B는 0과 1로 이루어져 있다.
수열 B에서 인덱스 l, r을 잡아 l:r범위를 모두 반전시켜야한다.
이때, 수열 A의 합을 구할 수 있다, B_i가 1이면 A_i의 값을 더하는 형식이다.
인덱스 반전을 한 번 하여, 얻을 수 있는 합의 최댓값을 구하시오.

읽고 이건 DP는 아니겠거니~ 했는데, 다시보니 DP.
Kadane의 알고리즘을 사용한다고 합니다.
그냥 연속합의 최대 구하는 간단한 알고리즘이에요.
첨엔 어렵게생각해서 삽질했는데, 풀고보니 정말 쉬운문제..
"""
n = int(input())
arr = list(map(int, input().split()))
brr = list(map(int, input().split()))
S = sum(arr[i] for i in range(n) if brr[i])
allM, M = S, [S]
for i in range(n):
    allM += -arr[i] if brr[i] else arr[i]
    if not brr[i]:
	allM = max(allM, S+arr[i])
	M.append(max(allM, arr[i]))
print(max(M) if brr.count(0) else S-min(arr))

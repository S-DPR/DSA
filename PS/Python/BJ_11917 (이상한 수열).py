import sys
input = sys.stdin.readline
"""
11917번 이상한 수열

길이가 n인 수열 A가 주어집니다.
이 수열을 확장할건데, A(1) ~ A(n)까지는 그대로 두고
A(n+1)은 A(1)부터 A(n)까지의 서로 다른 수의 개수입니다.
마지막으로, M이 주어집니다. A(M)을 출력하면 되는 문제입니다.

당연히 세상 느긋하게 for문으로 세월아 네월아 하면 시간초과납니다.
저같은경우 좌표압축 후 조건에 맞나 확인한 뒤,
결과에 따라 다른 값을 출력하여 처리했습니다.

1,2,3,-1 처럼 A의 최댓값이 수열 A에서 중복된 수보다 작다면, 그 뒤는 4,5,... 이렇게 확장되고,
1,2,3,5 처럼 A의 최댓값이 수열 A에서 중복된 수와 같거나 크다면 중간에 반드시 한 숫자만 나오게됩니다. 이 경우, A는 1,2,3,5,4,5,5,... 이렇게 가게됩니다.
사실 좌표압축같은건 전혀 필요없고, 그냥 아이디어를 떠올려 문제를 풀면 AC를 맞습니다.
"""
def lowerbound(x):
    l = 0; r = len(arr)-1
    while l < r:
        m = l + r >> 1
        if arr[m] >= x:
            r = m
        else:
            l = m+1
    return r if arr[r] >= x else -1

n = int(input())
arr = list(map(int, input().split()))
m = int(input())
if m <= n:
    print(arr[m-1])
else:
    arr = sorted(set(arr))
    k = lowerbound(len(arr))
    if k == -1 or m <= arr[k]:
        print(len(arr)+m-n-1)
    else:
        print(arr[k])

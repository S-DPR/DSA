import sys
input = sys.stdin.readline
"""
17943번 도미노 예측

수열 A가 있습니다. 이 수열의 길이 N과 쿼리의 개수 K가 주어집니다.
하지만 이 수열이 주어지진 않고, 대신 A[idx] XOR A[idx+1]를 한 값이 주어집니다.
(0 <= idx < N-1)
즉, N-1길이의 수열이 두번재 줄에 주어지게 됩니다.

이후 쿼리가 K줄 주어집니다. 각 쿼리는 아래의 형식을 갖추고있습니다.
0 x y : x XOR y를 구하세요.
1 x y d : x가 d일때 y의 값을 구하세요.

정해는 Prefix-Sum (누적합)이고, 저는 처음엔 세그트리를 써서 풀었습니다.
세그트리도 따지고보면 누적합 알고리즘이 포함되어있는거니 뭐..
누적합만을 이용한 숏코딩은 아래에 있습니다.

그리고 XOR에 대한 성질도 좀 알고있어야합니다.
A XOR A = 0
A XOR B = B XOR A
이 두개만 알고있어도 충분합니다.
사실 두번째 성질은 쓰이는지도 잘 모르겠지만..
"""
def init(arr):
    n = len(arr)
    seg = [0]*(n*2)
    for i in range(n):
        seg[i+n] = arr[i]
    for i in range(n-1, 0, -1):
        seg[i] = seg[i<<1] ^ seg[i<<1|1]
    return seg

def query(seg, l, r):
    res = 0; n = len(seg)//2
    l += n-1; r += n-1
    while l <= r:
        if l & 1:
            res ^= seg[l]; l += 1
        if not r & 1:
            res ^= seg[r]; r -= 1
        l >>= 1; r >>= 1
    return res

def main():
    n, m = map(int, input().split())
    arr = list(map(int, input().split()))
    seg = init(arr)
    for _ in ' '*m:
        q, *c = tuple(map(int, input().split()))
        match q:
            case 0:
                print(query(seg, c[0], c[1]-1))
            case 1:
                print(query(seg, c[0], c[1]-1)^c[2])

main()

""" 코드 길이 : 319B
import sys
input = sys.stdin.readline

n, m = map(int, input().split())
arr = list(map(int, input().split()))
dp = [0]*n
for i in range(1, n):
    dp[i] = dp[i-1] ^ arr[i-1]
for _ in ' '*m:
    q, *c = tuple(map(int, input().split()))
    if q: print(dp[c[1]-1]^dp[c[0]-1]^c[2])
    else: print(dp[c[1]-1] ^ dp[c[0]-1])
"""
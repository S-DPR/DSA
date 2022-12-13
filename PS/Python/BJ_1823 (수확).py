import sys
input = sys.stdin.readline
sys.setrecursionlimit(10**5)
"""
1823번 수확

길이 N 배열이 주어진다. (N <= 2000)
배열의 길이가 0이 될 때까지 배열의 가장 왼쪽, 가장 오른쪽을 pop할 수 있다.
이 때, K번째로 pop한 값은 (pop한 값) * K의 값을 가진다.
이 합의 최대를 구하시오.

문제를 처음 봤을 땐 투포인터 그리디인가 했는데,
좀 더 생각해보니 이거 그리디로 풀면 어디선가 와장창 무너질거같더라고요?
입력도 O(N^2) 가능할 수준으로 작은건 덤이고..
그래서 생각을 바꿨습니다.

먼저 나온 생각은 일단 이거 그리디가 아니니까 DP같은데 어떻게 DP로 보내야하는가였습니다.
몰라서 일단 브루트포스로 짜봤다가, 이것저것 해봤습니다.

이리저리 접근해보다가 어떻게 푸는지 몰라서 검색해봤는데 거의 다 온걸 보고,
손발을 덜덜 떨면서 풀었습니다.

푸는 방식은 간단합니다. 처음에 l, r = 0, n-1로 시작하여 dp[l][r]에 최대값을 저장할건데요,
l을 1 올리고, r은 1을 하나씩 빼가며 최댓값을 저장할겁니다.
l > r이 된다면 그냥 0 리턴하고요.
dp[l][r]이 이미 나와있다면 그걸 return해줍니다.

이게 하다보면 dp[l][r]이 중복되는 부분이 생긴다더라고요?
그래서 dp[l][r]이 있으면 그걸 return하는게 있고..
그래서 시간내에 되는거같습니다. 이게 뭐라 설명해야할지 참..
"""
def loop(arr, l, r, prod):
    if l > r: return 0
    if dp[l][r]: return dp[l][r]
    dp[l][r] = max(loop(arr, l+1, r, prod+1) + arr[l]*prod,
                   loop(arr, l, r-1, prod+1) + arr[r]*prod)
    return dp[l][r]

n = int(input())
arr = list(int(input()) for _ in ' '*n)
dp = [[0]*n for _ in ' '*n]
loop(arr, 0, n-1, 1)
print(max(map(max, dp)))

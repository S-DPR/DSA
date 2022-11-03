import sys
input = sys.stdin.readline
inf = 10**9
"""
13902번 개업 2

10000 이하의 자연수 N과 수열의 길이 M이 주어집니다.
이후, 길이가 M인 배열 A가 주어집니다.
배열 A의 서로 다른 두 원소를 합한 값이나 배열 A의 원소를 최소한으로 더해
N을 만들려고 합니다. 이때, 최소 항의 개수를 구해주세요.
두 원소를 합한 값은 항 하나로 취급합니다.
두 원소가 같은 값이여도 서로 다른 인덱스를 갖고있다면, 두 수를 더할 수 있습니다.

잘 읽어보면 동전 2 내용입니다.
어떻게하지?? 하다가 생각 좀 해보니 DP더라구요.
이게 맞나 싶긴했지만, 그냥 0부터 시작해서 N까지,
수 i를 만들기 위해 0과 i, 1과 i-1, 2와 i-2.. 이렇게 죽 더한 값중 가장 작은 값을 취하면됩니다.
물론, 그 전에 항 1개로 만들 수 있는 수는 1로 미리 초기화해둡시다.
"""
n, m = map(int, input().split())
arr = list(map(int, input().split()))
brr = sorted(set(arr[iidx]+arr[jidx] for iidx in range(m) for jidx in range(iidx+1, m)) | set(arr))
dp = [inf]*(n+1)
for i in brr:
	if i > n: break
	dp[i] = 1
for i in range(n+1):
	m = dp[i]
	for j in range(i//2+1):
		m = min(m, dp[j]+dp[i-j])
	dp[i] = m
print(dp[-1] if dp[-1] != inf else -1)

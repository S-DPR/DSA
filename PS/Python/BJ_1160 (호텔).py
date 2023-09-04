import sys
input = sys.stdin.readline
spl = lambda: map(int, input().split())
inf = float('inf')
"""
1160번 호텔

C명 이상의 사람을 모으려한다.
N개의 방법이 있다. 방법을 적절히 선택해 최소비용으로 해보자.
모든 방법은 원하는 횟수만큼 시행 할 수 있다.
w v : w원을 들여 v명의 사람을 모은다.

노트북이 RIP상태가 되셔서.. 스마트폰으로 푼 문제.
노트북 새로 오든 고쳐지든 둘중 하나 하기 전까진 파이썬써야겠네..
적당히 골드5 밀린거 처리하고..

그냥 배낭인데, 무한번 선택이 가능하므로 0에서 C로 가면됩니다.
최소비용을 찾는거니 사람수를 인덱스로 두고.
for문 적당히 돌리면 끝납니다. 할만하죠..
"""
C, N = spl()
dp = [inf]*(C+101)
for _ in ' '*N:
	w, v = spl()
	for i in range(C+101-v):
		if dp[i] == inf: continue
		dp[i+v] = min(dp[i+v], dp[i]+w)
	for i in range(v, C+101, v):
		dp[i] = min(dp[i], w*i//v)
print(min(dp[C:]))

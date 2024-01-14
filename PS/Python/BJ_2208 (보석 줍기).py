import sys
input = sys.stdin.readline
ini = lambda: int(input())
ins = lambda: map(int, input().split())
"""
2208번 보석 줍기

길이가 M 이상인 부분연속수열 중, 연속합이 가장 큰 부분연속수열을 구해보자.
이때, 그 값이 음수라면 0을 출력하자.

일본 1일차라서 미리 풀어둔거 5개중 1개
그냥 누적합에 dp 한번 굴리면 끝납니다.
"""
N, M = ins()
A = [ini() for _ in ' '*N]
pf = [0]
sm = [0]
for i in A:
	pf.append(pf[-1]+i)
	sm.append(max(i, sm[-1]+i, 0))
ret = 0
for i in range(M, N+1):
	ret = max(ret, pf[i]-pf[i-M]+sm[i-M])
print(ret)

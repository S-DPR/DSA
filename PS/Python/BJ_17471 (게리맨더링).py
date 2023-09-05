import sys
input = sys.stdin.readline
spl = lambda: map(int, input().split())
inf = float('inf')
"""
17471번 게리맨더링

노드에 가중치가 주어진 그래프가 주어진다. 그래프가 이어지도록 두 부분으로 나누어, 그 가중치의 차이가 최소가 되도록 해보자.

예엣날에 못풀었던 문제.
괜히 모든 조합 만들어보겠다고 설쳤다가..

그냥 비트마스킹 열심히 박는 문제입니다. 사실 dfs는 덤인 느낌이고.
그렇게 하루종일 비트마스킹 쓰다보면 AC.
"""
vis = 0
def dfs(bit, line, cur):
	global vis
	if line:
		if bit&(1<<cur) == 0: return
	else:
		if bit&(1<<cur): return
	if vis&(1<<cur): return
	vis |= 1<<cur
	for i in G[cur]:
		dfs(bit, line, i)

ret = inf
N = int(input())
H = [*spl()]
G = [[] for _ in ' '*N]
for i in range(N):
	n, *c = spl()
	for j in range(n):
		c[j] -= 1
	G[i] = c
for bit in range(1, 1<<N):
	z, f, s = 0, 0, 0
	for i in range(N):
		if bit&(1<<i) == 0:
			if not z:
				z = 1
				vis = 0
				dfs(bit, 0, i)
				if vis&bit: break
				if (vis|bit) != (1<<N)-1: break
			s += H[i]
		if bit&(1<<i) != 0:
			if not f:
				f = 1
				vis = 0
				dfs(bit, 1, i)
				if vis != bit: break
			s -= H[i]
	else:
		ret = min(ret, abs(s))
print(-1 if ret == inf else ret)

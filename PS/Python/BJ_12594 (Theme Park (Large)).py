import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
fini = lambda: int(input()[1:])
fins = lambda: [*map(int, input()[1:].split())]
inf = float('inf')
"""
12594번 Theme Park (Large)

가장 앞에 있는 그룹이 먼저 탑승하는 놀이기구가 있다.
각 그룹은 반드시 한 번에 탑승해야하며, 사람 수가 남아도 뒷 그룹이 먼저 탈 수는 없다.
탑승한 그룹은 다시 잘 맨 뒤쪽에 순서대로 서게된다.
반복할 횟수 R, 한번에 탑승 가능한 인원 수 K, 그룹의 수 N이 주어진다.
반복 횟수동안 놀이기구를 타본 인원의 수를 구해보자.

이분탐색..으로생각했다가 R이 10^8이길래 조금 다시 생각해본 문제.
생각 좀 해보니 함수형그래프로 나타낼 수 있고, 그러면 사이클 찾는 문제가 되더라고요.
대충 사이클 처리해주고 AC.
폰으로 푼거라 오랜만에 파이썬이 2연속으로 나오게됐네요..
"""
for tc in range(1, ini()+1):
    R, K, N = ins()
    A = ins()
    idx, ret = 0, 0
    vis = [-1]*N
    G = [0]*N
    cycle_sz, cycle_sm = -1, 0
    while R:
        k = K
        if vis[idx] != -1:
            if cycle_sz == -1:
                newV = [0]*N
                cur = idx
                while not newV[cur]:
                    newV[cur] = 1
                    cycle_sm += vis[cur]
                    cur = G[cur]
                cycle_sz = sum(newV)
            if cycle_sz <= R:
                go = R//cycle_sz
                R %= cycle_sz
                ret += cycle_sm*go
            else:
                ret += vis[idx]
                idx = G[idx]
                R -= 1
            continue
        start = idx
        sm = 0
        for i in range(N):
            if k < A[idx]: break
            k -= A[idx]
            sm += A[idx]
            idx = (idx+1)%N
        G[start] = idx
        vis[start] = sm
        ret += sm
        R -= 1
    print(f"Case #{tc}: {ret}")
import sys
input = sys.stdin.readline
sys.setrecursionlimit(10**6)
"""
25187번 고인물이 싫어요

1과 0으로 이루어진 수열 A가 주어진다.
같은 집합에 있는 인덱스가 x, y꼴로 M개 주어진다.
이후 인덱스가 주어질 때, 해당 인덱스가 있는 집합의 절반을 넘는 수가
1의 값을 가질경우 1을, 절반 이하가 1의 값을 가질경우 0을 출력하라.

.. Union-Find문제입니다.
저같은경우 dfs로 풀어버렸..는데..
좋은풀이는 아니네요.
유니온파인드 공부 해야겠습니다...
"""
def dfs(x, arr):
    if visit[x]: return
    visit[x] = 1
    connected.append(x)
    for i in G[x]:
        dfs(i, arr)

N, M, Q = map(int, input().split())
arr = list(map(int, input().split()))
G = [[i] for i in range(N+1)]

for _ in ' '*M:
    x, y = map(int, input().split())
    G[x].append(y)
    G[y].append(x)

visit = [0]*(N+1)
query = [0]*(N+1)
for i in range(1, N+1):
    if visit[i]: continue
    connected = []
    dfs(i, arr)
    clean = sum(1 for j in connected if arr[j-1])
    dirty = len(connected)-clean
    res = dirty < clean
    for j in connected:
        query[j] = res

for _ in ' '*Q:
    print([0, 1][query[int(input())]])

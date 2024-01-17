import sys
input = sys.stdin.readline
ini = lambda: int(input())
ins = lambda: map(int, input().split())
inf = float('inf')
"""
1219번 오만식의 고민

간선을 이용하는데 K원이 들고, 이동하면 정점에서 X원을 얻는다.
그리고, 정점 S에서 E로 가려 한다.
만약 갈 수 없으면 gg를, 갈 수 있는데 돈을 무한히 벌 수 있으면 Gee를,
아니면 E에서 가질 수 있는 최대 돈을 구해보자.

첫 벨만포드 문제
쉽지않네..
"""
def bel():
    dist = [-inf]*N
    dist[S] = price[S]
    for R in range(N*2):
        for curN, nxtN, nxtW in G:
            if dist[nxtN] >= dist[curN]+nxtW: continue
            if R >= N-1: dist[nxtN] = inf
            dist[nxtN] = max(dist[nxtN], dist[curN]+nxtW)
    return dist

N, S, E, K = ins()
g = [[*ins()] for _ in ' '*K]
price = [*ins()]
G = [[u, v, price[v]-w] for u, v, w in g]
dist = bel()
if dist[E] == -inf:
    print("gg")
else:
    print([dist[E], "Gee"][dist[E] == inf])

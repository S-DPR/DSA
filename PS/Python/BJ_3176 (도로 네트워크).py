import sys
input = sys.stdin.readline
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
3176번 도로 네트워크

간선에 가중치가 있는 트리가 주어진다.
이후 Q개의 u, v가 주어진다.
u에서 v로 최단경로로 갈 때 지나는 간선 가중치의 최소와 최댓값을 구해보자.

첨에 건들려다가 스읍 쉽지않네 하고 1억발 역돌격을 했던 문제.
어제 갑자기 생각나서 lca 열심히 건드려보니까 되더라고요.
lca 내부에서 건드는게 아닌, lca 결과를 활용해서 최소/최대를 구하는게 핵심.

"""
sys.setrecursionlimit(10**5+2)
def dfs(G, x, d, depth, sparse, mn_sparse, mx_sparse):
    depth[x] = d
    for i, w in G[x]:
        if depth[i] != -1: continue
        sparse[i][0] = x
        mn_sparse[i][0] = w
        mx_sparse[i][0] = w
        dfs(G, i, d+1, depth, sparse, mn_sparse, mx_sparse)

def lca(lgN, depth, mn_sparse, mx_sparse, sparse, u, v):
    if depth[u] < depth[v]:
        u, v = v, u
    ou, ov = u, v
    mn, mx = inf, -inf
    for i in range(lgN-1, -1, -1):
        if depth[v] <= depth[sparse[u][i]]:
            u = sparse[u][i]
    ret = u
    for i in range(lgN-1, -1, -1):
        if u == v: break
        if sparse[u][i] != sparse[v][i]:
            u = sparse[u][i]
            v = sparse[v][i]
        ret = sparse[u][i]
    for i in range(lgN-1, -1, -1):
        if depth[ret] <= depth[sparse[ou][i]]:
            mn = min(mn, mn_sparse[ou][i])
            mx = max(mx, mx_sparse[ou][i])
            ou = sparse[ou][i]
        if depth[ret] <= depth[sparse[ov][i]]:
            mn = min(mn, mn_sparse[ov][i])
            mx = max(mx, mx_sparse[ov][i])
            ov = sparse[ov][i]
    return mn, mx

def main():
    N = ini()
    lgN = N.bit_length()
    G = [[] for _ in ' '*(N+1)]
    for _ in ' '*(N-1):
        u, v, w = ins()
        G[u].append([v, w])
        G[v].append([u, w])
    depth = [-1]*(N+1)
    sparse = [[0]*N.bit_length() for _ in ' '*(N+1)]
    mn_sparse = [[inf]*N.bit_length() for _ in ' '*(N+1)]
    mx_sparse = [[-inf]*N.bit_length() for _ in ' '*(N+1)]
    dfs(G, 1, 0, depth, sparse, mn_sparse, mx_sparse)
    for i in range(1, lgN):
        for j in range(1, N+1):
            sparse[j][i] = sparse[sparse[j][i-1]][i-1]
            mn_sparse[j][i] = min(mn_sparse[j][i-1], mn_sparse[sparse[j][i-1]][i-1])
            mx_sparse[j][i] = max(mx_sparse[j][i-1], mx_sparse[sparse[j][i-1]][i-1])
    for _ in ' '*ini():
        print(*lca(lgN, depth, mn_sparse, mx_sparse, sparse, *ins()))

main()

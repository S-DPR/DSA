import sys
input = sys.stdin.readline
"""
2463번 비용

가중치가 모두 다른 그래프가 주어진다. 이중 두 노드 i, j를 잡자.
두 노드가 이어져있는동안 그래프에서 가중치가 가장 작은 간선을 빼자.
두 노드가 이어지지 않게 된 순간, 그 전까지 뺀 간선의 가중치가 cost(i, j)의 값이다.
세 노드 x, y, z에 대해 x와 y가 이어져있고 y와 z가 이어져있다면 x와 z가 이어져있다고 할 때,
모든 i, j (i < j)에 대해 위 행위를 한 뒤, 그 합을 구해보자.

쉬운 골드 2인줄 알았는데 이게 플레 5네..
똑같은 분리집합 문제 + G1인 원수의 원수는 대체 어떤문제일까????

옛날에 세그먼트 트리 열심히 풀 때의 기억이 도움이 되었습니다.
오프라인 쿼리 느낌인데, 이거도 조금 비슷합니다.
먼저, 간선을 가중치순으로 나열합니다.
두 노드 i, j가 속한 집합에 대해, i, j가 같다면 아래 과정을 진행하지 않습니다.
(i가 속한 집합의 크기)*(j가 속한 집합의 크기)*(현재 cost)를 구하고 답에 더해줍니다.
이후 union연산을 합니다. 이 때, 사이즈도 같이 세줍니다.

그래프의 모든 간선 가중치의 합을 저장하고 거기서 하나씩 빼기때문에,
처음에 모든 간선의 가중치를 합하고 그래프에서 하나씩 뺄때마다 해당 가중치를 빼주어야합니다.
위 행위는 처음부터 i에 속한 집합과 j에 속한 집합이 같았어도 진행해주어야합니다.
"""
def union(x, y):
    x = find(x)
    y = find(y)
    S[y] += S[x]
    U[x] = U[y]

def find(x):
    if U[x] != x:
        U[x] = find(U[x])
    return U[x]

MOD = 10**9
INF = 1 << 63
N, M = map(int, input().split())
G = [list(map(int, input().split())) for _ in ' '*M]
G.sort(key=lambda x: x[2])
S = [1]*(N+1)
U = [i for i in range(N+1)]

result = 0
prefix = sum(i[2] for i in G)
while G:
    u, v, w = G.pop()
    if find(u) != find(v):
        Usize, Vsize = S[find(u)], S[find(v)]
        result += Usize*Vsize*prefix
        result %= MOD
        union(u, v)
    prefix -= w
print(result)

import sys
sys.setrecursionlimit(10**6)
input = sys.stdin.readline
ini = lambda: int(input())
ins = lambda: map(int, input().split())
inf = float('inf')
"""
25545번 MMST

최소 신장 트리도 아니고, 최대 신장 트리도 아닌 스패닝 트리를 찾으려 한다.
그래프의 모든 간선의 가중치가 다를 때, 몇 번 간선을 선택해야 하는지 구해보자.

처음 생각)
1. 일단 최대/최소 스패닝 트리 구해서 몇 번 간선 쓰나 보자
2-1. 둘 다 안쓰는 간선 들고와서 미리 잇지
2-2. 그게 없으면 둘 중 하나만 쓰는거 각각 하나씩 가져와서 미리 잇지
3. 다시 mst굴리듯이 굴리면 끝~
와 AC~ 근데 왜이렇게 느리지?

정해)
1. 최소 스패닝 트리가 어떤 간선을 먹는지 본다
2. 최소 스패닝 트리가 갖지 않는 간선을 가져와서 잇는다.
3. 그대로 최소 스패닝 트리를 구축하듯이 한다.
아..
머..
맞긴했네 그죠?
근데 최대스패닝트리는 그냥 블러핑인걸 아예 몰랐네..
"""
def union(U, x, y):
    xp = find(U, x)
    yp = find(U, y)
    U[xp] = U[yp]
    return xp != yp

def find(U, x):
    if U[x] != x:
        U[x] = find(U, U[x])
    return U[x]

def mst(U, use, edge):
    for u, v, _, i in edge:
        if union(U, u, v):
            use[i] = 1
    return use

N, K = ins()
edge = [[*ins(), i+1] for i in range(K)]
minimum = mst([*range(N+1)], [0]*(K+1), sorted(edge, key=lambda x: x[2]))
U = [*range(N+1)]
use = [0]*(K+1)
for u, v, _, i in edge:
    if minimum[i]: continue
    union(U, u, v)
    use[i] = 1
    break
use = mst(U, use, sorted(edge, key=lambda x: x[2]))
if K == N-1:
    print("NO")
else:
    print("YES")
    print(*filter(lambda x: use[x], range(K+1)))

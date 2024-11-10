import sys
input = lambda: sys.stdin.readline().strip("\n")
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
14268번 회사 문화 2

트리가 주어진다. 아래 쿼리를 처리해보자.
1 u v : u번 정점의 서브트리에 모두 v를 더한다.
2 u : u번 정점의 값을 구한다.
초기 정점의 값은 모두 0이다.

오일러 경로 트릭 기초문제.
2번쿼리가 구간쿼리인줄알았는데 알고보니 포인트쿼리더라고요?
어휴..

암튼 덕분에 오랜만에 lazy segtree도 구현해보고.
오일러 경로 트릭은 이번에 처음 구현해봤습니다.
의외로 구현은 쉬워서 당황했네요.
"""
sys.setrecursionlimit(10**6)
class EulerTourTree:
    def __init__(self, sz):
        self.G = [[] for _ in ' '*(sz+1)]
        self.S = [0]*(sz+1)
        self.E = [0]*(sz+1)
        self.V = [0]*(sz+1)
        self.T = 0

    def add_edge(self, u, v):
        self.G[u].append(v)
        self.G[v].append(u)
    
    def dfs(self, x):
        self.V[x] = 1
        self.T += 1
        self.S[x] = self.T
        for i in self.G[x]:
            if not self.V[i]:
                self.dfs(i)
        self.E[x] = self.T
    
    def get_range(self, x):
        return [self.S[x], self.E[x]]

class LazySegTree:
    def __init__(self, sz):
        self.sz = sz
        self.T = [0]*(sz*4+2)
        self.lazy = [0]*(sz*4+2)
    
    def _query(self, node, left, right, qleft, qright):
        self._propagation(node, left, right)
        if qright < left or qleft > right: return 0
        if qleft <= left and right <= qright: return self.T[node]
        mid = (left + right) >> 1
        left = self._query(node*2, left, mid, qleft, qright)
        right = self._query(node*2+1, mid+1, right, qleft, qright)
        return left+right
 
    def _update(self, node, left, right, qleft, qright, value):
        self._propagation(node, left, right)
        if qright < left or qleft > right: return
        if qleft <= left and right <= qright:
            self.T[node] += (right-left+1)*value
            if right != left:
                self.lazy[node*2] += value
                self.lazy[node*2+1] += value
            return
        mid = (left + right) >> 1
        self._update(node*2, left, mid, qleft, qright, value)
        self._update(node*2+1, mid+1, right, qleft, qright, value)
        self.T[node] = self.T[node*2] + self.T[node*2+1]
    
    def _propagation(self, node, left, right):
        self.T[node] += (right-left+1) * self.lazy[node]
        if left != right:
            self.lazy[node*2] += self.lazy[node]
            self.lazy[node*2+1] += self.lazy[node]
        self.lazy[node] = 0
        
    def query(self, l, r):
        return self._query(1, 1, self.sz, l, r)

    def update(self, l, r, value):
        self._update(1, 1, self.sz, l, r, value)

N, Q = ins()
E = EulerTourTree(N)
L = LazySegTree(N)
for idx, i in enumerate(ins(), 1):
    if i == -1: continue
    E.add_edge(idx, i)
E.dfs(1)
for _ in ' '*Q:
    q, *args = ins()
    if q == 1:
        i, w = args
        l, r = E.get_range(i)
        L.update(l, r, w)
    else:
        i = args[0]
        l, _ = E.get_range(i)
        print(L.query(l, l))

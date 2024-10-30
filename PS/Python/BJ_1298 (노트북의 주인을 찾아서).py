import sys
input = lambda: sys.stdin.readline().strip("\n")
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
1298번 노트북의 주인을 찾아서

이분그래프가 주어진다. 최대 매칭을 구하시오.

제에가 최대매칭이라는 요상한 단어를 처음 본게 이 문제인데,
이분매칭인건 알고있었지만 귀찮아서 계속 묵혀놨었습니다.
그리고 이제와서 꺼내먹습니다.. 그래도 이번엔 클래스로 해놔서 덜 까먹게되겠네..
"""
class BipartiteMatching:
    def __init__(self, src_cnt, dst_cnt):
        self.G = [[] for _ in ' '*(src_cnt+1)]
        self.src_cnt = src_cnt
        self.dst_cnt = dst_cnt
    
    def addEdge(self, src, dst):
        self.G[src].append(dst)
    
    def dfs(self, x, match, vis):
        for i in self.G[x]:
            if vis[i]: continue
            vis[i] = 1
            if match[i] == -1 or self.dfs(match[i], match, vis):
                match[i] = x
                return 1
        return 0
    
    def get_max_BPM(self):
        match = [-1]*(self.dst_cnt+1)
        ret = 0
        for i in range(1, self.src_cnt+1):
            vis = [0]*(self.dst_cnt+1)
            ret += self.dfs(i, match, vis)
        return ret

N, M = ins()
BPM = BipartiteMatching(N, N)
for _ in ' '*M:
    u, v = ins()
    BPM.addEdge(u, v)
print(BPM.get_max_BPM())

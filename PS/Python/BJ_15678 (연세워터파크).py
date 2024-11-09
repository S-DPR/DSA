import sys
input = lambda: sys.stdin.readline().strip("\n")
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
15678번 연세워터파크

수열이 주어진다. 아래 조건에 맞춰 게임을 할 때, 얻을 수 있는 최댓값을 구해보자.
 - 시작은 수열의 아무 원소에서나 한다.
 - 현재 선택한 원소에서 양 옆으로 거리가 D 이하인 요소만 새로 선택할 수 있다.
 - 이미 선택했던 요소는 다시 선택할 수 없다.
 - 선택한 원소들의 합이 점수가 된다.

백준의 옛날옛적(2018)부터 전해져내려오는 좋은 문제 1
풀이가 정말 많은걸로 유명한 문제입니다, 히스토그램처럼..
그래서 분류도 머가 좀 많고.
저는 그냥 귀찮으니 세그트리 긁어서 끝냈습니다.

세그트리를 dp처럼 쓴건데, 그냥 dp쓰면 거리가 D 이하인거 싹 다 탐색해야해서 긴 시간이 걸리기 때문입니다.
그래서 업데이트 시간을 logD로 시간복잡도를 줄인건데.. 종종 나오는 아이디어입니다.
근데 lru_cache 그냥 떼도 성공해서 이걸 dp라고 할 수 있나 싶긴한데..
그냥 세그트리같습니다, dp없이.
귀찮게 저럴필요도 없이 역순 반복문에 갖다박아도 성공하는걸 보니 뭐.
"""
from functools import lru_cache
class SegTree:
    def __init__(self, sz):
        self.arr = [-inf]*(sz*2+1)
        self.sz = sz
    def update(self, idx, val):
        idx += self.sz
        self.arr[idx] = val
        while idx > 1:
            self.arr[idx>>1] = max(self.arr[idx^1], self.arr[idx])
            idx >>= 1
    def query(self, l, r):
        r = min(self.sz, r)
        l, r = l+self.sz, r+self.sz
        ret = -inf
        while l <= r:
            if l&1:
                ret = max(ret, self.arr[l])
                l += 1
            if ~r&1:
                ret = max(ret, self.arr[r])
                r -= 1
            l, r = l>>1, r>>1
        return ret

sys.setrecursionlimit(10**6)
@lru_cache(None)
def loop(idx):
    if idx == N+1: return
    loop(idx+1)
    S.update(idx, A[idx] + max(0, S.query(idx+1, idx+D)))

N, D = ins()
A = [-1] + ins()
S = SegTree(N+1)
loop(1)
print(S.query(1, N))

import sys
input = sys.stdin.readline
MAX = 2*10**9+1
"""
7469번 K번째 수

높은 난이도중에선 그나마 문제 이해가 쉽고, 원하는 바도 간결해서 뉴비들이 순진하게 짰다가 시간초과폭탄을 많이 맞는 문제입니다.
문제는 간단합니다! 배열을 주고 l, r, k를 줄테니 arr[l:r+1]에서 k번째 수를 골라라!
하지만 풀려면 자료구조를 알아야 하고, (PyPy로 나이브하게 풀 수도 있긴 합니다. 숏코딩 1등 방식.)
제 풀이는 PST를 씁니다. Persistent Segment Tree.
자료구조 설명 안합니다.

이 문제를 풀 당시에는 PST자료구조를 파이썬으로 구현해본게 거의 처음이라서,
그 때 당시의 코드를 그대로 들고오는게 아니라 각색을 하여 가져왔습니다.

사실 PST로 이분탐색을 하는 그냥 간단한 방식입니다.
트리의 각각의 리프노드를 다 인덱스로 보고요.
좌표압축과는 연이 없으시다면 idx에 10**9를 더해주셔야하고요. 결과에선 빼시구요.
그러면 끝납니다. PST를 이해했고 세그먼트트리로 이분탐색을 하는 방법을 아신다면
충분히 이해할거라 생각합니다.

참고로, 쿼리가 5000개밖에 안돼서 그런가, 좌표압축 안해도 풀립니다.
"""
class Node:
    def __init__(self, val = 0, l = 0, r = 0):
        self.val = val
        self.l = l
        self.r = r
        

def update(idx):
    root.append(len(seg))
    internal_update(root[-2], root[-1], 1, MAX, idx)

def internal_update(prev, cur, s, e, idx):
    if s == e: seg.append(Node(seg[prev].val+1))
    else:
        seg.append(Node(seg[prev].val + 1, seg[prev].l, seg[prev].r))
        m = s + e >> 1
        if idx <= m:
            seg[-1].l = len(seg)
            internal_update(seg[prev].l, seg[cur].l, s, m, idx)
        else:
            seg[-1].r = len(seg)
            internal_update(seg[prev].r, seg[cur].r, m+1, e, idx)

def query(l, r, k):
    return internal_query(root[l-1], root[r], 1, MAX, k)

def internal_query(prev, cur, s, e, k):
    if s == e: return s
    m = s + e >> 1
    if k <= (p:=seg[seg[cur].l].val - seg[seg[prev].l].val):
        return internal_query(seg[prev].l, seg[cur].l, s, m, k)
    else:
        return internal_query(seg[prev].r, seg[cur].r, m+1, e, k-p)

# 아래는 PST 베이스 및 입력을 받는 구간입니다.
# 주석을 해제하고 print문도 반전만 한 번 해주면, 좌표압축형식이 됩니다.
# 좌표압축시 파이썬으로 4.1초만에 돌아갑니다. (파이썬 시간제한 5초)
# 파이파이로는 1.3초만에 돌아갑니다.
# 좌표압축을 안하면 2.6초가 걸립니다. Python으로는 메모리초과가 납니다.

# 전체적인 시간복잡도 : O((M+N)lgK) (N은 배열의 크기, M은 쿼리의 개수, K는 좌표압축 안할시 걸리는 2*10**9)
n, m = map(int, input().split())
arr = list(map(int, input().split()))
"""
dic = {v:i for i, v in enumerate(sorted(arr), 1)}
unpressure = {v:i for i, v in dic.items()}
MAX = len(dic)
arr = [dic[i] for i in arr]
"""
seg = [Node()]
root = [0]
for i in arr: update(i)
for _ in ' '*m:
    print(query(*map(int, input().split())))
    #print(unpressure[query(*map(int, input().split()))])
